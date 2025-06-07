import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JTextAreaOutputStream extends OutputStream {
    private final JTextArea textArea;
    private StringBuilder stringBuilder = new StringBuilder();

    ArrayList<Byte> lineBytes = new ArrayList<>();

    public JTextAreaOutputStream(JTextArea textArea) {
//        super(nullOutputStream());
        this.textArea = textArea;

    }

    @Override
    public void write(int b) {
//        System.out.println((byte) b);
        if (b == '\n') {
            lineBytes.add((byte) b);
            byte[] line = new byte[lineBytes.size()];
            for (int i = 0; i < lineBytes.size(); i++) {
                line[i] = lineBytes.get(i);
            }
            String text = new String(line, StandardCharsets.UTF_16);
//            SwingUtilities.invokeLater(() -> {
                textArea.append(text);
//                textArea.append("\n");
//                System.out.println(text);
                textArea.setCaretPosition(textArea.getDocument().getLength());
                lineBytes.clear();
//            });

        } else {
            lineBytes.add((byte) b);
        }
    }
}
