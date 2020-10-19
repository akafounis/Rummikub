package game;

/**
 * A stone move function, which implies the move in Game.
 * StoneMoves can be passed to a general move method to allow non repeatable use of move methods.
 */
interface StoneMove {

  /**
   * Moves a Stone from the given sourcePosition to the given targetPosition.
   *
   * @param sourcePosition the position of the subject stone before moving it
   * @param targetPosition the position of the subject stone after moving it
   */
  void moveStone(Coordinate sourcePosition, Coordinate targetPosition);
}
