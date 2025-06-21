package ru.bmstu.hoiu3.core;

//import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.bmstu.hoiu3.Game;
import ru.bmstu.hoiu3.game_objects.Army;
import ru.bmstu.hoiu3.game_objects.Castle;
import ru.bmstu.hoiu3.game_objects.Hero;
import space.crtech.utils.Logger;

import java.util.ArrayList;

public class Player { // TODO add player id parsing
//    ArrayList<Army> armies;

    final static int MIN_STAMINA_TO_MOVE = 5;
    ArrayList<Hero> heroes;
    Castle castle;
    int id;

    int money = 1000;

    int cursorX, cursorY;

    Player(JSONObject jsonObject) {
        try {
            Logger.getLogger().tag("JSON").logWeak("Parsing player JSON");
            Logger.getLogger().tag("JSON").logWeak("Player JSON: " + jsonObject.toString());

            id = Integer.parseInt(jsonObject.get("id").toString());

            JSONArray heroesJSON =  (JSONArray)  jsonObject.get("armies"); // TODO rename to heroes
            JSONObject castleJSON = (JSONObject) jsonObject.get("castle");


            if (heroesJSON == null) {
                Logger.getLogger().tag("JSON").logError("Armies JSON is null");
                throw new RuntimeException("armies JSON array is null");
            }
            if (castleJSON == null) {
                Logger.getLogger().tag("JSON").logError("Castle JSON is null");
                throw new RuntimeException("castle JSON array is null");
            }

            heroes = new ArrayList<>(heroesJSON.length());

            for (Object o : heroesJSON) {
                Logger.getLogger().tag("JSON").logWeak("Parsing Army " + o.toString());
                JSONObject heroJSON = (JSONObject) o;
                Hero hero = new Hero(heroJSON);
                hero.setPlayer(this);
//                Army army = new Army(armyJSON);
                heroes.add(hero);
                Game.gameObjects.add(hero);
            }

            Castle castle = new Castle(castleJSON);
            this.castle = castle;
            Game.gameObjects.add(castle);

            Logger.getLogger().tag("JSON").logSuccess("Successfully parsed player JSON");

        } catch (Exception e) {
            Logger.getLogger().tag("JSON").logError("Unable to construct Player from JSON");
            throw e;
        }
    }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("id", id);

        JSONArray armiesJSON = new JSONArray();
        for (Hero hero: heroes) {
            armiesJSON.put(hero.toJSON());
        }

        jo.put("armies", armiesJSON);

        jo.put("castle", castle.toJSON());

        return jo;
    }

    boolean canMove() {
        for (Hero hero : heroes) {
            if (hero.getStamina()>MIN_STAMINA_TO_MOVE) {
                return true;
            }
        }
        return false;
    }

    public Hero getHero(int index) {
        return heroes.get(index);
    }

    public int getHeroesCount() {
        return heroes.size();
    }

    public Castle getCastle() {
        return castle;
    }

    public void removeHero(Hero hero) {
        heroes.remove(hero);
    }

    public int getCursorX() {
        return cursorX;
    }
    public int getCursorY() {
        return cursorY;
    }


    public int getMoney() {
        return money;
    }

    public void addMoney(int salary) {
        money += salary;
    }

    public boolean decreaseMoney(int tax) {
        if (tax <= money) {
            money -= tax;
            return true;
        }
        return false;
    }
}
