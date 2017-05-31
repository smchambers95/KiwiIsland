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
 *
 * @author Marc Tucker, Sean Chambers
 */
public class AudioPlayer implements GameEventListener 
{
    private final AudioManager audioManager;
    private final Game game;
    
    public AudioPlayer(Game game)
    {
        audioManager = new AudioManager();
        this.game = game;
        if(game != null)
            setAsGameListener();
    }
    
    private void setAsGameListener()
    {
        game.addGameEventListener(this); 
    }
    
    @Override
    public void gameStateChanged(List<EventName> events) 
    {
        for(EventName event : events)
            playSound(event);
    }
    
    public void playSound(EventName event)
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
