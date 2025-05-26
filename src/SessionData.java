
import org.json.simple.JSONObject;

public class SessionData {

    Long totalPlayers;
    Long currentPlayer;
    public GameField field;


    SessionData(JSONObject jsonObject) {
        try {
            this.totalPlayers = (Long) (jsonObject.getOrDefault("totalPlayers", 0));
            this.currentPlayer = (Long) (jsonObject.getOrDefault("currentPlayer", 0));
            this.field = new GameField((JSONObject) jsonObject.get("field"));
            Logger.getLogger().log("SessionData: totalPlayers: " + totalPlayers + " currentPlayer: " + currentPlayer);
        } catch (Exception e) {
            throw new RuntimeException("Invalid session data!\nMore info:\n"+e.getMessage());
        }
    }
}
