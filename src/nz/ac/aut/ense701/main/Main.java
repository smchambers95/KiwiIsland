package nz.ac.aut.ense701.main;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import nz.ac.aut.ense701.audio.AudioPlayer;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gui.KiwiIslandUI;

/**
 * Kiwi Count Project
 * 
 * @author AS
 * @version 2011
 */
public class Main 
{
    /**
     * Main method of Kiwi Island.
     * 
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws javax.sound.sampled.UnsupportedAudioFileException
     */
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException 
    {
        // create the game object
        final Game game = new Game();
        // create the audio player for the game
        final AudioPlayer ap = new AudioPlayer(game);
        // create the GUI for the game
        final KiwiIslandUI gui = new KiwiIslandUI(game);        
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
