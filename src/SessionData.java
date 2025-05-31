
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SessionData {

    JSONObject jsonData;

    Long totalPlayers;
    public GameField field;
    Long currentPlayer;
    ArrayList<Player> players;
    ArrayList<Army> enemies;
//    ArrayList<Treasure> treasures;
    Long gameState;
    Long day;




    SessionData(JSONObject jsonObject) {
        try {

            // plain fields in first layer of JSON
            this.totalPlayers = (Long) (jsonObject.get("totalPlayers"));
            this.currentPlayer = (Long) (jsonObject.get("currentPlayer"));
            this.field = new GameField((JSONObject) jsonObject.get("field"));
            this.gameState = (Long) (jsonObject.get("gameState"));
            this.day = (Long) (jsonObject.get("day"));

            // Parse players data
            players = new ArrayList<>(Math.toIntExact(this.totalPlayers));
            JSONArray playersJSON = (JSONArray) jsonObject.get("players");
            for (int i = 0; i < this.totalPlayers; i++) {

                players.add(new Player((JSONObject) playersJSON.get(i)));
                Logger.getLogger().log("Player " + i + " parsed: " + ((JSONObject) playersJSON.get(i)).toJSONString());
            }



            jsonData = jsonObject;
            Logger.getLogger().log("SessionData: totalPlayers: " + totalPlayers + " currentPlayer: " + currentPlayer);
        } catch (Exception e) {
            Logger.getLogger().logError("Parsed invalid session data!\n\tSessionData: " + e.getMessage());
            throw new RuntimeException("Invalid session data!\nMore info:\n"+e.getMessage());
        }
    }

    public JSONObject readField(String key) {
        return new JSONObject((JSONObject) jsonData.get(key));
    }
    public JSONObject getField(String key) {
        return (JSONObject) jsonData.get(key);
    }
    public void setField(String key, Object value) {

    }

    public static boolean setFieldOnJSON(JSONObject jsonObject, String key, Object value) {
        try {
            jsonObject.put(key, value);
            Logger.getLogger().log("SessionData: setFieldOnJSON: " + jsonObject.get(key));
            return true;
        } catch (Exception e) {
            Logger.getLogger().log("Failed to set field " + key + ": " + e.getMessage());
            return false;
        }
    }
}
