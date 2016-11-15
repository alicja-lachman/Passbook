package view;

import java.io.IOException;
import java.util.List;
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

    public void displayListOfMessages(List<String> messages) {
        for (String message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Method used for extracting first character from the standard input.
     *
     * @return extracted character
     */
    public char getMenuOption() {
        char choice = ' ';
        try {
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine().charAt(0);
        } catch (IndexOutOfBoundsException ex) {
            return choice;
        }
        return choice;
    }

    public void waitForUser(String message) {
        System.out.print(message);

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /**
     * Method used for clearing the screen.
     */
    public void clearScreen() {
        try {
            if (System.getProperty("os.name").startsWith("Window")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException exception) {
            for (int i = 0; i < 1000; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Method used for showing menu.
     */
//    public void showMenu()
//    {
//        displayMessage("***** Welcome to Debtors - your very own "
//                + "debtors database! *****\n");
//        displayMessage("The application helps you keep track of "
//                + "your debtors - it's a ahandy way of \nstoring your debtors' "
//                + "names, surnames and the amount of $$$ they owe you.\n");
//        displayMessage("What do you want to do?");
//        displayMessage("1. Add a debtor");
//        displayMessage("2. Change debt amount of a debtor");
//        displayMessage("3. Remove a debtor");
//        displayMessage("4. Get a list of all debtors");
//        displayMessage("5. Quit");
//        displayMessage("Pick one:");
//    }
}
