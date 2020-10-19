package communication.gameinfo;

import java.io.Serializable;

public class GameUsernames implements GameInfo, Serializable {

  private String username;
  private int id;

  public GameUsernames(String username, int id) {
    this.username = username;
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return this.username;
  }

  @Override
  public GameInfoID getGameInfoID() {
    return GameInfoID.USERNAME;
  }
}
