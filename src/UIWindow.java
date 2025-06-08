import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIWindow extends JFrame {
    private JTextArea gameFieldTextArea;
    private JTextArea outputTextArea;

    public UIWindow() {

        // General window setup
        BufferedImage myAppImage = loadIcon("./resources/s1200.jpg");
        if(myAppImage != null)
        {
            Logger.getLogger().logSuccess("Icon loaded");
            setIconImage(myAppImage);
        }
        else {
            Logger.getLogger().logWarning("Icon not loaded");
        }
        setTitle("HOIU3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0,0));
        setUndecorated( true );
        getRootPane().setWindowDecorationStyle(JRootPane.ERROR_DIALOG);




        // Game field display area
        gameFieldTextArea = new JTextArea(19, 63);
        gameFieldTextArea.setBackground(Color.BLACK);
        gameFieldTextArea.setForeground(Color.decode("#39FF14"));
        gameFieldTextArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        gameFieldTextArea.setEditable(false);

        // Output display area
        outputTextArea = new JTextArea(10, 63);
        outputTextArea.setBackground(Color.BLACK);
        outputTextArea.setForeground(Color.decode("#39FF14"));
        outputTextArea.append("Test text");
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        outputTextArea.setEditable(false);


        // Add all components
        add(gameFieldTextArea, BorderLayout.PAGE_START);
        add(outputTextArea, BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Запуск в потоке обработки событий
        SwingUtilities.invokeLater(() -> {
            UIWindow window = new UIWindow();
            window.display();
            window.setVisible(true);

        });
    }
    void display() {
        gameFieldTextArea.setText("\uD83E\uDDD9" +
                "\uD83D\uDE00" +
                "\uD83C\uDFF0" +
                "\uD83D\uDC00" +
                "\uD83C\uDF46" +
                "\uD83D\uDC8E" +
                "\uD83C\uDF7B" +
                "\uD83C\uDF7A");
    }

    JTextArea getGameFieldTextArea() {
        return gameFieldTextArea;
    }
    JTextArea getOutputTextField() {
        return outputTextArea;
    }
    private BufferedImage loadIcon(String strPath)
    {
        Logger.getLogger().logInfo("Loading Icon: " + strPath);
        try {
            return ImageIO.read(new File(strPath));
        } catch (IOException e) {
            Logger.getLogger().logWarning("Failed to load icon: " + strPath);
            return null;
        }
    }
}
