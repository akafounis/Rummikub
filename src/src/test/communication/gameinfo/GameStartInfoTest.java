package communication.gameinfo;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameStartInfoTest {
  @Test
  public void initTest() {
    GameStartInfo startInfo1 = new GameStartInfo(GameInfoID.HAND);
    assertTrue(startInfo1.getGameInfoID() == GameInfoID.GAME_START);
  }
}