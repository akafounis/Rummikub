package communication.gameinfo;

import java.io.Serializable;

/**
 * info for a stone in the grid info.
 */
public final class StoneInfo implements Serializable {
  private final String color;
  private final int number;

  public StoneInfo(String color, int number) {
    this.color = color;
    this.number = number;
  }

  public String getColor() {
    return color;
  }

  public int getNumber() {
    return number;
  }
}
