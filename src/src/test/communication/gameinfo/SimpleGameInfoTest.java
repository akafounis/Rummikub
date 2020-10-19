package communication.gameinfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleGameInfoTest {
  @Test
  public void initTest() {
    SimpleGameInfo info1 = new SimpleGameInfo(GameInfoID.BAG);

    assertTrue(info1.getGameInfoID() == GameInfoID.BAG);
  }
}