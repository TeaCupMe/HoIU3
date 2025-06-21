package ru.bmstu.hoiu3.core;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bmstu.hoiu3.Game;
import ru.bmstu.hoiu3.field.GameField;
import ru.bmstu.hoiu3.game_objects.Enemy;
import ru.bmstu.hoiu3.game_objects.Treasure;
import space.crtech.utils.Logger;

import java.util.ArrayList;

public class SessionData {

    JSONObject jsonData;

    int totalPlayers;
    public GameField field;
    int currentPlayer;
    ArrayList<Player> players;
    ArrayList<Enemy> enemies;
    ArrayList<Treasure> treasures;
    int gameState;
    int day;

    public SessionData(JSONObject jsonObject) {
        try {

            // plain fields in first layer of JSON
            Logger.getLogger().tag("JSON").logInfo("Parsing session metadata");
            this.totalPlayers = Integer.parseInt(jsonObject.get("totalPlayers").toString());
            this.currentPlayer = Integer.parseInt(jsonObject.get("currentPlayer").toString());
            this.field = new GameField((JSONObject) jsonObject.get("field"));
            this.gameState = Integer.parseInt(jsonObject.get("gameState").toString());
            this.day = Integer.parseInt(jsonObject.get("day").toString());
            Logger.getLogger().tag("JSON").logSuccess("Session metadata parsed");

            Game.gameObjects = new ArrayList<>();

            // Parse players data
            Logger.getLogger().tag("JSON").logInfo("Parsing players");
            players = new ArrayList<>(Math.toIntExact(this.totalPlayers));
            JSONArray playersJSON = (JSONArray) jsonObject.get("players");
            for (int i = 0; i < this.totalPlayers; i++) {
                Logger.getLogger().tag("JSON").logInfo("Parsing player " + i);
                Logger.getLogger().logWeak("Adding player: " + playersJSON.get(i));
                players.add(new Player((JSONObject) playersJSON.get(i)));
            }
            Logger.getLogger().tag("JSON").logSuccess(this.totalPlayers + " players added");



            // Parse enemies data
            Logger.getLogger().tag("JSON").logInfo("Parsing enemies");
            enemies = new ArrayList<>();
            JSONArray enemiesJSON = (JSONArray) jsonObject.get("enemies");
            Logger.getLogger().logWeak("Got array of " + enemiesJSON.length() + " enemies");
            for (int i = 0; i < enemiesJSON.length(); i++) {
                Logger.getLogger().logWeak("Adding enemy " + i);
                Enemy enemy = new Enemy((JSONObject) enemiesJSON.get(i));
                enemies.add(enemy);
                Game.gameObjects.add(enemy);
            }
            Logger.getLogger().tag("JSON").logSuccess(this.enemies.size() + " enemies added");

            // Parse treasures
            Logger.getLogger().tag("JSON").logInfo("Parsing treasures");
            treasures = new ArrayList<>();
            JSONArray treasuresJSON = (JSONArray) jsonObject.get("treasures");
            Logger.getLogger().logWeak("Got array of " + treasuresJSON.length() + " treasures");
            for (int i = 0; i < treasuresJSON.length(); i++) {
                Logger.getLogger().tag("JSON").logWeak("Adding treasure " + i);
                Treasure treasure = new Treasure((JSONObject) treasuresJSON.get(i));
                treasures.add(treasure);
                Game.gameObjects.add(treasure);
            }
            Logger.getLogger().tag("JSON").logSuccess(this.treasures.size() + " treasures added");

            jsonData = jsonObject;
            Logger.getLogger().tag("JSON").logSuccess("Successfully parsed SessionData: " + this);
        } catch (Exception e) {
            Logger.getLogger().tag("JSON").logError("Parsed invalid session data!\n\tSessionData: " + e.getMessage());
            throw new RuntimeException("Invalid session data!\nMore info:\n"+e.getMessage());
        }
    }



    public Player getPlayerById(int id) {
        for (Player player : players) {
            if (player.id == id) {
                return player;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "SessionData: totalPlayers: " +
                totalPlayers +
                ", currentPlayer: " +
                currentPlayer +
                ", day: " +
                day +
                ", gameState: " +
                gameState;
    }

    public String toDataString() {
        return "totalPlayers: " +
                totalPlayers +
                ", currentPlayer: " +
                currentPlayer +
                ", day: " +
                day +
                ", gameState: " +
                gameState;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();

        // Serialize plain fields
        jo.put("totalPlayers", this.totalPlayers);
        jo.put("currentPlayer", this.currentPlayer);
        jo.put("field", this.field.toJSON());
        jo.put("gameState", this.gameState);
        jo.put("day", this.day);

        // Serialize players
        JSONArray playersJSON = new JSONArray();
        for (Player player: players) {
            playersJSON.put(player.toJSON());
        }
        jo.put("players", playersJSON);

        // Serialize enemies
        JSONArray enemiesJSON = new JSONArray();
        for (Enemy enemy: enemies) {
            if (Game.gameObjects.contains(enemy)) {
                enemiesJSON.put(enemy.toJSON());
            }
        }
        jo.put("enemies", enemiesJSON);

        // Serialize treasures
        JSONArray treasuresJSON = new JSONArray();
        for (Treasure treasure: treasures) {
            if (Game.gameObjects.contains(treasure)) {
                treasuresJSON.put(treasure.toJSON());
            }
        }
        jo.put("treasures", treasuresJSON);


        return jo;
    }

    public static int getCurrentPlayer(JSONObject jsonObject) {
        return Integer.parseInt(jsonObject.get("currentPlayer").toString());
    }

    public void passTurn() {
        currentPlayer = (currentPlayer+1)%(totalPlayers);
    }
}
