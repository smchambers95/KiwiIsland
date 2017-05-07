/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.controllers;

import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Player;

/**
 * @author Marc Tucker
 */
public class PlayerController extends Controller {

    public PlayerController(Game game, Player player, boolean active) {
        super(game, player, active);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
