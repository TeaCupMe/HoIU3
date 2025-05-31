import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.security.KeyException;
import java.util.ArrayList;

public class Player {
    ArrayList<Army> armies;
    Castle castle;

    Player(JSONObject jsonObject) {
        try {
            JSONArray armiesJSON =  (JSONArray)  jsonObject.get("armies");
            JSONObject castleJSON = (JSONObject) jsonObject.get("castle");
            if (armiesJSON == null) {
                Logger.getLogger().logError("Armies JSON is null");
                throw new RuntimeException("armies JSON array is null");
            }
            if (castleJSON == null) {
                Logger.getLogger().logError("Castle JSON is null");
                throw new RuntimeException("castle JSON array is null");
            }

            armies = new ArrayList<>(armiesJSON.size());

            for (int i = 0; i < armiesJSON.size(); i++) {
                JSONObject armyJSON = (JSONObject) armiesJSON.get(i);
                armies.add(new Army(armyJSON));
            }

        } catch (Exception e) {
            Logger.getLogger().logError("Unable to construct Player from JSON");
//            System.exit(1);
            throw e;
        }

    }
}
