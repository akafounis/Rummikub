package game;

import game.Stone.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Class representing a bag of Stones.
 * Used to randomly draw Stones from.
 * Contains a fixed number (106) of Stones.
 * Each type of Stone (defined by a color and a numerical value)
 * is present twice.
 * There are to Jokers in the bag.
 */
public class RummiBag {
  private static final int MAX_BAG_SIZE = 106;
  private ArrayList<Stone> stones;
  private Random randomGenerator;


  /**
   * Initializes all Stones (2 x all different type of stones)
   * and a randomGenerator in order to remove stones randomly.
   */
  RummiBag() {
    stones = new ArrayList<>(MAX_BAG_SIZE);
    for (Color color : Color.values()) {
      if (color != Color.JOKER) {
        for (int i = Stone.MIN_VALUE; i <= Stone.MAX_VALUE; i++) {
          stones.add(new Stone(color, i));
          stones.add(new Stone(color, i));
        }
      }
    }
    stones.add(new Stone());
    stones.add(new Stone());
    randomGenerator = new Random();
  }

  /**
   * Removes a stone in this RummiBag randomly.
   *
   * @return the removed stone
   */
  Stone removeStone() {
    return stones.remove(randomGenerator.nextInt(size()));
  }

  int size() {
    return stones.size();
  }

  /**
   * Adds the given extra stones into this Bag.
   *
   * @param extraStones the stones to be put into this Bag
   */
  void addStones(Collection<Stone> extraStones) {
    this.stones.addAll(extraStones);
  }

  ArrayList<Stone> getStones() {
    return stones;
  }

  // for test
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bag size: ").append(size()).append('\n');
    for (Stone stone : stones) {
      stringBuilder.append('(').append(stone.getColor()).append(", ").append(stone.getNumber())
              .append(")\n");
    }
    return stringBuilder.toString();
  }
}
