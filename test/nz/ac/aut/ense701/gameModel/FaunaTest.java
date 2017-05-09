package nz.ac.aut.ense701.gameModel;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AS
 * @version 2011
 */
public class FaunaTest {
    Island island = new Island(5,5);
    Game game = new Game();
    
    public FaunaTest() {
    }

    /**
     * Test of getStringRepresentation method, of class Fauna.
     */
    @Test
    public void testGetStringRepresentation() {
        Position position = new Position(island, 4,4);
        Fauna instance = new Fauna(position, "Oystercatcher", " A nesting oystercatcher");
        String expResult = "F";
        String result = instance.getStringRepresentation();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of Fauna movement towards North.
     */
    @Test
    public void testFaunaMovementNorth(){
        Position position = new Position(island, 3,3);
        Fauna instance = new Fauna(position, "Oystercatcher", " A nesting oystercatcher");
        Boolean expResult = true;
        Boolean result = game.isFaunaMovePossible(instance, MoveDirection.NORTH);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of Fauna movement towards South.
     */
    @Test
    public void testFaunaMovementSouth(){
        Position position = new Position(island, 3,3);
        Fauna instance = new Fauna(position, "Oystercatcher", " A nesting oystercatcher");
        Boolean expResult = true;
        Boolean result = game.isFaunaMovePossible(instance, MoveDirection.SOUTH);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of Fauna movement towards West.
     */
    @Test
    public void testFaunaMovementWest(){
        Position position = new Position(island, 3,3);
        Fauna instance = new Fauna(position, "Oystercatcher", " A nesting oystercatcher");
        Boolean expResult = true;
        Boolean result = game.isFaunaMovePossible(instance, MoveDirection.WEST);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of Fauna movement towards East.
     */
    @Test
    public void testFaunaMovementEast(){
        Position position = new Position(island, 3,3);
        Fauna instance = new Fauna(position, "Oystercatcher", " A nesting oystercatcher");
        Boolean expResult = true;
        Boolean result = game.isFaunaMovePossible(instance, MoveDirection.EAST);
        assertEquals(expResult, result);
    }
}
