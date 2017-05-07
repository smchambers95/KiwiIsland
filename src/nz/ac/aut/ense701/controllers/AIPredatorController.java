/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.controllers;

import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.Predator;

/**
 * @author Marc Tucker
 */
public class AIPredatorController extends Controller {

    public AIPredatorController(Game game, Predator predator, boolean active) {
        super(game, predator, active);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }  
}
