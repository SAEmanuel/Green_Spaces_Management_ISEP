package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CreateCheckUpController;
import pt.ipp.isep.dei.esoft.project.domain.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

/**
 * This class represents the user interface for registering vehicle check-ups.
 * It prompts the user for necessary information such as vehicle plate ID, check-up date, and kilometers.
 * The data is then used to register the check-up using a controller.
 */

public class CheckUpRegisterUI implements Runnable {

    // Attributes
    private Data checkUpDate; // Stores the date of the check-up.
    private float checkUpKms; // Stores the kilometers of the check-up.
    private String plateID; // Stores the plate ID of the vehicle.
    private Vehicle vehicleByPlateID; // Stores the vehicle object corresponding to the plate ID.

    private final CreateCheckUpController controller; // Controller for creating check-ups.

    // Constructor
    public CheckUpRegisterUI() {
        controller = new CreateCheckUpController();
    }

    // Methods

    /**
     * Retrieves the CreateCheckUpController instance.
     *
     * @return The CreateCheckUpController instance.
     */
    private CreateCheckUpController getController() {
        return controller;
    }

    /**
     * Runs the user interface for registering a vehicle check-up.
     * It prompts the user for the vehicle plate ID, checks if the vehicle exists,
     * requests check-up date and kilometers, and finally submits the data.
     */
    public void run() {
        System.out.println("\n\n--- Register Vehicle Check Up ------------------------");

        requestVehiclePlateID(); // Requests the plate ID of the vehicle.
        vehicleByPlateID = getVehicleByPlateID(plateID); // Retrieves the vehicle object using the plate ID.

        if (vehicleByPlateID != null) {
            System.out.println();
            System.out.println( ANSI_GREEN+ "Vehicle license plate was found: " + plateID + ANSI_RESET);
            requestData(); // Requests the check-up date and kilometers.
            submitData(); // Submits the collected data for registering the check-up.
        } else {
            System.out.print(ANSI_BRIGHT_RED + "No vehicle was found for license plate: " + plateID + ANSI_RESET);

        }
    }

    /**
     * Requests the user to input the vehicle plate ID.
     */
    private void requestVehiclePlateID() {
        Scanner input = new Scanner(System.in);
        System.out.print("Plate ID in the format: __-__-__: ");
        plateID = input.nextLine();
    }

    /**
     * Retrieves the vehicle with the provided plate ID.
     *
     * @param plateID The plate ID of the vehicle.
     * @return The vehicle with the specified plate ID, or null if not found.
     */
    private Vehicle getVehicleByPlateID(String plateID) {
        return controller.getVehicleByPlateID(plateID);
    }


    /**
     * Requests the check-up date and kilometers from the user.
     * It prompts the user to input the check-up date and kilometers and stores them.
     */
    private void requestData() {
        checkUpDate = requestCheckUpDate(); // Requests the check-up date.
        checkUpKms = requestCheckUpKms(); // Requests the kilometers.
    }

    /**
     * Requests the user to input the check-up date.
     *
     * @return The check-up date input by the user.
     */
    private Data requestCheckUpDate() {
        System.out.print("\n-- Check-Up date --\n");
        return getData(); // Retrieves the check-up date from the user.
    }

    /**
     * Retrieves the check-up date input by the user.
     * It prompts the user to input the year, month, and day of the check-up date,
     * and constructs a Data object with the provided values.
     *
     * @return The check-up date object.
     */
    private Data getData() {
        Data data;
        while (true) {
            int year = requestYear(); // Requests the year.
            int month = requestMonth(); // Requests the month.
            int day = requestDay(); // Requests the day.
            try {
                data = new Data(year, month, day); // Constructs the Data object.
                return data;
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET); // Prints error message for invalid date.
            }
        }
    }

    /**
     * Requests the user to input the day of the check-up date.
     *
     * @return The day input by the user.
     */
    private int requestDay() {
        int resposta;
        Scanner input = new Scanner(System.in);
        System.out.print("- Day: ");
        while (true) {
            try {
                resposta = input.nextInt(); // Retrieves user input for the day.
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid DAY! Enter a new one: " + ANSI_RESET); // Prints error message for invalid input.
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

    private int requestMonth() {
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

    private int requestYear() {
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


    /**
     * Requests the user to input the kilometers of the check-up.
     *
     * @return The kilometers input by the user.
     */
    private float requestCheckUpKms() {
        float resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Check-Up Kilometers (KM): ");
        while (true) {
            try {
                resposta = input.nextFloat(); // Retrieves user input for kilometers.
                return resposta;
            } catch (InputMismatchException e) {
                // Clear the invalid input
                System.out.print(ANSI_BRIGHT_RED + "Invalid Current Kilometers! Enter a new one: " + ANSI_RESET); // Prints error message for invalid input.
                // Prompt the user again for a valid input
                input.nextLine();
            }
        }
    }

    /**
     * Submits the collected data for registering the check-up.
     * It attempts to register the check-up using the provided data.
     * Prints a success message if the registration is successful, or an error message if not.
     */
    private void submitData() {
        try {
            Optional<CheckUp> checkUp = getController().registerCheckUp(vehicleByPlateID, checkUpKms, checkUpDate); // Attempt to register the check-up.

            // Check if the registration was successful
            if (checkUp.isPresent()) {
                System.out.println();
                System.out.println(ANSI_BRIGHT_GREEN + "Check-Up successfully registered!" + ANSI_RESET); // Prints success message.
            } else {
                // Print error message if vehicle is already registered
                System.out.println();
                System.out.println(ANSI_BRIGHT_RED + "Check-Up not registered - Already registered!" + ANSI_RESET); // Prints error message.
            }
        } catch (IllegalArgumentException e) {
            // Catch IllegalArgumentException indicating invalid data
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET); // Prints error message.
            // Prompt user to repeat registration process
            //runException();
        }
    }

}
