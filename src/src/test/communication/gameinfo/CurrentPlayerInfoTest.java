package communication.gameinfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentPlayerInfoTest {

  @Test
  public void initTest() {
    CurrentPlayerInfo info1 = new CurrentPlayerInfo(1);

    assertTrue(info1.getPlayerID() == 1);
    assertTrue(info1.getGameInfoID() == GameInfoID.CURRENT_PLAYER);
  }
}