package network.client;

import communication.Deserializer;
import communication.gameinfo.GameInfo;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * ClientListener listens to the Server
 * and when the Server sends an object to the Client
 * the Client Listener receives it, decrypts it and
 * forwards it to RummiClient
 */
class ClientListener extends Thread {
  //THE CLIENT THAT THE LISTENER LISTENS FOR..
  private InputStream serverIn;
  private RummiClient client;
  private boolean connected;
  private Deserializer deserializer;

  /**
   * Creates a Listener that listens to a certain port
   * and communicates with only one client
   *
   * @param serverIn the port that listens to
   * @param client   the client that reports the receiving objects to
   */
  ClientListener(InputStream serverIn, RummiClient client) {
    this.serverIn = serverIn;
    this.client = client;
    connected = true;
    deserializer = new Deserializer();
  }

  /**
   * Main method of ClientListener Class, waits
   * until it receives an object from the server
   * and then forward it to RummiClient
   */
  @Override
  public void run() {
    try (Scanner scanner = new Scanner(serverIn)) {
      String json;
      while (connected) {
        json = scanner.nextLine();
        if (json != null) {
          GameInfo info = deserializer.deserializeInfo(json);
          if (info != null) {
            client.applyGameInfoHandler(info);
          }
        }
      }
    } catch (NoSuchElementException e) {
      if (connected) {
        System.out.println("*****-----***** Sever closed ******------*******");
        client.notifyServerClose();
        System.out.println("From Run scanner ClientListener: notifyDisconnection Client");
        client.disconnect();
      }
    }
    System.out.println("From ClientListener: ClientListener terminates..");
  }


  void notifyDisconnection() {
    connected = false;
  }
}
