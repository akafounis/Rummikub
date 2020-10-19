package communication.request;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConcreteSetPlayerTest {

  @Test
  public void initTest() {
    ConcreteSetPlayer player1 = new ConcreteSetPlayer("Peter", 9);

    assertTrue(player1.getName() == "Peter");
    assertTrue(player1.getAge() == 9);
    assertTrue(player1.getRequestID() == RequestID.JOIN);
  }
}