import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Army {
    ArrayList<Unit> units = new ArrayList<>();
    Hero hero;
    long id;
    long position;


    public Army() {

    }
    public Army(ArrayList<Unit> units) {
        this.units = units;
    }

    public Army(JSONObject jsonObject) {


        this.units = units;
    }

    public int getCumulativeCount() {
        int cumulativeCount = 0;
        for (Unit unit : units) {
            cumulativeCount += unit.getNumber();
        }
        return cumulativeCount;
    }
}
