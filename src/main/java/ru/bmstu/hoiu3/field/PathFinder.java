package ru.bmstu.hoiu3.field;

import ru.bmstu.hoiu3.game_objects.Cursor;
import ru.bmstu.hoiu3.game_objects.GameObject;
import ru.bmstu.hoiu3.game_objects.GameObjectType;
import ru.bmstu.hoiu3.game_objects.Hero;
import space.crtech.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class PathFinder {
    GameField gameField;
    ArrayList<GameObject> gameObjects;

    ArrayList<PathElement> pathElements = new ArrayList<>();

    public PathFinder(GameField gameField, ArrayList<GameObject> gameObjects) {
            this.gameField = gameField;
            this.gameObjects = gameObjects;
    }

    public void drawPath(Hero hero, Cursor cursor) {
        if (hero.intersects(cursor)) return;
        if (!gameField.fieldBuffer[hero.getY()][hero.getX()].isWalkable()) return;

        PathElement[][] blank = new PathElement[gameField.getHeight()][gameField.getWidth()];
        for (int y = 0; y<gameField.getHeight(); y++) {
            for (int x = 0; x<gameField.getWidth(); x++) {
                if (gameField.fieldBuffer[y][x].isWalkable()) blank[y][x]=new PathElement(x, y);

            }
        }

        for (GameObject gameObject: gameObjects) {
            if (gameObject.getType() != GameObjectType.GAME_OBJECT_TYPE_CURSOR)
                blank[gameObject.getY()][gameObject.getX()] = null;
        }
        blank[cursor.getY()][cursor.getX()].isTarget = true;
        for (PathElement[] row: blank) {
            Logger.getLogger().tag("PATH DEBUG").logInfo(Arrays.toString(row));
        }

//        boolean
//        while ()
//        Logger.getLogger().tag("PATH DEBUG").logInfo(Arrays.deepToString(blank));
    }

    public void clearPath() {
        for (PathElement pathElement: pathElements) {
            gameObjects.remove(pathElement);
        }
    }
}
