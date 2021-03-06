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

/**
 *
 * @author Sean Chambers, Marc Tucker 
 */
public class ImageManager {
    private final HashMap<ImageName, Image> imageMap;
    
    public ImageManager()
    {
        // Create the maps to hold the resources
        imageMap  = new HashMap();
        
        // Actually load in the resources from disc
        loadResources();
    }
    
    public void loadResources()
    {
        // Load Images
        addImage(ImageName.FOREST, "textures/grass-texture.png");
        addImage(ImageName.WETLAND, "textures/wetland-texture.png");
        addImage(ImageName.ROCK, "textures/rock-texture.png");
        addImage(ImageName.WATER, "textures/water-texture.png");
        addImage(ImageName.SAND, "textures/sand-texture.png");
        addImage(ImageName.SAFE, "textures/safe-texture.png");
        addImage(ImageName.PLAYER, "textures/player-texture.png");
        addImage(ImageName.UNKNOWN, "textures/unknown-texture.png");  
        addImage(ImageName.BASKET, "textures/basket-texture.png");
        addImage(ImageName.TUI, "textures/tui-texture.png");
        addImage(ImageName.CRAB, "textures/crab-texture.png");
        addImage(ImageName.TRAP, "textures/trap-texture.png");
        addImage(ImageName.KIWI, "textures/kiwi-texture.png");
        addImage(ImageName.STOAT, "textures/stoat-texture.png");
        addImage(ImageName.POSSUM, "textures/possum-texture.png"); 
        addImage(ImageName.HAZARD, "textures/hazard-texture.png");
    }
    
    private boolean addImage(ImageName imageName, String filePath)
    {
        try 
        {
            imageMap.put(imageName, (ImageIO.read(new File(filePath))));
            return true;
        } 
        catch (IOException ex) 
        {
            System.out.println("Error: Could not read texture at path: " + filePath);
            return false;
        }
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
