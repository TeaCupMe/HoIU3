import org.json.simple.JSONObject;

import java.util.ArrayList;

public class GameField {
    String field;
    Long width, height;


    GameField(String field, Long width, Long height) {
        this.field = field;
        this.height = height;
        this.width = width;
    }

    GameField(JSONObject jsonObject) { // TODO add try-catch clause
        this.field = (String) jsonObject.get("data");
        this.height = (Long) jsonObject.get("height");
        this.width = (Long) jsonObject.get("width");
    }



    public void printField() {
        System.out.printf("width: %d, height: %d, field: %s", this.width, this.height, this.field);
    }
}
