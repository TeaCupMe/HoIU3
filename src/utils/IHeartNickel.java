package utils;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class IHeartNickel {

    private JComponent ui = null;
    Vector<Font> fonts = new Vector<>();
    // heavy black heart in Unicode
    String heart = "\uD83E\uDDD9" +
            "\uD83D\uDE00" +
            "\uD83C\uDFF0" +
            "\uD83D\uDC00" +
            "\uD83C\uDF46" +
            "\uD83D\uDC8E" +
            "\uD83C\uDF7B" +
            "\uD83C\uDF7A";
            //new String(Character.toChars(10084));
    String msg = "I " + heart + " Nickel (%1s)";

    IHeartNickel() {
        initUI();
    }

    public final void initUI() {
        if (ui != null) {
            return;
        }

        ui = new JPanel(new BorderLayout(4, 4));
        ui.setBorder(new EmptyBorder(4, 4, 4, 4));
        String[] allFonts = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String f : allFonts) {
            Font font = new Font(f, Font.PLAIN, 1);
            if (font.canDisplayUpTo(msg) < 0) {
                fonts.add(new Font(f.toString(), Font.PLAIN, 20));

            }
        }
        try {
            fonts.add(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\alesh\\Downloads\\Noto_Color_Emoji\\NotoColorEmoji_WindowsCompatible.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        JList list = new JList(fonts);
        list.setVisibleRowCount(10);
        list.setCellRenderer(new HeartListCellRenderer());
        ui.add(new JScrollPane(list));
    }

    public JComponent getUI() {
        return ui;
    }

    public static void main(String[] args) {
        Runnable r = () -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception useDefault) {
            }
            IHeartNickel o = new IHeartNickel();

            JFrame f = new JFrame(o.getClass().getSimpleName());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationByPlatform(true);

            f.setContentPane(o.getUI());
            f.pack();
            f.setMinimumSize(f.getSize());

            f.setVisible(true);
        };
        SwingUtilities.invokeLater(r);
    }

    class HeartListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(
                JList<? extends Object> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            JLabel l = (JLabel)c;
            Font font = (Font) value;//new Font(value, Font.PLAIN, 20);
            l.setText(String.format(msg, font.getFontName()));
            l.setFont(font);

            return l;
        }
    }
}