
package nz.ac.aut.ense701.gameModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AS
 * @version 2011
 */
public class PredatorTest {
    private Game game;
    private Predator rat;
    private Position position;
    private Island island;
    
    
    public PredatorTest() {
    }
    
    @Before
    public void setUp() {
        game = new Game();
        island = game.getIsland();
        position = new Position(island, 4,4);
        rat = new Predator(position, "Rat", "A norway rat");   
    }
    
    @After
    public void tearDown() {
        game = null;
        island = null;
        position = null;
        rat = null;   
    }

    /**
     * Test of getStringRepresentation method, of class Predator.
     */
    @Test
    public void testGetStringRepresentation() {
        String expResult = "P";
        String result = rat.getStringRepresentation();
        assertEquals(expResult, result);
    }
         
    /**
     * Test that a predator is dead after using the kill function
     */
    @Test
    public void testPredatorKillable() {
        // Create a predator
        Position predatorPosition = new Position(island, 3, 3);
        Predator predator = new Predator(predatorPosition, "Rat", "A norway rat");
        assertFalse("Should not be dead", predator.isDead());
        predator.kill();
        assertTrue("Should  be dead", predator.isDead());
    }
    
    /**
     * Test a predator moving onto a square occupied by a Kiwi, and check that the Kiwi is killed
     */
    @Test
    public void testPredatorMoveAndKillKiwi() {
        // Create and place a Kiwi on the island
        Position kiwiPosition = new Position(island, 2,3);
        Kiwi kiwi = new Kiwi(kiwiPosition, "Kiwi", "A little spotted kiwi"); 
        island.addOccupant(kiwiPosition, kiwi);
        
        // Create and place a predator an adjacent square to the Kiwi
        Position predatorPosition = new Position(island, 3, 3);
        Predator predator = new Predator(predatorPosition, "Rat", "A norway rat");
        island.addOccupant(predatorPosition, predator);
        game.faunaMove(predator, MoveDirection.NORTH);
        
        // Check that the Kiwi is now dead
        assertTrue(kiwi.isDead());
    }
    
    /**
     * Will test to ensure if a predator moves into a trap that the predator is killed
     */
    @Test
    public void testPredatorKilledByMovingIntoTrap() {
        // Create and place a Trap on the island
        Position trapPosition = new Position(island, 2,3);
        Tool trap = new Tool(trapPosition, "Trap", "A predator trap", 2.0);
        island.addOccupant(trapPosition, trap);
        
        // Create and place a predator an adjacent square to the Trap
        Position predatorPosition = new Position(island, 3, 3);
        Predator predator = new Predator(predatorPosition, "Rat", "A norway rat");
        island.addOccupant(predatorPosition, predator);
        game.faunaMove(predator, MoveDirection.NORTH);
        
        // Check that the predators is now dead
        assertTrue(predator.isDead());
    }
}
