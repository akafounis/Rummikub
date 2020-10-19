package game;

import java.util.Map;

/**
 * Player of RummiGame. They have name, age and their RummiHands.
 */
class Player {
  private final String name;
  private final int age;
  private RummiHand hand;
  private boolean playedFirstMove; // it tells if this Player played their first move

  Player(String name, int age) {
    this.name = name;
    this.age = age;
    hand = new RummiHand();
    playedFirstMove = false;
  }

  void playedFirstMove() {
    this.playedFirstMove = true;
  }

  public int points() {
    return hand.points();
  }

  int getAge() {
    return age;
  }

  /**
   * Returns all stones with their associated Coordinates on this Player's Hand.
   *
   * @return all stones with their associated Coordinates on this Player's Hand
   */
  Map<Coordinate, Stone> getStones() {
    return hand.getStones();
  }

  /**
   * Pushes the given stone into the next free position of the player's hand.
   *
   * @param stone the new stone to be pushed on hand
   */
  void pushStone(Stone stone) {
    hand.setStone(nextFreeCoordinate(), stone);
  }

  /**
   * Calculates horizontally the next free coordinate on this Player's Hand
   * and returns the found.
   *
   * @return the next free coordinate on this Player's Hand
   * @throws IndexOutOfBoundsException if this Player's Hand is full
   */
  private Coordinate nextFreeCoordinate() {
    Map<Coordinate, Stone> stones = hand.getStones();
    Coordinate coordinate;
    for (int row = 0; row < hand.getHeight(); row++) {
      for (int col = 0; col < hand.getWidth(); col++) {
        coordinate = new Coordinate(col, row);
        if (!stones.containsKey(coordinate)) {
          return coordinate;
        }
      }
    }
    throw new IndexOutOfBoundsException("Hand is full.");
  }

  /**
   * Moves stone from the given sourcePosition to the given targetPosition o.
   * If a stone at the targetPosition already exist, it will be swapped.
   *
   * @param sourcePosition the position of the subject stone before moving it
   * @param targetPosition the position of the subject stone after moving it
   */
  void moveStone(Coordinate sourcePosition, Coordinate targetPosition) {
    // save the chosen stone
    Stone chosenStone = hand.removeStone(sourcePosition);
    // move the stone at targetPosition to the sourcePosition
    hand.setStone(sourcePosition, hand.removeStone(targetPosition));
    // move the chosen stone to the targetPosition
    hand.setStone(targetPosition, chosenStone);
  }

  /**
   * Pops the stone at the given sourcePosition.
   *
   * @param sourcePosition the position of the wanted stone
   * @return the wanted stone
   */
  Stone popStone(Coordinate sourcePosition) {
    return hand.getStones().remove(sourcePosition);
  }

  /**
   * Returns the Size of this Player's Hand.
   *
   * @return the Size of this Player's Hand
   */
  int getHandSize() {
    return hand.size();
  }

  String getName() {
    return name;
  }

  int getHandWidth() {
    return hand.getWidth();
  }

  int getHandHeight() {
    return hand.getHeight();
  }

  /**
   * Returns the Hand Grid of this Player.
   *
   * @return the Hand Grid of this Player
   */
  RummiHand getHand() {
    return hand;
  }

  /**
   * Reminds this Player that they have played the first move.
   */
  void notifyEndOfFirstMove() {
    playedFirstMove = true;
  }

  /**
   * Returns if this Player has played their first move.
   *
   * @return true if only if this player has played the first move
   */
  boolean hasPlayedFirstMove() {
    return playedFirstMove;
  }

  /**
   * Gives the sum of all of the points of stones on this player's hand.
   * Points of stones on this Player's Hand are negative at the end.
   *
   * @return the sum of the points of stones on this player's hand
   */
  int getPoints() {
    return -hand.getPoints();
  }

  void sortHandByGroup() {
    hand.sortByGroup();
  }

  void sortHandByRun() {
    hand.sortByRun();
  }

  void clearHand() {
    hand.clear();
  }

  // for test
  @Override
  public String toString() {
    return "Player(" + age + ")";
  }
}