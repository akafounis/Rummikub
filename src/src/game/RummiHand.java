package game;

import game.Stone.Color;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class representing the hand of a player.
 * Contains all the Stones a player has received and not put down on the table.
 */
public class RummiHand implements Grid {
  private static final int HEIGHT = 3;
  private static final int WIDTH = 20;

  private Map<Coordinate, Stone> stones;

  RummiHand() {
    stones = new HashMap<>();
  }

  @Override
  public void setStone(Coordinate coordinate, Stone stone) {
    if (stone != null) {
      stones.put(coordinate, stone);
    }
  }

  public int points() {
    int points = 0;
    for (Map.Entry<Coordinate, Stone> entry : stones.entrySet()) {
      Stone stone = entry.getValue();
      points += stone.getNumber();
    }
    return points;
  }


  /**
   * Removes a stone (if there is any) at the given coordinate on this Hand and returns it.
   *
   * @param coordinate the coordinate of the removed stone
   * @return the removed stone from the given coordinate
   */
  @Override
  public Stone removeStone(Coordinate coordinate) {
    return stones.remove(coordinate);
  }

  /**
   * Returns all stones with their associated Coordinates on this Hand.
   *
   * @return all stones with their associated Coordinates on this Hand
   */
  @Override
  public Map<Coordinate, Stone> getStones() {
    return stones;
  }

  @Override
  public void clear() {
    stones.clear();
  }

  public int size() {
    return stones.size();
  }

  @Override
  public int getHeight() {
    return HEIGHT;
  }

  @Override
  public int getWidth() {
    return WIDTH;
  }

  /**
   * Groups stones with the same number and sorts these groups of stones by their color.
   */
  void sortByGroup() {
    sortStonesWith((stone1, stone2) -> {
      int number1 = stone1.getNumber();
      int number2 = stone2.getNumber();
      return (number1 == number2) ? stone1.getColor().compareTo(stone2.getColor())
              : number1 - number2;
    });
  }

  /**
   * Groups stones with the same color and sorts these groups of stones by their number.
   */
  void sortByRun() {
    sortStonesWith((stone1, stone2) -> {
      Color color1 = stone1.getColor();
      Color color2 = stone2.getColor();
      return (color1 == color2) ? stone1.getNumber() - stone2.getNumber()
              : color1.compareTo(color2);
    });
  }

  /**
   * sorts stones on this Hand with the given comparator
   * and replaces(fills) stones on this hand in the sorted order row after row
   * starting from the coordinate (0, 0).
   *
   * @param comparator the comparator to be used to compare stones
   */
  private void sortStonesWith(Comparator<Stone> comparator) {
    // check if there are at least 3 stones (if not, there is no meaning to sort)
    if (stones.size() < 3) {
      return;
    }
    // get iterator of the sorted stream of all stones on this hand with the given comparator
    Iterator<Stone> iterator = stones.values().stream().sorted(comparator).iterator();
    // reset stones
    stones = new HashMap<>();
    for (int row = 0; row < HEIGHT; row++) {
      for (int col = 0; col < WIDTH; col++) {
        // check if there is no stone left to be replaced
        if (!iterator.hasNext()) {
          return;
        }
        stones.put(new Coordinate(col, row), iterator.next());
      }
    }
  }

  //Testmethods
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    Coordinate coordinate;
    for (int row = 0; row < HEIGHT; row++) {
      for (int col = 0; col < WIDTH; col++) {
        if (stones.containsKey((coordinate = new Coordinate(col, row)))) {
          stringBuilder.append("Coordinate: ").append(coordinate)
                  .append(", Stone: ").append(stones.get(coordinate)).append('\n');
        }
      }
    }
    stringBuilder.append(stones.size());
    return stringBuilder.toString();
  }
}
