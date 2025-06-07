
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
    ArrayList<Enemy> enemies;
    ArrayList<Treasure> treasures;
//    ArrayList<Treasure> treasures;
    Long gameState;
    Long day;




    SessionData(JSONObject jsonObject) {
        try {

            // plain fields in first layer of JSON
            Logger.getLogger().logInfo("Parsing session metadata");
            this.totalPlayers = (Long) (jsonObject.get("totalPlayers"));
            this.currentPlayer = (Long) (jsonObject.get("currentPlayer"));
            this.field = new GameField((JSONObject) jsonObject.get("field"));
            this.gameState = (Long) (jsonObject.get("gameState"));
            this.day = (Long) (jsonObject.get("day"));
            Logger.getLogger().logSuccess("Session metadata parsed");

            // Parse players data
            Logger.getLogger().logInfo("Parsing players");
            players = new ArrayList<>(Math.toIntExact(this.totalPlayers));
            JSONArray playersJSON = (JSONArray) jsonObject.get("players");
            for (int i = 0; i < this.totalPlayers; i++) {
                Logger.getLogger().logInfo("Adding player: " + playersJSON.get(i));
                players.add(new Player((JSONObject) playersJSON.get(i)));
            }
            Logger.getLogger().logSuccess(this.totalPlayers + " players added");

            // Parse enemies data
            Logger.getLogger().logInfo("Parsing enemies");
            enemies = new ArrayList<>();
            JSONArray enemiesJSON = (JSONArray) jsonObject.get("enemies");
            Logger.getLogger().logWeak("Got array of " + enemiesJSON.size() + " enemies");
            for (int i = 0; i < enemiesJSON.size(); i++) {
                Logger.getLogger().logWeak("Adding enemy " + i);
                Enemy enemy = new Enemy((JSONObject) enemiesJSON.get(i));
                enemies.add(enemy);
                Game.gameObjects.add(enemy);
            }
            Logger.getLogger().logSuccess(this.enemies.size() + " enemies added");

            // Parse treasures
            Logger.getLogger().logInfo("Parsing treasures");
            treasures = new ArrayList<>();
            JSONArray treasuresJSON = (JSONArray) jsonObject.get("treasures");
            Logger.getLogger().logWeak("Got array of " + treasuresJSON.size() + " treasures");
            for (int i = 0; i < treasuresJSON.size(); i++) {
                Logger.getLogger().logWeak("Adding treasure " + i);
                Treasure treasure = new Treasure((JSONObject) treasuresJSON.get(i));
                treasures.add(treasure);
                Game.gameObjects.add(treasure);
            }
            Logger.getLogger().logSuccess(this.treasures.size() + " treasures added");

            jsonData = jsonObject;
            Logger.getLogger().logSuccess("Successfully parsed SessionData: totalPlayers: " + totalPlayers + ", currentPlayer: " + currentPlayer + ", day: " + day);
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
            Logger.getLogger().logWeak("SessionData: setFieldOnJSON: " + jsonObject.get(key));
            return true;
        } catch (Exception e) {
            Logger.getLogger().logError("Failed to set field " + key + ": " + e.getMessage());
            return false;
        }
    }
}
