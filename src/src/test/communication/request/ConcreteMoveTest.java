package communication.request;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConcreteMoveTest {
  @Test
  public void initTest(){
    RequestID id = RequestID.TABLE_MOVE;
    int initCol = 0;
    int initRow = 0;
    int targetCol = 1;
    int targetRow = 1;

    ConcreteMove move1 = new ConcreteMove(id, initCol, initRow, targetCol, targetRow);

    assertTrue(move1.getInitCol() == initCol);
    assertTrue(move1.getInitRow() == initRow);
    assertTrue(move1.getTargetCol() == targetCol);
    assertTrue(move1.getTargetRow() == targetRow);
    assertTrue(move1.getRequestID() == id);

  }


}