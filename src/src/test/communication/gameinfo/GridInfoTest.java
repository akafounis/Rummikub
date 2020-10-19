package communication.gameinfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class GridInfoTest {

  @Test
  public void initTest() {
    StoneInfo[][] grid = new StoneInfo[1][1];
    StoneInfo stoneInfo1 = new StoneInfo("BLUE", 2);
    grid[0][0] = stoneInfo1;
    GridInfo gridInfo1 = new GridInfo(GameInfoID.BAG, grid);

    assertTrue(gridInfo1.getGrid()[0][0].getColor() == "BLUE");
    assertTrue(gridInfo1.getGrid()[0][0].getNumber() == 2);
    assertTrue(gridInfo1.getGameInfoID() == GameInfoID.BAG);
  }
}