package communication.gameinfo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HandSizesInfoTest {



  @Test
  public void initTest()  {
    List<Integer> testList = new ArrayList();
    testList.add(1);
    testList.add(2);
    HandSizesInfo info1 = new HandSizesInfo(testList);

    assertTrue(info1.getGameInfoID() == GameInfoID.HAND_SIZES);
    assertTrue(info1.getHandSizes() == testList);

  }
}