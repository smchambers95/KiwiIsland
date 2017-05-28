package nz.ac.aut.ense701.gameModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.ac.aut.ense701.controllers.AIPredatorController;

/**
 * This is the class that knows the Kiwi Island game rules and state
 * and enforces those rules.
 *
 * @author AS
 * @version 1.0 - created
 * Maintenance History
 * August 2011 Extended for stage 2. AS
 */

public class Game implements Runnable
{
    //Constants shared with UI to provide player data
    public static final int STAMINA_INDEX = 0;
    public static final int MAXSTAMINA_INDEX = 1;
    public static final int MAXWEIGHT_INDEX = 2;
    public static final int WEIGHT_INDEX = 3;
    public static final int MAXSIZE_INDEX = 4;
    public static final int SIZE_INDEX = 5;
    
    /**
     * A new instance of Kiwi island that reads data from "IslandData.txt".
     */
    public Game() 
    {   
        eventListeners = new HashSet<GameEventListener>();
        createNewGame();
    }
    
    
    /**
     * Starts a new game.
     * At this stage data is being read from a text file
     */
    public void createNewGame()
    {
        predatorControllers = new HashSet<AIPredatorController>();
        totalPredators = 0;
        totalKiwis = 0;
        predatorsTrapped = 0;
        savedKiwiCount = 0;
        deadKiwis = 0;
        enoughStaminaToMove = true;
        initialiseIslandFromFile("IslandData.txt");
        drawIsland();
        state = GameState.PLAYING;
        winMessage = "";
        loseMessage = "";
        playerMessage = "";
        Thread gameThread = new Thread(this);
        gameThread.start();
        notifyGameEventListeners();
        outputMessages = new ArrayList<String>();
    }
    
    /***********************************************************************************************************************
     * Accessor methods for game data
    ************************************************************************************************************************/
    
    /**
     * Get number of rows on island
     * @return number of rows.
     */
    public int getNumRows()
    {
        return island.getNumRows();
    }
    
    /**
     * Get number of columns on island
     * @return number of columns.
     */
    public int getNumColumns()
    {
        return island.getNumColumns();
    }
    
    /**
     * Gets the current state of the game.
     * 
     * @return the current state of the game
     */
    public GameState getState()
    {
        return state;
    }    
 
    /**
     * Provide a description of occupant
     * @param whichOccupant
     * @return description if whichOccuoant is an instance of occupant, empty string otherwise
     */
    public String getOccupantDescription(Object whichOccupant)
    {
       String description = "";
        if(whichOccupant !=null && whichOccupant instanceof Occupant)
        {
            Occupant occupant = (Occupant) whichOccupant;
            description = occupant.getDescription();
        }
        return description;
    }
 
        /**
     * Gets the player object.
     * @return the player object
     */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Checks if possible to move the player in the specified direction.
     * 
     * @param direction the direction to move
     * @return if the move is possible returns true, otherwise it will return false
     */
    public boolean isPlayerMovePossible(MoveDirection direction)
    {
        boolean isMovePossible = false;
        // what position is the player moving to?
        Position newPosition = player.getPosition().getNewPosition(direction);
        // is that a valid position?
        if ( (newPosition != null) && newPosition.isOnIsland() )
        {
            // what is the terrain at that new position?
            Terrain newTerrain = island.getTerrain(newPosition);
                      
            // can the player do it?
            isMovePossible = player.hasStaminaToMove(newTerrain) && 
                             player.isAlive();
            
            //Set enoughStamina so that we can check this scenario in MovePlayer to print out stamina is too low message
            if(!player.hasStaminaToMove(newTerrain))
                enoughStaminaToMove = false;
            else
                enoughStaminaToMove = true;   
        }       
        return isMovePossible;
    }
    
    /**
     * Check if the given Fauna instance is able to move in the specified direction
     * 
     * @param fauna the instance of the Fauna that wants to move
     * @param direction the direction in which the Fauna wants to move
     * @return if the move is possible returns true, otherwise it will return false 
     */
    public boolean isFaunaMovePossible(Fauna fauna, MoveDirection direction)
    {
        // what position is the fauna moving to?
        Position newPosition = fauna.getPosition().getNewPosition(direction);
        
        // Check that the new postion is a valid location for fauna
        if((newPosition != null) && newPosition.isOnIsland())
        {
            Terrain newTerrain = island.getTerrain(newPosition);
            if(newTerrain.getStringRepresentation().equals("S"))
                return false;
            // Check if there are hazards
            for (Occupant occupant : island.getOccupants(newPosition))
                if (occupant instanceof Hazard)
                   return false;
            // All checks have passed, the move is valid
            return true;
        }
        // Move is not valid
        return false;
    }
    
    /**
     * Get terrain for position
     * @param row
     * @param column
     * @return Terrain at position row, column
     */
    public Terrain getTerrain(int row, int column) {
        return island.getTerrain(new Position(island, row, column));
    }
   
    /**
    * Is this position explored?
    * @param row
    * @param column
    * @return true if position row, column is explored.
    */
    public boolean isExplored(int row, int column) {
        return island.isExplored(new Position(island, row, column));
    }

    /**
     * Get occupants for player's position
     * @return occupants at player's position
     */
    public Occupant[] getOccupantsPlayerPosition()
    {
        return island.getOccupants(player.getPosition());
    }
    
    /**
     * Get string for occupants of this position
     * @param row
     * @param column
     * @return occupant string for this position row, column
     */
    public String getOccupantStringRepresentation(int row, int column) {
        return island.getOccupantStringRepresentation(new Position(island, row, column));
    }
    
    /**
     * Get values from player for GUI display
     * @return player values related to stamina and backpack.
     */
    public int[] getPlayerValues()
    {
        int[] playerValues = new int[6];
        playerValues[STAMINA_INDEX ]= (int) player.getStaminaLevel();
        playerValues[MAXSTAMINA_INDEX]= (int) player.getMaximumStaminaLevel();
        playerValues[MAXWEIGHT_INDEX ]= (int) player.getMaximumBackpackWeight();
        playerValues[WEIGHT_INDEX]= (int) player.getCurrentBackpackWeight();
            
        return playerValues;
        
    }
    
    /**
     * How many kiwis have been saved?
     * @return count
     */
    public int getSavedKiwisCount()
    {
        return savedKiwiCount;
    }
    
    /**
     * How many kiwis are in the world?
     * @return count
     */
    public int getTotalKiwis()
    {
        return totalKiwis;
    }
    
    /**
     * How many predators are left?
     * @return number remaining
     */
    public int getPredatorsRemaining()
    {
        return totalPredators - predatorsTrapped;
    }
    
    /**
     * Get contents of player backpack
     * @return objects in backpack
     */
    public Object[] getPlayerInventory()
            {
              return  player.getInventory().toArray();
            }
    
    /**
     * Get player name
     * @return player name
     */
    public String getPlayerName()
    {
        return player.getName();
    }

    /**
     * Is player in this position?
     * @param row
     * @param column
     * @return true if player is at row, column
     */
    public boolean hasPlayer(int row, int column) 
    {
        return island.hasPlayer(new Position(island, row, column));
    }
    
    /**
     * Only exists for use of unit tests
     * @return island
     */
    public Island getIsland()
    {
        return island;
    }
    
    /**
     * Draws the island grid to standard output.
     */
    public void drawIsland()
    {  
          island.draw();
    }
    
     /**
     * Is this object collectable
     * @param itemToCollect
     * @return true if is an item that can be collected.
     */
    public boolean canCollect(Object itemToCollect)
    {
        boolean result = (itemToCollect != null)&&(itemToCollect instanceof Item);
        if(result)
        {
            Item item = (Item) itemToCollect;
            result = item.isOkToCarry();
        }
        return result;
    }
    
    /**
     * Is this object usable
     * @param itemToUse
     * @return true if is an item that can be collected.
     */
    public boolean canUse(Object itemToUse)
    {
        boolean result = (itemToUse != null)&&(itemToUse instanceof Item);
        if(result)
        {
            //Food can always be used (though may be wasted)
            // so no need to change result
            
            // Kiwi's cannot be used
            if(itemToUse instanceof Kiwi){
                result = false;
            }
            else if(itemToUse instanceof Tool)
            {
                Tool tool = (Tool)itemToUse;
                //Traps can only be dropped. A dropped trap is a set trap.
                if(tool.isTrap())
                {
                    result = false;
                }
                //Screwdriver can only be used if player has a broken trap
                else if (tool.isScrewdriver() && player.hasTrap())
                {
                    result = player.getTrap().isBroken();
                }
                else
                {
                    result = false;
                }
            }            
        }
        return result;
    }
    
        
    /**
     * Details of why player won
     * @return winMessage
     */
    public String getWinMessage()
    {
        return winMessage;
    }
    
    /**
     * Details of why player lost
     * @return loseMessage
     */
    public String getLoseMessage()
    {
        return loseMessage;
    }
    
    /**
     * Details of information for player
     * @return playerMessage
     */
    public String getPlayerMessage()
    {
        String message = playerMessage;
        playerMessage = ""; // Already told player.
        return message;
    }
    
    /**
     * Is there a message for player?
     * @return true if player message available
     */
    public boolean messageForPlayer() {
        return !("".equals(playerMessage));
    }
    
    public ArrayList<String> getOutputMessages()
    {
        return (ArrayList<String>) outputMessages;
    }
    
    public void clearMessages()
    {
        this.outputMessages.clear();
    }
    
    /***************************************************************************************************************
     * Mutator Methods
    ****************************************************************************************************************/
    
   
    
    /**
     * Picks up an item at the current position of the player
     * Ignores any objects that are not items as they cannot be picked up
     * @param item the item to pick up
     * @return true if item was picked up, false if not
     */
    public boolean collectItem(Object item)
    {
        boolean success = (item instanceof Item) && (player.collect((Item)item));
        if(success)
        {
            Item collectedItem = (Item) item;
            // player has picked up an item: remove from grid square
            island.removeOccupant(player.getPosition(), (Item)item);
            
            // If the item was a kiwi, check whether or not the kiwi was safe and change the kiwi saved count accordingly
            if(item instanceof Kiwi){
                if(((Kiwi) item).saved()){
                    ((Kiwi) item).setSafe(false);
                    savedKiwiCount--;
                    outputMessages.add("You remove a kiwi from the safe zone.");
                }
            }
            outputMessages.add("You pick up a: "+collectedItem.getName()+".");
            // everybody has to know about the change
            notifyGameEventListeners();
        }      
        else
            outputMessages.add("Your backpack is full. You must drop items to make more room.");
        return success;
    } 
    
    /**
     * Drops what from the player's backpack.
     *
     * @param what  to drop
     * @return true if what was dropped, false if not
     */
    public boolean dropItem(Object what)
    {
        boolean success = player.drop((Item)what);
        if ( success )
        {
            // player has dropped an what: try to add to grid square
            Item item = (Item)what;
            success = island.addOccupant(player.getPosition(), item);
            if ( success )
            {
                // drop successful: everybody has to know that
                notifyGameEventListeners();
                
                // If the item was a kiwi, check whether or not it was dropped in a safe zone
                if(item instanceof Kiwi){
                    if(island.hasTrap(player.getPosition())){
                        ((Kiwi)item).kill();
                        deadKiwis++;
                        outputMessages.add("You drop the kiwi on a trap. It dies.");
                    }
                    else if(island.getTerrain(player.getPosition()).getStringRepresentation().equals("S")){
                        ((Kiwi) item).setSafe(true);
                        savedKiwiCount++;  
                        outputMessages.add("You drop the kiwi in the safe zone.");
                    }
                    else
                        outputMessages.add("You drop the kiwi.");    
                    
                    updateGameState();       
                }
                else if (item instanceof Tool)
                {
                    Tool tool = (Tool) item;
                    
                    if (tool.isTrap()&& !tool.isBroken())
                    {
                        Occupant[] occupants = island.getOccupants(player.getPosition());
                        boolean trapExists = false;
                        for(Occupant occupant : occupants){
                            if(occupant instanceof Tool){
                                Tool tmp = (Tool) occupant;
                                if(tmp != tool && tmp.isTrap())
                                    trapExists = true;
                            }         
                        }
                        if(!trapExists)
                        {
                            outputMessages.add("You set a trap.");
                            activateTrap(); 
                        }
                        else
                        {
                            collectItem(tool);
                            outputMessages.add("You already have a trap set there.");
                        }
                    }
                    else
                    {
                        //Drop other tool type
                        outputMessages.add("You drop a: "+item);
                    }
                    // Kill predator or kiwi if trap is used when player is in same square                              
                    updateGameState();
                }
                else
                {
                    outputMessages.add("You drop a: "+item);
                }
            }          
            else
            {
                // grid square is full: player has to take what back
                player.collect(item);     
                outputMessages.add("Cannot drop anymore items on current tile.");
            }
        }
        return success;
    } 
    
    /**
     * Uses an item in the player's inventory.
     * This can  be food or tool items.
     * @param item to use
     * @return true if the item has been used, false if not
     */
    public boolean useItem(Object item)
    {  
        boolean success = false;
        if ( item instanceof Food && player.hasItem((Food) item) )
        //Player east food to increase stamina
        {
            Food food = (Food) item;
            
            // player gets energy boost from food
            player.increaseStamina(food.getEnergy());
            outputMessages.add("You consume the "+food+" and feel replenished.");
            // player has consumed the food: remove from inventory
            player.drop(food);
            // use successful: everybody has to know that
            notifyGameEventListeners();
        }
        else if (item instanceof Tool)
        {
            Tool tool = (Tool) item;
            if(tool.isScrewdriver())// Use screwdriver (to fix trap)
            {
                if(player.hasTrap())
                    {
                        Tool trap = player.getTrap();
                        trap.fix();
                        outputMessages.add("You fix the broken trap.");
                    }
            }
        }
        updateGameState();
        return success;
    }
       
    /**
     * Attempts to move the player in the specified direction.
     * 
     * @param direction the direction to move
     * @return true if the move was successful, false if it was an invalid move
     */
    public boolean playerMove(MoveDirection direction)
    {
        // what terrain is the player moving on currently
        boolean successfulMove = false;
        if (isPlayerMovePossible(direction))
        {
            Position newPosition = player.getPosition().getNewPosition(direction);
            Terrain  terrain     = island.getTerrain(newPosition);

            // move the player to new position
            player.moveToPosition(newPosition, terrain);
            island.updatePlayerPosition(player);
            successfulMove = true;
                    
            // Is there a hazard?
            checkForHazard();
            updateGameState();            
        }
        else if(!enoughStaminaToMove)
            outputMessages.add("You are out of breath, you need to rest before you can move again");

        return successfulMove;
    }
    
    /**
     * Attempts to move a fauna instance in the specified direction
     * 
     * @param fauna the instance of the fauna that desires to move
     * @param direction the direction to move
     * @return will return true if the move was successful, false if it was an invalid move
     */
    public boolean faunaMove(Fauna fauna, MoveDirection direction)
    {
        boolean successfulMove = false;
        if (isFaunaMovePossible(fauna, direction))
        {
            Position oldPosition = fauna.getPosition();
            Position newPosition = fauna.getPosition().getNewPosition(direction);
            Terrain terrain = island.getTerrain(newPosition);

            // move the fauna instance to new position
            fauna.moveToPosition(newPosition, terrain);
            // update the faunas position on the island
            island.updateFaunaPosition(fauna, oldPosition);
            successfulMove = true;
            
            // If the fauna that is moving is a predator check if there are kill conditions from the resulting move
            if(fauna instanceof Predator){
                for(Occupant occupant : island.getOccupants(newPosition)){
                    // If a Kiwi is in the same square kill it
                    if(occupant instanceof Kiwi){
                        outputMessages.add("A predator has killed a kiwi.");  
                        ((Kiwi)occupant).kill();
                        deadKiwis++;   
                    }
                    // If a trap is in the same square kill the predator
                    else if(occupant instanceof Tool && ((Tool)occupant).isTrap()){
                        ((Predator)fauna).kill();
                        island.removeOccupant(newPosition, fauna);
                        predatorsTrapped++;
                        outputMessages.add("You have trapped a predator. Well done.");    
                    }  
                }
            }
                
            updateGameState();            
        }
        return successfulMove;
    }
    
    /**
     * Adds a game event listener.
     * @param listener the listener to add
     */
    public void addGameEventListener(GameEventListener listener)
    {
        eventListeners.add(listener);
    }
    
    
    /**
     * Removes a game event listener.
     * @param listener the listener to remove
     */
    public void removeGameEventListener(GameEventListener listener)
    {
        eventListeners.remove(listener);
    }
   
    
    /*********************************************************************************************************************************
     *  Private methods
     *********************************************************************************************************************************/
    
    /**
     * The game can be run as a thread to allow things that need constant updates to be updated (the game loop)
     */
    @Override
    public void run() 
    {
        while(state == GameState.PLAYING)
        {
            long delta = System.currentTimeMillis() - prvTime;
            prvTime = System.currentTimeMillis();
            
            if(player != null)
                player.updateStamina(delta);  
            
            // Update every predator controller we have
            for(AIPredatorController controller : predatorControllers)
                controller.update(delta);
            
            updateGameState();
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Used after player actions to update game state.
     * Applies the Win/Lose rules.
     */
    private void updateGameState()
    {
        String message;
        if ( !player.isAlive() )
        {
            state = GameState.LOST;
            message = "Sorry, you have lost the game. " + this.getLoseMessage();
            this.setLoseMessage(message);
        }
        else if(deadKiwis > 0)
        {
            state = GameState.LOST;
            message = "Sorry, you have lost the game. A kiwi has been killed";
            this.setLoseMessage(message);
        }
        else if(predatorsTrapped == totalPredators)
        {
            state = GameState.WON;
            message = "You win! You have done an excellent job and trapped all the predators.";
            this.setWinMessage(message);
        }
        else if(savedKiwiCount == totalKiwis)
        {
            if(predatorsTrapped >= totalPredators)
            {
                state = GameState.WON;
                message = "You win! You have saved all the kiwi and trapped all of the predators.";
                this.setWinMessage(message);
            }
        }               
        
        // notify listeners about changes
        notifyGameEventListeners();
    }
    
       
    /**
     * Sets details about players win
     * @param message 
     */
    private void setWinMessage(String message)
    {
        winMessage = message;
    }
    
    /**
     * Sets details of why player lost
     * @param message 
     */
    private void setLoseMessage(String message)
    {
        loseMessage = message;
    }
    
    /**
     * Set a message for the player
     * @param message 
     */
    private void setPlayerMessage(String message) 
    {
        playerMessage = message;
        
    }
    /**
     * Check if player able to move
     * @return true if player can move
     */
    private boolean playerCanMove() 
    {
        return ( isPlayerMovePossible(MoveDirection.NORTH)|| isPlayerMovePossible(MoveDirection.SOUTH)
                || isPlayerMovePossible(MoveDirection.EAST) || isPlayerMovePossible(MoveDirection.WEST));

    }
    
    /**
     * Check if player is dropping trap in same tile as a kiwi or predator, if so, kill them. 
     */
    private void activateTrap()
    {
        Position currentPosition = player.getPosition();
        
        for(Occupant occupant : island.getOccupants(currentPosition)){
            if(occupant instanceof Predator){
                island.removeOccupant(currentPosition, occupant); 
                predatorsTrapped++;
                outputMessages.add("You have trapped a predator. Well done.");  
            }
            else if(occupant instanceof Kiwi){
                 outputMessages.add("You set a trap on a kiwi. It dies.");
                ((Kiwi) occupant).kill();
                island.removeOccupant(currentPosition, occupant); 
                deadKiwis++;   
            }
        }
    }
            
    /**
     * Checks if the player has met a hazard and applies hazard impact.
     * Fatal hazards kill player and end game.
     */
    private void checkForHazard()
    {
        //check if there are hazards
        for ( Occupant occupant : island.getOccupants(player.getPosition())  )
        {
            if ( occupant instanceof Hazard )
            {
               handleHazard((Hazard)occupant) ;
            }
        }
    }
    
    /**
     * Apply impact of hazard
     * @param hazard to handle
     */
    private void handleHazard(Hazard hazard) {
        if (hazard.isFatal()) 
        {
            player.kill();
            outputMessages.add(hazard.getDescription() + " has killed you.");
            this.setLoseMessage(hazard.getDescription() + " has killed you.");
        } 
        else if (hazard.isBreakTrap()) 
        {
            Tool trap = player.getTrap();
            if (trap != null) {
                trap.setBroken();
                outputMessages.add("Sorry your predator trap is broken. You will need to find tools to fix it before you can use it again.");
                this.setPlayerMessage("Sorry your predator trap is broken. You will need to find tools to fix it before you can use it again.");
            }
        } 
        else // hazard reduces player's stamina
        {
            double impact = hazard.getImpact();
            // Impact is a reduction in players energy by this % of Max Stamina
            double reduction = player.getMaximumStaminaLevel() * impact;
            player.reduceStamina(reduction);
            outputMessages.add(hazard.getDescription() + " has reduced your stamina.");
            this.setPlayerMessage(hazard.getDescription() + " has reduced your stamina.");
        }
    }
    
    /**
     * Notifies all game event listeners about a change.
     */
    private void notifyGameEventListeners()
    {
        for ( GameEventListener listener : eventListeners ) 
        {
            listener.gameStateChanged();
        }
    }

    
    /**
     * Loads terrain and occupant data from a file.
     * At this stage this method assumes that the data file is correct and just
     * throws an exception or ignores it if it is not.
     * 
     * @param fileName file name of the data file
     */
    private void initialiseIslandFromFile(String fileName) 
    {
        try
        {
            Scanner input = new Scanner(new File(fileName));
            // make sure decimal numbers are read in the form "123.23"
            input.useLocale(Locale.US);
            input.useDelimiter("\\s*,\\s*");

            // create the island
            int numRows    = input.nextInt();
            int numColumns = input.nextInt();
            island = new Island(numRows, numColumns);

            // read and setup the terrain
            setUpTerrain(input);

            // read and setup the player
            setUpPlayer(input);

            // read and setup the occupants
            setUpOccupants(input);

            input.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Unable to find data file '" + fileName + "'");
        }
        catch(IOException e)
        {
            System.err.println("Problem encountered processing file.");
        }
    }

    /**
     * Reads terrain data and creates the terrain.
     * 
     * @param input data from the level file
     */
    private void setUpTerrain(Scanner input) 
    {
        for ( int row = 0 ; row < island.getNumRows() ; row++ ) 
        {
            String terrainRow = input.next();
            for ( int col = 0 ; col < terrainRow.length() ; col++ )
            {
                Position pos = new Position(island, row, col);
                String   terrainString = terrainRow.substring(col, col+1);
                Terrain  terrain = Terrain.getTerrainFromStringRepresentation(terrainString);
                island.setTerrain(pos, terrain);
            }
        }
    }

    /**
     * Reads player data and creates the player.
     * @param input data from the level file
     */
    private void setUpPlayer(Scanner input) 
    {
        String playerName              = input.next();
        int    playerPosRow            = input.nextInt();
        int    playerPosCol            = input.nextInt();
        double playerMaxStamina        = input.nextDouble();
        double playerMaxBackpackWeight = input.nextDouble();
        
        Position pos = new Position(island, playerPosRow, playerPosCol);
        player = new Player(pos, playerName, 
                playerMaxStamina, 
                playerMaxBackpackWeight);
        island.updatePlayerPosition(player);
    }

    /**
     * Creates occupants listed in the file and adds them to the island.
     * @param input data from the level file
     */
    private void setUpOccupants(Scanner input) 
    {
        int numItems = input.nextInt();
        for ( int i = 0 ; i < numItems ; i++ ) 
        {
            String occType  = input.next();
            String occName  = input.next(); 
            String occDesc  = input.next();
            int    occRow   = input.nextInt();
            int    occCol   = input.nextInt();
            Position occPos = new Position(island, occRow, occCol);
            Occupant occupant    = null;

            if ( occType.equals("T") )
            {
                double weight = input.nextDouble();
                occupant = new Tool(occPos, occName, occDesc, weight);
            }
            else if ( occType.equals("E") )
            {
                double weight = input.nextDouble();
                double energy = input.nextDouble();
                occupant = new Food(occPos, occName, occDesc, weight, energy);
            }
            else if ( occType.equals("H") )
            {
                double impact = input.nextDouble();
                occupant = new Hazard(occPos, occName, occDesc,impact);
            }
            else if ( occType.equals("K") )
            {
                occupant = new Kiwi(occPos, occName, occDesc);
                totalKiwis++;
            }
            else if ( occType.equals("P") )
            {
                occupant = new Predator(occPos, occName, occDesc);
                AIPredatorController controller = new AIPredatorController(this, (Predator)occupant);
                predatorControllers.add(controller);
                totalPredators++;
            }
            else if ( occType.equals("F") )
            {
                occupant = new Fauna(occPos, occName, occDesc);
            }
            if ( occupant != null ) island.addOccupant(occPos, occupant);
        }
    }    

    private Island island;
    private Player player;
    private GameState state;
    private int savedKiwiCount;
    private int totalPredators;
    private int totalKiwis;
    private int predatorsTrapped;
    private int deadKiwis;
    private Set<AIPredatorController> predatorControllers;
    private Set<GameEventListener> eventListeners;
    private long prvTime;
        
    private String winMessage = "";
    private String loseMessage  = "";
    private String playerMessage  = "";   
    
    private List<String> outputMessages;
    private boolean enoughStaminaToMove;
}
