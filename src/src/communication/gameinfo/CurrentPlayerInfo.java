package communication.gameinfo;

import java.io.Serializable;

/**
 * game info that stores a player-ID relative to the player who receives this info.
 * 0 -> self
 * 1 -> left
 * 2 -> middle
 * 3 -> right
 */
public final class CurrentPlayerInfo implements GameInfo, Serializable {
  private final int playerID;

  public CurrentPlayerInfo(int playerID) {
    this.playerID = playerID;
  }

  @Override
  public GameInfoID getGameInfoID() {
    return GameInfoID.CURRENT_PLAYER;
  }

  public int getPlayerID() {
    return playerID;
  }
}
