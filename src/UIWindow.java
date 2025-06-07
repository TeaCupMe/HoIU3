import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UIWindow extends JFrame {
    public JTextArea textArea;

    public UIWindow() {
        // Настройка основного окна
        setTitle("HOIU3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        // Создание текстовой области
        textArea = new JTextArea(19, 63);
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE); // Белый текст для контраста
        textArea.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        textArea.setEditable(false); // Только для отображения
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
