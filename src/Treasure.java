import org.json.simple.JSONObject;

public class Treasure extends GameObject {
    int position;
    String treasureType;

    Treasure(JSONObject jsonObject) {
        try {
            Logger.getLogger().logInfo("Parsing Treasure Object");
            Logger.getLogger().logWeak("Treasure Object: " + jsonObject.toString());

            this.position = Integer.parseInt(jsonObject.get("position").toString());
            this.treasureType = jsonObject.get("type").toString();

            if (this.position <= 0) {
                Logger.getLogger().logError("Army position is less than 0");
                throw new RuntimeException("Army position is less than 0");
            }
            if (this.position >= 40*20) {
                Logger.getLogger().logError("Army position is too large");
                throw new RuntimeException("Army position is too large");
            }

            this.x = this.position%40;
            this.y = this.position/40;
            this.type = treasureTypeToGameObjectType(treasureType);



        } catch (Exception e) {
            Logger.getLogger().logError("Unable to construct Treasure object from JSON");
        }

    }

    static GameObjectType treasureTypeToGameObjectType(String _treasureType) {
        return switch (_treasureType) {
            case "1" -> GameObjectType.GAME_OBJECT_TYPE_TREASURE_SMALL;
            case "2" -> GameObjectType.GAME_OBJECT_TYPE_TREASURE_BIG;
            case "это просто *из*е*" -> GameObjectType.GAME_OBJECT_TYPE_TREASURE_SPECIAL;
            default -> {
                Logger.getLogger().logWarning("Unrecognized Treasure Type: " + _treasureType);
                yield GameObjectType.GAME_OBJECT_TYPE_TREASURE;
            }
        };
    }
}
