import org.json.simple.JSONObject;

public class Unit {
    private String name;
    private String description;
    private int hp;
    private int damage;
    private int number;

    Unit (JSONObject jsonObject) {
//        System.out.println(jsonObject.toString());
        try {
            this.name = (String) jsonObject.get("name");
            this.damage = Integer.parseInt(jsonObject.get("damage").toString());
            this.hp = Integer.parseInt(jsonObject.get("hp").toString());
            this.number = Integer.parseInt(jsonObject.get("number").toString());

            if (jsonObject.containsKey("description")) {
                this.description = (String) jsonObject.get("description");
            }

            if (this.number < 0) {
                this.number = 0;
                Logger.getLogger().logError("Number of units is negative, setting to zero");
            }
        } catch (Exception e) {
            Logger.getLogger().logError("Unable to construct Unit object from jSON");
            throw e;
        }

    }

    public int getNumber() {
        return number;
    }
}
