package pt.ipp.isep.dei.esoft.project.domain.validations;

public class Validations {


    //-------------------------- String Validations ---------------------------------------------
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


    /**
     * Validates if the skill name contains only letters and spaces.
     *
     * @param skillName The skill name to validate.
     * @return True if the skill name contains only letters and spaces; otherwise, false.
     */
    public static boolean hasOnlyLettersAndSpaces(String skillName) {
        //This loop iterate through each character in the string
        for (int i = 0; i < skillName.length(); i++) {
            char ch = skillName.charAt(i);
            // Check if the character is not a letter
            if (!Character.isLetter(ch) && skillName.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }


    //-------------------------------------------------------------------------------------------------

}
