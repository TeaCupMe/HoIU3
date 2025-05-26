import org.json.simple.JSONObject;

public class Unit {
    private String name;
    private String description;
    private String hp;
    private String damage;
    private int number;

    Unit (JSONObject jsonUnit) {
        System.out.println(jsonUnit.toString());
    }

    public int getNumber() {
        return number;
    }
}
