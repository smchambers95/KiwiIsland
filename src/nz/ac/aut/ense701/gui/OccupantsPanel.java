/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

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
    public synchronized void setOccupants(String occupantsRepresentation) 
    {
        for(int i = 0; i < occupantsRepresentation.length(); i++)
        {
            // Create an ImageName to hold the enum of the image we should use for this occupant
            ImageName imageName;
            switch (occupantsRepresentation.charAt(i))
            {
                case 'X' : imageName = ImageName.PLAYER; break;
                case 'H' : imageName = ImageName.HAZARD; break;
                default  : imageName = ImageName.UNKNOWN; break;
            }
            // If a StretchImage already exists use it, instead of making another (save performance)
            if(i < images.size())
                images.get(i).setImageName(imageName);
            else 
            {
                FittedImage image = new FittedImage(imageName, resourceManager);  
                images.add(image);
                this.add(image);
            }        
        }
        
        // Remove any extra images from the panel and list
        if(occupantsRepresentation.length() < images.size()){
            // Remove in reverse order to avoid element shifting
            for(int i = images.size(); i > occupantsRepresentation.length(); i--){
                this.remove(i - 1);
                images.remove(i - 1);
            }
        }
           
        // Invoke revalidate and repaint on the frame to force the visual update and change      
        this.revalidate();
        this.repaint();
    }
}
