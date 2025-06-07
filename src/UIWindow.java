import raven.emoji.AutoWrapText;
import raven.emoji.EmojiIcon;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UIWindow extends JFrame {
    public JTextPane textArea;

    public UIWindow() {
        // Настройка основного окна
        setTitle("HOIU3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        // Создание текстовой области
        textArea = new JTextPane();
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE); // Белый текст для контраста
        textArea.setFont(new Font("Noto Color Emoji", Font.PLAIN, 18)); // Noto Color Emoji; Segoe UI Emoji
        textArea.setEditable(false); // Только для отображения
        EmojiIcon.getInstance().installEmojiSvg();

//  create jtextpane with wrap text
        textArea.setEditorKit(new AutoWrapText(textArea));

// install this jtextpane to use emoji
        EmojiIcon.getInstance().installTextPane(textArea);
//        textArea.se


//        // Добавление в скролл-панель
//        JScrollPane scrollPane = new JScrollPane(textArea);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        add(scrollPane, BorderLayout.CENTER);
        add(textArea);
        // Автоматическая настройка размера окна
        pack();
        setLocationRelativeTo(null); // Центрирование окна
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
        textArea.setText("\uD83E\uDDD9" +
                "\uD83D\uDE00" +
                "\uD83C\uDFF0" +
                "\uD83D\uDC00" +
                "\uD83C\uDF46" +
                "\uD83D\uDC8E" +
                "\uD83C\uDF7B" +
                "\uD83C\uDF7A");
    }
}
