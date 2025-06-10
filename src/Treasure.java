import org.json.simple.JSONObject;

import java.util.Random;
import java.util.random.RandomGenerator;

public class Treasure extends GameObject {
    int position;
    String treasureType;
    int treasureValue;
    final static int MINIMUM_TREASURE_VALUE = 50;

    Treasure(JSONObject jsonObject) {
        try {
            Logger.getLogger().tag("JSON").logWeak("Parsing Treasure Object");
            Logger.getLogger().tag("JSON").logWeak("Treasure Object: " + jsonObject.toString());

            this.position = Integer.parseInt(jsonObject.get("position").toString());
            this.treasureType = jsonObject.get("type").toString();

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
            this.type = treasureTypeToGameObjectType(treasureType);

            switch (type) {
                case GAME_OBJECT_TYPE_TREASURE_SMALL:
                    this.treasureValue = MINIMUM_TREASURE_VALUE + (int)(Math.random()*20);
                    break;
                case GAME_OBJECT_TYPE_TREASURE_BIG:
                    this.treasureValue = MINIMUM_TREASURE_VALUE + (int)(Math.random()*200);
                    break;
                case GAME_OBJECT_TYPE_TREASURE:
                    this.treasureValue = MINIMUM_TREASURE_VALUE + (int)(Math.random()*100);
                    break;
                case GAME_OBJECT_TYPE_TREASURE_SPECIAL:
                    this.treasureValue = MINIMUM_TREASURE_VALUE + (int)(Math.random()*1000);
            }

        } catch (Exception e) {
            Logger.getLogger().tag("JSON").logError("Unable to construct Treasure object from JSON");
        }

    }

    static GameObjectType treasureTypeToGameObjectType(String _treasureType) {
        return switch (_treasureType) {
            case "1" -> GameObjectType.GAME_OBJECT_TYPE_TREASURE_SMALL;
            case "2" -> GameObjectType.GAME_OBJECT_TYPE_TREASURE_BIG;
            case "это просто *из*е*" -> GameObjectType.GAME_OBJECT_TYPE_TREASURE_SPECIAL;
            default -> {
                Logger.getLogger().tag("JSON").logWarning("Unrecognized Treasure Type: " + _treasureType);
                yield GameObjectType.GAME_OBJECT_TYPE_TREASURE;
            }
        };
    }

    @Override
    public String description() {
        return "Treasure";
    }
}
