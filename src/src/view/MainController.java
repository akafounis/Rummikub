package view;

import communication.gameinfo.StoneInfo;
import communication.request.ConcreteMove;
import communication.request.RequestID;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import network.client.GameInfoHandler;
import network.client.RequestBuilder;
import network.client.RummiClient;
import network.server.RummiServer;
import view.music.Audio;

/**
 * Main controller for Rummikub.
 *
 * Connects the game to the network.
 *
 * Connects to and organizes all other sub-controllers.
 * Delegates direct control of the different views to those controllers.
 */
public class MainController implements Controller {

  private GameController gameController;
  private StartController startController;
  private WaitController waitController;
  private WinnerController winnerController;
  private ErrorController errorController;
  private Stage primaryStage;
  private RummiClient client;
  private RequestBuilder requestBuilder;
  private String serverIP;

  /**
   * Constructor setting up the primary Stage.
   *
   * @param primaryStage of the game.
   */
  public MainController(Stage primaryStage) {
    this.primaryStage = primaryStage;
    primaryStage.setOnCloseRequest(event -> {
      if (client != null) {
        System.out.println("From in MainCtrl.: disconnect client!");
        killThreads();
      }
      Platform.exit();
    });
  }

  /**
   * Switches between different scenes based on what screen the user is supposed to see.
   *
   * @param fxml same of the fxml-file to be displayed
   * @throws IOException if the scene can't be loaded
   */
  private void switchScene(String fxml) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
    Parent root = loader.load();
    switch (fxml) {
      case ViewConstants.START_FXML:
        Audio.selectMusic(Audio.Music.START);
        Audio.playMusicNow();
        startController = loader.getController();
        startController.setMainController(this);
        break;
      case ViewConstants.WAIT_FXML:
        Audio.selectMusic(Audio.Music.WAIT);
        Audio.playMusicNow();
        waitController = loader.getController();
        waitController.setMainController(this);
        break;
      case ViewConstants.GAME_FXML:
        Audio.selectMusic(Audio.Music.GAME);
        gameController = loader.getController();
        gameController.setMainController(this);
        break;
      case ViewConstants.WINNER_FXML:
        winnerController = loader.getController();
        winnerController.setMainController(this);
        break;
      default:
    }
    Platform.runLater(() -> {
      Scene scene = new Scene(root, 1500, 900);
      primaryStage.setMinWidth(1500);
      primaryStage.setMinHeight(900);
      primaryStage.setScene(scene);
      primaryStage.show();
    });
  }

  /**
   * Switches to the wait-view.
   * Is supposed to be called when waiting for other players to join.
   *
   * @throws IOException if scene cant' be loaded
   */
  void switchToWaitScene() throws IOException {
    switchScene(ViewConstants.WAIT_FXML);
    waitController.setServerIP(serverIP);
  }

  /**
   * Switches to start-view.
   * Is supposed to be called befor joining/hosting a game.
   *
   * @throws IOException when scene can`t be loaded.
   */
  public void switchToStartScene() throws IOException {
    switchScene(ViewConstants.START_FXML);
  }

  /**
   * Switches to the game-view.
   * Should be called when the game starts.
   *
   * @throws IOException if the scene can't be loaded
   */
  private void switchToGameScene() throws IOException{
     switchScene(ViewConstants.GAME_FXML);

  }

  /**
   * Switches to the winner view.
   * Should be called after a winning move.
   *
   * @throws IOException if the scene can't be loaded
   */
  private void switchToWinnerScene() throws IOException {
    switchScene(ViewConstants.WINNER_FXML);
  }

  /**
   * Displays an error-message indicating that there is no connection to the host.
   */
  public void notifyServerClose() {
    client = null;
    showErrorGotToStart(ViewConstants.SERVER_NOT_AVAILABLE_ERROR);
  }

  /**
   * Displays an error-message indicating that connecting to the host is impossible.
   */
  public void connectionRejected() {
    showErrorGotToStart(ViewConstants.CONNECTION_REJECTED_ERROR);
  }

  private void showErrorGotToStart(String errorMessage) {
    if (gameController != null) {
      gameController.stopTimer();
    }
    showError(errorMessage);
    try {
      switchScene(ViewConstants.START_FXML);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Shows a generic error-message.
   *
   * @param errorMessage text of the error-message.
   */
  @Override
  public void showError(String errorMessage) {
    Platform.runLater(() -> {
      Stage stage = new Stage();
      Parent root;
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewConstants.ERROR_FXML));
        root = loader.load();
        errorController = loader.getController();
        errorController.setErrorMessage(errorMessage);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.showAndWait();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Switches to winner view and displays the final ranking of the game.
   *
   * @param finalRank Map of player names with their respective negative points.
   */
  @Override public void showRank(Map<String, Integer> finalRank) {
    gameController.stopTimer();
    Platform.runLater(() -> {
      try {
        gameController.stopTimer();
        switchToWinnerScene();
        winnerController.setRank(finalRank);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Displays the game instruction
   */
  void showHelpScene() {
    Platform.runLater(() -> {
      Stage stage = new Stage();
      Parent root;
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewConstants.HELP_FXML));
        root = loader.load();
        stage.setScene(new Scene(root));
        stage.initOwner(primaryStage);
        stage.showAndWait();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  /**
   * Sets the names of all the players in the game.
   *
   * @param names list of names, ordered clockwise
   *             The name of the recipient is on position 0
   */
  @Override
  public void setPlayerNames(List<String> names) {
    if (startController != null) {
      waitController.setPlayerNames(names);
    }
    if (gameController != null) {
      gameController.setPlayerNames(names);
    }
  }

  /**
   * Sets the number of Stones each player has in its hand.
   *
   * @param sizes list of sizes, ordered clockwise
   *              The hand-size of the recipient is on position 0
   */
  @Override
  public void setHandSizes(List<Integer> sizes) {
    gameController.setHandSizes(sizes);
  }

  /**
   * Updates the table of the game-model.
   *
   * @param table the new table
   */
  @Override
  public void setTable(StoneInfo[][] table) {
    System.out.println("set table");
    gameController.setTable(table);
  }


  /**
   * Updates the hand (including the stones) of the player.
   *
   * @param hand the new hand
   */
  @Override
  public void setPlayerHand(StoneInfo[][] hand) {
    gameController.setPlayerHand(hand);
  }

  /**
   * Notifies the controller that it his his turn.
   */
  @Override
  public void notifyTurn() {
    //needed for the styling of the opponents
    gameController.notifyCurrentPlayer(0);

    gameController.yourTurn();
  }

  /**
   * Notifies the controller that the game has started.
   */
  @Override
  public void notifyGameStart() {
    try {
      switchToGameScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Notifies the controller about the player that is currently playing.
   *
   * @param playerID te player-ID who has the turn to play in the perspective of this
   */
  @Override
  public void notifyCurrentPlayer(int playerID) {
    gameController.notifyCurrentPlayer(playerID);
  }

  /**
   * Updates the number of Stones present in the drawing-bag.
   *
   * @param bagSize number of stones in the bag
   */
  @Override
  public void setBagSize(int bagSize) {
    gameController.setBagSize(bagSize);
  }

  /**
   * Sends a request to draw a stone from the bag to the server.
   */
  void sendDrawRequest() {
    requestBuilder.sendDrawRequest();
  }

  /**
   * Informs the server that the game-clock has run out of time.
   */
  void sendTimeOutRequest() {
    requestBuilder.sendTimeOutRequest();
  }

  /**
   * Sends a request to move a stone from the hand to the table to the server.
   *
   * @param sourceColumn x-coordinate of the Stone on the hand
   * @param sourceRow y-coordinate of the Stone on the hand
   * @param targetColumn x-Coordinate on the table
   * @param targetRow y-Coodinate on the table
   */
  void sendMoveSetOnTableRequest(int sourceColumn, int sourceRow, int targetColumn, int targetRow) {
    client.sendRequest(new ConcreteMove(RequestID.TABLE_SET_MOVE, sourceColumn, sourceRow, targetColumn, targetRow));
  }

  /**
   * Creates a player for the game.
   * Including the infrastructure to communicate with the host.
   *
   * @param serverIP ip-address of the host
   * @param name of the player
   * @param age of the player
   */
  void initPlayer(String serverIP, String name, int age) {
    try {
      client = new RummiClient(serverIP);
      client.setGameInfoHandler(new GameInfoHandler(this));
      client.start();
    } catch (IOException e) {
      startController.showNoServerError();
      return;
    }
    requestBuilder = new RequestBuilder(client);

    // Set IP for displaying it in wait view
    if (this.serverIP == null) {
      this.serverIP = serverIP;
    }
    try{
      switchToWaitScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
    requestBuilder.sendSetPlayerRequest(name, age);
  }

  /**
   * Starts the server.
   */
  boolean startServer() {
    try {
      new RummiServer().start();
    } catch (IOException e) {
      showError(ViewConstants.MULTIPLE_HOSTS_ON_SINGLE_MACHINE_ERROR);
      return false;
    }
    try{
      serverIP = Inet4Address.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      showError(ViewConstants.IP_NOT_DETERMINED_ERROR);
      return false;
    }
    return true;
  }

  /**
   * Sends request to start the game to the server.
   */
  void sendStartRequest() {
    requestBuilder.sendStartRequest();
  }

  /**
   * Sends request to move a stone on the hand to the server.
   *
   * @param sourceColumn x-Coordinate of the Stone that will be moved
   * @param sourceRow y-Coordinate of the Stone that will be moved
   * @param targetColumn x-Coordinate the Stone will be moved to
   * @param targetRow y-Coordinate the Stone will be moved to
   */
  void sendMoveStoneOnHand(int sourceColumn, int sourceRow, int targetColumn, int targetRow) {
    requestBuilder.moveStoneOnHand(sourceColumn, sourceRow, targetColumn, targetRow);
  }

  /**
   * Sends request to move a stone from the hand to the table to the server.
   *
   * @param sourceColumn x-Coordinate of the Stone that will be moved
   * @param sourceRow y-Coordinate of the Stone that will be moved
   * @param targetColumn x-Coordinate the Stone will be moved to
   * @param targetRow y-Coordinate the Stone will be moved to
   */
  void sendPutStoneRequest(int sourceColumn, int sourceRow, int targetColumn, int targetRow) {
    requestBuilder.sendPutStoneRequest(sourceColumn, sourceRow, targetColumn, targetRow);
  }

  /**
   * Sends request to move a set of Stones on the table to the server.
   *
   * @param sourceColumn x-Coordinate of the Stone that will be moved
   * @param sourceRow y-Coordinate of the Stone that will be moved
   * @param targetColumn x-Coordinate the Stone will be moved to
   * @param targetRow y-Coordinate the Stone will be moved to
   */
  void sendMoveStoneOnTable(int sourceColumn, int sourceRow, int targetColumn, int targetRow) {
    requestBuilder.sendMoveStoneOnTable(sourceColumn, sourceRow, targetColumn, targetRow);
  }

  /**
   * Sends request to move a set of Stones on the hand to the server.
   *
   * @param sourceColumn x-Coordinate of the Stone that will be moved
   * @param sourceRow y-Coordinate of the Stone that will be moved
   * @param targetColumn x-Coordinate the Stone will be moved to
   * @param targetRow y-Coordinate the Stone will be moved to
   */
  void sendMoveSetOnHand(int sourceColumn, int sourceRow, int targetColumn, int targetRow) {
    requestBuilder.sendMoveSetOnHand(sourceColumn, sourceRow, targetColumn, targetRow);
  }

  /**
   * Sends request to move a stone on the hand to the server.
   *
   * @param sourceColumn x-Coordinate of the Stone that will be moved
   * @param sourceRow y-Coordinate of the Stone that will be moved
   * @param targetColumn x-Coordinate the Stone will be moved to
   * @param targetRow y-Coordinate the Stone will be moved to
   */
  void sendPutSetRequest(int sourceColumn, int sourceRow, int targetColumn, int targetRow) {
    requestBuilder.sendPutSetRequest(sourceColumn, sourceRow, targetColumn, targetRow);
  }

  /**
   * Sends a Request that a User wants to check the validity of their move.
   */
  void sendConfirmMoveRequest() {
    requestBuilder.sendConfirmMoveRequest();
  }

  /**
   * Sends a request to the server to sort the Stones on the hand by color.
   */
  void sendSortHandByGroupRequest() {
    requestBuilder.sendSortHandByGroupRequest();
  }

  /**
   * Sends a request to the server to sort the Stones on the hand by numerical value.
   */
  void sendSortHandByRunRequest() {
    requestBuilder.sendSortHandByRunRequest();
  }

  /**
   * Quits the game and goes back to the start-view.
   */
  void handleQuitPressed() {
    killThreads();
    try {
      switchToStartScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Kills all threads and the game's timer.
   */
  private void killThreads() {
    // delete wait- and gameController
    waitController = null;
    if (gameController != null) {
      gameController.stopTimer();
      gameController = null;
    }
    client.disconnect();
    client = null;
  }

  /**
   * Sends request to the server to reset table and hand
   * to the state they had before making any moves.
   */
  void sendResetRequest() {
    requestBuilder.sendResetRequest();
  }

  /**
   * Sends a request to the server to undo the last move.
   */
  void sendUndoRequest() {
    requestBuilder.sendUndoRequest();
  }

  void restartGame() throws IOException {
    requestBuilder.sendUpdateRequest();
    switchToWaitScene();
  }
}
