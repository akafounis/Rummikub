package communication.request;

import org.omg.CORBA.TIMEOUT;

import java.io.Serializable;

/**
 * Request telling the game that the game-clock has run out for one of the player.
 */
public class TimeOut implements Request, Serializable {

  @Override
  public RequestID getRequestID() {
    return RequestID.TIME_OUT;
  }
}
