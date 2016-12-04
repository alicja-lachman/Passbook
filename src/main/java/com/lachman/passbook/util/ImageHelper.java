package com.lachman.passbook.util;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * Helper class for managing images in application.
 *
 * @version 1.0
 * @author Alicja Lachman
 */
public class ImageHelper {

    private static ImageHelper instance;

    /**
     * Providing only one instance of class per application (Singleton)
     *
     * @return
     */
    public static ImageHelper get() {
        if (instance == null) {
            instance = new ImageHelper();
        }
        return instance;
    }

    /**
     * Method returning image from resource folder.
     *
     * @param name
     * @return
     */
    public Image getIcon(String name) {
        return Toolkit.getDefaultToolkit().getImage(getClass()
                .getClassLoader().getResource(name));
    }
}
