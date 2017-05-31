/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.audio;

import java.util.List;
import javax.sound.sampled.Clip;
import nz.ac.aut.ense701.gameModel.EventName;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameEventListener;

/**
 * This class handles playing audio that corresponds to in-game events
 * @author Marc Tucker, Sean Chambers
 */
public class AudioPlayer implements GameEventListener 
{
    private final AudioManager audioManager;
    private final Game game;
    
    /**
     * Constructs an AudioPlayer with reference to the game to play audio events for
     * it also constructs itself an AudioManager which will pull in the required assets
     * 
     * @param game reference to game to receive event updates from
     */
    public AudioPlayer(Game game)
    {
        audioManager = new AudioManager();
        this.game = game;
        if(game != null)
            setAsGameListener();
    }
    
    /**
     * Set this AudioPlayer as a game listener for the current game reference
     */
    private void setAsGameListener()
    {
        game.addGameEventListener(this); 
    }
    
    /**
     * When the game loop updates this function will be notified
     * 
     * @param events the list of events that has happened since the last update
     */
    @Override
    public void gameStateChanged(List<EventName> events) 
    {
        for(EventName event : events)
            playAudio(event);
    }
    
    /**
     * This function will play the Clip object that corresponds to the event if one exists in the audioManager
     * 
     * @param event the name of the event to play audio for
     */
    public void playAudio(EventName event)
    {
        if(audioManager.containsAudio(event)){
            Clip clip = audioManager.getAudio(event);
            clip.setMicrosecondPosition(0);
            if(clip.isActive())
                clip.flush();
            clip.start();
        } 
    }
}
