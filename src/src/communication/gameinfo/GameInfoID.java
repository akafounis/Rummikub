package communication.gameinfo;

/**
 * Identifier for classes implementing the GameInfo-Interface.
 * Every implementation of GameInfo should have an GameInfoID associated with it.
 */
public enum GameInfoID {

  TABLE,
  HAND,
  BAG,
  HAND_SIZES,
  PLAYER_NAMES,
  DRAW, // for shell,
  CURRENT_PLAYER,
  ERROR,
  YOUR_TURN,
  GAME_START,
  IP_ADDRESS,
  USERNAME,
  SERVER_NOT_AVAILABLE,
  RANK,
  TOO_MANY_CLIENTS
}
