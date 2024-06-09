package pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

/**
 * The Password class represents a password input by the user.
 */
public class Password implements Serializable {
    public static final int NUMBER_CAP_LETTERS = 3;
    public static final int NUMBER_DIGITS = 2;
    public static final int PASSWORD_LENGTH = 7;

    private final String pass;

    /**
     * Constructs a Password object with the specified password.
     *
     * @param pass The password to be set.
     * @throws IllegalArgumentException if the password does not meet the criteria.
     */
    public Password(String pass) {
        if (!verifyPassword(pass)) {
            throw new IllegalArgumentException("Invalid password!");
        }
        this.pass = pass;

    }

    /**
     * Verifies if a password meets the criteria.
     *
     * @param password The password to be verified.
     * @return true if the password meets the criteria, false otherwise.
     */
    public static boolean verifyPassword(String password) {
        return password.length() == PASSWORD_LENGTH &&
                verifyCapitalLetters(password) && verifyNumber(password);
    }

    /**
     * Verifies if a password contains the required number of capital letters.
     *
     * @param password The password to be verified.
     * @return true if the password contains the required number of capital letters, false otherwise.
     */
    private static boolean verifyCapitalLetters(String password) {
        int counter = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                counter++;
            }
        }
        return counter == NUMBER_CAP_LETTERS;
    }

    /**
     * Verifies if a password contains the required number of digits.
     *
     * @param password The password to be verified.
     * @return true if the password contains the required number of digits, false otherwise.
     */
    private static boolean verifyNumber(String password) {
        int counter = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                counter++;
            }
        }
        return counter == NUMBER_DIGITS;
    }

    /**
     * Retrieves the password.
     *
     * @return The password.
     */
    public String getPass() {
        return pass;
    }

    /**
     * Prompts the user to input a password.
     *
     * @return The password input by the user.
     */
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

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(pass, password.pass);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(pass);
    }

    /**
     * Returns a string representation of the Password object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("%s", pass);
    }
}
