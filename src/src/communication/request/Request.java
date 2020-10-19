package communication.request;

/**
 * Interface defining classes that represent requests for changes to the game.
 * Requests get sent from a client to the server, where the game will be modified accordingly.
 * Implementations of this interface should be specific to a specific type of change,
 * e.g. drawing a stone
 * Each implementation must have a unique RequestID.
 */
public interface Request {

  /**
   * @return RequestID to identify the concrete implementation of the interface.
   */
  public RequestID getRequestID();
}
