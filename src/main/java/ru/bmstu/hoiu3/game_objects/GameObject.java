package ru.bmstu.hoiu3.game_objects;

import java.lang.reflect.Array;
import java.util.Collection;
import ru.bmstu.hoiu3.Game;
import space.crtech.utils.Logger;

public class GameObject {
    int x, y;
    GameObjectType type;
    public GameObject(int x, int y, GameObjectType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    GameObject() {
        
    }

    public String interact(Hero hero) {
//        Game.ui.println("Interaction with general GameObject");
        Logger.getLogger().tag("Game Object").logWarning("Looks like shit happened, hero interacted with blank GameObject");
        return "Interaction with plain GameObject (shit has happened, go fix it, stupid programmer)";
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

    public static GameObject getByCoordinates(Collection<GameObject> array, int x, int y) {
        for (GameObject go: array) {
            if (go.getX()==x && go.getY()==y) return go;
        }
        return null;
    }
}


