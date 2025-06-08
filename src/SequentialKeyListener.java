import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SequentialKeyListener implements KeyListener {
//    String line = "";
    StringBuilder stringBuilder = new StringBuilder();
    JTextArea textArea;
    public boolean done = false;
    public boolean escaped = false;

    int startOffset = 0;

    public SequentialKeyListener(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    public synchronized void keyPressed(@NotNull KeyEvent e) {
        if (!done) {
            Logger.getLogger().logWeak("Key pressed: " + e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Logger.getLogger().logWeak("Enter key pressed");
                this.done = true;
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Logger.getLogger().logWeak("Escape key pressed");
                stringBuilder.setLength(0);
                this.escaped = true;
                this.done = true;
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                Logger.getLogger().logWeak("Backspace key pressed");
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            } else {
                char c = e.getKeyChar();
                if (Character.isLetter(c)) {
                    if (e.isShiftDown()) {
                        stringBuilder.append(Character.toUpperCase(c));
                    } else {
                        stringBuilder.append(c);
                    }
                }
            }

            setNewString();
            if (escaped || done) {
                notify();
            }
            Logger.getLogger().logWeak("String: " + getCurrentString() + "; done: " + done + "; escaped: " + escaped);
        }


    }
    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }
    public String getCurrentString() {
        return stringBuilder.toString();
    }

    private void setNewString() {
        Logger.getLogger().logWeak("New string: " + getCurrentString() + " to be set at: " + startOffset + " to " + textArea.getText().length());
        textArea.replaceRange(getCurrentString(), startOffset, textArea.getText().length());
    }
}
