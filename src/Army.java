import java.util.ArrayList;

public class Army {
    ArrayList<Unit> units = new ArrayList<>();
    public Army() {

    }
    public Army(ArrayList<Unit> units) {
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
