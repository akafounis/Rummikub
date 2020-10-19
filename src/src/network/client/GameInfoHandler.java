package network.client;

import communication.gameinfo.*;
import view.Controller;

/**
 * GameInfoHandler handles the received from the ClientListener gameinfos
 * and chooses where each should go.
 */
public class GameInfoHandler {

  private Controller controller;

  /**
   * Creates a new GameInfoHandler that contains a controller.
   *
   * @param controller an object of a class that implements the interface Controller
   */
  public GameInfoHandler(Controller controller) {
    this.controller = controller;
  }

  /**
   * Main method of the class: forwards the received information to the controller
   * accordingly it's GameInfoID.
   *
   * @param gameInfo the received Object that the Server sent.
   */
  void applyGameInfo(Object gameInfo) {
    switch (((GameInfo) gameInfo).getGameInfoID()) {
      case HAND:
        controller.setPlayerHand(((GridInfo) gameInfo).getGrid());
        break;
      case TABLE:
        controller.setTable(((GridInfo) gameInfo).getGrid());
        break;
      case ERROR:
        controller.showError(((ErrorInfo) gameInfo).getErrorMessage());
        break;
      case BAG:
        controller.setBagSize(((BagInfo) gameInfo).getSize());
        break;
      case YOUR_TURN:
        System.out.println("handling yourturn");
        controller.notifyTurn();
        break;
      case HAND_SIZES:
        controller.setHandSizes(((HandSizesInfo) gameInfo).getHandSizes());
        break;
      case PLAYER_NAMES:
        controller.setPlayerNames(((PlayerNamesInfo) gameInfo).getNames());
        break;
      case CURRENT_PLAYER:
        System.out.println("handling currentplayer");
        System.out.println("received " + ((CurrentPlayerInfo) gameInfo).getPlayerID() + " as current player");
        controller.notifyCurrentPlayer(((CurrentPlayerInfo) gameInfo).getPlayerID());
        break;
      case GAME_START:
        System.out.println("handling gamestart");
        controller.notifyGameStart();
        break;
      case RANK:
        controller.showRank(((RankInfo) gameInfo).getFinalRank());
        break;
      case TOO_MANY_CLIENTS:
        controller.connectionRejected();
        break;
      default:
    }
    System.out.println("Info handled");
  }

  /**
   * Notify the user that the Server is not available anymore.
   */
  void notifyServerClose() {
    controller.notifyServerClose();
  }
}
