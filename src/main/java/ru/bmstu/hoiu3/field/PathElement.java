package ru.bmstu.hoiu3.field;

import ru.bmstu.hoiu3.game_objects.GameObject;
import ru.bmstu.hoiu3.game_objects.GameObjectType;

public class PathElement extends GameObject {
    boolean isTarget = false;
    int staminaToHere;
    public PathElement(int x, int y) {
        super(x, y, GameObjectType.GAME_OBJECT_TYPE_PATH);
    }

    @Override
    public String toString() {
        if (isTarget) return "T"+staminaToHere;
        else return "P" + staminaToHere;
    }
}
