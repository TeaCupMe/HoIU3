public class GameObject {
    int x, y;
    GameObjectType type;
    GameObject(int x, int y, GameObjectType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    GameObject() {
        
    }

    public String description() {
        return "Generic Game Object of type " + type + " at " + x + ", " + y;
    }
    }
}


