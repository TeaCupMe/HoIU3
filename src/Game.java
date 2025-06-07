import java.io.IOException;
import java.io.PrintStream;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import org.json.simple.JSONObject;

// Основной класс игры
public class Game {
    public static ArrayList<Integer> myHeroes;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();

    static UI ui; // General ui handler
    static UIWindow window = new UIWindow();
    public static void main(String[] args) {
        Logger.getLogger().setLogLevel(2);
        window.setVisible(true);
        ui = new UI(System.out, new JTextAreaOutputStream(window.textArea), System.in);//(System.out, System.in);

        WebClient cl = new WebClient("http://hoiu3.crtech.space", "HOIU3U");
        SessionData gs = cl.fetchGameState();
        ui.drawField(gs.field);
    }

//    public static JSONObject createSessionJSON() {
//        JSONObject obj = new JSONObject();
//
//    }

}