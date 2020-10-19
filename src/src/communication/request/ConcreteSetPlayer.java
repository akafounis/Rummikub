package communication.request;

import java.io.Serializable;

/**
 * Request to join a game with a player.
 */
public final class ConcreteSetPlayer implements Request, Serializable {
  private final String name;
  private final int age;

  public ConcreteSetPlayer(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public RequestID getRequestID() {
    return RequestID.JOIN;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

}
