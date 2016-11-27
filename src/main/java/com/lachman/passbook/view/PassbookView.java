package com.lachman.passbook.view;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * View class of the application.
 *
 * @version 2.0
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

    /**
     * Method for displaying list of elements
     *
     * @param messages - elements to be displayed
     */
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

    /**
     * Method waiting for user input
     *
     * @param message
     */
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
}
