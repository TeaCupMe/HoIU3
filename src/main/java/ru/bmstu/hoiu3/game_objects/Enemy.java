package ru.bmstu.hoiu3.game_objects;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bmstu.hoiu3.Game;
import ru.bmstu.hoiu3.core.Unit;
import space.crtech.utils.Logger;

import java.util.ArrayList;

public class Enemy extends GameObject {
    int position;
    int number;
    //    ArrayList<Unit> units;
    Army army;

    public Enemy(JSONObject jsonObject) {
        try {
            Logger.getLogger().tag("JSON").logWeak("Parsing Enemy " + jsonObject.toString());

            this.position = Integer.parseInt(jsonObject.get("position").toString());
            this.number = Integer.parseInt(jsonObject.get("number").toString());

            if (this.position <= 0) {
                Logger.getLogger().tag("JSON").logError("Enemy position is less than 0");
                throw new RuntimeException("Enemy position is less than 0");
            }
            if (this.position >= 40 * 20) {
                Logger.getLogger().tag("JSON").logError("Enemy position is too large");
                throw new RuntimeException("Enemy position is too large");
            }

            this.x = this.position % 40;
            this.y = this.position / 40;
            this.type = GameObjectType.GAME_OBJECT_TYPE_ENEMY;

            JSONArray unitsJSON = (JSONArray) jsonObject.get("units");
            Logger.getLogger().tag("JSON").logWeak(unitsJSON.length() + " units in JSON");
            Logger.getLogger().tag("JSON").logWeak("Enemy units: " + unitsJSON.toString());

//            this.units = new ArrayList<>(unitsJSON.length());
            ArrayList<Unit> units = new ArrayList<>(unitsJSON.length());
            for (Object o : unitsJSON) {
                JSONObject unitJSON = (JSONObject) o;
//                this.units.add(new Unit(unitJSON));
                units.add(new Unit(unitJSON));
            }
            // TODO Get rid of ArrayList<Unit> and use Army instead
            this.army = new Army(units);

            Logger.getLogger().tag("JSON").logWeak("Enemy parsed with " + army.description());

        } catch (Exception e) {
            Logger.getLogger().tag("JSON").logError("Unable to construct Enemy from JSON");
            throw e;
        }
    }

    @Override
    public String interact(Hero hero) {
        StringBuilder interactionResult = new StringBuilder();
        interactionResult.append("Fight!\n")
                .append("\tHero ")
                .append(hero.getName())
                .append(" with \n\t\t")
                .append(hero.armyDescription())
                .append("\nis fighting \n")
                .append("\tEnemy with\n\t\t")
                .append(armyDescription())
                .append("\n");

        int heroPower = hero.getPower() * 2000;
        int myPower = army.getCumulativePower() * 0; // For debug purposes we set enemy power to 0

        // repeat hitting each other until there is only one(or less) man standing
        while (hero.isAlive() && army.getCumulativeCount() > 0) {
            this.army.receiveDamage(heroPower);
            hero.receiveDamage(myPower);
        }

        if (!hero.isAlive()) {
            interactionResult.append("Enemy killed hero ")
                    .append(hero.getName())
                    .append("!");
        }
        if (army.getCumulativeCount() == 0) {
            interactionResult.append("Hero ")
                    .append(hero.getName())
                    .append(" killed enemy!");
            Game.gameObjects.remove(this);
        }

        // TODO uses magic number as tile type
        Game.getGameSession().field.updateTileType(this.x, this.y, 8);

        return interactionResult.toString();
    }

    @Override
    public String description() {
//        int totalNumber = army.getCount();
//        for (Unit u : units) {
//            totalNumber += u.getNumber();
//        }
        return "Enemy with " + army.description();
    }

    public String armyDescription() {
        return army.description() + ", power: " + army.getCumulativePower();
    }

    public JSONObject toJSON() {
    JSONObject jo = new JSONObject();

    jo.put("position", y * 40 + x);
    jo.put("number", army.getCumulativeCount());

    JSONArray unitsJSON = new JSONArray();
    for (Unit unit: army.units) {
        unitsJSON.put(unit.toJSON());
    }

    jo.put("units", unitsJSON);
    return jo;
}

}
