package communication.gameinfo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerNamesInfoTest {


  @Test
  public void initTest() {
    List<String> names = new ArrayList<>();
    names.add("Peter");
    names.add("Pan");
    PlayerNamesInfo info1 = new PlayerNamesInfo(names);

    assertTrue(info1.getGameInfoID() == GameInfoID.PLAYER_NAMES);
    assertTrue(info1.getNames().get(0) == "Peter");
  }

}