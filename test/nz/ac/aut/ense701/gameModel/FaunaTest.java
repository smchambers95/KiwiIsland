package nz.ac.aut.ense701.gameModel;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AS
 * @version 2011
 */
public class FaunaTest {
    Game game;
    Island island;
    
    public FaunaTest() {
        game = new Game();
        island = new Island(5,5);
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
     * Test that a Fauna instance can move North when valid to do so
     */
    @Test
    public void testFaunaMovementNorth(){
        Position position = new Position(island, 3,3);
        Fauna fauna = new Fauna(position, "Heron", "An active Heron");
        game.faunaMove(fauna, MoveDirection.NORTH);
        position.equals(fauna.position);
        assertFalse(fauna.position.getColumn() > position.getColumn());
    }
    
    /**
     * Test that a Fauna instance can move South when valid to do so
     */
    @Test
    public void testFaunaMovementSouth(){
        Position position = new Position(island, 3,3);
        Fauna fauna = new Fauna(position, "Heron", "An active Heron");
        game.faunaMove(fauna, MoveDirection.SOUTH);
        position.equals(fauna.position);
        assertFalse(fauna.position.getColumn() < position.getColumn());
    }
    
    /**
     * Test that a Fauna instance can move West when valid to do so
     */
    @Test
    public void testFaunaMovementWest(){
        Position position = new Position(island, 3,3);
        Fauna fauna = new Fauna(position, "Heron", "An active Heron");
        game.faunaMove(fauna, MoveDirection.WEST);
        position.equals(fauna.position);
        assertFalse(fauna.position.getColumn() > position.getColumn());
    }
    
    /**
     * Test that a Fauna instance can move East when valid to do so
     */
    @Test
    public void testFaunaMovementEast(){
        Position position = new Position(island, 3,3);
        Fauna fauna = new Fauna(position, "Heron", "An active Heron");
        game.faunaMove(fauna, MoveDirection.EAST);
        position.equals(fauna.position);
        assertFalse(fauna.position.getColumn() < position.getColumn());
    }
}
