package controller;

import model.PassbookModel;
import view.PassbookView;

/**
 * Main class of the application. Passbook is a simple, console-based
 * application for storing and ciphering passwords. In the future, new features
 * will be added to enhance the user experience.
 *
 * Used arguments: To save password to file: -w filePath domainName username
 * password encryptionKey To read password from file: -r filePath encryptionKey
 *
 * Provided encryptionKey should be at least 24 characters long.
 *
 * Used libraries: common-apache used for Base64 coding.
 *
 * @version 1.0
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
        PassbookModel passbookModel = new PassbookModel();
        PassbookView passbookView = new PassbookView();
        NewController passbookController = new NewController(passbookModel, passbookView);
        passbookController.init(args);
    }
}
