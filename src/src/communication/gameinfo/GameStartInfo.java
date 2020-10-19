package communication.gameinfo;

import java.io.Serializable;

/**
 * GameInfo containing the information that the game has started.
 */
public class GameStartInfo implements GameInfo, Serializable {

  private GameInfoID gameInfoID;

  public GameStartInfo(GameInfoID gameInfoID) {
    this.gameInfoID = gameInfoID;
  }

  @Override
  public GameInfoID getGameInfoID() {
    return GameInfoID.GAME_START;
  }
}
