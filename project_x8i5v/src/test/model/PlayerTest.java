package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//test for player class
class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void runBefore() { testPlayer = new Player("Justin Herbert");}

    @Test
    void testConstructor() {
        assertEquals("Justin Herbert", testPlayer.getName());
    }

    @Test
    void testAddTouchdownOnce() {
        testPlayer.addTouchdowns(1);
        assertEquals(1, testPlayer.getTouchDowns());
    }

    @Test
    void testAddTouchdownMultiple() {
        testPlayer.addTouchdowns(1);
        testPlayer.addTouchdowns(3);
        testPlayer.addTouchdowns(0);

        assertEquals(4, testPlayer.getTouchDowns());
    }

    @Test
    void testAddGamesPlayed() {
        testPlayer.addGames(1);
        testPlayer.addGames(2);

        assertEquals(3, testPlayer.getGamesPlayed());
    }

    @Test
    void testToString() {
        assertEquals("Justin Herbert 0 0", testPlayer.toString());

    }
}