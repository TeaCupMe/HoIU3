package ru.bmstu.hoiu3.ui;

import space.crtech.utils.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

public class UIWindow extends JFrame {
    String fieldHandler = """ 
            \t\t                                                        \s
            \t\t                                                        \s
            \t\t                                                        \s
            \t\t     `7MMF'  `7MMF' .g8""8q. `7MMF'`7MMF'   `7MF'       \s
            \t\t       MM      MM .dP'    `YM. MM    MM       M         \s
            \t\t       MM      MM dM'      `MM MM    MM       M  pd""b. \s
            \t\t       MMmmmmmmMM MM        MM MM    MM       M (O)  `8b\s
            \t\t       MM      MM MM.      ,MP MM    MM       M      ,89\s
            \t\t       MM      MM `Mb.    ,dP' MM    YM.     ,M    ""Yb.\s
            \t\t     .JMML.  .JMML. `"bmmd"' .JMML.   `bmmmmd"'       88\s
            \t\t                                                (O)  .M'\s
            \t\t                                                 bmmmd' """;
    private final JTextArea gameFieldTextArea;
    private final JTextArea outputTextArea;

    private final InteractiveKeyListener interactiveKeyListener;
    private final SequentialKeyListener sequentialKeyListener;

    private String inputLine = "";

    public UIWindow() {

        // General window setup
        BufferedImage myAppImage = loadIcon("PonyKnightV2.jpg");
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
        setBackground(Color.black);
        setResizable(false);




        // Game field display area
        gameFieldTextArea = new JTextArea(19, 63);
        gameFieldTextArea.setBackground(Color.BLACK);
        gameFieldTextArea.setForeground(Color.decode("#39FF14"));
//        gameFieldTextArea.setForeground(Color.red);

        gameFieldTextArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        gameFieldTextArea.setEditable(false);
//        gameFieldTextArea.setBorder(BorderFactory.createLineBorder(Color.black, 10));
//        gameFieldTextArea.insert(fieldHandler, 0);
        gameFieldTextArea.getCaret().setVisible(false);

        // Output display area
        outputTextArea = new JTextArea(10, 63);
        outputTextArea.setBackground(Color.BLACK);
        outputTextArea.setForeground(Color.decode("#39FF14"));
//        outputTextArea.setForeground(Color.red);

        //        outputTextArea.append("Test text\n");
        outputTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        outputTextArea.setBorder(BorderFactory.createEmptyBorder());
        outputTextArea.setEditable(false);
        outputTextArea.getCaret().setVisible(false);


        // Init listeners
        sequentialKeyListener = new SequentialKeyListener(outputTextArea);
        interactiveKeyListener = new InteractiveKeyListener();

//        gameFieldTextArea.setPreferredSize(gameFieldTextArea.getSize());
//        outputTextArea.setPreferredSize(outputTextArea.getSize());
        JScrollPane jp = new JScrollPane(outputTextArea);
        jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        jp.setViewportBorder(BorderFactory.createLineBorder(Color.black));
        jp.setBorder(BorderFactory.createEmptyBorder());
        // Add all components

        add(jp, BorderLayout.PAGE_END);
        add(gameFieldTextArea, BorderLayout.PAGE_START);

        addWindowListener(new WindowListenerSetFocusOn(outputTextArea));

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

    public JTextArea getGameFieldTextArea() {
        return gameFieldTextArea;
    }
    public JTextArea getOutputTextField() {
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

    public void getInteractiveInput(Function<Integer, Boolean> listener, ArrayList<KeyEvent> keyEvents) {
        interactiveKeyListener.setKeyEvents(keyEvents);
        outputTextArea.addKeyListener(interactiveKeyListener);
        synchronized(interactiveKeyListener) {
            try {
                interactiveKeyListener.wait();
            } catch (InterruptedException e) {
                Logger.getLogger().logError("Interrupted while waiting for interactive key listener");
                throw new RuntimeException(e);
            }
        }
        removeKeyListener(interactiveKeyListener);
    }

    public void getInteractiveInput(ArrayList<KeyEvent> keyEvents) {
        interactiveKeyListener.setKeyEvents(keyEvents);
        outputTextArea.addKeyListener(interactiveKeyListener);
        synchronized(interactiveKeyListener) {
            try {
                interactiveKeyListener.wait();
            } catch (InterruptedException e) {
                Logger.getLogger().logError("Interrupted while waiting for interactive key listener");
                throw new RuntimeException(e);
            }
        }
        removeKeyListener(interactiveKeyListener);
    }

    public void startInteractiveInput(Function<Integer, Boolean> listener, ArrayList<KeyEvent> keyEvents) {
        interactiveKeyListener.setKeyEvents(keyEvents);
        interactiveKeyListener.setListener(listener);
        outputTextArea.addKeyListener(interactiveKeyListener);
//        synchronized(interactiveKeyListener) {
//            try {
//                interactiveKeyListener.wait();
//            } catch (InterruptedException e) {
//                Logger.getLogger().logError("Interrupted while waiting for interactive key listener");
//                throw new RuntimeException(e);
//            }
//        }
//        removeKeyListener(interactiveKeyListener);
    }
    public void endInteractiveInput() {
        outputTextArea.removeKeyListener(interactiveKeyListener);
    }


    public String getLineInput() {
        int inputStartOffset;
        sequentialKeyListener.reset();
        inputStartOffset = outputTextArea.getText().length();
        sequentialKeyListener.setStartOffset(inputStartOffset);
        outputTextArea.addKeyListener(sequentialKeyListener);
        synchronized(sequentialKeyListener) {
            try {
                sequentialKeyListener.wait();
            } catch (InterruptedException e) {
                Logger.getLogger().logError("Interrupted while waiting for sequential key listener");
                throw new RuntimeException(e);
            }
        }
        removeKeyListener(sequentialKeyListener);
        Logger.getLogger().tag("INPUT").logSuccess("Finished waiting for sequential key listener, result: " + sequentialKeyListener.getCurrentString());
        return sequentialKeyListener.getCurrentString();
    }

    public void enableField() {
        gameFieldTextArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
    }

    public void disableField() {
        gameFieldTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
    }

    public void showFieldSlow(String fieldString, int lineDelay) {
        enableField();
        String[] lines = fieldString.split("\n");
        gameFieldTextArea.setText("");
        for (String line : lines) {
            gameFieldTextArea.append(line + "\n");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Logger.getLogger().tag("Interrupted while printing field");
                throw new RuntimeException(e);
            }
        }
//        gameFieldTextArea.setText(fieldString);
    }

    public void showField(String fieldString) {
        enableField();
        gameFieldTextArea.setText(fieldString);
    }

    public void showHelloMessage() {
        disableField();
        gameFieldTextArea.setText(fieldHandler);
    }

    public void printTemporaryLine(String line) {
        int caretPosition = outputTextArea.getCaretPosition();
        outputTextArea.append(line);
        outputTextArea.setCaretPosition(caretPosition);
    }

    public void eraseTemporaryLine() {
        outputTextArea.replaceRange("", outputTextArea.getCaretPosition(), outputTextArea.getText().length());
    }

    public void debugFocus() {
        Logger.getLogger().tag("FOCUS DEBUG").logInfo("Output text area: " + outputTextArea.toString());
        Logger.getLogger().tag("FOCUS DEBUG").logInfo(getFocusOwner().toString());
    }
}
