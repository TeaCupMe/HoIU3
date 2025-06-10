import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Army extends GameObject {
    ArrayList<Unit> units = new ArrayList<>();
    Hero hero;
    int id;
    int position;


    public Army() {

    }
    public Army(ArrayList<Unit> units) {
        this.units = units;
    }

    public Army(JSONObject jsonObject) {
        try {
            Logger.getLogger().tag("JSON").logWeak("Parsing Army Object");
            Logger.getLogger().tag("JSON").logWeak("Army Object: " + jsonObject.toString());

            this.id = Integer.parseInt(jsonObject.get("id").toString());
            this.position = Integer.parseInt(jsonObject.get("position").toString());

            if (this.id < 0) {
                Logger.getLogger().tag("JSON").logError("Army ID is invalid");
                throw new RuntimeException("Army ID is less than 0");
            }
            if (this.position <= 0) {
                Logger.getLogger().tag("JSON").logError("Army position is less than 0");
                throw new RuntimeException("Army position is less than 0");
            }
            if (this.position >= 40*20) {
                Logger.getLogger().tag("JSON").logError("Army position is too large");
                throw new RuntimeException("Army position is too large");
            }

            this.x = this.position%40;
            this.y = this.position/40;
            this.type = GameObjectType.GAME_OBJECT_TYPE_ARMY;

            JSONArray unitsJSON = (JSONArray) jsonObject.get("army"); // FIXME rename field to units
            Logger.getLogger().tag("JSON").logWeak(unitsJSON.size() + " units in JSON");
            Logger.getLogger().tag("JSON").logWeak("Army units: " + unitsJSON.toJSONString());

            this.units = new ArrayList<>(unitsJSON.size());
            for (Object o : unitsJSON) {
                JSONObject unitJSON = (JSONObject) o;
                this.units.add(new Unit(unitJSON));
            }

            Logger.getLogger().tag("JSON").logWeak("Army parsed!");
        }
        catch (Exception e) {
            Logger.getLogger().tag("JSON").logError("Unable to create Army from JSON" + e.getMessage());
            throw e;
        }
    }

    public int getCumulativeCount() {
        int cumulativeCount = 0;
        for (Unit unit : units) {
            cumulativeCount += unit.getNumber();
        }
        return cumulativeCount;
    }

    @Override
    public String description() {
        return "Army of " + this.units.size() + " units. Total: " + this.getCumulativeCount();
    }

}
