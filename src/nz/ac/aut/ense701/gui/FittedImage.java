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
    private final ImageManager imageManager;
    
    private double imageWidth;
    private double imageHeight;

    /**
     * Creates a Fitted Image object
     * @param imageName allows the Image to start with an image (leave null if no picture is desired)
     * @param imageManager reference to the ImageManager that will hold the assets for this picture
     */
    public FittedImage(ImageName imageName, ImageManager imageManager){
        super();
        this.imageName = imageName;
        this.imageManager = imageManager;
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
        if(imageName != null && imageManager != null && imageManager.containsImage(imageName)){
            ImageIcon tmp = new ImageIcon(imageManager.getImage(imageName));
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
            if(imageManager != null && imageManager.containsImage(imageName)){ 
                // Images are held in a HashMap so it's an Order 1 look up (not wasting performance)   
                Image image = imageManager.getImage(imageName);
                double widthRatio = (double)getWidth() / imageWidth;
                double heightRatio = (double)getHeight() / imageHeight;
                double ratio = Math.min(widthRatio, heightRatio);
                int adjustedWidth = (int)(imageWidth * ratio);
                int adjustedHeight = (int)(imageHeight * ratio);
                g.drawImage(image, (getWidth()/2) - (adjustedWidth/2), (getHeight()/2) - (adjustedHeight/2), adjustedWidth, adjustedHeight, this);
            }
            else
               this.setText("Missing Image"); 
        } 
    }
}
