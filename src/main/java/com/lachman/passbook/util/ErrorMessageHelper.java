package com.lachman.passbook.util;

/**
 * Helper class used for getting error messages.
 *
 * @version 1.0
 * @author Alicja Lachman
 */
public class ErrorMessageHelper {

    /**
     * Method returning message for given error code.
     *
     * @param resultCode
     * @return
     */
    public static String getErrorMessage(int resultCode) {
        switch (resultCode) {
            case 1:
            default:
                return "You provided incomplete data. Please fill all fields.";
            case 2:
                return "You misspelled password. Please check it.";
            case 3:
                return "Encryption key should be at least 24 characters long";

        }
    }

}
