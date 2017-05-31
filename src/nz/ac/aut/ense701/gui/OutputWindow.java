/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.util.List;
import java.awt.TextArea;
import nz.ac.aut.ense701.gameModel.EventName;

/**
 *
 * @author Sean Chambers, Marc Tucker
 */
public class OutputWindow extends TextArea {
    private String outputMessage;
    
    public OutputWindow()
    {
        resetOutputWindow();
    }

    /**
     * Add new text to outputmessage
     * @param text past from the updateWindowText to add to the output message 
     */
    public void updateOutputWindow(String eventText)
    {
            //set the output window message to the output message
            this.setText(outputMessage+=eventText+"\n");
            //Set scroll bar to bottom so you can see latest output
            this.setSelectionStart(outputMessage.length());
    }
    
    /**
     * Resets the outputput window to the welcome message
     */
    public final void resetOutputWindow()
    {
        outputMessage = "Welcome to Kiwi Island!"
                + "\n\nIt is your mission to save all the kiwis, trapping "
                + "\nthe predators before they get to them first!"
                + "\n\nYou can use W,S,A,D to move, and E to pick up items."
                + "\nOther controls can be accessed via the right sidebar."
                + "\n\nGood Luck!\n\n";
        this.setText(outputMessage);
    }
    
    /**
     * Passes the updateOutputWindow the message that occurred when an event happened in game.
     * @param events 
     */
    public void updateWindowText(List<EventName> events)
    {
                //Update message box
        if(!events.isEmpty())
        {
            for(EventName event : events)
            {
                switch(event)
                {
                    case  KIWI_PICKUP_SAFEZONE: 
                        updateOutputWindow("You remove a kiwi from the safe zone.");
                        break;
                    case SNACK_PICKUP:
                        updateOutputWindow("You find a snack in the picnic basket.");
                        break;
                    case ITEM_PICKUP:
                        updateOutputWindow("You pick up the item.");
                        break;
                    case BACKPACK_FULL:
                        updateOutputWindow("Your backpack is full. You must drop items to make more room.");
                        break;
                    case TRAP_KIWI:
                        updateOutputWindow("You drop the kiwi on a trap. It dies.");
                        break;
                    case KIWI_SAVED:
                        updateOutputWindow("You drop the kiwi in the safe zone");
                        break;
                    case KIWI_DROPPED:
                       updateOutputWindow("You drop the kiwi.");
                       break; 
                    case KIWI_PICKUP:
                        updateOutputWindow("You pick up a kiwi.");
                        break;
                    case TRAP_DROPPED:
                        updateOutputWindow("You set a trap.");
                        break;
                    case MULTIPLE_TRAPS_ON_GRID:
                        updateOutputWindow("You already have a trap set there.");
                        break;
                    case ITEM_DROP:
                        updateOutputWindow("You drop the item.");
                        break;
                    case GRID_FULL:
                        updateOutputWindow("Cannot drop anymore items on current tile.");
                        break;  
                    case SNACK_USE:
                        updateOutputWindow("You enjoy your snack and feel replenished.");
                        break;
                    case TRAP_FIX:
                        updateOutputWindow("You fix the broken trap.");
                        break;
                    case PLAYER_NO_STAMINA:
                        updateOutputWindow("You are out of breath, you need to rest before you can move again.");
                        break;
                    case PREDATOR_KILL_KIWI:
                        updateOutputWindow("A predator has killed a kiwi.");
                        break;
                    case TRAP_PREDATOR:
                        updateOutputWindow("You have trapped a predator. Well done.");
                        break;
                    case TRAP_BROKEN:
                        updateOutputWindow("Your trap has broken. You will need to find tools to fix it before you can use it again.");
                        break;
                    case HAZARD_FATAL:
                        updateOutputWindow("A fatal hazard has killed you.");
                        break;
                    case HAZARD_MINOR:
                        updateOutputWindow("You hit a minor hazard. Your stamina has been reduced.");
                        break;     
                }
            }
        }
    }
    
}
