/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

/**
 *
 * @author Marc Tucker, Sean Chambers
 */
public enum EventName {
    PLAYER_TO_WATER,
    PLAYER_TO_SAND,
    PLAYER_TO_WETLAND,
    PLAYER_TO_FOREST,
    PLAYER_TO_ROCK,
    PLAYER_TO_HAZARD,
    PLAYER_NO_STAMINA,
    HAZARD_FATAL,
    HAZARD_MINOR,
    ITEM_PICKUP,
    ITEM_DROP,
    TRAP_FIX,
    TRAP_BROKEN,
    TRAP_DROPPED,
    TRAP_PICKUP,
    TRAP_KIWI,
    TRAP_PREDATOR,
    TRAP_BREAK,
    SNACK_USE,
    SNACK_DROP,
    SNACK_PICKUP,
    PREDATOR_KILL_KIWI,
    KIWI_PICKUP,
    KIWI_PICKUP_SAFEZONE,
    KIWI_DROPPED,
    KIWI_SAVED,
    BACKPACK_FULL,
    MULTIPLE_TRAPS_ON_GRID,
    GRID_FULL,
    GAME_LOST,
    GAME_WON,
    GAME_ALERT,
}
