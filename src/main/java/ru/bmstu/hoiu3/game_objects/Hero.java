package ru.bmstu.hoiu3.game_objects;

import ru.bmstu.hoiu3.Game;
import ru.bmstu.hoiu3.core.Player;
import space.crtech.utils.Logger;

import java.awt.event.KeyEvent;

public class Hero extends GameObject {
    private String name;
    private int stamina;
    Army army;
    Player player;
    private boolean interacted;

    public boolean checkInteracted() {
        boolean toReturn = interacted;
        interacted = false;
        return toReturn;
    }

    public boolean isAlive() {
        return army.getCumulativeCount()>0;
    }

    public Hero(int x, int y, String name) {
        super(x, y, GameObjectType.GAME_OBJECT_TYPE_HERO);
        this.name = name;
        this.stamina = 0;
    }

    public Hero(Army army) {
        super(army.x, army.y, GameObjectType.GAME_OBJECT_TYPE_HERO);
        this.name = "Hero";
        this.stamina = 100;
        this.army = army;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }


//    boolean checkIfCanMove(int requiredStamina, int distance) {
//        if (requiredStamina>)
//    }
    @Override
    public String description() {
        return "Hero " + name + ", Stamina: " + stamina;
    }

    public String armyDescription() {
        return army.description() + ", power: " + army.getCumulativePower();
    }

    public Army getArmy() {
        return army;
    }

    // TODO uses field from Game class, change later...
    public String moveByKey(int keyCode) {
        Logger.getLogger().tag("Hero").logInfo("Moving by key " + keyCode);
        int newX = x;
        int newY = y;
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            newY = y - 1;
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            newX = x - 1;
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            newY = y + 1;
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            newX = x + 1;
        } else {
            return null;
        }
        if (!Game.getGameSession().field.isWalkable(newX, newY)) {
            return "Can't walk on " + Game.getGameSession().field.getTile(newX, newY).description();
        }
        GameObject occupant = GameObject.getByCoordinates(Game.gameObjects, newX, newY);
        int walkingPrice = Game.getGameSession().field.getWalkingPrice(x, y);
        if (stamina < walkingPrice) return "Can't move, not enough stamina";
        stamina -= walkingPrice;

        StringBuilder interactionResult = new StringBuilder();
        if (occupant != null) {
            interactionResult.append(occupant.interact(this));
            if (isAlive()) {
                interactionResult.append("\n");
            }
            interacted = true;
        }
//        if (!isAlive()) return false;
        this.x = newX;
        this.y = newY;
        if (isAlive()){
            interactionResult.append("Now on ")
                    .append(Game.getGameSession().field.getTile(newX, newY).description())
                    .append(". Stamina remaining: ")
                    .append(stamina);
        }
        return interactionResult.toString();
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return army.getCumulativePower();
    }

    public void receiveDamage(int damage) {
        Logger.getLogger().tag("Hero").logInfo("Receiving " + damage + " points of damage");
        this.army.receiveDamage(damage);
    }
}
