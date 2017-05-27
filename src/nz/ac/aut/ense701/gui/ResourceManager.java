/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

/**
 *
 * @author Sean Chambers, Marc Tucker 
 */
public class ResourceManager {
    private final HashMap<ImageName, Image> imageMap;
    private final HashMap<SoundName, Clip> audioMap;
    
    public ResourceManager()
    {
        // Create the maps to hold the resources
        imageMap  = new HashMap();
        audioMap = new HashMap();
        
        // Actually load in the resources from disc
        loadResources();
    }
    
    public void loadResources()
    {
        // Load Images
        addImage(ImageName.FOREST, "textures/grass-texture.png");
        addImage(ImageName.WETLAND, "textures/wetland-texture.png");
        addImage(ImageName.SCRUB, "textures/wetland-texture.png");
        addImage(ImageName.WATER, "textures/water-texture.png");
        addImage(ImageName.SAND, "textures/sand-texture.png");
        addImage(ImageName.SAFE, "textures/safe-texture.png");
        addImage(ImageName.PLAYER, "textures/player-texture.png");
        addImage(ImageName.UNKNOWN, "textures/unknown-texture.png");
    }
    
    private boolean addImage(ImageName imageName, String filePath)
    {
        try {
            imageMap.put(imageName, (ImageIO.read(new File(filePath))));
            return true;
        } catch (IOException ex) {
            System.out.println("Error: Could not read texture at path: " + filePath);
            return false;
        }
    }
    
    private void addSound(SoundName soundName, String filePath)
    {
        
    }
    
    public boolean containsImage(ImageName imageName)
    {
        return imageMap.containsKey(imageName);
    }
    
    public Image getImage(ImageName imageName)
    {
        return imageMap.get(imageName);
    }
}
