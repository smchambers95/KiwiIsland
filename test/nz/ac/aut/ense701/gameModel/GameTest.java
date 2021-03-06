package nz.ac.aut.ense701.gameModel;

import org.junit.Test;

/**
 * The test class GameTest.
 *
 * @author  AS
 * @version S2 2011
 */
public class GameTest extends junit.framework.TestCase
{
    Game       game;
    Player     player;
    Position   playerPosition;
    Island island ;
    
    /**
     * Default constructor for test class GameTest
     */
    public GameTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Override
    protected void setUp()
    {
        // Create a new game from the data file.
        // Player is in position 2,0 & has 100 units of stamina
        game           = new Game();
        //So AId oesnt move around in the testing. 
        game.toggleAIPaused();
        playerPosition = game.getPlayer().getPosition();
        player         = game.getPlayer();
        island = game.getIsland();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @Override
    protected void tearDown()
    {
        game = null;
        player = null;
        playerPosition = null;
    }

    /*********************************************************************************************************
     * Game under test
      
---------------------------------------------------
|    |    |@   | F  | T  |    |    | PK |    |    |
|~~~~|~~~~|....|....|....|~~~~|^^^^|^^^^|^^^^|^^^^|
---------------------------------------------------
|    |    |    |    | H  |    |    |    |    |    |
|~~~~|####|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|^^^^|
---------------------------------------------------
|    |    | H  |    | E  |    | P  |    | K  |    |
|####|####|####|####|^^^^|^^^^|^^^^|^^^^|^^^^|~~~~|
---------------------------------------------------
| T  |    |    |    | P  | H  |    |    |    |    |
|....|####|####|####|****|****|^^^^|....|~~~~|~~~~|
---------------------------------------------------
| F  | P  |    |    |    |    | F  |    |    |    |
|....|^^^^|^^^^|^^^^|****|****|^^^^|....|~~~~|~~~~|
---------------------------------------------------
| H  |    | P  | T  | E  |    |    |    |    |    |
|....|****|****|****|****|****|####|####|####|~~~~|
---------------------------------------------------
|    |    | K  |    | P  | H  | K  | E  | F  |    |
|....|****|****|****|****|****|****|####|####|####|
---------------------------------------------------
| K  |    |    | F  | H  |    | H  | K  | T  |    |
|****|****|****|****|****|~~~~|****|****|~~~~|~~~~|
---------------------------------------------------
|    |    | E  | K  |    |    |    |    | F  |    |
|....|....|****|****|~~~~|~~~~|~~~~|****|****|....|
---------------------------------------------------
|    |    |    | K  | K  |    | K  | P  |    |    |
|~~~~|....|****|****|****|~~~~|****|****|****|....|
---------------------------------------------------
 *********************************************************************************************************/
    /**
     * Tests for Accessor methods of Game, excluding those which are wrappers for accessors in other classes.
     * Other class accessors are tested in their test classes.
     */
    
    @Test
    public void testGetNumRows(){
        assertEquals("Check row number", game.getNumRows(), 10);
    }
    
    @Test
    public void testGetNumColumns(){
        assertEquals("Check column number", game.getNumRows(), 10);
    }
    
    @Test
    public void testGetPlayer(){
        String name = player.getName();
        String checkName = "Player";
        assertTrue("Check player name", name.equals(checkName) );
    } 

    @Test
    public void testGetInitialState(){
        assertEquals("Wrong initial state", game.getState(), GameState.PLAYING);
    }
    
    @Test
    public void testGetPlayerValues(){
        int[] values = game.getPlayerValues(); 
        assertEquals("Check max stamina.", values[Game.MAXSTAMINA_INDEX], 100);
        assertEquals("Check max backpack weight.", values[Game.MAXWEIGHT_INDEX], 10);
        assertEquals("Check initialstamina", values[Game.STAMINA_INDEX], 100);
        assertEquals("Check initial backpack weight.", values[Game.WEIGHT_INDEX], 0);
        assertEquals("Check initial backp[ack size.", values[Game.SIZE_INDEX], 0);
    }
    
    @Test
    public void testIsPlayerMovePossibleValidMove(){
        //At start of game player has valid moves EAST, West & South
        assertTrue("Move should be valid", game.isPlayerMovePossible(MoveDirection.SOUTH));
    }
    
    @Test
    public void testIsPlayerMovePossibleInvalidMove(){
        //At start of game player has valid moves EAST, West & South
        assertFalse("Move should not be valid", game.isPlayerMovePossible(MoveDirection.NORTH));
    }
    
    @Test
    public void testCanCollectCollectable(){
        //Items that are collectable and fit in backpack
        Item valid = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0);
        assertTrue("Should be able to collect", game.canCollect(valid));
    }
    
    @Test
    public void testCanUseFoodValid(){
        //Food can always be used
        Item valid = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0);
        assertTrue("Should be able to use", game.canUse(valid));
    }
    
    @Test
    public void testCanUseTrapNoPredator(){
        //Trap can be used if there is a predator here
        Item tool = new Tool(playerPosition,"Trap", "A predator trap",1.0);

        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseTool(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new Tool(playerPosition,"Screwdriver", "A good tool to fix a trap",1.0);
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap",1.0);
        trap.setBroken();
        player.collect(trap);

        assertTrue("Should be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseToolNoTrap(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new Tool(playerPosition,"Screwdriver", "A good tool to fix a trap",1.0);
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap",1.0);
        trap.setBroken();

        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
    @Test
    public void testCanUseToolTrapNotBroken(){
        //Screwdriver can be used if player has a broken trap
        Item tool = new Tool(playerPosition,"Screwdriver", "A good tool to fix a trap",1.0);
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap",1.0);
        player.collect(trap);

        assertFalse("Should not be able to use", game.canUse(tool));
    }
    
    @Test
    public void testGetKiwiCountInitial()
    {
       assertEquals("Shouldn't have counted any kiwis yet",game.getSavedKiwisCount(),0); 
    }
    /**
     * Test for mutator methods
     */
    
    @Test
    public void testCollectValid(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0, 1.0);
        island.addOccupant(playerPosition, food);
        assertTrue("Food now on island", island.hasOccupant(playerPosition, food));
        game.collectItem(food);
        
        assertTrue("Player should have food",player.hasItem(food));
        assertFalse("Food should no longer be on island", island.hasOccupant(playerPosition, food));
    }
    
    @Test
    public void testCollectNotCollectable(){
        Item notCollectable = new Food(playerPosition,"House", "Can't collect",-1.0, 1.0);
        island.addOccupant(playerPosition, notCollectable);
        assertTrue("House now on island", island.hasOccupant(playerPosition, notCollectable));
        game.collectItem(notCollectable);
        
        assertFalse("Player should not have house",player.hasItem(notCollectable));
        assertTrue("House should be on island", island.hasOccupant(playerPosition, notCollectable));
    }
    
    @Test    
    public void testDropValid(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0,1.0);
        island.addOccupant(playerPosition, food);
        game.collectItem(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        game.dropItem(food);
        assertFalse("Player should no longer have food",player.hasItem(food));
        assertTrue("Food should be on island", island.hasOccupant(playerPosition, food));
    }
    
    @Test
    public void testUseItemFoodCausesIncrease(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0,1.3);
        player.collect(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        // Will only get a stamina increase if player has less than max stamina
        player.reduceStamina(5.0);
        game.useItem(food);
        assertFalse("Player should no longer have food",player.hasItem(food));
        assertTrue("Player should have more than 95% stamina after eating food", player.getStaminaLevel() > 95);
    }
 
    public void testUseItemFoodNoIncrease(){
        Item food = new Food(playerPosition,"Sandwich", "Yummy",1.0,1.3);
        player.collect(food);
        assertTrue("Player should have food",player.hasItem(food));
        
        // Will only get a stamina increase if player has less than max stamina
        game.useItem(food);
        assertFalse("Player should no longer have food",player.hasItem(food));
        assertEquals("Wrong stamina level", player.getStaminaLevel(), 100.0);
    }  
    
    @Test
    public void testDropTrapOnPredator(){
        Item trap = new Tool(playerPosition,"Trap", "Rat trap",1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        
        // Can only use trap if there is a predator.
        Predator predator = new Predator(playerPosition,"Rat", "Norway rat");
        island.addOccupant(playerPosition, predator);
        game.dropItem(trap);
        
        assertFalse("Trap should be on ground",player.hasItem(trap));
        assertFalse("Predator should be gone.", island.hasPredator(playerPosition));
    }
    
        @Test
    public void testDropTrapOnKiwi(){
        Item trap = new Tool(playerPosition,"Trap", "Rat trap",1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        
        // Can only use trap if there is a predator.
        Kiwi kiwi = new Kiwi(playerPosition,"Kiwi", "Kiwi");
        island.addOccupant(playerPosition, kiwi);
        game.dropItem(trap);       
        assertFalse("Trap should be on ground",player.hasItem(trap));
        assertFalse("Kiwi should be gone.", island.hasKiwi(playerPosition));
    }
    
    @Test
    public void testUseItemBrokenTrap(){
        Tool trap = new Tool(playerPosition,"Trap", "Rat trap",1.0);
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        
        // Can only use trap if there is a predator.
        Predator predator = new Predator(playerPosition,"Rat", "Norway rat");
        island.addOccupant(playerPosition, predator);
        trap.setBroken();
        game.dropItem(trap);
        
        assertFalse("Trapped should be on ground",player.hasItem(trap));
        assertTrue("Predator should still be there as trap broken.", island.hasPredator(playerPosition));
    }
    
    @Test
    public void testUseItemToolFixTrap(){
        Tool trap = new Tool(playerPosition,"Trap", "Rat trap",1.0);
        trap.setBroken();
        player.collect(trap);
        assertTrue("Player should have trap",player.hasItem(trap));
        Tool screwdriver = new Tool(playerPosition,"Screwdriver", "Useful screwdriver",1.0);
        player.collect(screwdriver);
        assertTrue("Player should have screwdriver",player.hasItem(screwdriver));
        
        game.useItem(screwdriver);
        assertFalse("Trap should be fixed", trap.isBroken());
    }
   
    @Test
    public void testPlayerMoveToInvalidPosition(){
        //A move NORTH would be invalid from player's start position
        assertFalse("Move not valid", game.playerMove(MoveDirection.NORTH));
    }
 
    @Test
    public void testPlayerMoveValidNoHazards(){
        double stamina = player.getStaminaLevel();  

        assertTrue("Move valid", game.playerMove(MoveDirection.SOUTH));
        Position newPos = game.getPlayer().getPosition();
        assertEquals("Wrong position", newPos.getRow(), 1);
        assertFalse("Player should not be here", game.hasPlayer(playerPosition.getRow(), playerPosition.getColumn()));
    }
    
    @Test
    public void testPlayerMoveFatalHazard(){ 
        Position hazardPosition = new Position(island, playerPosition.getRow()+1, playerPosition.getColumn());
        Hazard fatal = new Hazard(hazardPosition, "Cliff", "Steep cliff", 1.0);
        island.addOccupant(hazardPosition, fatal);
        
        assertTrue("Move valid", game.playerMove(MoveDirection.SOUTH));
        //Force update so we dont have to wait for game to refresh
        game.updateGameState();
        //Fatal Hazard should kill player
        assertTrue("Player should be dead.", !player.isAlive());
        assertTrue("Game should be over", game.getState()== GameState.LOST);
    }
    
    @Test
    public void testPlayerMoveDeadPlayer(){
        player.kill();
        assertFalse(game.playerMove(MoveDirection.SOUTH));
    }
    
    @Test
    public void testPlayerMoveNonFatalHazardNotDead(){
        double stamina = player.getStaminaLevel(); 
        Position hazardPosition = new Position(island, playerPosition.getRow()+1, playerPosition.getColumn());
        Hazard fatal = new Hazard(hazardPosition, "Cliff", "Not so steep cliff", 0.5);
        island.addOccupant(hazardPosition, fatal);
        
        assertTrue("Move valid", game.playerMove(MoveDirection.SOUTH));
        //Non-fatal Hazard should reduce player stamina
        assertTrue("Player should be alive.", player.isAlive());
        assertTrue("Game should not be over", game.getState()== GameState.PLAYING);
    }
    
    @Test
    public void testPlayerMoveNotEnoughStamina(){
        // Reduce player's stamina to less than is needed for the most challenging move
        //Most challenging move is WEST as Terrain is water
        player.reduceStamina(99.0);
        assertFalse("Player should not have required stamina", game.playerMove(MoveDirection.WEST));
    }
    
    @Test
    public void testSavedKiwi()
    {
        // Pickup a Kiwi
        Kiwi kiwi = new Kiwi(playerPosition, "Kiwi", "Kiwi");
        island.addOccupant(playerPosition, kiwi);
        game.collectItem(kiwi);       
        
        // Drop the Kiwi in the safezone
        game.dropItem(kiwi);
        
        assertEquals("Wrong saved count", game.getSavedKiwisCount(), 1);
    }

    @Test
    public void testKiwiDeathByDroppingTrapOnKiwi() {
        // Place a Kiwi on the square below
        Kiwi kiwi = new Kiwi(playerPosition, "Kiwi", "Kiwi");
        island.addOccupant(playerPosition, kiwi);
        
        // Give the player a trap
        Tool trap = new Tool(playerPosition,"Trap", "Rat trap",1.0);
        game.collectItem(trap);
        game.dropItem(trap);
        
        // Kiwi should now be dead
        assertTrue("Kiwi should have died", kiwi.isDead());
    }
    
    @Test
    public void testKiwiDeathByDroppingKiwiOnTrap() {
        // Give the player a trap
        Tool trap = new Tool(playerPosition,"Trap", "Rat trap",1.0);
        game.collectItem(trap);
        game.dropItem(trap);
        
        // Drop a Kiwi on the recently dropped trap
        Kiwi kiwi = new Kiwi(playerPosition, "Kiwi", "Kiwi");
        game.collectItem(kiwi);
        game.dropItem(kiwi);
        
        // Kiwi should now be dead
        assertTrue("Kiwi should have died", kiwi.isDead());
    }
    
    @Test
    public void testOneTrapPerSquare()
    {
        Tool trap1 = new Tool(playerPosition,"Trap", "Rat trap",1.0);
        game.collectItem(trap1);
        game.dropItem(trap1);
        assertTrue("There should be 1 trap on the square", island.hasOccupant(playerPosition, trap1));
         
        Tool trap2 = new Tool(playerPosition,"Trap", "Rat trap",1.0);
        game.collectItem(trap2);
        game.dropItem(trap2);
        assertFalse("Trap 2 shouldn't have been placed in the same square as trap1", island.hasOccupant(playerPosition, trap2));
    }
    
    @Test
    public void testLoseGameOnKiwiDeathFromTrap(){
        // Add a kiwi to the current square
        Kiwi kiwi = new Kiwi(playerPosition, "Kiwi", "Kiwi");
        island.addOccupant(playerPosition, kiwi);
        
        // Set a trap in the square of the kiwi
        Tool trap = new Tool(playerPosition, "Trap", "A trap", 1.0);
        game.collectItem(trap);
        game.dropItem(trap);
        //Force update so we dont have to wait for game to refresh
        game.updateGameState();
        // Kiwi should've been killed by the trap
        assertTrue("Game should be over", game.getState()== GameState.LOST);
    }
    
    @Test
    public void testTrapAllPredators()
    {
        // Reset the game
        setUp();
        
        //Trap all predators
        assertTrue("All predators should be dead", trapAllPredators());
    }
    
    @Test
    public void testAllKiwisSaved()
    {
        // Reset the game
        setUp();
        
        //Save all Kiwis
        assertTrue("All kiwis should be saved", saveAllKiwis());
    }
    
    @Test
    public void testGameWinable()
    {
        // Reset the game
        setUp();       
        
        saveAllKiwis();
        trapAllPredators();
        
        assertTrue("Game should be won", game.getState() == GameState.WON);           
    }
/**
 * Private helper methods
 */
    
    private boolean saveAllKiwis()
    { 
        
       //Move player to start position if they are not already.
       player.setPosition(new Position(island,0,5));
      
       //Move to kiwi 1
       boolean moveOK = playerMoveEast(3);
       //Save kiwi 1
       if(moveOK)
       {
           //Collect all objects at player location
            for(Object e : game.getOccupantsPlayerPosition())
            {
                game.collectItem(e);   
            }
            
            //Move back to safezone
            moveOK = playerMoveWest(3);
       } 
       //Drop kiwi 1 in safezone
       if(moveOK)
       {
            for(Object item : game.getPlayerInventory()) 
                game.dropItem(item);
       }
       
       
       //Give player stamina - this doesnt effect the results because the player has stamina 
       //regen in game. So if the run out of stamina, they just need to wait. But we don't 
       //want the test to wait, or go out of it's way to find baskets.
       player.increaseStamina(10);
       
       //Move player to kiwi 2 and collect kiwi
        if(moveOK)
            moveOK = playerMoveSouth(3);  
        if(moveOK)
        {   
            moveOK = playerMoveEast(4);
            //Collect all objects at player location
            for(Object e : game.getOccupantsPlayerPosition())
            {
                game.collectItem(e);   
            } 
        }
        //move player back to safe zone with kiwi 2
        if(moveOK)
            moveOK = playerMoveWest(4);
        if(moveOK) 
            moveOK = playerMoveNorth(3);
        //drop kiwi 2 in safe zone
        if(moveOK)
        {
            for(Object item : game.getPlayerInventory()) 
            {
                game.dropItem(item);
            }              
        }
        
        //Give player stamina
        player.increaseStamina(10);  
       
        //save kiwi 3
        if(moveOK)
           moveOK = playerMoveSouth(4);  
        if(moveOK)
        {   
            moveOK = playerMoveEast(1);
            //Collect all objects at player location
            for(Object e : game.getOccupantsPlayerPosition())
            {
                game.collectItem(e);   
            } 
        }
        //Move player back to safe zone and drop kiwi
        if(moveOK)
            moveOK = playerMoveWest(1);
        if(moveOK) 
            moveOK = playerMoveNorth(4);
        //drop kiwi 3 in safe zone
        if(moveOK)
        {
            for(Object item : game.getPlayerInventory()) 
            {
                game.dropItem(item);
            }              
        }
        
        //give player max stamina
        player.increaseStamina(100);
        
        //Move player to kiwi 4
        if(moveOK)
           moveOK = playerMoveWest(2);  
         if(moveOK)
           moveOK = playerMoveSouth(9);  
        if(moveOK)
        {   
            moveOK = playerMoveWest(3);
            //give player max stamina
            player.increaseStamina(100);
            //Collect all objects at player location
            for(Object e : game.getOccupantsPlayerPosition())
            {
                game.collectItem(e);   
            } 
        }
        //Move player back to safe zone and drop kiwi
        if(moveOK)
            moveOK = playerMoveEast(3);
        if(moveOK) 
            moveOK = playerMoveNorth(9);
         if(moveOK) 
            moveOK = playerMoveEast(2);
        //drop kiwi 3 in safe zone
        if(moveOK)
        {
            for(Object item : game.getPlayerInventory()) 
            {
                game.dropItem(item);
            }              
        }
        
        //Save kiwi 5
        //give player max stamina
        player.increaseStamina(100);
        
        //Move player to kiwi 5
        if(moveOK)
            moveOK = playerMoveSouth(8);
        if(moveOK) 
            moveOK = playerMoveEast(4);
        if(moveOK)
        {               
            //Collect all objects at player location
            for(Object e : game.getOccupantsPlayerPosition())
            {
                game.collectItem(e);   
            } 
        }
        //Move player back to safe zone and drop kiwi
        if(moveOK)
            moveOK = playerMoveWest(4);
        if(moveOK) 
            moveOK = playerMoveNorth(8);
        //drop kiwi 5 in safe zone
        if(moveOK)
        {
            for(Object item : game.getPlayerInventory()) 
            {
                game.dropItem(item);
            }              
        }
        
       return game.getSavedKiwisCount() == game.getTotalKiwis();  
    }
    
    //Trap all predators
    private boolean trapAllPredators()
    {
        //give player max stamina
        player.increaseStamina(100);
        
        //Move player to start position if they are not already.
        player.setPosition(new Position(island,0,5));
       
        //Firstly player needs a trap
        Tool trap = new Tool(playerPosition,"Trap", "A predator trap",1.0);
        game.collectItem(trap);
        
        //Now player needs to trap all predators
        //Predator 1 stoat
        boolean moveOK = playerMoveWest(2);
       
        if(moveOK){
            game.dropItem(trap);
            game.collectItem(trap);
            moveOK = playerMoveSouth(4);
        }
        //Predator 2 possum
        if(moveOK){
            moveOK = playerMoveWest(3);
            game.dropItem(trap);
            game.collectItem(trap);
        }
        //Predator 3 possum
        if(moveOK){
            moveOK = playerMoveEast(4);
        }
        if(moveOK){
            moveOK = playerMoveSouth(2);
            game.dropItem(trap);
            game.collectItem(trap);
        } 
        //Predator 4 stoat
        if(moveOK)
        {
            moveOK = playerMoveSouth(1);
            game.dropItem(trap);
            game.collectItem(trap);
        }
        
        //If a move was not ok, then return false
        if(!moveOK)
            return false;
        
        //Force game update
        game.updateGameState();
        
        //Check all predators have been killed
        return game.getPredatorsRemaining() == 0; 
    }
    
    
    
    private boolean playerMoveNorth(int numberOfMoves)
    {
        boolean success = false;
        for (int i = 0; i < numberOfMoves; i++) {
            success = game.playerMove(MoveDirection.NORTH);
            if(!success)break;
            
        }
        return success;
    }
    
    private boolean playerMoveSouth(int numberOfMoves)
    {
        boolean success = false;
        for (int i = 0; i < numberOfMoves; i++) {
            success = game.playerMove(MoveDirection.SOUTH);
            if(!success)break;
            
        }
        return success;
    }
    
    private boolean playerMoveEast(int numberOfMoves)
    {
        boolean success = false;
        for (int i = 0; i < numberOfMoves; i++) {
            success = game.playerMove(MoveDirection.EAST);
            if(!success)break;
            
        }
        return success;
    }
    
    private boolean playerMoveWest(int numberOfMoves)
    {
        boolean success = false;
        for (int i = 0; i < numberOfMoves; i++) {
            success = game.playerMove(MoveDirection.WEST);
            if(!success)break;
            
        }
        return success;
    }
}
