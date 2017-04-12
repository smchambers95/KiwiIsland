package nz.ac.aut.ense701.main;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gui.KiwiCountUI;

/**
 * Kiwi Count Project
 * 
 * @author AS
 * @version 2011
 */
public class Main 
{
    /**
     * Main method of Kiwi Count.
     * 
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     */
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException 
    {
        // create the game object
        final Game game = new Game();
        // create the GUI for the game
        final KiwiCountUI  gui  = new KiwiCountUI(game);
        // make the GUI visible
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                gui.setVisible(true);
            }
        });
    }

}
