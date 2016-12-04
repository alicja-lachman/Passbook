package com.lachman.passbook.controller;

import com.lachman.passbook.model.PassbookModel;
import com.lachman.passbook.view.PassbookSwingView;
import java.awt.EventQueue;

/**
 * Main class of the application. Passbook is a simple application for storing
 * and ciphering passwords.
 *
 * EncryptionKey used in application should be at least 24 characters long.
 *
 * Used libraries: - common-apache used for Base64 coding. - gson used for json
 * parsing.
 *
 * @version 3.0
 * @author Alicja Lachman
 */
public class PassbookMain {

    /**
     * Main function of the application. It initializes the Swing view
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            PassbookSwingView view = new PassbookSwingView();
            view.setVisible(true);
        });
    }
}
