package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a player having a name, a team, touchdowns, games played
public class Player implements Writable {

    private String name;         // a players name
//    private String team;         // a players team
    private int touchDowns;      // a players touchdowns
    private int gamesPlayed;     // games played by player

    /*
    / REQUIRES: playerName has a non-zero length
    / EFFECTS: constructs a player, with a name,
      touchdowns and games played.
     */
    public Player(String playerName) {
        this.name = playerName;
        this.touchDowns = 0;
        this.gamesPlayed = 0;
    }

    public String getName() {
        return name;
    }

    public int getTouchDowns() {
        return touchDowns;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    //REQUIRES: touchdowns >= 0
    //MODIFIES: this
    //EFFECTS: add tds to players total touchDowns
    public int addTouchdowns(int tds) {
        touchDowns = touchDowns + tds;
        return touchDowns;
    }

    //REQUIRES: games >= 0
    //MODIFIES: this
    //EFFECTS: add games to gamesPlayed
    public int addGames(int games) {
        gamesPlayed = gamesPlayed + games;
        return gamesPlayed;
    }


    //// reference  from JsonSerialization Demo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("touchdowns", touchDowns);
        json.put("games played", gamesPlayed);
        return json;
    }

    @Override
    public String toString() {
        return name + " " + touchDowns + " " + gamesPlayed;
    }

}


