import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.security.auth.login.LoginException;
import java.security.KeyException;
import java.util.ArrayList;

public class Player { // TODO add player id parsing
//    ArrayList<Army> armies;

    final static int MIN_STAMINA_TO_MOVE = 5;
    ArrayList<Hero> heroes;
    Castle castle;
    int id;

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

            heroes = new ArrayList<>(armiesJSON.size());

            for (Object o : armiesJSON) {
                Logger.getLogger().tag("JSON").logWeak("Parsing Army " + o.toString());
                JSONObject armyJSON = (JSONObject) o;
                Hero hero = new Hero(new Army(armyJSON));
//                Army army = new Army(armyJSON);
                heroes.add(hero);
                Game.gameObjects.add(hero);
            }

            Castle castle = new Castle(castleJSON);
            this.castle = castle;
            Game.gameObjects.add(castle);

            Logger.getLogger().tag("JSON").logSuccess("Successfully parsed player JSON");

        } catch (Exception e) {
            Logger.getLogger().tag("JSON").logError("Unable to construct Player from JSON");
            throw e;
        }
    }

    boolean canMove() {
        for (Hero hero : heroes) {
            if (hero.getStamina()>MIN_STAMINA_TO_MOVE) {
                return true;
            }
        }
        return false;
    }
}
