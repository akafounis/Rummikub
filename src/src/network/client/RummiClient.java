package network.client;

import communication.Serializer;
import communication.request.Request;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * RummiClient Class creates a client-object which
 * creates a ClientListener that listens to the server.\
 * At the same time it waits until it gets a Request object so it
 * can send it direct to the server
 */
public class RummiClient {
  private static final int port = 48410;

  //Connection variables
  private Socket serverSocket;
  private PrintWriter outToServer;
  private ClientListener listener;
  private Serializer serializer;
  private GameInfoHandler gameInfoHandler;

  /**
   * Creates a new client that is able to send objects to
   * a server
   *
   * @param serverIPAddress the ip Address of the server that wants to connect to
   * @throws IOException in case the server ip address is false and no server exists there
   */
  public RummiClient(String serverIPAddress) throws IOException {
    serializer = new Serializer();
    serverSocket = new Socket(serverIPAddress, port);
    outToServer = new PrintWriter(serverSocket.getOutputStream());
  }

  /**
   * Used only one time to save an already generated in MainController
   * GameInfoHandler
   *
   * @param gameInfoHandler the class where all the received objects are forwarded to
   */
  public void setGameInfoHandler(GameInfoHandler gameInfoHandler) {
    this.gameInfoHandler = gameInfoHandler;
  }

  /**
   * Main method of RummiClient class which starts a new thread "ClientListener"
   * that listens to the wanted server
   *
   * @throws IOException case the socket of the server is not open
   */
  public void start() throws IOException {
    // Add a listener to this Client
    listener = new ClientListener(serverSocket.getInputStream(), this);
    listener.start();
  }

  /**
   * Terminates the ClientListener thread and automatic
   * the RummiClient class
   */
  public void disconnect() {
    // notify listener that this client closes it instead of the server
    listener.notifyDisconnection();
    try {
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Used from MainController to send a Request Object
   * to the server
   *
   * @param request the object that we want to send to the server
   */
  public void sendRequest(Object request) {
    outToServer.println(serializer.serialize((Request) request));
    outToServer.flush();
  }

  /**
   * Forwards a received Object to GameInfoHandler
   * so it can be processed and applied in view
   *
   * @param gameInfo the received from the server object
   */
  void applyGameInfoHandler(Object gameInfo) {
    gameInfoHandler.applyGameInfo(gameInfo);
  }

  /**
   * In case Server is no longer online
   * it notifies the user that the client is
   * about to get terminated
   */
  void notifyServerClose() {
    gameInfoHandler.notifyServerClose();
  }
}
