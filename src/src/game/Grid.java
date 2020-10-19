package game;

import java.util.Map;

/**
 * Interface for different types of grids of Stones.
 */
public interface Grid {

  /**
   * Returns all stones with their associated Coordinates on this Grid.
   *
   * @return all stones with their associated Coordinates on this Grid
   */
  Map<Coordinate, Stone> getStones();

  /**
   * Puts a new Stone on the grid.
   *
   * @param coordinate the Stone will be put on on grid.
   * @param stone      to be put on the grid.
   */
  void setStone(Coordinate coordinate, Stone stone);

  /**
   * Removes a stone (if there is any) at the given coordinate on this Grid and returns it.
   *
   * @param coordinate the coordinate of the removed stone
   * @return the removed stone from the given coordinate
   */
  Stone removeStone(Coordinate coordinate);

  /**
   * Return the width of the Grid.
   *
   * @return width of the Grid.
   */
  int getWidth();

  /**
   * Return the height of the Grid.
   *
   * @return height of the Grid.
   */
  int getHeight();

  /**
   * Deletes all Stones on the grid.
   */
  void clear();

  /**
   * Returns the first coordinate of the neighbored stones at the given coordinate.
   * Hereby, by default the first coordinate refers the coordinate of the leftist stone
   * of the horizontally neighbored stones.
   *
   * @param coordinate the coordinate of the neighbored stones
   * @return the coordinate of the first stone of the neighbored stones at the given coordinate
   */
  default Coordinate getFirstCoordOfStonesAt(Coordinate coordinate) {
    int col = coordinate.getCol();
    // find the first stone for a potential set
    while (getStones().containsKey(new Coordinate(col - 1, coordinate.getRow()))) {
      col--;
    }
    return new Coordinate(col, coordinate.getRow());
  }

  /**
   * Returns the size of neighbored stones at the given coordinate.
   * Hereby, by default neighbored refers horizontally neighbored.
   *
   * @param coordinate the coordinate of neighbored stones
   * @return the size of neighbored stones at the given coordinate
   */
  default int getNeighborStonesSize(Coordinate coordinate) {
    int col = coordinate.getCol();
    int row = coordinate.getRow();
    Map<Coordinate, Stone> stones = getStones();
    int size = 0;
    while (stones.containsKey(new Coordinate(col++, row))) {
      size++;
    }
    return size;
  }

  default int getPoints() {
    return getStones().values().stream().mapToInt(Stone::getNumber).sum();
  }
}

