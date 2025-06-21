package ru.bmstu.hoiu3.core;

import org.json.JSONObject;
import space.crtech.utils.Logger;

public class Unit {
    private String name;
    private String description;
    private int hp;
    private int damage;
    private int number;

    public Unit (JSONObject jsonObject) {
//        System.out.println(jsonObject.toString());
        try {
            this.name = (String) jsonObject.get("name");
            this.damage = Integer.parseInt(jsonObject.get("damage").toString());
            this.hp = Integer.parseInt(jsonObject.get("hp").toString());
            this.number = Integer.parseInt(jsonObject.get("number").toString());

            if (jsonObject.has("description")) {
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

    public int getCumulativePower() {
        return damage*number;
    }

    public int getCumulativeHp() {
        return hp*number;
    }

    public int receiveDamage(int damage) {
        int killed = 0;
//        if (getCumulativeHp() <= damage) {
//            killed = number;
//        } else {
            killed = Math.min((int) Math.ceil(((double) damage)/hp), number);
//        }
        number-=killed;
        return damage - (killed * hp);
    }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();

        jo.put("name", name);
        jo.put("damage", damage);
        jo.put("hp", hp);
        jo.put("number", number);

        return jo;
    }
}
