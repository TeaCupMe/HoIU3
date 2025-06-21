package ru.bmstu.hoiu3.core;

import org.json.JSONException;
import org.json.JSONObject;
//import org.json.parser.JSONParser;
import org.json.HTTP;
import space.crtech.utils.Logger;
//import org.json.JSONObject;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//import java.net.ssl.

public class WebClient {
    String url;
    String sessionID;
    static HttpClient client = HttpClient.newHttpClient();
    static HTTP jsonParser = new HTTP();
//    JSONObject jo = new
    public WebClient(String url, String sessionID) {
        this.sessionID = sessionID;
        this.url = url;
    }

    public boolean checkIfMyMove(int myId) {
        Logger.getLogger().tag("WebClient").logInfo("Fetching game state from " + url + sessionID);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/v1/session/" + sessionID))
                .GET()
                .build();
        HttpResponse<?> res = null;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
//            Logger.getLogger().logInfo(res.body().toString());
            try {
                Logger.getLogger().logInfo(res.body().toString());
                return myId == SessionData.getCurrentPlayer(new JSONObject(res.body().toString()));
            } catch (JSONException e) {
                Logger.getLogger().tag("Web Client").logError("Unable to parse json");
                throw new RuntimeException(e);
            }

        } catch (IOException | InterruptedException e) {
            Logger.getLogger().tag("WebClient").logError("Could not get response: " + e.toString());
            throw new RuntimeException(e);
        }
    }


    public void uploadString(String str) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/v1/session/" + sessionID))
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
        Logger.getLogger().tag("WebClient").logInfo("Fetching game state from " + url + sessionID);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/v1/session/" + sessionID))
                .GET()
                .build();
        HttpResponse<?> res = null;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
//            Logger.getLogger().logInfo(res.body().toString());
            try {
                Logger.getLogger().logInfo(res.body().toString());
                return new SessionData(new JSONObject(res.body().toString()));
            } catch (JSONException e) {
                Logger.getLogger().tag("Web Client").logError("Unable to parse json");
                throw new RuntimeException(e);
            }

        } catch (IOException | InterruptedException e) {
            Logger.getLogger().tag("WebClient").logError("Could not get response: " + e.toString());
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySession(String url, String sessionID) {
        Logger.getLogger().tag("WebClient").logInfo("Verifying session " + url + "/api/v1/session/" + sessionID);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/v1/session/" + sessionID))
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

    public JSONObject fetchSession() {
        Logger.getLogger().tag("WebClient").logInfo("Fetching game state from " + url + sessionID);
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/v1/session/" + sessionID))
                .GET()
                .build();
        HttpResponse<?> res = null;
        try {
            res = client.send(req, HttpResponse.BodyHandlers.ofString());
            try {
                return new JSONObject(res.body().toString());
            } catch (JSONException e) {
                Logger.getLogger().tag("Web Client").logError("Unable to parse json");
                throw new RuntimeException(e);
            }

        } catch (IOException | InterruptedException e) {
            Logger.getLogger().tag("WebClient").logError("Could not get response: " + e.toString());
            throw new RuntimeException(e);
        }
    }

    public void uploadSession(JSONObject jsonObject) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/api/v1/session/" + sessionID))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString(4)))
                .build();

        HttpResponse<?> res = null;
        try {
            res = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Logger.getLogger().tag("WebClient").logError("Error uploading game state: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        WebClient webClient = new WebClient("https://hoiu3u.crtech.space/api/v1/session/", "HOIU3U_ideal");
//        verifySession("https://hoiu3u.crtech.space/api/v1/session/", "HOIU3U");
//        Logger.getLogger().logInfo(Integer.toString(webClient.fetchGameState().getTotalPlayers()));
        JSONObject testSession = webClient.fetchSession();
//        webClient.sessionID = "HOIU3U"
        webClient.uploadSession(testSession);
    }
//    public static Integer[] getPlayersForSession(String sessionID) {
//
//    }
}
