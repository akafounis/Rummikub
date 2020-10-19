package game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.ArrayList;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


public class RummiBagTest {
  static final int BAG_SIZE = 106;


  @Test
  public void addStoneTest() {
    RummiBag bag1 = new RummiBag();
    ArrayList<Stone> stones = new ArrayList<>();
    Stone stone1 = new Stone(Stone.Color.JOKER, -11);
    Stone stone2 = new Stone(Stone.Color.RED, -10);
    boolean stone1Added = false;
    boolean stone2Added = true;

    stones.add(stone1);
    stones.add(stone2);
    bag1.addStones(stones);
    assertTrue(bag1.size() == BAG_SIZE + 2);

    for (Stone k : stones){
      if (k.getNumber() == stone1.getNumber()){
        if (k.getColor() == stone1.getColor()){
          stone1Added = true;
        }
      }
      if (k.getNumber() == stone2.getNumber()){
        if (k.getColor() == stone2.getColor()){
          stone2Added = true;
        }
      }
    }

    assertTrue(stone1Added && stone2Added);
  }


  @Test
  public void isBagFullTest(){
    RummiBag bag1 = new RummiBag();
    RummiBag bag2 = new RummiBag();

    assertEquals(bag1.getStones().size(), BAG_SIZE);
    assertTrue(bag1.size() == BAG_SIZE);
    assertTrue(bag2.size() == BAG_SIZE);

    for(int i = 0; i < BAG_SIZE; i++ ){
      bag1.removeStone();
      assertTrue(bag1.size() == BAG_SIZE - i - 1);
    }

    assertEquals(bag1.getStones().size(), 0);
  }

  @Test
  public void printTest() {
    RummiBag bag1 = new RummiBag();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bag size: ").append(bag1.size()).append('\n');
    for (Stone stone : bag1.getStones()) {
      stringBuilder.append('(').append(stone.getColor()).append(", ").append(stone.getNumber())
          .append(")\n");
    }

    assertTrue (stringBuilder.toString().equals(bag1.toString()));
  }

}

