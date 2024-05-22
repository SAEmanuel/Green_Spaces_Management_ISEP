package pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;


public class Password {
    public static final int NUMBER_CAP_LETTERS = 3;
    public static final int NUMBER_DIGITS = 2;
    public static final int PASSWORD_LENGTH = 7;

    private final String pass;

    public Password(String pass) {
        if (!verifyPassword(pass)) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED + "Password invalid" + ANSI_RESET);
        }
        this.pass = pass;

    }

    public static boolean verifyPassword(String password) {
        return password.length() == PASSWORD_LENGTH &&
                verifyCapitalLetters(password) && verifyNumber(password);
    }

    private static boolean verifyCapitalLetters(String password) {
        int counter = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                counter++;
            }
        }
        return counter == NUMBER_CAP_LETTERS;
    }

    private static boolean verifyNumber(String password) {
        int counter = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                counter++;
            }
        }
        return counter == NUMBER_DIGITS;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return String.format("%s", pass);
    }
}
