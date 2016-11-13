package controller;

/**
 * Enum containing possible menu options of the application. 
 * 
 * @version 1.0
 * @author Alicja Lachman
 */
public enum MenuOptions {
    /**
     * Option used for adding password
     */
    ADD_PASSWORD("1. Add a password"),
    /**
     * Option used for changing password for chosen domain
     */
    CHANGE_PASSWORD("2. Change password"),
    /**
     * Option used for getting password for chosen domain
     */
    GET_PASSWORD("3. Get password"),
    /**
     * Option used for removing a password
     */
    REMOVE_PASSWORD("4. Remove password"),
    /**
     * Option used for listing all saved domain names
     */
    LIST_ALL_DOMAINS("5. List all domains"),
    /**
     * Option used for quitting the application
     */
    QUIT("6. Quit"),
    /**
     * Option used for indicating an input error
     */
    INVALID_OPTION("");

    private String description;

    private MenuOptions(String description) {
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
    
    
    /**
     * Method for parsing a character into an enumeration value of the class
     *
     * @param option character to parse
     * @return enumeration value of the class
     */
    public MenuOptions parseFromChar(char option) {
        switch (option) {
            case '1':
                return ADD_PASSWORD;
            case '2':
                return CHANGE_PASSWORD;
            case '3':
                return GET_PASSWORD;
            case '4':
                return REMOVE_PASSWORD;
            case '5':
                return LIST_ALL_DOMAINS;
            case '6':
                return QUIT;
            default:
                return INVALID_OPTION;
        }

    }
}
