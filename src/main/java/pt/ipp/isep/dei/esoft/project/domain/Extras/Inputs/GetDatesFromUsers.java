package pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs;

import pt.ipp.isep.dei.esoft.project.domain.Data;

import java.util.InputMismatchException;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

/**
 * The GetDatesFromUsers class provides methods to prompt users for date inputs.
 */
public class GetDatesFromUsers {

    /**
     * Prompts the user to input a date (day, month, year).
     *
     * @return The Data object representing the date entered by the user.
     */
    public static Data getData() {
        Data date;
        while (true) {
            int year = requestYear();
            int month = requestMonth();
            int day = requestDay();
            try {
                date = new Data(year, month, day);
                return date;
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            }
        }
    }

    /**
     * Prompts the user to input a day.
     * This method prompts the user to input a day as an integer.
     * It handles any invalid inputs gracefully and keeps prompting
     * until a valid day is provided.
     *
     * @return The day input by the user.
     */
    private static int requestDay() {
        int resposta;
        Scanner input = new Scanner(System.in);
        System.out.print("- Day: ");
        while (true) {
            try {
                resposta = input.nextInt();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid DAY! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }


    /**
     * Prompts the user to input a month.
     * This method prompts the user to input a month as an integer.
     * It handles any invalid inputs gracefully and keeps prompting
     * until a valid month is provided.
     *
     * @return The month input by the user.
     */
    private static int requestMonth() {
        int resposta;
        Scanner input = new Scanner(System.in);
        System.out.print("- Month: ");
        while (true) {
            try {
                resposta = input.nextInt();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid MONTH! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

    /**
     * Prompts the user to input a year.
     * This method prompts the user to input a year as an integer.
     * It handles any invalid inputs gracefully and keeps prompting
     * until a valid year is provided.
     *
     * @return The year input by the user.
     */
    private static int requestYear() {
        int resposta;
        Scanner input = new Scanner(System.in);
        System.out.print("- Year: ");
        while (true) {
            try {
                resposta = input.nextInt();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid DAY! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

}
