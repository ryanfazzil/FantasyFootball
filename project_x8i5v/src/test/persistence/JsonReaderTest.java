package persistence;

import model.FantasyTeam;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// reference  from JsonSerialization Demo

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            FantasyTeam ft = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFantasyTeam() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFantasyTeam.json");
        try {
            FantasyTeam ft = reader.read();
            assertEquals("My fantasy team", ft.getTeamName());
            assertEquals(0, ft.numPlayers());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFantasyTeam() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralFantasyTeam.json");
        try {
            FantasyTeam ft = reader.read();
            assertEquals("My fantasy team", ft.getTeamName());
            List<Player> players = ft.getPlayers();
            assertEquals(2, players.size());
            checkPlayer("brady", players.get(0));
            checkPlayer("herbert", players.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
