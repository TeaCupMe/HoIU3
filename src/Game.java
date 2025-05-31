import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONObject;

// Основной класс игры
public class Game {
    static boolean LogStackTrace = true;
    public static void main(String[] args) {
        Hero hero = new Hero(3,3,"Hero1");
        UI ui = new UI(System.out, System.in);
        WebClient cl = new WebClient("http://hoiu3.crtech.space", "HOIU3");
//        cl.checkIfMyMove();
        // // // //cl.uploadString("{\"gameState\": 0,\"currentPlayer\": 1,\"totalPlayers\": 3,\"field\": {\"height\":40,\"width\":40,\"data\": \"00101024025291230242\"}}");
        SessionData gs = cl.fetchGameState();
//        gs.readField()
        gs.field.gameObjects.add(hero);
        ui.drawField(gs.field);
    }

//    public static JSONObject createSessionJSON() {
//        JSONObject obj = new JSONObject();
//
//    }
}