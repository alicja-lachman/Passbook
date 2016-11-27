/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * @version 1.0
 * @author Alicja Lachman
 */
public class ImageHelper {
    private static ImageHelper instance;
    
    public static ImageHelper get(){
        if(instance == null)
            instance = new ImageHelper();
        return instance;
    }
    
    
      public Image getApplicationIcon(String name) {
        return Toolkit.getDefaultToolkit().getImage(getClass()
                .getClassLoader().getResource(name));
    }
}
