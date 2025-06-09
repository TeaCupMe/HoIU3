import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.JSONParser;
//import org.json.JSONObject;


import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebClient {
    String url;
    String sessionID;
    static HttpClient client = java.net.http.HttpClient.newHttpClient();
    static JSONParser jsonParser = new JSONParser();
    WebClient(String url, String sessionID) {
        this.sessionID = sessionID;
        this.url = url;
    }

    public boolean checkIfMyMove() {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url + "?id=%s".formatted(sessionID)))
                .GET()
                .build();
        HttpResponse<?> res;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println(res.body());
            return true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void uploadGameState() {

    }

    public void uploadString(String str) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "?id=%s".formatted(sessionID)))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .build();

        HttpResponse<?> res = null;
        try {
            res = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Logger.getLogger().tag("WebClient").logError("Error uploading game state: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public SessionData fetchGameState() {
        Logger.getLogger().tag("WebClient").logInfo("Fetching game state from " + url + "?id=%s".formatted(sessionID));
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url + "?id=%s".formatted(sessionID)))
                .GET()
                .build();
        HttpResponse<?> res = null;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
            try {
                return new SessionData((JSONObject) jsonParser.parse(res.body().toString()));
            } catch (ParseException e) {
                Logger.getLogger().tag("WebClient").logError("Could not parse JSON response: " + e.toString());
                throw new RuntimeException(e);
            }

        } catch (IOException | InterruptedException e) {
            Logger.getLogger().tag("WebClient").logError("Could not get response: " + e.toString());
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySession(String url, String sessionID) {
        Logger.getLogger().tag("WebClient").logInfo("Verifying session " + url + "?id=%s".formatted(sessionID));
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url + "?id=%s".formatted(sessionID)))
                .GET()
                .build();
        HttpResponse<?> res;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200) {
                Logger.getLogger().tag("WebClient").logSuccess("Session successfully verified");
            } else {
                Logger.getLogger().tag("WebClient").logError("Session verification failed");
            }
            return res.statusCode() == 200;
        } catch (IOException | InterruptedException e) {
            Logger.getLogger().tag("WebClient").logError("Could not get response: " + e.toString());
            throw new RuntimeException(e);
        }
    }

//    public static Integer[] getPlayersForSession(String sessionID) {
//
//    }
}
