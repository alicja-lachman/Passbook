package view;

import java.util.Scanner;

/**
 * View class of the application.
 *
 * @version 1.0
 * @author Alicja Lachman
 */
public class PassbookView {

    /**
     * Method for displaying given message in the console.
     *
     * @param message text to be displayed
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Method for getting user input from console.
     *
     * @return input
     */
    public String getInputFromUser() {
        Scanner reader = new Scanner(System.in);
        return reader.nextLine();
    }

}
