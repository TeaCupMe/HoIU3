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
            Logger.getLogger().tag("INPUT DEBUG").logInfo("Key pressed: " + e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                Logger.getLogger().tag("INPUT DEBUG").logWeak("Enter key pressed");
//                stringBuilder.append("\n");
                this.done = true;
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Logger.getLogger().tag("INPUT DEBUG").logWeak("Escape key pressed");
                stringBuilder.setLength(0);
                this.escaped = true;
                this.done = true;
            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                Logger.getLogger().tag("INPUT DEBUG").logWeak("Backspace key pressed");
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
            } else {
                char c = e.getKeyChar();
                if (Character.isLetter(c) || Character.isDigit(c)) {
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
                textArea.append("\n");
                textArea.removeKeyListener(this);
            }
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
        Logger.getLogger().tag("INPUT DEBUG").logWeak("New string: " + getCurrentString() + " to be set at: " + startOffset + " to " + textArea.getText().length());
        textArea.replaceRange(getCurrentString(), startOffset, textArea.getText().length());
    }

    public void reset() {
        done = false;
        escaped = false;
        stringBuilder.setLength(0);
    }
}
