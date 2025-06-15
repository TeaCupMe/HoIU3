package ru.bmstu.hoiu3;

import ru.bmstu.hoiu3.core.Player;
import ru.bmstu.hoiu3.core.SessionData;
import ru.bmstu.hoiu3.core.UserAction;
import ru.bmstu.hoiu3.core.WebClient;
import ru.bmstu.hoiu3.game_objects.GameObject;
import ru.bmstu.hoiu3.game_objects.Cursor;
import ru.bmstu.hoiu3.game_objects.Hero;
import ru.bmstu.hoiu3.ui.HOIU3SplashScreen;
import ru.bmstu.hoiu3.ui.JTextAreaOutputStream;
import ru.bmstu.hoiu3.ui.UI;
import ru.bmstu.hoiu3.ui.UIWindow;
import space.crtech.utils.Logger;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

// Основной класс игры
public class Game {
    public static final boolean showSplashScreen = true;
    static final int FIELD_REDRAW_TIMEOUT = 50;

    public static ArrayList<Integer> myHeroes;
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();
    public static int playerID = -1;
    public static Player player;
//    static
    static final String url = "http://hoiu3.crtech.space";
    public static UI ui; // General ui handler
    static UIWindow window;
    static WebClient cl;
    public static SessionData gs;
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

        // Thread for periodic field redraw
        Thread drawFieldThread = new Thread(() -> {
            while (true) {
                ui.redrawField(gs.field);
                try {
                    Thread.sleep(FIELD_REDRAW_TIMEOUT);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Show UIWindow
        window.setVisible(true);

        // Get session name and player id
        collectInitialData();

        // Start drawing field
        drawFieldThread.start();

        // Main game loop
        gameLoop();


    }

    static void gameLoop() {

        // Select action: select Hero, list Resources, end move
        try {
            while (true) {
                Logger.getLogger().tag("GameLoop").logInfo("Prompting user to select top-level action");
                ui.println("Select action:");
                ArrayList<UserAction> actions = new ArrayList<>();

                // Act on Hero
                actions.add(new UserAction("Act on hero") {
                    public void act() {
                        chooseHeroAction();
                    }
                });

                // Get info on Map
                actions.add(new UserAction("Show cursor") {
                    public void act() {
                        showCursorAction();
                    }
                });

                // List all available resources
                actions.add(new UserAction("Show available resources") {
                    public void act() {
                        showResourcesAction();
                    }
                });

                // End move
                actions.add(new UserAction("End turn") {
                    public void act() {
                        endTurnAction();
                    }
                });

                // End game
                actions.add(new UserAction("End game") {
                    public void act() {
                        endGameAction();
                    }
                });

                // Run action Selector
                runActionSelector(actions);

            }
        } catch (Exception e) {
            Logger.getLogger().tag("Game Loop").logError(e.getMessage());
            e.printStackTrace();
        }
    }

    // Universal method to get action from user
    static void runActionSelector(ArrayList<UserAction> actions) {
        int selectedAction = ui.getActionSelectorInput(actions);
        actions.get(selectedAction).act();
    }

    // Action to choose available hero and act on it
    static void chooseHeroAction() {
        Logger.getLogger().tag("HeroAction").logInfo("Prompting user to select hero");
        if (player.getHeroesCount() < 1) {
            ui.println("You have no heroes! Can't select hero.");
            return;
        }

        ui.println("Select hero:");
        ArrayList<UserAction> actions = new ArrayList<>();
        for (int i = 0; i < player.getHeroesCount(); i++) {
            int heroID = i;
            actions.add(new UserAction(player.getHero(heroID).description()) {
                public void act() {
                    actOnHeroAction(player.getHero(heroID));
                }
            });
        }
        runActionSelector(actions);
    }

    // Action to show cursor
    static void showCursorAction() {
        Logger.getLogger().tag("ShowCursorAction").logInfo("Showing cursor");

        cursor = new Cursor(player.getCursorX(), player.getCursorY());
        gameObjects.add(cursor);

        ui.enableCursor(true);
        ui.println("Cursor enabled. Press Enter to view info about tile or Escape to exit");

        ArrayList<KeyEvent> keyEvents = new ArrayList<>();
        AtomicInteger keyCode = new AtomicInteger(0);
        AtomicBoolean newInput = new AtomicBoolean(false);

        ui.startInteractiveInput(integer -> {
            keyCode.set(integer);
            newInput.set(true);
            return true;
        }, keyEvents);

        while (cursor.getCursorInput(keyCode, newInput, KeyEvent.VK_ESCAPE, KeyEvent.VK_ENTER)) {
            ui.println("Tile (%d; %d): %s".formatted(cursor.getX(), cursor.getY(), describeTileUnder(cursor)));
        }
            Logger.getLogger().tag("INTERACTIVE INPUT DEBUG").logInfo("Escaped from cursor action");

        gameObjects.remove(cursor);
        cursor = null;

        ui.endInteractiveInput();
        ui.enableCursor(false);
    }

    // Get string description of tile under GameObject
    static String describeTileUnder(GameObject occupant) {
        for (GameObject g : gameObjects) {
            if (g.intersects(occupant) && g.getClass()!=Cursor.class) {
                return g.description();
            }
        }
        return gs.field.fieldBuffer[occupant.getY()][occupant.getX()].description();
    }

    // Action to end turn and pass initiative to next player
    static void endTurnAction() { // TODO implement
        Logger.getLogger().tag("EndMoveAction").logInfo("Ending turn");
    }

    // Action to end game, upload current session and exit application
    static void endGameAction() { // TODO implement
        Logger.getLogger().tag("EndGameAction").logInfo("Ending game");
        endGame();
    }

    // Closing application
    static void endGame() {
        Logger.getLogger().tag("EndGame").logInfo("Ending game");
        System.exit(0);
    }

    // Action to show available resources
    static void showResourcesAction() { // TODO implement
        Logger.getLogger().tag("ShowResourcesAction").logInfo("Showing resources");
    }

    // Action to do something with a hero
    static void actOnHeroAction(Hero hero) {
        Logger.getLogger().tag("actOnHero").logInfo("Acting on hero");
        ArrayList<UserAction> actions = new ArrayList<>();
        if (hero.intersects(player.getCastle())) {
            // TODO implement buying stuff in castle
        }
        actions.add(new UserAction("Move") {
            @Override
            public void act() {
                moveHeroAction(hero);
            }
        });

        actions.add(new UserAction("Describe Army") {
            // TODO maybe move to separate method to comply with general UserAction logic
            @Override
            public void act() {
                ui.println("Hero army: " + hero.armyDescription());
            }
        });
        runActionSelector(actions);
    }

    // Action to move Hero



    static void moveHero(Hero hero) {
        ArrayList<KeyEvent> keyEvents = new ArrayList<>();
        AtomicInteger keyCode = new AtomicInteger(0);
        AtomicBoolean newInput = new AtomicBoolean(false);

        ui.startInteractiveInput(integer -> {
            keyCode.set(integer);
            newInput.set(true);
            return true;
        }, keyEvents);

        while (!(keyCode.get() == KeyEvent.VK_ESCAPE) && hero.isAlive()) {
            if (newInput.get()) {
                newInput.set(false);

                // TODO maybe just throw HeroMoveError?
                String moveResult = hero.moveByKey(keyCode.get());
                if (moveResult != null) {
                    ui.println(moveResult);
                    if (hero.checkInteracted()){
                        ui.println("Press ENTER to continue");
                        while (keyCode.get() != KeyEvent.VK_ENTER);
                    }
                }
//                Logger.getLogger().tag("Hero movement").logInfo("Pressed " + keyCode.get());
            }
        }

        if (!hero.isAlive()) {
            ui.println("Hero died!");
            gameObjects.remove(hero);
            player.removeHero(hero);
        }

        ui.endInteractiveInput();
//        gameObjects.remove(cursor);
//        cursor = null;
//        ui.enableCursor(false);
    }

    // Collect initial data from user
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
        ui.redrawFieldSlow(gs.field, 200);


        // Get player id from user
        do {
            String input = "";
            try {
                input = ui.getLineInput("Enter player id(0-%d): ".formatted(gs.getTotalPlayers() - 1));
                playerID = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                playerID = -1;
                Logger.getLogger().tag("Game").logError("Error parsing user id: " + input);
            }

        } while (!((playerID < gs.getTotalPlayers()) && (playerID >= 0)) && ui.println("Incorrect player id! Try again?"));
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

    // Utility method to set up useful properties of space.crtech.utils.Logger
    static void setupLogger() {
        Logger.getLogger().setLogLevel(3);
        Logger.getLogger().enableStacktraceLogging(false);
        Logger.getLogger().enableLogTypeLogging(true);
        Logger.getLogger().enableTagLogging(true);
        Logger.getLogger().enableTagsWhiteList(false);
        Logger.getLogger().enableTagsBlackList(true);
        Logger.getLogger().addTagToWhiteList("TestTag");

        Logger.getLogger().addTagToBlackList("INTERACTIVE KEY LISTENER");
        Logger.getLogger().addTagToBlackList("INPUT DEBUG");
        Logger.getLogger().addTagToBlackList("OUTPUT DEBUG");
    }


}