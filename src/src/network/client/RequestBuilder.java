package network.client;

import communication.request.ConcreteMove;
import communication.request.ConcreteSetPlayer;
import communication.request.RequestID;
import communication.request.SimpleRequest;

/**
 * RequestBuilder Class builds a new Request object
 * and sends it THROUGH the client to the Server.
 */
public class RequestBuilder {

  private RummiClient client;

  /**
   * Creates a RequestBuilder that communicates with only one client
   *
   * @param client sends the new created Requests to the server
   */
  public RequestBuilder(RummiClient client) {
    this.client = client;
  }

  /**
   * Sends a request to Server to start the game
   */
  public void sendStartRequest() {
    client.sendRequest(new SimpleRequest(RequestID.START));
  }

  /**
   * Sends a request to the Server that the user
   * wants to draw a new stone on the hand.
   */
  public void sendDrawRequest() {
    client.sendRequest(new SimpleRequest(RequestID.DRAW));
  }

  /**
   * Sends a request to the Server to reset all user's
   * moves since his turn came
   */
  public void sendResetRequest() {
    client.sendRequest(new SimpleRequest(RequestID.RESET));
  }

  /**
   * Sends a request to the Server that the user has just moved a Stone ON the table.
   *
   * @param srcCol    The number of the column of the old cell that the stone was (ON THE TABLE)
   * @param srcRow    The number of the row of the old cell that the stone was (ON THE TABLE)
   * @param targetCol The number of the column of the new cell that the stone now is (ON THE TABLE)
   * @param targetRow The number of the row of the new cell that the stone now is (ON THE TABLE)
   */
  public void sendMoveStoneOnTable(int srcCol, int srcRow, int targetCol, int targetRow) {
    client.sendRequest(new ConcreteMove(RequestID.TABLE_MOVE, srcCol, srcRow, targetCol, targetRow));
  }

  /**
   * Sends a request to the Server that the user has just putted a new stone from their hand on the table.
   *
   * @param srcCol    The number of the column of the old cell that the stone was (ON HAND)
   * @param srcRow    The number of the row of the old cell that the stone was (ON HAND)
   * @param targetCol The number of the column of the new cell that the stone now is (ON THE TABLE)
   * @param targetRow The number of the row of the new cell that the stone now is (ON THE TABLE)
   */
  public void sendPutStoneRequest(int srcCol, int srcRow, int targetCol, int targetRow) {
    client.sendRequest(new ConcreteMove(RequestID.PUT_STONE, srcCol, srcRow, targetCol, targetRow));
  }

  /**
   * Sends a request to the Server that the user has just changed the position of a stone ON their Hand
   *
   * @param srcCol    The number of the column of the old cell that the stone was (ON HAND)
   * @param srcRow    The number of the row of the old cell that the stone was on  (ON HAND)
   * @param targetCol The number of the column of the new cell that the stone now is (ON HAND)
   * @param targetRow The number of the row of the new cell that the stone now is (ON HAND)
   */
  public void moveStoneOnHand(int srcCol, int srcRow, int targetCol, int targetRow) {
    client.sendRequest(new ConcreteMove(RequestID.HAND_MOVE, srcCol, srcRow, targetCol, targetRow));
  }

  /**
   * Sends a request to the Server that the user wants to move more than 1 Stones FROM THEIR HAND TO THEIR HAND
   *
   * @param srcCol    The number of the column of the old cell that the stone was (ON HAND)
   * @param srcRow    The number of the row of the old cell that the stone was on  (ON HAND)
   * @param targetCol The number of the column of the new cell that the stone now is (ON HAND)
   * @param targetRow The number of the row of the new cell that the stone now is (ON HAND)
   */
  public void sendMoveSetOnHand(int srcCol, int srcRow, int targetCol, int targetRow) {
    client.sendRequest(new ConcreteMove(RequestID.HAND_SET_MOVE, srcCol, srcRow, targetCol, targetRow));
  }

  /**
   * Sends a request to the Server that the user wants to move more than 1 Stones from their hand on the table
   *
   * @param srcCol    The number of the column of the old cell that the stone was (ON HAND)
   * @param srcRow    The number of the row of the old cell that the stone was on  (ON HAND)
   * @param targetCol The number of the column of the new cell that the stone now is (ON TABLE)
   * @param targetRow The number of the row of the new cell that the stone now is (ON TABLE)
   */
  public void sendPutSetRequest(int srcCol, int srcRow, int targetCol, int targetRow) {
    client.sendRequest(new ConcreteMove(RequestID.PUT_SET, srcCol, srcRow, targetCol, targetRow));
  }

  /**
   * Sends to the Server a request that the user wants the game to check if the moves that
   * the user has done on this round are valid or not.
   */
  public void sendConfirmMoveRequest() {
    client.sendRequest(new SimpleRequest(RequestID.CONFIRM_MOVE));
  }

  /**
   * Sends to the Server a request that includes the username that the user chose
   * and his age
   *
   * @param username the username of the user
   * @param age      the age that the user claims to be
   */
  public void sendSetPlayerRequest(String username, int age) {
    client.sendRequest(new ConcreteSetPlayer(username, age));
  }

  /**
   * Sends a request to the Server that the timer on the GameView is finished with the countdown
   * and the user failed to do a right move
   */
  public void sendTimeOutRequest() {
    client.sendRequest(new SimpleRequest(RequestID.TIME_OUT));
  }

  /**
   * Sends a request to the Server that the user wants to sort the stones on
   * their hand by group
   */
  public void sendSortHandByGroupRequest() {
    client.sendRequest(new SimpleRequest(RequestID.SORT_HAND_BY_GROUP));
  }

  /**
   * Sends a request to the Server that the user wants to sort the stones on
   * their hand by run
   */
  public void sendSortHandByRunRequest() {
    client.sendRequest(new SimpleRequest(RequestID.SORT_HAND_BY_RUN));
  }

  /**
   * Sends a request to the Server that the user wants to revert
   * their last move on the game
   */
  public void sendUndoRequest() {
    client.sendRequest(new SimpleRequest(RequestID.UNDO));
  }

  public void sendUpdateRequest() {
    client.sendRequest(new SimpleRequest(RequestID.UPDATE_PLAYERS));
  }
}
