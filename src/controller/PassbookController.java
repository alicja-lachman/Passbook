package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.NoSuchElementException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import exception.EmptyPasswordFileException;
import model.PassbookModel;
import model.Password;
import view.PassbookView;

/**
 * Controller class of the application.
 *
 * @version 1.0
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
     * it isn't, it starts analyzing them. It also handles possible exceptions.
     *
     *
     * @param args program arguments provided by user
     */
    public void run(String[] args) {
        wasError = false;
        if (args.length < 1) {
            view.displayMessage("No arguments provided!");
            getProgramArgumentsFromUser();
            return;
        }
        try {
            analyzeArguments(args);

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
                getProgramArgumentsFromUser();
            }
        }
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
     * Method responsible for analyzing program arguments Depending on option
     * chosen by user, it can read a password from file, save password to file
     * or redirect user to manual input of arguments.
     *
     * @param args program arguments
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
    private void analyzeArguments(String[] args) throws FileNotFoundException,
            IOException, EmptyPasswordFileException, InvalidKeyException, InvalidKeySpecException,
            IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException, IndexOutOfBoundsException {

        if (args[0].equals("-r")) {
            Password password = model.getPasswordFromFile(args[1]);
            view.displayMessage(model.getDecryptedPassword(password, args[2]));
        } else if (args[0].equals("-w")) {
            Password password = model.createPassword(args[2], args[3], args[4], args[5]);
            //model.savePasswordToFile(args[1], password);
        } else {
            getProgramArgumentsFromUser();
        }
    }

    /**
     * Method getting information from user whether he wants to read or write
     * password.
     */
    private void getProgramArgumentsFromUser() {
        view.displayMessage("Something went wrong. Please provide program arguments again");
        view.displayMessage("Would you like to read or write password to file?");
        view.displayMessage("'-r' : read, '-w' : write");
        String programMode = view.getInputFromUser();
        if (programMode.equals("-r")) {
            getArgumentsForReadMode();
            return;
        }
        if (programMode.equals("-w")) {
            getArgumentsForWriteMode();
            return;
        }
        getProgramArgumentsFromUser();
    }

    /**
     * Method getting from user further arguments for read mode
     *
     */
    private void getArgumentsForReadMode() {
        view.displayMessage("Please provide path to file with password");
        String file = view.getInputFromUser();
        view.displayMessage("Please provide encryption key you used for encrypting file");
        String encryptionKey = view.getInputFromUser();
        String[] newArguments = {"-r", file, encryptionKey};
        run(newArguments);
    }

    /**
     * Method getting from user further arguments for write mode
     *
     */
    private void getArgumentsForWriteMode() {

        view.displayMessage("Please provide path to file which you want to create/write");
        String file = view.getInputFromUser();
        view.displayMessage("Please provide domain name");
        String domainName = view.getInputFromUser();
        view.displayMessage("Please provide username");
        String username = view.getInputFromUser();
        view.displayMessage("Please provide password");
        String password = view.getInputFromUser();
        view.displayMessage("Please provide encryption key");
        String encryptionKey = view.getInputFromUser();
        String[] newArguments = {"-w", file, domainName, username, password, encryptionKey};
        run(newArguments);
    }
}
