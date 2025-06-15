package ru.bmstu.hoiu3.core;

public abstract class UserAction {
    String name;
    public UserAction(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    abstract public void act();
}
