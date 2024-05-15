package pt.ipp.isep.dei.esoft.project.domain.validations;

public class Validations {

    /**
     * Validates if a string is not null or empty.
     *
     * @param string The string to be validated.
     * @return true if the string is not null or empty, false otherwise.
     */
    public static boolean validateString(String string) {
        return (string != null && !string.trim().isEmpty());
    }


    /**
     * Validates if a string contains special characters.
     *
     * @param string The string to be validated.
     * @return true if the string contains special characters, false otherwise.
     */
    public static boolean haveSpecialCharacters(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }


}
