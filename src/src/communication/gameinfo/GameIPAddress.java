package communication.gameinfo;

import java.io.Serializable;

/**
 * GameInfo storing an IP-address.
 */
public class GameIPAddress implements GameInfo, Serializable {

  private String ip;

  public GameIPAddress(String ip) {
    this.ip = ip;
  }

  public String getIpAddress() {
    return ip;
  }

  @Override
  public GameInfoID getGameInfoID() {
    return GameInfoID.IP_ADDRESS;
  }
}
