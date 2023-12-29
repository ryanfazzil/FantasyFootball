package ui;

import model.FantasyTeam;
import model.Player;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

//Fantasy team application
public class FantasyApp {
    private static final String JSON_STORE = "./data/workroom.json";
    private FantasyTeam fantasyTeam;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    //EFFECTS: runs the fantasy application

    public FantasyApp() throws FileNotFoundException  {
        input = new Scanner(System.in);

        fantasyTeam = new FantasyTeam("my fantasy team");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runFantasy();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    public void runFantasy() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye! Your team is SICK!");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            doView();
        } else if (command.equals("a")) {
            doAddPlayer();
        } else if (command.equals("td")) {
            doAddTds();
        } else if (command.equals("vtd")) {
            doViewTotalTouchdowns();
        } else if (command.equals("s")) {
            saveFantasyTeam();
        } else if (command.equals("l")) {
            loadFantasyTeam();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: initialize fantasyTeam
    private void init() {
        fantasyTeam = new FantasyTeam("My Team");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //MODIFIES: this
    //EFFECTS: views your players on team
    private void doView() {
        FantasyTeam selected  = selectTeam();
        System.out.println("My Fantasy Team Players: ");

        //ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> players = selected.getPlayers();

        for (Player player : players) {
            System.out.println(player.getName());
        }
    }

    //MODIFIES: this
    //EFFECTS: adds player to team
    private void doAddPlayer() {
        FantasyTeam selected = selectTeam();
        System.out.println("Enter Player Name to add");
        String name = input.next();

        Player player = new Player(name);

        if (selected.checkDuplicate(player)) {
            System.out.println("Player is already on team!");
        } else {
            selected.addPlayer(player);
            System.out.println("Player has been added to your team!");
        }
    }

    //MODIFIES: this
    //EFFECTS: add touchdowns to a player
    private void doAddTds() {
        FantasyTeam selected = selectTeam();
        printTeam(selected);

        System.out.println("\nWhich Player to add Touchdowns? ");

        String name = input.next();
        ArrayList<Player> players = selected.getPlayers();

        for (Player player : players) {
            if (name.equals(player.getName())) {
                System.out.println("How many Touchdowns to add?");
                int tds = input.nextInt();
                player.addTouchdowns(tds);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: view total touchdowns for input player
    private void doViewTotalTouchdowns() {
        FantasyTeam selected = selectTeam();
        printTeam(selected);

        System.out.println("\nWhich player to view touchdowns?");

        String name = input.next();

        ArrayList<Player> players = selected.getPlayers();

        for (Player player : players) {
            if (name.equals(player.getName())) {
                System.out.println("Total Touchdowns = " + player.getTouchDowns());
            }
        }
    }

    // EFFECTS: prompts user to select their fantasy team
    private FantasyTeam selectTeam() {
        String selection = "";

        while (!(selection.equals("my"))) {
            System.out.println("my for my fantasy team");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("my")) {
            return fantasyTeam;
        } else {
            return null;
        }
    }



   /* private Player selectPlayer() {
        String selection = "";

        while (!(selection.equals("my"))) {
            System.out.println("my for my fantasy team");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("my")) {
            return fantasyTeam;
        } else {
            return null;
        }
    }*/

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view players");
        System.out.println("\ta -> add player to team");
        System.out.println("\ttd -> add touchdowns to player");
        System.out.println("\tvtd -> view total touchdowns of player");
        System.out.println("\ts -> save fantasy team to file");
        System.out.println("\tl -> load fantasy team from file");
        System.out.println("\tq -> quit");
    }

    //EFFECTS: prints players on fantasy team to console
    private void printTeam(FantasyTeam selected) {
        System.out.printf("Team: %s", selected.getPlayers().toString());
    }

    // EFFECTS: saves the FantasyTeam to file
    private void saveFantasyTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(fantasyTeam);
            jsonWriter.close();
            System.out.println("Saved " + fantasyTeam.getTeamName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads FantasyTeam from file
    private void loadFantasyTeam() {
        try {
            fantasyTeam = jsonReader.read();
            System.out.println("Loaded " + fantasyTeam.getTeamName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }



}
