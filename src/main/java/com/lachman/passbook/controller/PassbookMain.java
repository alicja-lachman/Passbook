package com.lachman.passbook.controller;

import com.lachman.passbook.model.PassbookModel;
import com.lachman.passbook.view.PassbookSwingView;
import com.lachman.passbook.view.PassbookView;
import java.awt.EventQueue;

/**
 * Main class of the application. Passbook is a simple, console-based
 * application for storing and ciphering passwords. In the future, new features
 * will be added to enhance the user experience.
 *
 * Used arguments: path (path to file where the user wants to store his
 * passwords)
 *
 * EncryptionKey used in application should be at least 24 characters long.
 *
 * Used libraries: - common-apache used for Base64 coding. - gson used for json
 * parsing.
 *
 * @version 2.0
 * @author Alicja Lachman
 */
public class PassbookMain {

    /**
     * Main function of the application. It creates instances of model and view
     * classes and initializes an instance of a controller class with them.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        PassbookModel passbookModel = new PassbookModel();
//        PassbookView passbookView = new PassbookView();
//        PassbookController passbookController = new PassbookController(passbookModel, passbookView);
//        passbookController.init(args);
   EventQueue.invokeLater(() -> {
            PassbookSwingView view = new PassbookSwingView();
            view.setVisible(true);
        });
    }
}
