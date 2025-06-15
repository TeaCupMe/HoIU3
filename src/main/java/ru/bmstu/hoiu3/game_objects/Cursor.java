package ru.bmstu.hoiu3.game_objects;

import ru.bmstu.hoiu3.Game;
import ru.bmstu.hoiu3.ui.Direction;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Cursor extends GameObject {

    public AtomicBoolean hasMoved = new AtomicBoolean(false);
    public Cursor(int x, int y) {
        super(x, y, GameObjectType.GAME_OBJECT_TYPE_CURSOR);
    }
    public void move(Direction direction) {
        switch (direction) {
            case DIRECTION_UP -> moveUp();
            case DIRECTION_DOWN -> moveDown();
            case DIRECTION_LEFT -> moveLeft();
            case DIRECTION_RIGHT -> moveRight();
        }
    }

    public void moveByKey(int keyCode) {

    }

    /**
     *
     * @return true i
     */
    public boolean getCursorInput(AtomicInteger keyCode,
                                  AtomicBoolean inputAvailable,
                                  int escapeCode, int proceedCode) {
        while (keyCode.get() != escapeCode) {
            if (inputAvailable.get()) {
                inputAvailable.set(false);
                int code = keyCode.get();
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    move(Direction.DIRECTION_UP);
                } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    move(Direction.DIRECTION_DOWN);
                } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                    move(Direction.DIRECTION_RIGHT);
                } else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                    move(Direction.DIRECTION_LEFT);
                } else if (code == proceedCode) {
                    return true;
                }
                hasMoved.set(true);
//                Logger.getLogger().tag("THREAD DEBUG").logInfo(Thread.currentThread().getName());
//                Thread.currentThread().notify();
            }
        }
        return false;
    }

    private void moveUp() {
        if (canMoveTo(x, y - 1)) {
            this.y -= 1;
        }
    }
    private void moveRight() {
        if (canMoveTo(x + 1, y)) {
            this.x += 1;
        }
    }
    private void moveLeft() {
        if (canMoveTo(x - 1, y)) {
            this.x -= 1;
        }
    }
    private void moveDown() {
        if (canMoveTo(x, y + 1)) {
            this.y += 1;
        }
    }

    private boolean canMoveTo(int x, int y) {
        int fieldWidth = Game.gs.field.getWidth();
        int fieldHeight = Game.gs.field.getHeight();
        return x >= 0 && x < fieldWidth && y >= 0 && y < fieldHeight;
    }

}
