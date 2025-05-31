import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Army {
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
            this.id = Integer.parseInt(jsonObject.get("id").toString());
            this.position = Integer.parseInt(jsonObject.get("position").toString());
            if (this.id < 0) {
                Logger.getLogger().log("Army ID is invalid");
                throw new RuntimeException("Army ID is less than 0");
            }
            if (this.position <= 0) {
                Logger.getLogger().log("Army Position is less than 0");
                throw new RuntimeException("Army Position is less than 0");
            }
            if (this.position >= 40*20) {
                Logger.getLogger().log("Army Position is too large");
                throw new RuntimeException("Army Position is too large");
            }

            JSONArray unitsJSON = (JSONArray) jsonObject.get("army"); // FIXME rename field to units
            Logger.getLogger().log("Army units: " + unitsJSON.toJSONString());

            this.units = new ArrayList<>(unitsJSON.size());
            for (Object o : unitsJSON) {
                JSONObject unitJSON = (JSONObject) o;
                this.units.add(new Unit(unitJSON));
            }
        }
        catch (Exception e) {
            Logger.getLogger().logError("Unable to create Army from JSON" + e.getMessage());
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
}
