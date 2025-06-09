import org.json.simple.JSONObject;

public class Castle extends GameObject{
    int position;
    Castle(int x, int y) {
        super(x, y, GameObjectType.GAME_OBJECT_TYPE_CASTLE);
    }
    Castle(JSONObject jsonObject) { // TODO add try-catch clause
        this.position = Integer.parseInt(jsonObject.get("position").toString());
        this.x = position%40;
        this.y = position/40;
        this.type = GameObjectType.GAME_OBJECT_TYPE_CASTLE;
    }
}
