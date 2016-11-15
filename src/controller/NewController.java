package controller;

import exception.EmptyPasswordFileException;
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
import model.PassbookModel;
import model.Password;
import view.PassbookView;

/**
 * @version 2.0
 * @author Alicja Lachman
 */
public class NewController {

    /**
     * class member representing model of application
     */
    private PassbookModel model;
    /**
     * class member representing view
     */
    private PassbookView view;
    
    private String filePath;
    private boolean wasError = false;

    /**
     * Constructor of the class
     *
     * @param passbookModel instance of model class
     * @param passbookView instance of view class
     */
    NewController(PassbookModel passbookModel, PassbookView passbookView) {
        this.model = passbookModel;
        this.view = passbookView;
    }
    
    public void init(String[] args) {
        if (args.length < 1) {
            getUserInput();
        } else {
            initializeFile(args[0]);
        }
        
        run();
    }

    private void initializeFile(String filePath) {
        try {
            model.setFile(filePath);
            model.getPasswordsFromFile();
            
        } catch (IOException e) {
            handleException("There was a problem with the file");
           getUserInput();
        }
    }

    private void getUserInput() {
        view.displayMessage("Please provide path to file in which you want to store your passwords");
        String filePath = view.getInputFromUser();
        initializeFile(filePath);
        
    }
    
    private void handleException(String message) {
        view.displayMessage(message);
        wasError = true;
        
    }

    /**
     * Main loop method of the application. It shows the menu and analyzes
     * user's choice.
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
    
    private void showMenu() {
        view.clearScreen();
        view.displayMessage("Please press key 1-6 to specify what you want to do");
        for (MenuOptions menu : MenuOptions.values()) {
            view.displayMessage(menu.getDescription());
        }
    }
    
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
        
        Password passwordToAdd = model.createPassword(domainName, username, password, encryptionKey);
        
    }
    
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
    
    private void removePassword() throws IOException {
        view.displayMessage("Please provide domain name for which you want to delete password");
        String domainName = view.getInputFromUser();
        
        if (!model.checkIfPasswordForDomainExists(domainName)) {
            view.displayMessage("No such domain");
            return;
        }
        model.deletePasswordForDomain(domainName);
    }
    
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
