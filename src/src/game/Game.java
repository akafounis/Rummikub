package game;

import java.util.List;
import java.util.Map;

/**
 * Interface for the Model (including logic) of a game of Rummikub.
 * To be accessed by the network.
 */
public interface Game {

  /**
   * Sets a new Player with the given name and age and tags them with
   * the given playerID.
   *
   * @param playerID the id the new Player associated to
   * @param name     the name of the new Player
   * @param age      the age of the new Player
   * @throws IllegalStateException if game is on or full
   */
  void join(int playerID, String name, int age) throws IllegalStateException;

  /**
   * Attempts to start this Game.
   *
   * @throws IllegalStateException if it is not yet able to start
   */
  void start() throws IllegalStateException;

  /**
   * Moves a group of stones on the Table of this Game from the given sourcePosition
   * to the given targetPosition.
   *
   * @param sourcePosition the position of a stone of moving stones before moving them
   * @param targetPosition the position of the selected stone after moving it with its group
   * @return true if subject stones are moved
   */
  boolean moveSetOnTable(Coordinate sourcePosition, Coordinate targetPosition);

  /**
   * Moves a stone at the given sourcePosition to the given targetPosition
   * on the Table of this Game.
   *
   * @param sourcePosition the position of a stone before moving it
   * @param targetPosition the position of the stone after moving it
   */
  void moveStoneOnTable(Coordinate sourcePosition, Coordinate targetPosition);

  /**
   * Puts a group of stones at the given sourcePosition from the current Hand of this Game
   * to the given targetPosition on the Table of this Game.
   *
   * @param sourcePosition the position of a stone of moving stones before moving them
   * @param targetPosition the position of the selected stone after moving it with its group
   * @return true if subject stones are moved
   */
  boolean putSet(Coordinate sourcePosition, Coordinate targetPosition);

  /**
   * Puts a stone from a Hand of this Game at the given sourcePosition to
   * the Table of this Game at the given targetPosition.
   *
   * @param sourcePosition the position of a stone before moving it
   * @param targetPosition the position of the stone after moving it
   * @throws IllegalArgumentException if a stone is already at the targetPosition
   */
  void putStone(Coordinate sourcePosition, Coordinate targetPosition)
      throws IllegalArgumentException;

  /**
   * Moves a group of stones on the Hand of a Player with the given playerId
   * from the given sourcePosition to the given targetPosition.
   *
   * @param playerID       the id of the Player on whose Hand stones will be moved
   * @param sourcePosition the position of a stone of its group before moving them
   * @param targetPosition the position of the stone after moving it with its group
   * @return true if only if the stones are moved
   */
  boolean moveSetOnHand(int playerID, Coordinate sourcePosition, Coordinate targetPosition);

  /**
   * Moves a stone on the Hand of a Player with the given playerId
   * from the given sourcePosition to the given targetPosition.
   *
   * @param playerID       the id of the Player on whose hand stones will be moved
   * @param sourcePosition the position of a stone before moving it
   * @param targetPosition the position of the stone after moving it
   */
  void moveStoneOnHand(int playerID, Coordinate sourcePosition, Coordinate targetPosition);

  /**
   * Makes the current Player of this Game to draw a stone.
   *
   * @throws IllegalArgumentException if the playerID is not the current playerID
   */
  void draw(int playerID) throws IllegalArgumentException;

  /**
   * Removes a Player with the given playerID out of this Game.
   *
   * @param playerID the id of the removed Player
   */
  void removePlayer(int playerID);

  /**
   * Resets all moves of stones that had been done on and to the Table of this Game.
   */
  void reset();

  /**
   * Undoes a move of a stone that had been done on and to the Table of this Game.
   */
  void undo();

  /**
   * Checks if there is a winner of this Game.
   *
   * @return true if there is a winner
   */
  boolean hasWinner();

  /**
   * Gives all stones and their associated Coordinates on the Table of this Game.
   *
   * @return all stones and their associated Coordinates on the Table of this Game
   */
  Map<Coordinate, Stone> getTableStones();

  /**
   * Gives all stones and their associated Coordinates
   * on the Hand of a Player with the given playerId in this Game.
   *
   * @param playerID the id of the subject Player
   * @return all stones and their associated Coordinates
   * on the Hand of a Player with the given playerId in this Game
   */
  Map<Coordinate, Stone> getPlayerStones(int playerID);

  /**
   * Gives all of Players' Hand-sizes in this Game.
   *
   * @return Gives all of Players' Hand-sizes in this Game
   */
  List<Integer> getPlayerHandSizes();

  /**
   * Returns the size of the Bag in this Game.
   *
   * @return the size of the Bag in this Game.
   */
  int getBagSize();

  int getCurrentPlayerID();

  int getTableWidth();

  int getTableHeight();

  int getPlayerHandWidth(int playerID);

  int getPlayerHandHeight(int playerID);

  int getNumberOfPlayers();

  /**
   * Returns all names of players with their points sorted by their points.
   *
   * @return all names of players with their points sorted by their points
   */
  Map<String, Integer> getFinalRank();

  /**
   * Returns names of players in this Game in order of their IDs.
   *
   * @return names of players in this Game
   */
  List<String> getPlayerNames();

  /**
   * Sorts stones on the Hand of the Player with the given playerId for the Set type, the Group.
   * Group is a group of (3 or 4) stones that have the same number but different colors.
   *
   * @param playerId the ID of the Player whose Hand will be sorted
   */
  void sortPlayerHandByGroup(int playerId);

  /**
   * Sorts stones on the Hand of the Player with the given playerId for the Set type, the Run.
   * Run is a group of at least 3 ordered stones by their number that have the same color.
   * For example: (13, 1, 2) is considered an ordered sequence.
   *
   * @param playerId the ID of the Player whose Hand will be sorted
   */
  void sortPlayerHandByRun(int playerId);

  /**
   * Draws a Stone when if there are Stones available.
   * Switches to the next player if not.
   */
  void timeOut(int playerID);

  /**
   * Returns true if this Game is on going.
   *
   * @return true if this Game is on going
   */
  boolean isGameOn();

  /**
   * Checks the consistency of all of moves the current Player did.
   * This Game is consistent, if the current Player has played at least a Stone from their Hand
   * and played total 30 points of stones from Hand if it was their first turn
   * and the Table is consistent.
   *
   * @throws IllegalArgumentException if the given playerID is not the currentPlayerID
   * @throws IllegalStateException if table is not consistent,
   *     or the moves are against the valid game
   */
  void confirmMove(int playerID);

}
