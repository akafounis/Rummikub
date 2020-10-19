package communication.request;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleRequestTest {

  @Test
  public void initTest() {
    SimpleRequest request1 = new SimpleRequest(RequestID.DRAW);

    assertTrue(request1.getRequestID() == RequestID.DRAW);
  }
}