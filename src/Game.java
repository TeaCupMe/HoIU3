import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
    static Cursor cursor;

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

        Thread drawFieldThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    ui.drawField(gs.field);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        window.setVisible(true);
        collectInitialData();
//        gameObjects.add(new GameObject(4, 4, GameObjectType.GAME_OBJECT_TYPE_CURSOR));
        drawFieldThread.start();
        gameLoop();


    }

    static void gameLoop() {
//        ui.println("\u001b[31;1;4mHello\u001b[0m");
        // Select action: select Hero, list Resources, end move
        try {
            while (true) {
                Logger.getLogger().tag("GameLoop").logInfo("Prompting user to select top-level action");
                ui.println("Select action:");
                ArrayList<UserAction> actions = new ArrayList<>();

                // Act on Hero
                actions.add(new UserAction("Act on hero") {
                    public void act() {
                        heroAction();
                    }
                });

                // Get info on Map
                actions.add(new UserAction("Act on hero") {
                    public void act() {
                        heroAction();
                    }
                });

                // List all available resources
                actions.add(new UserAction("Show available resources") {
                    public void act() {
                        showResourcesAction();
                    }
                });

                // End move
                actions.add(new UserAction("End move") {
                    public void act() {
                        endMoveAction();
                    }
                });

                // End game
                actions.add(new UserAction("End game") {
                    public void act() {
                        endGame();
                    }
                });

                // Run action Selector
                runActionSelector(actions);

            }
        } catch (Exception e) {
            Logger.getLogger().tag("Game Loop").logError(e.getMessage());
        }
    }

    static void runActionSelector(ArrayList<UserAction> actions) {
        int selectedAction = ui.getActionSelectorInput(actions);
        actions.get(selectedAction).act();
    }

    static void heroAction() {
        Logger.getLogger().tag("HeroAction").logInfo("Prompting user to select hero");
    }

    static void endMoveAction() {
        Logger.getLogger().tag("EndMoveAction").logInfo("Ending move");
    }

    static void endGameAction() {
        Logger.getLogger().tag("EndGameAction").logInfo("Ending game");
    }

    static void endGame() {
        Logger.getLogger().tag("EndGame").logInfo("Ending game");
        System.exit(0);
    }

    static void showResourcesAction() {
        Logger.getLogger().tag("ShowResourcesAction").logInfo("Showing resources");
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
        Logger.getLogger().tag("Game").logSuccess("Loaded game session: " + gs.toDataString());
        ui.println("Loaded game session: " + gs.toDataString());

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
        Logger.getLogger().disabledTags.add("GAME DEBUG");
    }


}