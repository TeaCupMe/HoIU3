import java.awt.*;
import java.awt.event.*;

public class HOIU3SplashScreen extends Frame {// implements ActionListener {

    public HOIU3SplashScreen() {
        super("SplashScreen demo");
        if (!Game.showSplashScreen) {
            return;
        }

        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        for(int i=0; i<50; i++) {
            toFront();
//            renderSplashFrame(g, i);
            splash.update();
            try {
                Thread.sleep(90);
            }
            catch (InterruptedException e) {
            }
        }
        splash.close();
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }

    public static void main (String[] args) {
        HOIU3SplashScreen test = new HOIU3SplashScreen();
    }
}
