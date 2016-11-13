/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import model.PassbookModel;
import view.PassbookView;

/**
 *
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

        }
        try {
            model.getPasswordsFromFile(args[0]);
        } catch (IOException e) {
            handleException("There was a problem with the file");
        }

        run();
    }

    private void handleException(String message) {
        view.displayMessage(message);

    }

    /**
     * Main loop method of the application. It shows the menu and analyzes
     * user's choice.
     */
    private void run() {
        final String waitMessage = "ENTER will take you back to the menu. ";
        showMenu();

        MenuOptions menuOption = MenuOptions.INVALID_OPTION;
        menuOption = menuOption.parseFromChar(view.getMenuOption());

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

    }
    
        /**
     * Method used for telling view class to clear screen and print instructions.
     */
    private void showInstructions()
    {
        view.clearScreen();
        view.displayMessage("show instructions");
    }
    
        private void showMenu(){
        view.clearScreen();
        view.displayMessage("Please press key 1-6 to specify what you want to do");
        for(MenuOptions menu: MenuOptions.values())
            view.displayMessage(menu.getDescription());
    }

    private void addPassword() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void changePassword() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void getPassword() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void removePassword() {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listAllDomains() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
