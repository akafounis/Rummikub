package communication.request;

import java.io.Serializable;

/**
 * represents a simple request which stores only its RequestID to be identified.
 */
public final class SimpleRequest implements Request, Serializable {
  private final RequestID id;

  public SimpleRequest(RequestID id) {
    this.id = id;
  }

  @Override
  public RequestID getRequestID() {
    return id;
  }
}
