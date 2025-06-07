package utils;
// Java Program to extract the list of Fonts
import java.awt.*;

// Driver class to check available Fonts in AWT
class GFG {
    // Main Function
    public static void main(String[] args)
    {
        System.out.println("To Know the available font family names");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        System.out.println("Getting the font family names");

        // Array of all the fonts available in AWT
        String fonts[] = ge.getAvailableFontFamilyNames();

        // Getting the font family names
        for (String i : fonts) {
            System.out.println(i + " ");
        }
    }
}