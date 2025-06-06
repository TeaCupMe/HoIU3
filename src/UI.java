import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;

public class UI {
    // 🟥🟩🟫🟫🟧🟣
    int inputRetries = 0;
    PrintStream outputStream;
    PrintStream fieldStream;
    InputStream inputStream;
    Scanner scanner;

    // 9 - дорога,
    // 8 - кратер после битвы(по нему ходить дороже),
    // 1, 2, 3 - препятствия,
    // 0 - пустая земля

    Map<Integer, String> mapLookUpTable = Map.of(
                0, "\uD83D\uDFE9",
                1, "\uD83D\uDEA7",
                2, "\uD83D\uDFE5",
                3, "\uD83D\uDFE7",
                8, "⬛",
                9, "\uD83D\uDFE8",
                10, "\uD83D\uDFEA",
                11, "",
                12, ""
            );

    Map<GameObjectType, String> gameObjectLookUpTable = Map.of(
            GameObjectType.GAME_OBJECT_TYPE_HERO, "\uD83E\uDDD9", // 🧙
            GameObjectType.GAME_OBJECT_TYPE_ARMY, "\uD83D\uDE00", // 𓀀
            GameObjectType.GAME_OBJECT_TYPE_CASTLE, "\uD83C\uDFF0", // 🏰
            GameObjectType.GAME_OBJECT_TYPE_ENEMY, "\uD83D\uDC00", // 🐀
            GameObjectType.GAME_OBJECT_TYPE_TREASURE, "\uD83D\uDCB0", // 💰
            GameObjectType.GAME_OBJECT_TYPE_TREASURE_SMALL, "\uD83D\uDC8E", // 💎
            GameObjectType.GAME_OBJECT_TYPE_TREASURE_SPECIAL, "\uD83C\uDF7A", // 🍺
            GameObjectType.GAME_OBJECT_TYPE_MATVEI, "\uD83E\uDD21" // 🤡

        );

    UI(OutputStream _output, InputStream _input) {
        outputStream = new PrintStream(_output);
        fieldStream = new PrintStream(_output);
        inputStream = _input;
        scanner = new Scanner(inputStream);
    }

    UI(OutputStream _output, OutputStream _field, InputStream _input) {
        outputStream = new PrintStream(_output);
        fieldStream = new PrintStream(_field);
        inputStream = _input;
        scanner = new Scanner(inputStream);
    }


    public void drawField(@NotNull GameField gameField) {
        assert Game.gameObjects != null;

        MapTile[][] fieldBuffer = new MapTile[Math.toIntExact(gameField.height)][Math.toIntExact(gameField.width)];

        // Fill the map with 'ground-layer' tiles
        for (int row = 0; row < gameField.height; row++){
            for (int col = 0; col < gameField.width; col++){
                fieldBuffer[row][col] = new MapTile(mapLookUpTable.get(Character.getNumericValue(gameField.field.charAt((int) (row*gameField.width + col)))));
            }
        }

        // overlap game objects over ground-layer
        for (GameObject obj: Game.gameObjects) {
            fieldBuffer[obj.y][obj.x] = new MapTile(gameObjectLookUpTable.get(obj.type));
        }

        // output everything
        StringBuilder outputField = new StringBuilder();

        for (MapTile[] mapTiles : fieldBuffer) {
            for (MapTile mapTile : mapTiles) {
                outputField.append(mapTile.toString());
            }
            outputField.append("\n");
        }

        UI.printToStream(outputField.toString(), fieldStream);




    }

    static void printToStream(String output, PrintStream stream) {
        stream.print(output);
    }

    public int getIntInput(String query, int min, int max) {
        int retries = inputRetries;
        outputStream.println(query);
        int input;
        input = scanner.nextInt();
        while ((input < min || input > max) && retries-- != 0) {
            outputStream.println("Invalid input! Try again.\n");
            outputStream.println(query);
            input = scanner.nextInt();
        }
        return input;
    }

    public int getIntInput(String query, @NotNull Collection<Integer> validInputs) {
        outputStream.println(query);
        int input;
        input = scanner.nextInt();
        while ((!validInputs.contains(input)) && inputRetries-- != 0) {
            outputStream.println("Invalid input! Try again.\n");
            outputStream.println(query);
            input = scanner.nextInt();
        }
        return input;
    }
}
