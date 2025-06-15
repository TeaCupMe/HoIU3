package ru.bmstu.hoiu3.ui;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class JTextFieldOutputStream extends OutputStream {
    private JTextField textField;

    public JTextFieldOutputStream(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void write(int b) throws IOException {
        SwingUtilities.invokeLater(() -> {
            textField.setText(textField.getText() + String.valueOf((char) b));
        });
    }
}