/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;
import nz.ac.aut.ense701.gameModel.OccupantName;

/**
 * This panel is for representing occupants
 * 
 * @author Marc Tucker
 */
public class OccupantsPanel extends JPanel 
{
    private final ResourceManager resourceManager;
    
    private GridLayout layout;
    private final ArrayList<FittedImage> images;
    
    /**
     * Creates a new OccupantsPanel
     * @param resourceManager give reference to the resource manager that holds the necessary assets for expected occupants
     */
    public OccupantsPanel(ResourceManager resourceManager) 
    {
        this.resourceManager = resourceManager;
        images = new ArrayList();
        setup();
    }
    
    /**
     * This method is for post construction tasks that should still be done after initial construct
     * (due to possibly unfinished construction)
     */
    private void setup() 
    {
        this.setOpaque(false);
        
        // Create and set the layout
        layout = new GridLayout(1,1);
        setLayout(layout);
    }
    
    /**
     * This function handles updating what occupants this square should represent
     * @param occupantsRepresentation a string where every character represents an occupant
     */
    public synchronized void setOccupants(LinkedList<OccupantName> occupantsRepresentation) 
    {
        int occupantsCompleted = 0;
        for(OccupantName occupantName : occupantsRepresentation)
        {
            // Create an ImageName to hold the enum of the image we should use for this occupant
            ImageName imageName;
            switch(occupantName)
            {
                case PLAYER     : imageName = ImageName.PLAYER; break; 
                case KIWI       : imageName = ImageName.KIWI; break;  
                case BASKET     : imageName = ImageName.BASKET; break;
                case HAZARD     : imageName = ImageName.HAZARD; break;
                case TUI        : imageName = ImageName.TUI; break;
                case CRAB       : imageName = ImageName.CRAB; break;
                case TRAP       : imageName = ImageName.TRAP; break;
                case STOAT      : imageName = ImageName.STOAT; break;
                case POSSUM     : imageName = ImageName.POSSUM; break;
                default         : imageName = ImageName.UNKNOWN; break;
            }
            
            // If a StretchImage already exists use it, instead of making another (save performance)
            if(occupantsCompleted < images.size())
                images.get(occupantsCompleted).setImageName(imageName);
            else 
            {
                FittedImage image = new FittedImage(imageName, resourceManager);  
                images.add(image);
                this.add(image);
            }        
            
            occupantsCompleted++;
        }

        // Remove any extra images from the panel and list
        if(occupantsRepresentation.size() < images.size()){
            // Remove in reverse order to avoid element shifting
            for(int i = images.size(); i > occupantsRepresentation.size(); i--){
                this.remove(i - 1);
                images.remove(i - 1);
            }
        }
           
        // Invoke revalidate and repaint on the frame to force the visual update and change      
        this.revalidate();
        this.repaint();
    }
}
