package communication.gameinfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class BagInfoTest {

  @Test
  public void initTest() {
    BagInfo bagInfo1 = new BagInfo(2);
    assertTrue(bagInfo1.getSize() == 2);
    assertTrue(bagInfo1.getGameInfoID() == GameInfoID.BAG);
  }
}