package communication.gameinfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class ErrorInfoTest {

  @Test
  public void initTest() {
    ErrorInfo info1 = new ErrorInfo("Hello");
    assertEquals(info1.getErrorMessage(), "Hello");
    assertEquals(info1.getGameInfoID(), GameInfoID.ERROR);
  }
}