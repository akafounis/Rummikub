package communication.gameinfo;

import java.io.Serializable;

/**
 * game info that stores the current size of the RummiBag.
 */
public final class BagInfo implements GameInfo, Serializable {
  private final int size;

  public BagInfo(int size) {
    this.size = size;
  }

  @Override
  public GameInfoID getGameInfoID() {
    return GameInfoID.BAG;
  }

  public int getSize() {
    return size;
  }
}
