package ru.bmstu.hoiu3.ui;

import javax.swing.JTextArea;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JTextAreaOutputStream extends OutputStream {
    private final JTextArea textArea;
    private StringBuilder stringBuilder = new StringBuilder();

    private boolean isSecondByte = false;
    private byte firstByte = 0;
    public boolean useFourBytes;
    ArrayList<Byte> lineBytes = new ArrayList<>();

    public JTextAreaOutputStream(JTextArea textArea) {
//        super(nullOutputStream());
        this.textArea = textArea;

    }

    @Override
    public void write(int b) {


        lineBytes.add((byte) b);
        if (lineBytes.size() % 2 == 0) {
            char c = (char)(lineBytes.get(0)<<8 | lineBytes.get(1));
            if (Character.isHighSurrogate((char)c) && lineBytes.size() < 3) {
                return;
            }
            byte[] line = new byte[lineBytes.size()];
            for (int i = 0; i < lineBytes.size(); i++) {
                line[i] = lineBytes.get(i);
            }
            String text = new String(line, StandardCharsets.UTF_16);
            textArea.append(text);
            textArea.setCaretPosition(textArea.getDocument().getLength());
            lineBytes.clear();
        }

//        if (b == '\n') {
//            lineBytes.add((byte) b);
//            byte[] line = new byte[lineBytes.size()];
//            for (int i = 0; i < lineBytes.size(); i++) {
//                line[i] = lineBytes.get(i);
//            }
//
//            for (int i = 0; i<lineBytes.size(); i+=2) {
//                Logger.getLogger().tag("OUTPUT DEBUG 2").logInfo(Integer.toString(lineBytes.get(i)) + " " + lineBytes.get(i+1));
//            }
//
//            String text = new String(line, StandardCharsets.UTF_16);
//                textArea.append(text);
//                textArea.setCaretPosition(textArea.getDocument().getLength());
//                lineBytes.clear();
//
//        } else {
//            lineBytes.add((byte) b);
//        }
    }
}
