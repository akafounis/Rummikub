package communication.gameinfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameIPAddressTest {

  @Test
  public void initTest() {
    GameIPAddress gameIPAdresse1 = new GameIPAddress("Hans");

    assertTrue(gameIPAdresse1.getIpAddress().equals("Hans"));
    assertTrue(gameIPAdresse1.getGameInfoID() == GameInfoID.IP_ADDRESS);
  }
}