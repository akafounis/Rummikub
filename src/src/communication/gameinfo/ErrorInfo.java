package communication.gameinfo;

import java.io.Serializable;

/**
 * Error-Info for game with message.
 */
public final class ErrorInfo implements GameInfo, Serializable {
  private final String message;

  public ErrorInfo(String message) {
    this.message = message;
  }

  @Override
  public GameInfoID getGameInfoID() {
    return GameInfoID.ERROR;
  }

  public String getErrorMessage() {
    return message;
  }
}
