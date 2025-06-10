import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowListenerSetFocusOn implements WindowListener {
    private JComponent component;

    @Override
    public void windowOpened(WindowEvent e) {
     component.requestFocusInWindow();
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    WindowListenerSetFocusOn(JComponent component) {
        this.component = component;
    }
}
