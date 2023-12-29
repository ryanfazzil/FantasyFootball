package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//tests for FantasyTeam class
public class FantasyTeamTest {
    FantasyTeam myTeam;
    Player herbert;
    Player brady;

    @BeforeEach
    public void setUp() {
        myTeam = new FantasyTeam("Among-ra");
        herbert = new Player("Justin Herbert");
        brady = new Player("Tom Brady");

    }

    @Test
    public void testAddOnePlayer() {
        myTeam.addPlayer(herbert);
        List<Player> players = myTeam.getPlayers();

        assertEquals(1, players.size());
        assertEquals(herbert, players.get(0));
    }

    @Test
    public void testAddSamePlayer() {
        myTeam.addPlayer(herbert);

        List<Player> players = myTeam.getPlayers();
        assertEquals(1, players.size());
        assertEquals(herbert, players.get(0));
    }

    @Test
    public void testAddMultiplePlayer() {
        myTeam.addPlayer(herbert);
        myTeam.addPlayer(brady);

        List<Player> players = myTeam.getPlayers();
        assertEquals(2, players.size());
        assertEquals(herbert, players.get(0));
        assertEquals(brady, players.get(1));
    }

    @Test
    public void testRemoveOnePlayer() {
        myTeam.addPlayer(herbert);
        myTeam.addPlayer(brady);
        myTeam.removePlayer(herbert);

        List<Player> players = myTeam.getPlayers();
        assertEquals(1, players.size());
        assertEquals(brady, players.get(0));
    }


    @Test
    public void testAddPlayerCheckTeamName() {
        assertEquals("Among-ra", myTeam.getTeamName());
    }

    @Test
    public void testDuplicate() {
        myTeam.addPlayer(brady);

        assertTrue(myTeam.checkDuplicate(brady));
        assertFalse(myTeam.checkDuplicate(herbert));
    }


}
