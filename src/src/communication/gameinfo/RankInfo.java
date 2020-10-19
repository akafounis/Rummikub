package communication.gameinfo;

import java.io.Serializable;
import java.util.Map;

/**
 * GameInfo containing the Information about the final rank of the finished game.
 */
public final class RankInfo implements GameInfo, Serializable {
  private final Map<String, Integer> finalRank;

  public RankInfo(Map<String, Integer> finalRank) {
    this.finalRank = finalRank;
  }

  @Override
  public GameInfoID getGameInfoID() {
    return GameInfoID.RANK;
  }

  public Map<String, Integer> getFinalRank() {
    return finalRank;
  }
}
