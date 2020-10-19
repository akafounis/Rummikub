package game;

import static org.junit.Assert.*;

import globalconstants.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


public class StoneTest {

  @Test
  public void initStoneTest() {
    Stone stone1 = new Stone(Stone.Color.RED, 1);
    Stone stone2 = new Stone(Stone.Color.JOKER, 2);
    Stone stone3 = new Stone(Stone.Color.BLUE, 3);
    Stone stone4 = new Stone(Stone.Color.YELLOW, 4);
    Stone stone5 = new Stone();

    assertTrue(stone1.getColor() == Stone.Color.RED);
    assertTrue(stone2.getColor() == Stone.Color.JOKER);
    assertTrue(stone3.getColor() == Stone.Color.BLUE);
    assertTrue(stone4.getColor() == Stone.Color.YELLOW);
    assertTrue(stone5.getColor() == Stone.Color.JOKER);

    assertEquals(stone1.getNumber(), 1);
    assertEquals(stone2.getNumber(), Constants.JOKER_POINTS);
    assertEquals(stone3.getNumber(), 3);
    assertEquals(stone4.getNumber(), 4);
    assertEquals(stone5.getNumber(), Constants.JOKER_POINTS);
  }

}