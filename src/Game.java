import java.io.IOException;
import java.io.PrintStream;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import org.apache.maven.surefire.shared.io.filefilter.TrueFileFilter;
import org.json.simple.JSONObject;

// Основной класс игры
public class Game {
    public static ArrayList<Integer> myHeroes;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    Player player;
//    static

    static UI ui; // General ui handler
    static UIWindow window = new UIWindow();

    public static void main(String[] args) {
        setupLogger();
        window.setVisible(true);
        ui = new UI(System.out, new JTextAreaOutputStream(window.getGameFieldTextArea()), System.in);//(System.out, System.in);

        WebClient cl = new WebClient("http://hoiu3.crtech.space", "HOIU3U");
        SessionData gs = cl.fetchGameState();
        ui.drawField(gs.field);

        window.getLineInput();
    }

    void collectInitialData() {

    }
//    public static JSONObject createSessionJSON() {
//        JSONObject obj = new JSONObject();
//
//    }

    static void setupLogger() {
        Logger.getLogger().setLogLevel(4);
        Logger.getLogger().logStackTrace = false;
        Logger.getLogger().logLogType = true;
        Logger.getLogger().logTag = true;
        Logger.getLogger().allowSelectedTags = false;
        Logger.getLogger().dropSelectedTags = true;
        Logger.getLogger().visibleTags.add("TestTag");

//        Logger.getLogger().disabledTags.add("INPUT");
        Logger.getLogger().disabledTags.add("INPUT DEBUG");

    }


}