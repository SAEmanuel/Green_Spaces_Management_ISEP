package pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;


public class Password implements Serializable {
    public static final int NUMBER_CAP_LETTERS = 3;
    public static final int NUMBER_DIGITS = 2;
    public static final int PASSWORD_LENGTH = 7;

    private final String pass;

    public Password(String pass) {
        if (!verifyPassword(pass)) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED + "Invalid password!" + ANSI_RESET);
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

    public static String getPasswordInput() {
        String pass;
        Scanner input = new Scanner(System.in);
        System.out.print("- Password: ");
        while (true) {
            try {
                pass = input.nextLine();
                return pass;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid Password! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(pass, password.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pass);
    }

    @Override
    public String toString() {
        return String.format("%s", pass);
    }
}
