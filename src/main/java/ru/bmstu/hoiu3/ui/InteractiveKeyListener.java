package ru.bmstu.hoiu3.ui;

import org.jetbrains.annotations.NotNull;
import space.crtech.utils.Logger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.function.Function;

public class InteractiveKeyListener implements KeyListener {
    //    String line = "";
    int keyCode;
    public boolean done = false;
    public boolean escaped = false;
    private ArrayList<KeyEvent> keyEvents;
    int startOffset = 0;
    Function<Integer, Boolean> listener;


    public InteractiveKeyListener() {
        this.keyEvents = new ArrayList<>();
    }

    public void setKeyEvents(ArrayList<KeyEvent> keyEvents) {
        this.keyEvents = keyEvents;
    }

    public void setListener(Function<Integer, Boolean> listener) {
        this.listener = listener;
    }

    public synchronized void keyPressed(@NotNull KeyEvent e) {
        Logger.getLogger().tag("INTERACTIVE KEY LISTENER").logInfo("keyPressed: " + e.getKeyCode());

//        if (keyEvents.contains(e)) { // TODO: Add filtration of events
//
//
//        }
        listener.apply(e.getKeyCode());


    }
    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }

}

