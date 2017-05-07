/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.controllers;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import nz.ac.aut.ense701.gameModel.Fauna;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameState;
import nz.ac.aut.ense701.gameModel.MoveDirection;

/**
 * @author Marc Tucker
 */
public class AIFaunaController extends Controller {
    private static final int MIN_COOLDOWN = 5000;
    private static final int MAX_COOLDOWN = 10000;
    
    Random rnd;
    
    public AIFaunaController(Game game, Fauna fauna, boolean active) {
        super(game, fauna, active);
        rnd = new Random();
    }

    @Override
    public void run() {
        // Run this thread will the thread isn't signaled for killing
        while(!kill){
            // If the controller is set to active run the navigation logic
            if(active && game.getState() == GameState.PLAYING && occupant != null){
                Fauna fauna = (Fauna) occupant;
                // User a random integer to decide what direction to move in
                int direction = rnd.nextInt(5);
                switch (direction){
                    case 0:
                        game.faunaMove(fauna, MoveDirection.NORTH);
                        break;
                    case 1:
                        game.faunaMove(fauna, MoveDirection.SOUTH);
                        break;
                    case 2:
                        game.faunaMove(fauna, MoveDirection.EAST);
                        break;
                    case 3:
                        game.faunaMove(fauna, MoveDirection.WEST);
                        break;
                    case 4:
                        // Take this as a choice not to move at all
                        break;
                }  
                
                // Tell the thread to go to sleep (give delay between fauna movement)
                try {
                    Thread.sleep(rnd.nextInt(MAX_COOLDOWN - MIN_COOLDOWN) + MIN_COOLDOWN);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AIFaunaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
