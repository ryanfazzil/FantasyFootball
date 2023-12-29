package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

//represents a fantasy with a name and a list of players
public class FantasyTeam implements Writable {
    private final String teamName;
    private final ArrayList<Player> players;

    public FantasyTeam(String team) {
        this.teamName = team;
        players = new ArrayList<>();
    }

    //REQUIRES: a player
    //EFFECTS: return true if player is already
    //         on the team
    public boolean checkDuplicate(Player p) {

        for (Player player : players) {
            if (p.getName() == player.getName()) {
                return true;
            }
        }
        return false;
    }

    //MODIFIES this
    //EFFECTS: add player to fantasy team
    public void addPlayer(Player p) {
        players.add(p);
    }

    //MODIFIES this
    //EFFECTS: remove player from fantasy team
    public void removePlayer(Player p) {
        players.remove(p);
    }

    public int numPlayers() {
        return players.size();
    }


    public String getTeamName() {
        return teamName;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


    // reference  from JsonSerialization Demo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", teamName);
        json.put("players", playersToJson());
        return json;
    }

    // EFFECTS: returns things in this fantasy team as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player t : players) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }

}
