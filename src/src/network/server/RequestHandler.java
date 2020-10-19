package network.server;
//Might be better to move this class to the game-package

import communication.gameinfo.BagInfo;
import communication.gameinfo.CurrentPlayerInfo;
import communication.gameinfo.ErrorInfo;
import communication.gameinfo.GameInfoID;
import communication.gameinfo.GameStartInfo;
import communication.gameinfo.GridInfo;
import communication.gameinfo.HandSizesInfo;
import communication.gameinfo.PlayerNamesInfo;
import communication.gameinfo.RankInfo;
import communication.gameinfo.SimpleGameInfo;
import communication.gameinfo.StoneInfo;
import communication.request.ConcreteMove;
import communication.request.ConcreteSetPlayer;
import communication.request.Request;
import game.Coordinate;
import game.Game;
import game.Stone;
import globalconstants.ErrorMessages;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class that can take Request-Objects and transform the into method-calls for a Game.
 * Acts as a Link between RummiServer and Game.
 * Notifies all clients about changes to the game.
 */
class RequestHandler {

  private static final String NOT_ALLOWED_MOVE = "not allowed to move stones like that!";

  private Game game;
  private Server server;

  /**
   * Constructor establishing the connection server-requesthandler-game.
   *
   * @param server to be connected to
   * @param game   to be connected to
   */
  RequestHandler(Server server, Game game) {
    this.server = server;
    this.game = game;
  }

  private static StoneInfo[][] parseStoneInfoGrid(int width, int height, Map<Coordinate, Stone> stones) {
    StoneInfo[][] grid = new StoneInfo[width][height];
    Stone stone;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if ((stone = stones.get(new Coordinate(i, j))) != null) {
          grid[i][j] = new StoneInfo(stone.getColor().toString(), stone.getNumber());
        }
      }
    }
    return grid;
  }

  /**
   * Applies a Request to the game and generates responses to all clients.
   *
   * @param request  being issued by a client
   * @param playerID identifier of the specific client issuing the request.
   */
  void applyRequest(Object request, int playerID) {
    // for test
    System.out.println("From RequestHandler: applying " + request);
    try {
      switch (((Request) request).getRequestID()) {
        case START:
          if (playerID != 0) {
            sendErrorToPlayer(playerID, ErrorMessages.CLIENT_CANNOT_START_GAME_ERROR);
            break;
          }
          game.start();
          notifyGameStartToAll();
          break;

        case JOIN:
          ConcreteSetPlayer setPlayer = (ConcreteSetPlayer) request;
          game.join(playerID, setPlayer.getName(), setPlayer.getAge());
        case UPDATE_PLAYERS:
          server.sendToAll(new PlayerNamesInfo(game.getPlayerNames()));
          break;

        case HAND_MOVE:
          ConcreteMove handMove = (ConcreteMove) request;
          game.moveStoneOnHand(playerID,
              new Coordinate(handMove.getInitCol(), handMove.getInitRow()),
              new Coordinate(handMove.getTargetCol(), handMove.getTargetRow()));
          sendHandToPlayer(playerID);
          break;

        case HAND_SET_MOVE:
          ConcreteMove handSetMove = (ConcreteMove) request;
          if (!game.moveSetOnHand(playerID,
              new Coordinate(handSetMove.getInitCol(), handSetMove.getInitRow()),
              new Coordinate(handSetMove.getTargetCol(), handSetMove.getTargetRow()))) {
            sendErrorToPlayer(playerID, NOT_ALLOWED_MOVE);
          }
          sendHandToPlayer(playerID);
          break;

        case TABLE_MOVE:
          if (isCurrentPlayer(playerID)) {
            ConcreteMove tableMove = (ConcreteMove) request;
            game.moveStoneOnTable(new Coordinate(tableMove.getInitCol(), tableMove.getInitRow()),
                new Coordinate(tableMove.getTargetCol(), tableMove.getTargetRow()));
          }
          sendTableToAll();
          break;

        case TABLE_SET_MOVE:
          if (isCurrentPlayer(playerID)) {
            ConcreteMove tableMove = (ConcreteMove) request;
            if (!game.moveSetOnTable(new Coordinate(tableMove.getInitCol(), tableMove.getInitRow()),
                new Coordinate(tableMove.getTargetCol(), tableMove.getTargetRow()))) {
              sendErrorToPlayer(playerID, NOT_ALLOWED_MOVE);
            }
          }
          sendTableToAll();
          break;

        case PUT_STONE:
          if (isCurrentPlayer(playerID)) {
            ConcreteMove putStone = (ConcreteMove) request;
            game.putStone(new Coordinate(putStone.getInitCol(), putStone.getInitRow()),
                new Coordinate(putStone.getTargetCol(), putStone.getTargetRow()));
            sendHandSizesToAll();
          }
          sendTableToAll();
          sendHandToPlayer(playerID);
          break;

        case PUT_SET:
          if (isCurrentPlayer(playerID)) {
            ConcreteMove putSet = (ConcreteMove) request;
            if (!game.putSet(new Coordinate(putSet.getInitCol(), putSet.getInitRow()),
                new Coordinate(putSet.getTargetCol(), putSet.getTargetRow()))) {
              sendErrorToPlayer(playerID, NOT_ALLOWED_MOVE);
            }
            sendHandSizesToAll();
          }
          sendTableToAll();
          sendHandToPlayer(playerID);
          break;

        case DRAW:
          game.draw(playerID);
          sendHandToPlayer(playerID);
          sendTableToAll();
          sendHandSizesToAll();
          sendBagSizeToAll();
          notifyTurnToPlayer();
          break;

        case CONFIRM_MOVE:
          game.confirmMove(playerID);
          if (!hasGameWinner()) {
            sendHandToPlayer(playerID);
            sendHandSizesToAll();
            sendBagSizeToAll();
            notifyTurnToPlayer();
          }
          break;

        case RESET:
          if (isCurrentPlayer(playerID)) {
            game.reset();
            sendTableToAll();
            sendHandToPlayer(playerID);
            sendHandSizesToAll();
          }
          break;

        case TIME_OUT:
          // sends original table
          sendTableToAll();
          sendHandToPlayer(playerID);
          // draw stone cause table not consistent and the time is out
          game.timeOut(playerID);
          sendHandToPlayer(playerID);
          sendTableToAll();
          sendHandSizesToAll();
          sendBagSizeToAll();
          notifyTurnToPlayer();
          break;

        case SORT_HAND_BY_GROUP:
          game.sortPlayerHandByGroup(playerID);
          sendHandToPlayer(playerID);
          break;

        case SORT_HAND_BY_RUN:
          game.sortPlayerHandByRun(playerID);
          sendHandToPlayer(playerID);
          break;

        case UNDO:
          if (isCurrentPlayer(playerID)) {
            game.undo();
            sendTableToAll();
            sendHandToPlayer(playerID);
          }
          break;

        default:
      }

    } catch (IllegalArgumentException | IllegalStateException e) {
      sendErrorToPlayer(playerID, e.getMessage());
    }
  }

  private boolean isCurrentPlayer(int playerID) {
    return game.getCurrentPlayerID() == playerID;
  }

  private void sendTableToPlayer(int playerID) {
    server.sendToPlayer(playerID, new GridInfo(GameInfoID.TABLE, parseStoneInfoGrid(game.getTableWidth(), game.getTableHeight(), game.getTableStones())));
  }

  /**
   * Notifies the currently playing player that it is his turn.
   */
  private void notifyTurnToPlayer() {

    int currentPlayerID = game.getCurrentPlayerID();

    for (int i = 0; i < 4; i++) {
      if (i == currentPlayerID) {
        // Tells player that it is his turn
        server.sendToPlayer(i, new SimpleGameInfo(GameInfoID.YOUR_TURN));
      } else {
        // Tells other players whose turn it is
        int relativeID = calculateRelativeID(i, currentPlayerID);
        server.sendToPlayer(i, new CurrentPlayerInfo(relativeID));
      }
    }
  }

  /**
   * Calculates the relative position between the currently playing client
   * and a client who is to receive a GameInfo.
   * <p>
   * Used when telling all the clients who - relative to them - is currently
   * playing.
   *
   * @param recipientID     ID of the player who is supposed to receive the GameInfo
   * @param currentPlayerID ID of the player who is currently playing
   * @return int representing number of steps (clockwise) the recipient has to
   * perform in order to find the currently playing opponent
   */
  private int calculateRelativeID(int recipientID, int currentPlayerID) {

    int relativeID;
    int numOfPlayers = game.getNumberOfPlayers();

    relativeID = currentPlayerID - recipientID;
    if (relativeID < 0) {
      relativeID = numOfPlayers + relativeID;
    }

    return relativeID;
  }

  /**
   * Notifies all clients that the game has started.
   */
  private void notifyGameStartToAll() {
    server.sendToAll(new GameStartInfo(GameInfoID.GAME_START));
    // send table first to all
    sendTableToAll();
    // send to each player their hand
    for (int playerID = 0; playerID < game.getNumberOfPlayers(); playerID++) {
      sendHandToPlayer(playerID);
    }
    sendPlayerNamesToAll();
    // send to each player their hand sizes in a corresponding order
    sendHandSizesToAll();
    // send bag size to all
    sendBagSizeToAll();
    // notify the start player
    notifyTurnToPlayer();
  }

  private void sendBagSizeToAll() {
    server.sendToAll(new BagInfo(game.getBagSize()));
  }

  private void sendTableToAll() {
    server.sendToAll(new GridInfo(GameInfoID.TABLE, parseStoneInfoGrid(game.getTableWidth(), game.getTableHeight(), game.getTableStones())));
  }

  private void sendHandToPlayer(int playerID) {
    server.sendToPlayer(playerID, new GridInfo(GameInfoID.HAND, parseStoneInfoGrid(game.getPlayerHandWidth(playerID),
            game.getPlayerHandHeight(playerID), game.getPlayerStones(playerID))));
  }

  private void sendHandSizesToAll() {
    List<Integer> handSizes = game.getPlayerHandSizes();
    server.sendToPlayer(0, new HandSizesInfo(handSizes));
    for (int playerID = 1; playerID < game.getNumberOfPlayers(); playerID++) {
      Collections.rotate(handSizes, -1);
      server.sendToPlayer(playerID, new HandSizesInfo(handSizes));
    }
  }

  private void sendPlayerNamesToAll() {
    List<String> names = game.getPlayerNames();
    server.sendToPlayer(0, new PlayerNamesInfo(names));
    for (int playerID = 1; playerID < game.getNumberOfPlayers(); playerID++) {
      Collections.rotate(names, -1);
      server.sendToPlayer(playerID, new PlayerNamesInfo(names));
    }
  }

  private void sendHandSizesToPlayer(int playerID) {
    List<Integer> handSizes = game.getPlayerHandSizes();
    Collections.rotate(handSizes, -playerID);
    server.sendToPlayer(playerID, new HandSizesInfo(handSizes));
  }

  private boolean hasGameWinner() {
    if (game.hasWinner()) {
      server.sendToAll(new RankInfo(game.getFinalRank()));
      return true;
    }
    return false;
  }

  private void sendErrorToPlayer(int playerID, String message) {
    server.sendToPlayer(playerID, new ErrorInfo(message));
  }

  /**
   * Notifies all the clients that one og the client has left the game.
   */
  void notifyClientClose() {
    if (game.isGameOn()) {
      sendBagSizeToAll();
      sendHandSizesToAll();
      notifyTurnToPlayer();
    }
    server.sendToAll(new PlayerNamesInfo(game.getPlayerNames()));
  }
}
