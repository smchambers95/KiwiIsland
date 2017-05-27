/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * This is a label designed to display pictures and fit them their maximum size while maintaining aspect ratio
 * 
 * @author Marc Tucker
 */
public class FittedImage extends JLabel {
    private ImageName imageName;
    private final ResourceManager resourceManager;
    
    private double imageWidth;
    private double imageHeight;

    /**
     * Creates a Fitted Image object
     * @param imageName allows the Image to start with an image (leave null if no picture is desired)
     * @param resourceManager reference to the ResourceManager that will hold the assets for this picture
     */
    public FittedImage(ImageName imageName, ResourceManager resourceManager){
        super();
        this.imageName = imageName;
        this.resourceManager = resourceManager;
        init();
    }
    
    /**
     * Completes post construction tasks (incase components have not finished)
     */
    private void init(){
        this.setVerticalAlignment(SwingConstants.TOP);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setForeground(Color.red);
        // This will load the imageWidth and Height too
        setImageName(imageName);
    }

    /**
     * Will update the image name and if it is not null, it will retrieve the images dimensions for scaling
     * @param imageName 
     */
    public void setImageName(ImageName imageName){
        this.imageName = imageName;
        if(imageName != null && resourceManager != null && resourceManager.containsImage(imageName)){
            ImageIcon tmp = new ImageIcon(resourceManager.getImage(imageName));
            imageWidth = (double)tmp.getIconWidth();
            imageHeight = (double)tmp.getIconHeight();
        }
    }
    
    /**
     * This function is where the magic happens, it will change the width and height of the image to fit the available
     * width and height while maintaining the original aspect ratio
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // Do not provide an else for this condition, as we hope the missing image name is on purpose and no image is desired to be drawn
        if(imageName != null){
            if(resourceManager != null && resourceManager.containsImage(imageName)){ 
                // Images are held in a HashMap so it's an Order 1 look up (not wasting performance)   
                Image image = resourceManager.getImage(imageName);
                double widthRatio = (double)getWidth() / imageWidth;
                double heightRatio = (double)getHeight() / imageHeight;
                double ratio = Math.min(widthRatio, heightRatio);
                g.drawImage(image, 0, 0, (int)(imageWidth * ratio), (int)(imageHeight * ratio), this);
            }
            else
               this.setText("Missing Image"); 
        } 
    }
}
