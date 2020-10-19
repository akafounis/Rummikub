package network.server;

import communication.gameinfo.GameInfo;

import java.net.UnknownHostException;

/**
 * Interface defining a server for the game Rummikub.
 * Handles the communication between central game-model and the different clients.
 */
public interface Server {

  /**
   * Sends a GameInfo to all clients.
   *
   * @param info GameInfo to be sent
   */
  public void sendToAll(GameInfo info);

  /**
   * Sends a GameInfo to the player that is currently playing.
   *
   * @param playerId id of the current player (0-n)
   * @param info     GameInfo getting sent to the player
   */
  public void sendToPlayer(int playerId, GameInfo info);

  /**
   * Returns the IP-address of the server.
   *
   * @return String representing the IP-address
   * @throws UnknownHostException whenever the IP-address could not be determined
   */
  public String getIP() throws UnknownHostException;
}
