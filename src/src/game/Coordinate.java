package game;

import java.util.Objects;

/**
 * coordinate for a stone on game table or player hand.
 */
public final class Coordinate {
  private final int col;
  private final int row;

  public Coordinate(int col, int row) {
    this.col = col;
    this.row = row;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Coordinate)) {
      return false;
    }
    Coordinate otherCoord = (Coordinate) other;
    return col == otherCoord.col && row == otherCoord.row;
  }

  @Override
  public int hashCode() {
    return Objects.hash(col, row);
  }

  int getCol() {
    return col;
  }

  int getRow() {
    return row;
  }
}
