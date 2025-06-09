import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Enemy extends GameObject {
    int position;
    int number;
    ArrayList<Unit> units;

    Enemy (JSONObject jsonObject) {
        try {
            Logger.getLogger().tag("JSON").logWeak("Parsing Enemy " + jsonObject.toString());

            this.position = Integer.parseInt(jsonObject.get("position").toString());
            this.number = Integer.parseInt(jsonObject.get("number").toString());

            if (this.position <= 0) {
                Logger.getLogger().tag("JSON").logError("Enemy position is less than 0");
                throw new RuntimeException("Enemy position is less than 0");
            }
            if (this.position >= 40*20) {
                Logger.getLogger().tag("JSON").logError("Enemy position is too large");
                throw new RuntimeException("Enemy position is too large");
            }

            this.x = this.position%40;
            this.y = this.position/40;
            this.type = GameObjectType.GAME_OBJECT_TYPE_ENEMY;

            JSONArray unitsJSON = (JSONArray) jsonObject.get("units");
            Logger.getLogger().tag("JSON").logWeak(unitsJSON.size() + " units in JSON");
            Logger.getLogger().tag("JSON").logWeak("Enemy units: " + unitsJSON.toJSONString());

            this.units = new ArrayList<>(unitsJSON.size());
            for (Object o : unitsJSON) {
                JSONObject unitJSON = (JSONObject) o;
                this.units.add(new Unit(unitJSON));
            }
            Logger.getLogger().tag("JSON").logWeak("Enemy parsed with " + this.units.size() + " units");

        } catch (Exception e) {
            Logger.getLogger().tag("JSON").logError("Unable to construct Enemy from JSON");
            throw e;
        }
    }

}
