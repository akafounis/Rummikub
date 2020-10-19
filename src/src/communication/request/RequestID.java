package communication.request;

/**
 * Enum used to differentiate between the different types of requests.
 */
public enum RequestID {

  START,
  JOIN,
  HAND_MOVE,
  TABLE_MOVE,
  PUT_STONE,
  DRAW,
  CONFIRM_MOVE,
  RESET,
  TIME_OUT,
  SORT_HAND_BY_GROUP,
  SORT_HAND_BY_RUN,
  TABLE_SET_MOVE,
  HAND_SET_MOVE,
  PUT_SET,
  UNDO,
  UPDATE_PLAYERS
}
