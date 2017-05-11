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
        island = game.getIsland();
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
        island.addOccupant(position, fauna);
        game.faunaMove(fauna, MoveDirection.NORTH);
        assertFalse(fauna.position.getColumn() > position.getColumn());
    }
    
    /**
     * Test that a Fauna instance can move South when valid to do so
     */
    @Test
    public void testFaunaMovementSouth(){
        Position position = new Position(island, 3,3);
        Fauna fauna = new Fauna(position, "Heron", "An active Heron");
        island.addOccupant(position, fauna);
        game.faunaMove(fauna, MoveDirection.SOUTH);
        assertFalse(fauna.position.getColumn() < position.getColumn());
    }
    
    /**
     * Test that a Fauna instance can move West when valid to do so
     */
    @Test
    public void testFaunaMovementWest(){
        Position position = new Position(island, 3,3);
        Fauna fauna = new Fauna(position, "Heron", "An active Heron");
        island.addOccupant(position, fauna);
        game.faunaMove(fauna, MoveDirection.WEST);
        assertFalse(fauna.position.getColumn() > position.getColumn());
    }
    
    /**
     * Test that a Fauna instance can move East when valid to do so
     */
    @Test
    public void testFaunaMovementEast(){
        Position position = new Position(island, 3,3);
        Fauna fauna = new Fauna(position, "Heron", "An active Heron");
        island.addOccupant(position, fauna);
        game.faunaMove(fauna, MoveDirection.EAST);
        assertFalse(fauna.position.getColumn() < position.getColumn());
    }
    
    /**
     * This test will attempt to move a fauna onto the SAFE terrain (safe zone) it should not be able to move onto
     */
    @Test
    public void testFaunaInvalidMoveToSafeZone() {
        // Set the intended terrain for the fauna to move onto, into SAFE
        Position safePosition = new Position(island, 2,3);
        game.getIsland().setTerrain(safePosition, Terrain.SAFE);
        
        // Position and create the fauna
        Position currentPosition = new Position(island, 3,3);
        Fauna fauna = new Fauna(currentPosition, "Heron", "An active Heron");
        game.getIsland().addOccupant(currentPosition, fauna);
        
        // Attempt to move the fauna to the SAFE terrain
        game.faunaMove(fauna, MoveDirection.NORTH);
        
        // The fauna should not have moved position
        assertTrue(fauna.position.equals(currentPosition));
    }
    
    /**
     * This test will attempt to move a fauna into a land hazard, which it should not be able to move into
     */
    @Test
    public void testFaunaInvalidMoveToHazard() {
        // Add a hazard to the gridsquare we intend on moving the fauna to
        Position hazardPosition = new Position(island, 2,3);
        Occupant hazard = new Hazard(hazardPosition, "Deep hole", "A very deep hole", 100);
        game.getIsland().addOccupant(hazardPosition, hazard);
        
        // Position and create the fauna
        Position currentPosition = new Position(island, 3,3);
        Fauna fauna = new Fauna(currentPosition, "Heron", "An active Heron");
        game.getIsland().addOccupant(currentPosition, fauna);
        
        // Attempt to move the fauna into the hazard
        game.faunaMove(fauna, MoveDirection.NORTH);
        
        // The fauna should not have moved position
        assertTrue(fauna.position.equals(currentPosition));
    }
}
