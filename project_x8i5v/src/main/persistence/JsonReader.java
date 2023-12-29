package persistence;

import model.Player;
import model.FantasyTeam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// reference  from JsonSerialization Demo
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads fantasy from file and returns it;
    // throws IOException if an error occurs reading data from file
    public FantasyTeam read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFantasyTeam(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses fantasyTeam from JSON object and returns it
    private FantasyTeam parseFantasyTeam(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        FantasyTeam ft = new FantasyTeam(name);
        addPlayers(ft, jsonObject);
        return ft;
    }

    // MODIFIES: ft
    // EFFECTS: parses players from JSON object and adds them to fantasyTeam
    private void addPlayers(FantasyTeam ft, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(ft, nextPlayer);
        }
    }

    // MODIFIES: ft
    // EFFECTS: parses player from JSON object and adds it to fantasyTeam
    private void addPlayer(FantasyTeam ft, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int td = jsonObject.getInt("touchdowns");
        Player player = new Player(name);
        player.addTouchdowns(td);
        ft.addPlayer(player);
    }
}
