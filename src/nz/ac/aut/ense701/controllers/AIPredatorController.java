/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.controllers;

import java.util.Random;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.MoveDirection;
import nz.ac.aut.ense701.gameModel.Predator;

/**
 * @author Marc Tucker
 */
public class AIPredatorController 
{
    private static final int MIN_COOLDOWN = 5000;
    private static final int MAX_COOLDOWN = 10000;
    
    private Predator predator;
    private int currentSleep;
    private int requiredSleep;
    
    private final Random rnd;
    private final Game game;  
    
    /**
     * Creates a new AIPredatorController which will randomly move a given predator with sleep delays between moves
     * 
     * @param game the reference to the game instance that the predator is part of
     * @param predator the predator to control, can be null
     */
    public AIPredatorController(Game game, Predator predator) 
    {
        rnd = new Random();
        this.game = game;
        this.predator = predator;
        currentSleep = 0;
        requiredSleep = rnd.nextInt(MAX_COOLDOWN - MIN_COOLDOWN) + MIN_COOLDOWN;
    }

    /**
     * This function should be executed by the main game loop to allow the controller to update
     * @param delta the change in time since the last update
     */
    public void update(long delta) 
    {
        // Do not bother updating this controller if it has nothing to control
        if(predator != null)
        {
            // Adjust the currentSleep variable
            currentSleep += delta;
            if(currentSleep >= requiredSleep)
            {
                // The Predator has slept for the required amount and can now make a move
                // User a random integer to decide what direction to move in
                int direction = rnd.nextInt(5);
                switch (direction)
                {
                    case 0:
                        game.faunaMove(predator, MoveDirection.NORTH);
                        break;
                    case 1:
                        game.faunaMove(predator, MoveDirection.SOUTH);
                        break;
                    case 2:
                        game.faunaMove(predator, MoveDirection.EAST);
                        break;
                    case 3:
                        game.faunaMove(predator, MoveDirection.WEST);
                        break;
                    case 4:
                        // Take this as a choice not to move at all
                        break;
                }
                // reset the currentSleep and requiredSleep
                currentSleep = 0;
                requiredSleep = rnd.nextInt(MAX_COOLDOWN - MIN_COOLDOWN) + MIN_COOLDOWN;
            }
        }      
    }
    
    /**
     * Get the currently controlled predator
     * @return the currently controlled predator
     */
    public Predator getControlledPredator()
    {
        return predator;
    }
    
    /**
     * Allows changing of what predator the controller will control
     * @param predator the new predator to control (can be null)
     * @return gives back the previously controlled predator
     */
    public Predator setControlledPredator(Predator predator)
    {
        Predator oldPredator = this.predator;
        this.predator = predator;
        return oldPredator;
    }
}
