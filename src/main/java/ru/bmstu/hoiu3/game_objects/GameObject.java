package ru.bmstu.hoiu3.game_objects;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameObjectType getType() {
        return type;
    }

    public String description() {
        return "Generic Game Object of type " + type + " at " + x + ", " + y;
    }

    public boolean intersects(GameObject other) {
        return this.x==other.x && this.y==other.y;
    }
}


