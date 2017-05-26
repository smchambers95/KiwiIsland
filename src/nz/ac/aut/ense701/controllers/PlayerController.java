/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.MoveDirection;

/**
 *
 * @author Marc Tucker
 */
public class PlayerController extends KeyAdapter{
    private Game game;
    private HashMap<String, Boolean> keyStates;
    
    public PlayerController(Game game){
        this.game = game;
        keyStates = new HashMap<String, Boolean>(){};
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_W:
                if(!keyStates.containsKey("W") || !keyStates.get("W")){
                    game.playerMove(MoveDirection.NORTH);
                    keyStates.put("W", Boolean.TRUE);
                }
                break;
            case KeyEvent.VK_S:
                if(!keyStates.containsKey("S") || !keyStates.get("S")){
                    game.playerMove(MoveDirection.SOUTH);
                    keyStates.put("S", Boolean.TRUE);
                }
                break;
            case KeyEvent.VK_A:
                if(!keyStates.containsKey("A") || !keyStates.get("A")){
                    game.playerMove(MoveDirection.WEST);
                    keyStates.put("A", Boolean.TRUE);
                }
                break;
            case KeyEvent.VK_D:
                if(!keyStates.containsKey("D") || !keyStates.get("D")){
                    game.playerMove(MoveDirection.EAST);
                    keyStates.put("D", Boolean.TRUE);
                }
                break;
            default:
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
       int keyCode = e.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_W:
                if(!keyStates.containsKey("W") || keyStates.get("W")){
                    keyStates.put("W", Boolean.FALSE);
                }
                break;
            case KeyEvent.VK_S:
                if(!keyStates.containsKey("S") || keyStates.get("S")){
                    keyStates.put("S", Boolean.FALSE);
                }
                break;
            case KeyEvent.VK_A:
                if(!keyStates.containsKey("A") || keyStates.get("A")){
                    keyStates.put("A", Boolean.FALSE);
                }
                break;
            case KeyEvent.VK_D:
                if(!keyStates.containsKey("D") || keyStates.get("D")){
                    keyStates.put("D", Boolean.FALSE);
                }
                break;
            default:
                break;
        }
    }
}
