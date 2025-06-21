package ru.bmstu.hoiu3.game_objects;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bmstu.hoiu3.core.Unit;

import java.util.ArrayList;

public class Castle extends GameObject {
    int position;
    ArrayList<Unit> units  = new ArrayList<>();
    public Castle(int x, int y) {
        super(x, y, GameObjectType.GAME_OBJECT_TYPE_CASTLE);
    }
    public Castle(JSONObject jsonObject) { // TODO add try-catch clause
        this.position = Integer.parseInt(jsonObject.get("position").toString());
        this.x = position%40;
        this.y = position/40;
        this.type = GameObjectType.GAME_OBJECT_TYPE_CASTLE;

        JSONArray unitsJSON = (JSONArray) jsonObject.get("units");
        for (Object o: unitsJSON) {
            units.add(new Unit((JSONObject) o));
        }
    }

    @Override
    public String description() {
        return "Castle";
    }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        JSONArray unitsJSON = new JSONArray();
        for (Unit unit: units) {
            unitsJSON.put(unit.toJSON());
        }
        jo.put("units", unitsJSON);
        jo.put("position", 40 * y + x);
        return jo;
    }
}
