/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.audio;

import java.util.HashMap;
import javax.sound.sampled.Clip;
import nz.ac.aut.ense701.gameModel.EventName;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Marc Tucker, Sean Chambers
 */
public class AudioManager {
    private final HashMap<EventName, Clip> audioMap;
    
    /**
     * Constructs an AudioManager and runs the loadResources function
     */
    public AudioManager()
    {
        // Create the maps to hold the resources
        audioMap  = new HashMap();
        
        // Actually load in the resources from disc
        loadResources();
    }
    
    /**
     * Loads in all the required assets
     */
    public void loadResources()
    {
        // Load Audio files
        addAudio(EventName.PLAYER_TO_FOREST, "audio/grass-footstep.wav");
        addAudio(EventName.PLAYER_TO_WETLAND, "audio/grass-footstep.wav");
        addAudio(EventName.PLAYER_TO_ROCK, "audio/rock-footstep.wav");
    }
    
    /**
     * This function will attempt to create a Clip object from the file path provided and put it in the map under its event name
     * 
     * @param eventName the event that corresponds to this audio file
     * @param filePath the path of the audio file to bring in
     */
    private void addAudio(EventName eventName, String filePath)
    {
        AudioInputStream audioStream = null;
        try {
            File audioFile = new File(filePath);
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);
            audioMap.put(eventName, audioClip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(AudioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(audioStream != null)
                    audioStream.close();
            } catch (IOException ex) {
                Logger.getLogger(AudioManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Checks if the provided eventName exists within the map
     * 
     * @param eventName
     * @return 
     */
    public boolean containsAudio(EventName eventName)
    {
        return audioMap.containsKey(eventName);
    }
    
    /**
     * Returns the Clip the corresponds to the event name
     * 
     * @param eventName the event name associated with the desired Clip object
     * @return the Clip object associated with the event name
     */
    public Clip getAudio(EventName eventName)
    {
        return audioMap.get(eventName);
    }
}
