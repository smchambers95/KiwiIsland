/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioStream;




/**
 *
 * @author Sean 
 */
public class ResourceManager {
    public HashMap<Images,BufferedImage> imageMap;
    public HashMap<Sounds,Clip> soundMap;
    
    public Mixer mixer;
    public Clip clip;
    
    BufferedImage img;
    File audioFile;
    
    
    public ResourceManager() throws IOException, UnsupportedAudioFileException
    {
        imageMap  = new HashMap();
        soundMap = new HashMap();
        
        //Load in images first
        //Load Player, and fauna sprites.
        //Player
        img = ImageIO.read(new File("textures/playerSprite.jpg"));
        imageMap.put(Images.PLAYER,img);
        //Kiwi
        img = ImageIO.read(new File("textures/kiwiSprite.png"));
        imageMap.put(Images.KIWI,img);
        //Possum
        img = ImageIO.read(new File("textures/possumSprite.gif"));
        imageMap.put(Images.POSSUM,img);
        //Stoat
        img = ImageIO.read(new File("textures/stoatSprite.png"));
        imageMap.put(Images.STOAT,img);
        //Textures
        //Grass
        img = ImageIO.read(new File("textures/grassTexture.jpg"));
        imageMap.put(Images.GRASS,img);
        //Water
        img = ImageIO.read(new File("textures/waterTexture.jpg"));
        imageMap.put(Images.WATER,img);
        //Sand
        img = ImageIO.read(new File("textures/sandTexture.jpg"));
        imageMap.put(Images.SAND,img);
        //Hole
        img = ImageIO.read(new File("textures/holeTexture.png"));
        imageMap.put(Images.HOLE,img);
        
        //Load sound assets    
        addSound(Sounds.SETTRAP, "../sounds/setTrapSound.wav");
        
        playSound(Sounds.SETTRAP);
        
                
        
     
    }
    
    //get clip from soundMap and play
    public void playSound(Sounds soundName)
    {   
        soundMap.get(soundName).start();
    }
    
    //
    public void addSound(Sounds soundName, String soundFileURL)
    {
        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixInfos[0]);
        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);  
        try{
            clip = (Clip) mixer.getLine(dataInfo);
        }
        catch(LineUnavailableException lue)
        {
            lue.printStackTrace();
        }
        
        try{
            URL soundURL = ResourceManager.class.getResource(soundFileURL);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);           
            clip.open(audioStream);  
            
            //Add sound clip to soundMap
            soundMap.put(soundName, clip);
        }
        catch(LineUnavailableException lue){
            System.out.println("Make sure the sound file you are reading in, is 16bit wav.");
            lue.printStackTrace();
        }
        catch(UnsupportedAudioFileException uafe){uafe.printStackTrace();}
        catch(IOException ioe){ioe.printStackTrace();}
    }
    
}
