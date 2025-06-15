package ru.bmstu.hoiu3.ui;

import org.jetbrains.annotations.NotNull;
import ru.bmstu.hoiu3.Game;
import ru.bmstu.hoiu3.field.GameField;
import ru.bmstu.hoiu3.field.MapTile;
import ru.bmstu.hoiu3.core.UserAction;
import ru.bmstu.hoiu3.game_objects.GameObject;
import ru.bmstu.hoiu3.game_objects.GameObjectType;
import space.crtech.utils.Logger;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

public class UI {
    // 🟥🟩🟫🟫🟧🟣
    final static int CURSOR_FLICKER_OFFSET = 400;
    long lastCursorSwap = 0;
    boolean cursorEnabled = false;
    private boolean cursorVisible = false;

    int inputRetries = 0;
    OutputStream outputStream;
    OutputStream fieldStream;
    InputStream inputStream;
    Scanner scanner;
    UIWindow window;
    // 9 - дорога,
    // 8 - кратер после битвы(по нему ходить дороже),
    // 1, 2, 3 - препятствия,
    // 0 - пустая земля

    //TODO move to Tile class
    public static Map<Integer, String> mapLookUpTable = Map.of(
                0, "⬜",  // ⬜
                1, "☢️",      // ❌🌋🌩️⚡❄️⛓️☢️
                2, "⛰️",      // ⛰️
                3, "\uD83D\uDFEA",      // 🌊
                8, "⬛",                // ⬛
                9, "\uD83D\uDFE8",      // 🟨
                10, "\uD83D\uDFEA",     // 🟪
                11, "",
                12, ""
            );

    Map<GameObjectType, String> gameObjectLookUpTable = Map.of(
            GameObjectType.GAME_OBJECT_TYPE_HERO, "\uD83E\uDDD9",               // 🧙🧙
            GameObjectType.GAME_OBJECT_TYPE_ARMY, "\uD83E\uDDD9",               // 😀🧙
            GameObjectType.GAME_OBJECT_TYPE_CASTLE, "\uD83C\uDFF0",             // 🏰
            GameObjectType.GAME_OBJECT_TYPE_ENEMY, "\uD83D\uDC00",              // 🐀
            GameObjectType.GAME_OBJECT_TYPE_TREASURE, "\uD83C\uDF46",           // 🍆
            GameObjectType.GAME_OBJECT_TYPE_TREASURE_SMALL, "\uD83D\uDC8E",     // 💎
            GameObjectType.GAME_OBJECT_TYPE_TREASURE_BIG, "\uD83C\uDF7B",       // 🍻
//            GameObjectType.GAME_OBJECT_TYPE_TREASURE_SPECIAL, "\uD83C\uDF7A",   // 🍺
            GameObjectType.GAME_OBJECT_TYPE_MATVEI, "\uD83E\uDD21",             // 🤡
            GameObjectType.GAME_OBJECT_TYPE_CURSOR, "⬛",
            GameObjectType.GAME_OBJECT_TYPE_PATH, "⬛"
        );

    UI(OutputStream _output, InputStream _input) {
        outputStream = _output;
        fieldStream = _output;
        inputStream = _input;
        scanner = new Scanner(inputStream);
    }

    public UI(OutputStream _output, OutputStream _field, InputStream _input) {
        outputStream = _output;
        fieldStream = _field;
        inputStream = _input;
        scanner = new Scanner(inputStream);
    }


    private String drawField(@NotNull GameField gameField) {
        assert Game.gameObjects != null;
        MapTile[][] fieldBuffer = new MapTile[Math.toIntExact(gameField.getHeight())][Math.toIntExact(gameField.getWidth())];

        // Fill the map with 'ground-layer' tiles
//        for (int row = 0; row < gameField.height; row++){
//            for (int col = 0; col < gameField.width; col++){
//                fieldBuffer[row][col] = new MapTile(mapLookUpTable.get(Character.getNumericValue(gameField.field.charAt((int) (row*gameField.width + col)))));
//            }
//        }
        for (int y = 0; y < gameField.getHeight(); y++) {
            if (gameField.getWidth() >= 0) System.arraycopy(gameField.fieldBuffer[y], 0, fieldBuffer[y], 0, gameField.getWidth());
        }
//        fieldBuffer = gameField.fieldBuffer.clone();

        // overlap game objects over ground-layer
        for (GameObject obj: Game.gameObjects) {
            if (obj.getType() == GameObjectType.GAME_OBJECT_TYPE_CURSOR) {
                if (cursorEnabled) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastCursorSwap > CURSOR_FLICKER_OFFSET) {
                        lastCursorSwap = currentTime;
                        cursorVisible = !cursorVisible;
                    }
                    if (cursorVisible) {
                        fieldBuffer[obj.getY()][obj.getX()] = new MapTile(gameObjectLookUpTable.get(obj.getType()));
                    }
                }
            } else {
                fieldBuffer[obj.getY()][obj.getX()] = new MapTile(gameObjectLookUpTable.get(obj.getType()));
            }
        }

        // output everything
        StringBuilder outputField = new StringBuilder();

        for (MapTile[] mapTiles : fieldBuffer) {
            for (MapTile mapTile : mapTiles) {
                outputField.append(mapTile.toString());
            }
            outputField.append("\n");
        }
        return outputField.toString();
//        redrawField(outputField.toString());
    }

    static void printToStream(String output, OutputStream stream) {
        try {
            if (stream.getClass() == JTextAreaOutputStream.class) {
                stream.write(output.getBytes(StandardCharsets.UTF_16));
            } else {
                stream.write(output.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            Logger.getLogger().tag("UI").logError("IOException while printing to stream");
            throw new RuntimeException(e);
        }
    }

    public void showHelloMessage() {
        window.showHelloMessage();
    }

    public void redrawField(GameField gameField) {
        window.showField(drawField(gameField));
    }

    public void redrawFieldSlow(GameField gameField, int lineDelay) {
        window.showFieldSlow(drawField(gameField), lineDelay);
    }

    public String getLineInput(String prompt) {
        print(prompt);
        return window.getLineInput();
    }

    public void getInteractiveInput(Function<Integer, Boolean> listener, ArrayList<KeyEvent> keyEvents) {
        window.getInteractiveInput(listener, keyEvents);
    }


    public void startInteractiveInput(Function<Integer, Boolean> listener, ArrayList<KeyEvent> keyEvents) {
        window.startInteractiveInput(listener, keyEvents);
    }
    public void endInteractiveInput() {
        window.endInteractiveInput();
    }

    public int getActionSelectorInput(ArrayList<UserAction> actions) {
        for (UserAction action : actions) {
            int actionID = actions.indexOf(action);
            println("\t" + actionID + " - " + action.getName());
        }
        int selectedAction = -1;
        String selectedActionString = "";

        do {
            try {
                selectedActionString = getLineInput("Enter action: ");
                selectedAction = Integer.parseInt(selectedActionString);
            } catch (NumberFormatException e) {
                Logger.getLogger().tag("INPUT").logError("NumberFormatException while getting action");
                selectedAction = -1;
            }
        } while (!(selectedAction>=0 && selectedAction<actions.size()) && println("Incorrect action number!"));

        return selectedAction;

    }

    public void setUIWindow(UIWindow window) {
        this.window = window;
    }

    public boolean print(String prompt) {
        printToStream(prompt, outputStream);
        return true;
    }
    public boolean println(String prompt) {
        printToStream(prompt+"\n", outputStream);
        return true;
    }

    public void printTemporaryLine(String line) {
        window.printTemporaryLine(line);
    }

    public void eraseTemporaryLine() {
        window.eraseTemporaryLine();
    }

    public void enableCursor(boolean enable) {
        cursorEnabled = enable;
    }

    public boolean isCursorEnabled() {
        return cursorEnabled;
    }
}
