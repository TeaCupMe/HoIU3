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
    HttpClient client;
    JSONParser jsonParser;
    WebClient(String url, String sessionID) {
        this.sessionID = sessionID;
        this.url = url;
        this.client = java.net.http.HttpClient.newHttpClient();
        this.jsonParser = new JSONParser();
    }

    public boolean checkIfMyMove() {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url + "?id=%s".formatted(sessionID)))
                .GET()
                .build();
        HttpResponse<?> res = null;
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
            e.printStackTrace();
        }
    }

    public SessionData fetchGameState() {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url + "?id=%s".formatted(sessionID)))
                .GET()
                .build();
        HttpResponse<?> res = null;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println(res.body());
            try {
                return new SessionData((JSONObject) jsonParser.parse(res.body().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
