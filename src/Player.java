import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.security.auth.login.LoginException;
import java.security.KeyException;
import java.util.ArrayList;

public class Player {
    ArrayList<Army> armies;
    Castle castle;

    Player(JSONObject jsonObject) {
        try {
            Logger.getLogger().tag("JSON").logWeak("Parsing player JSON");
            Logger.getLogger().tag("JSON").logWeak("Player JSON: " + jsonObject.toString());

            id = Integer.parseInt(jsonObject.get("id").toString());

            JSONArray armiesJSON =  (JSONArray)  jsonObject.get("armies");
            JSONObject castleJSON = (JSONObject) jsonObject.get("castle");
            if (armiesJSON == null) {
                Logger.getLogger().tag("JSON").logError("Armies JSON is null");
                throw new RuntimeException("armies JSON array is null");
            }
            if (castleJSON == null) {
                Logger.getLogger().tag("JSON").logError("Castle JSON is null");
                throw new RuntimeException("castle JSON array is null");
            }

            armies = new ArrayList<>(armiesJSON.size());

            for (Object o : armiesJSON) {
                Logger.getLogger().tag("JSON").logWeak("Parsing Army " + o.toString());
                JSONObject armyJSON = (JSONObject) o;
                Army army = new Army(armyJSON);
                armies.add(army);
                Game.gameObjects.add(army);
            }

            Castle cstl = new Castle(castleJSON);
            this.castle = cstl;
            Game.gameObjects.add(cstl);

            Logger.getLogger().tag("JSON").logSuccess("Successfully parsed player JSON");

        } catch (Exception e) {
            Logger.getLogger().tag("JSON").logError("Unable to construct Player from JSON");
            throw e;
        }

    }
}
