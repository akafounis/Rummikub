package communication.gameinfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameUsernamesTest {

  @Test
  public void initTest() {
    GameUsernames user1 = new GameUsernames("Cedrik", 1);

    assertTrue(user1.getId() == 1);
    assertTrue(user1.getUsername().equals("Cedrik"));
    assertTrue(user1.getGameInfoID() == GameInfoID.USERNAME);

  }
}