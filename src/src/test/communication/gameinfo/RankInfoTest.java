package communication.gameinfo;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class RankInfoTest {

  @Test
  public void initTest(){
    Map<String, Integer> finalRank = new HashMap<>();
    finalRank.put("name",1);

    RankInfo info = new RankInfo(finalRank);
    assertEquals(info.getFinalRank(), finalRank);
    assertEquals(info.getGameInfoID(), GameInfoID.RANK);
  }
}