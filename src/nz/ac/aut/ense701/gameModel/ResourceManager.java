/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gameModel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import sun.audio.AudioStream;

/**
 *
 * @author Sean 
 */
public class ResourceManager {
    HashMap<Images,BufferedImage> imageMap;
    HashMap<Sounds,AudioStream> soundMap;
    BufferedImage img;
    
    public ResourceManager() throws IOException
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
    }
    
    
}
