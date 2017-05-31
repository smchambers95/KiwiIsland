/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Marc Tucker
 */
public class StretchImage extends JLabel {
    private ImageName imageName;
    private final ImageManager resourceManager;

    public StretchImage(ImageName imageName, ImageManager resourceManager){
        super();
        this.imageName = imageName;
        this.resourceManager = resourceManager;
        init();
    }
    
    private void init(){
        this.setVerticalAlignment(SwingConstants.TOP);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setForeground(Color.red);
    }

    public void setImageName(ImageName imageName){
        this.imageName = imageName;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(imageName != null){
            if(resourceManager != null && resourceManager.containsImage(imageName)){ 
                // Images are held in a HashMap so it's an Order 1 look up (not wasting performance)   
                Image image = resourceManager.getImage(imageName);
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
            else
               this.setText("Missing Image"); 
        } 
    }
}
