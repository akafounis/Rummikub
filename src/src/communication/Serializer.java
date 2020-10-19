package communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import communication.gameinfo.GameInfo;
import communication.gameinfo.GameUsernames;
import communication.gameinfo.GridInfo;
import communication.gameinfo.StoneInfo;
import communication.request.ConcreteMove;
import communication.request.Request;
import communication.request.RequestID;

/**
 * Class serializing Request- and GameInfo-Objects to JSON.
 */
public class Serializer {

  /**
   * Serializes a Request to JSON.
   *
   * @param request to be serialized
   * @return serialized representation of the Request
   */
  public String serialize(Request request) {

    Gson gson;
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Request.class, new InterfaceAdapter<Request>());
    gson = builder.create();

    return gson.toJson(request, Request.class);
  }

  /**
   * Serializes a GameInfo to JSON.
   *
   * @param gameInfo to be serialized
   * @return serialized representation of the GameInfo
   */
  public String serialize(GameInfo gameInfo) {

    Gson gson;
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(GameInfo.class, new InterfaceAdapter<GameInfo>());
    gson = builder.create();

    return gson.toJson(gameInfo, GameInfo.class);
  }
}
