package game;

import org.junit.Test;

import static org.junit.Assert.*;


public class TraceTest {

  @Test
  public void initTest(){

    Trace trace2 = new Trace(TraceMove.MOVE_STONE_ON_TABLE, new Coordinate(2,2), new Coordinate(3,2));

    assertTrue(trace2.getCommand().equals(TraceMove.MOVE_STONE_ON_TABLE));
    assertTrue(trace2.getInitialPosition().equals(new Coordinate(2,2)));
    assertTrue(trace2.getTargetPosition().equals(new Coordinate(3,2)));
  }

}