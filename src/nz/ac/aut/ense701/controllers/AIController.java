package nz.ac.aut.ense701.controllers;

import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Occupant;

/**
 * @author Marc Tucker
 */
public abstract class AIController implements Runnable{
    protected Game game;
    protected Occupant occupant;
    protected boolean active;
    protected boolean kill;
    
    public AIController(Game game, Occupant occupant, boolean active){
        this.game = game;
        this.occupant = occupant;
        this.active = active;
        kill = false;
    }

    /**
     * This function should be implemented by classes that extend controller for custom logic
     */
    @Override
    public abstract void run();
    
    /**
     * get the active status of the controller
     * 
     * @return returns true if the controller is active, false if not
     */
    public boolean isActive(){
        return active;
    }
    
    /**
     * This allows for stopping and starting a controller
     * 
     * @param active the new status of the controller
     */
    public void setActive(boolean active){
        this.active = active;
    }
    
    /**
     * This will change the controllers occupant
     * 
     * @param newOccupant the new occupant to control (can be null, if the controller has no occupant to process)
     * @return this will return the old occupant of the controller
     */
    public Occupant setOccupant(Occupant newOccupant){
        Occupant oldOccupant = occupant;
        occupant = newOccupant;
        return oldOccupant;
    }
    
    /**
     * This function should be used when the controller should be killed
     */
    public void killController(){
        kill = true;
    }
}
