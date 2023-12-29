package persistence;

import model.FantasyTeam;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// reference  from JsonSerialization Demo

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            FantasyTeam ft = new FantasyTeam("My fantasy team");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFantasyTeam() {
        try {
            FantasyTeam ft = new FantasyTeam("My fantasy team");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFantasyTeam.json");
            writer.open();
            writer.write(ft);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFantasyTeam.json");
            ft = reader.read();
            assertEquals("My fantasy team", ft.getTeamName());
            assertEquals(0, ft.numPlayers());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralFantasyTeam() {
        try {
            FantasyTeam ft = new FantasyTeam("My fantasy team");
            ft.addPlayer(new Player("brady"));
            ft.addPlayer(new Player("herbert"));
            JsonWriter writer = new JsonWriter("./data/testWriterFantasyTeam.json");
            writer.open();
            writer.write(ft);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFantasyTeam.json");
            ft = reader.read();
            assertEquals("My fantasy team", ft.getTeamName());
            List<Player> thingies = ft.getPlayers();
            assertEquals(2, thingies.size());
            checkPlayer("brady", thingies.get(0));
            checkPlayer("herbert", thingies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
