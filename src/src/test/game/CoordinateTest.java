package game;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.Objects;

import static org.junit.Assert.*;


public class CoordinateTest {

  @Test
  public void initTest() {
    Coordinate coordinate1 = new Coordinate(1, 4);
    Coordinate coordinate2 = new Coordinate(1, 5);

    assertEquals(coordinate1.getCol(), 1);
    assertEquals(coordinate1.getRow(), 4);
    assertEquals(coordinate2.getCol(), 1);
    assertEquals(coordinate2.getRow(), 5);
  }


  @Test
  public void equalsTest() {
    Coordinate coordinate1 = new Coordinate(1, 4);
    Coordinate coordinate2 = new Coordinate(1, 5);
    Coordinate coordinate3 = new Coordinate(1,4);

    assertEquals(coordinate1, coordinate3);
    assertTrue(coordinate1.equals(coordinate3));
    assertTrue(coordinate2.equals(coordinate2));
    assertFalse(coordinate2.equals(new Object()));
    assertFalse(coordinate1.equals(coordinate2));

  }



  @Test
  public void hashTest() {
    Coordinate coordinate1 = new Coordinate(1, 4);

    assertEquals(Objects.hash(coordinate1.getCol(), coordinate1.getRow()), coordinate1.hashCode());
  }


}