package communication.request;

public class UpdatePlayersRequest implements Request {
  /**
   * @return RequestID to identify the concrete implementation of the interface.
   */
  @Override
  public RequestID getRequestID() {
    return RequestID.UPDATE_PLAYERS;
  }
}
