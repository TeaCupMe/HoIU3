import org.json.simple.JSONObject;

import java.util.ArrayList;

public class GameField {
    String field;
    int width, height;
    MapTile[][] fieldBuffer;

    GameField(String field, int width, int height) {
        this.field = field;
        this.height = height;
        this.width = width;
        fieldBuffer = new MapTile[height][width];
    }

    GameField(JSONObject jsonObject) { // TODO add try-catch clause
        this.field = (String) jsonObject.get("data");
        this.height = Integer.parseInt(jsonObject.get("height").toString());
        this.width = Integer.parseInt(jsonObject.get("width").toString());
        fieldBuffer = new MapTile[height][width];

        for (int row = 0; row < this.height; row++){
            for (int col = 0; col < this.width; col++){
                int value = Character.getNumericValue(this.field.charAt((row*this.width + col)));
                fieldBuffer[row][col] = new MapTile(value);
            }
        }
    }

    public boolean isValidPosition(int x, int y){
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }



    public void printField() {
        System.out.printf("width: %d, height: %d, field: %s", this.width, this.height, this.field);
    }
}
