package communication.request;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeOutTest {

  @Test
  public void initTest() {
    TimeOut timeOut1 = new TimeOut();

    assertTrue(timeOut1.getRequestID() == RequestID.TIME_OUT);
  }
}