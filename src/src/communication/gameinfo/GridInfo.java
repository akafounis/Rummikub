package communication.gameinfo;

import java.io.Serializable;

/**
 * Info for Game Table or Player Hand.
 */
public final class GridInfo implements GameInfo, Serializable {
  private final StoneInfo[][] grid;
  private final GameInfoID gameInfoID;

  public GridInfo(GameInfoID gameInfoID, StoneInfo[][] grid) {
    this.gameInfoID = gameInfoID;
    this.grid = grid;
  }

  public StoneInfo[][] getGrid() {
    return grid;
  }

  @Override
  public GameInfoID getGameInfoID() {
    return gameInfoID;
  }
}
