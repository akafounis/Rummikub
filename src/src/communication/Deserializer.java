package communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import communication.gameinfo.GameInfo;
import communication.request.Request;

/**
 * Class deserializing Request- and GameInfo-Objects from Json.
 */
public class Deserializer {

  private Gson gson;

  /**
   * Deserializes GameInfo's from JSON.
   *
   * @param json-String representing a GameInfo
   * @return a GameInfo
   */
  public GameInfo deserializeInfo(String json) {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(GameInfo.class, new InterfaceAdapter<GameInfo>());
    gson = builder.create();
    return gson.fromJson(json, GameInfo.class);
  }

  /**
   * Deserializes Request's form JSON.
   *
   * @param json-String representing a GameInfo
   * @return a GameInfo
   */
  public Request deserializeRequest(String json) {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Request.class, new InterfaceAdapter<Request>());
    gson = builder.create();
    return gson.fromJson(json, Request.class);
  }
}
