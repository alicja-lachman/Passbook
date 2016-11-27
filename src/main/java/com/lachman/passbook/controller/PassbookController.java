package com.lachman.passbook.controller;

import com.lachman.passbook.exception.EmptyPasswordFileException;
import com.lachman.passbook.model.PassbookModel;
import com.lachman.passbook.model.Password;
import com.lachman.passbook.view.PassbookView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @version 2.0
 * @author Alicja Lachman
 */
public class PassbookController {

    /**
     * class member representing model of application
     */
    private PassbookModel model;
    /**
     * class member representing view
     */
    private PassbookView view;

    /**
     * flag tracking if there were any errors with program arguments
     */
    private boolean wasError = false;

    /**
     * Constructor of the class
     *
     * @param passbookModel instance of model class
     * @param passbookView instance of view class
     */
    PassbookController(PassbookModel passbookModel, PassbookView passbookView) {
        this.model = passbookModel;
        this.view = passbookView;
    }

    /**
     * Main method of the class. It checks if list of arguments is not empty. If
     * it isn't, it initializes file.
     *
     *
     * @param args program arguments provided by user
     */
    public void init(String[] args) {
        if (args.length < 1) {
            getUserInput();
        } else {
            initializeFile(args[0]);
        }
        run();
    }

    /**
     * Method sets filepath in model and loads passwords from file to model.
     *
     * @param filePath path to file storing passwords
     */
    private void initializeFile(String filePath) {
        try {
            model.setFile(filePath);
            model.getPasswordsFromFile();

        } catch (IOException e) {
            handleException("There was a problem with the file");
            getUserInput();
        }
    }

    /**
     * Method getting information from user about the filePath
     */
    private void getUserInput() {
        view.displayMessage("Please provide path to file in which you want to store your passwords");
        String filePath = view.getInputFromUser();
        initializeFile(filePath);

    }

    /**
     * Method handling exceptions and setting wasError flag
     *
     * @param message exception message to be displayed
     */
    private void handleException(String message) {
        view.displayMessage(message);
        wasError = true;

    }

    /**
     * Main loop method of the application. It shows the menu, analyzes user's
     * choice and handles exceptions.
     */
    private void run() {
        final String waitMessage = "ENTER will take you back to the menu. ";
        wasError = false;
        showMenu();

        MenuOptions menuOption = MenuOptions.INVALID_OPTION;
        menuOption = menuOption.parseFromChar(view.getMenuOption());
        try {
            while (menuOption != MenuOptions.QUIT) {
                switch (menuOption) {
                    case ADD_PASSWORD:
                        addPassword();
                        break;
                    case CHANGE_PASSWORD:
                        changePassword();
                        break;
                    case GET_PASSWORD:
                        getPassword();
                        break;
                    case REMOVE_PASSWORD:
                        removePassword();
                        break;
                    case LIST_ALL_DOMAINS:
                        listAllDomains();
                }
                view.waitForUser(waitMessage);
                showMenu();
                menuOption = menuOption.parseFromChar(view.getMenuOption());
            }
        } catch (FileNotFoundException e) {
            handleException("File was not found!");
        } catch (IOException e) {
            handleException("IO Exception");
        } catch (IndexOutOfBoundsException e) {
            handleException("Missing program arguments");
        } catch (NoSuchElementException e) {
            handleException("Provided file didn't have all necessary data");
        } catch (EmptyPasswordFileException e) {
            handleException("Provided file was empty!");
        } catch (NoSuchAlgorithmException e) {
            handleException("No such algorithm");
        } catch (NoSuchPaddingException e) {
            handleException("No such padding exc");
        } catch (InvalidKeyException e) {
            handleException("Encryption key should be at least 24 characters long");
        } catch (InvalidKeySpecException e) {
            handleException("Invalid key spec exception");
        } catch (IllegalBlockSizeException e) {
            handleException("Illegal block size");
        } catch (BadPaddingException e) {
            handleException("Bad padding exception");

        } finally {
            if (wasError) {
                view.displayMessage("Last operation did not end successfully");
            }
        }
    }

    /**
     * Method used for telling view class to clear screen and print
     * instructions.
     */
    private void showInstructions() {
        view.clearScreen();
        view.displayMessage("show instructions");
    }

    /**
     * Method displaying menu options.
     *
     */
    private void showMenu() {
        view.clearScreen();
        view.displayMessage("Please press key 1-6 to specify what you want to do");
        for (MenuOptions menu : MenuOptions.values()) {
            view.displayMessage(menu.getDescription());
        }
    }

    /**
     * Method getting data from user and creating a new password.
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws EmptyPasswordFileException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IndexOutOfBoundsException
     */
    private void addPassword() throws FileNotFoundException,
            IOException, EmptyPasswordFileException, InvalidKeyException, InvalidKeySpecException,
            IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException, IndexOutOfBoundsException {
        view.displayMessage("Please provide domain name");
        String domainName = view.getInputFromUser();
        view.displayMessage("Please provide username");
        String username = view.getInputFromUser();
        view.displayMessage("Please provide password");
        String password = view.getInputFromUser();
        view.displayMessage("Please provide encryption key");
        String encryptionKey = view.getInputFromUser();

        model.createPassword(domainName, username, password, encryptionKey);

    }

    /**
     * Method changing existing password.
     *
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IndexOutOfBoundsException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private void changePassword() throws BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException, IndexOutOfBoundsException,
            IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException, IOException {
        view.displayMessage("Please provide domain name for which you want to change the password");
        String domainName = view.getInputFromUser();
        if (!model.checkIfPasswordForDomainExists(domainName)) {
            view.displayMessage("No such domain");
            return;

        }
        view.displayMessage("Please provide new password for " + domainName);
        String newPassword = view.getInputFromUser();
        view.displayMessage("Please provide encryption key");
        String encryptionKey = view.getInputFromUser();

        model.changePasswordForDomain(domainName, newPassword, encryptionKey);
    }

    /**
     * Method for getting existing password.
     *
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IndexOutOfBoundsException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws UnsupportedEncodingException
     */
    private void getPassword() throws BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException, IndexOutOfBoundsException,
            IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException {
        view.displayMessage("Please provide domain name for which you want to get the password");
        String domainName = view.getInputFromUser();
        if (!model.checkIfPasswordForDomainExists(domainName)) {
            view.displayMessage("No such domain");
            return;

        }
        view.displayMessage("Please provide encryption key");
        String encryptionKey = view.getInputFromUser();
        Password passwordForDomain = model.getPasswordForDomain(domainName);
        view.displayMessage(model.getDecryptedPassword(passwordForDomain, encryptionKey));

    }

    /**
     * Method removing existing password.
     *
     * @throws IOException
     */
    private void removePassword() throws IOException {
        view.displayMessage("Please provide domain name for which you want to delete password");
        String domainName = view.getInputFromUser();

        if (!model.checkIfPasswordForDomainExists(domainName)) {
            view.displayMessage("No such domain");
            return;
        }
        model.deletePasswordForDomain(domainName);
    }

    /**
     * Method listing all saved domain names.
     */
    private void listAllDomains() {
        List<String> domainNames = model.listAllDomainNames();
        if (domainNames.isEmpty()) {
            view.displayMessage("No passwords saved in this file");
            return;
        }
        view.displayMessage("You have passwords saved for: ");
        view.displayListOfMessages(model.listAllDomainNames());
    }

}
