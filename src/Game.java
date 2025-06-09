import java.awt.event.WindowEvent;
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
    static final boolean showSplashScreen = true;

    public static ArrayList<Integer> myHeroes;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static int playerID = -1;
    public static Player player;
//    static
    static final String url = "http://hoiu3.crtech.space";
    static UI ui; // General ui handler
    static UIWindow window;
    static WebClient cl;
    static SessionData gs;
    static Thread splashScreenThread;

    public static void main(String[] args) {
        setupLogger();

        if (showSplashScreen) {
            splashScreenThread = new Thread(new Runnable() {
                public void run() {
                    new HOIU3SplashScreen();
                }
            });
            splashScreenThread.start();
        }

        window = new UIWindow();
        ui = new UI(new JTextAreaOutputStream(window.getOutputTextField()), new JTextAreaOutputStream(window.getGameFieldTextArea()), System.in);//(System.out, System.in);
//        ui = new UI(System.out, System.in); // For debug only
        ui.setUIWindow(window);
        ui.showHelloMessage();

        if (showSplashScreen) {
            try {
                splashScreenThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        window.setVisible(true);
        collectInitialData();
        gameLoop();


    }

    static void gameLoop() {
        ui.drawField(gs.field);
        if (player.canMove()) {

        }

    }

    static void collectInitialData() {
        Logger.getLogger().tag("Game").logInfo("Collecting initial data");
        Logger.getLogger().tag("GAME DEBUG").logWeak("Collecting session name");


        // Get session name from user
        String sessionName;
        do {
             sessionName = ui.getLineInput("Enter the name of the game: ");
        } while (!WebClient.verifySession(url, sessionName) && ui.println("Incorrect session name! Try again?"));
        Logger.getLogger().tag("GAME DEBUG").logInfo("Session name obtained: " + sessionName);

        ui.println("Session found!");

        // Create web client for selected game session
        Logger.getLogger().tag("GAME DEBUG").logInfo("Creating web client");
        cl = new WebClient(url, sessionName);

        // Fetch game session
        Logger.getLogger().tag("Game").logInfo("Fetching game session");
        gs = cl.fetchGameState();
        Logger.getLogger().tag("Game").logSuccess("Got game session: " + gs.toDataString());
        ui.println("Got game session: " + gs.toDataString());

        // Get player id from user

        do {
            String input = "";
            try {
                input = ui.getLineInput("Enter player id(0-%d): ".formatted(gs.totalPlayers - 1));
                playerID = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                playerID = -1;
                Logger.getLogger().tag("Game").logError("Error parsing user id: " + input);
            }

        } while (!((playerID < gs.totalPlayers) && (playerID >= 0)) && ui.println(""));
        Logger.getLogger().tag("Game").logSuccess("Got player id: " + playerID);
        ui.println("Your player id: " + playerID);

        // Assign selected player to player
        player = gs.getPlayerById(playerID);
        if (player == null) {
            Logger.getLogger().tag("Game").logError("No player found with id: " + playerID);
        }

    }
//    public static JSONObject createSessionJSON() {
//        JSONObject obj = new JSONObject();
//
//    }

    static void setupLogger() {
        Logger.getLogger().setLogLevel(3);
        Logger.getLogger().logStackTrace = false;
        Logger.getLogger().logLogType = true;
        Logger.getLogger().logTag = true;
        Logger.getLogger().allowSelectedTags = false;
        Logger.getLogger().dropSelectedTags = true;
        Logger.getLogger().visibleTags.add("TestTag");

//        Logger.getLogger().disabledTags.add("INPUT");
        Logger.getLogger().disabledTags.add("INPUT DEBUG");
        Logger.getLogger().disabledTags.add("OUTPUT DEBUG");
    }


}