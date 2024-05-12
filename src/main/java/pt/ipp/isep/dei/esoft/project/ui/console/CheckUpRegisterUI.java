package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CreateCheckUpController;
import pt.ipp.isep.dei.esoft.project.domain.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class CheckUpRegisterUI implements Runnable {

    private Data checkUpDate;
    private float checkUpKms;
    private String plateID;
    private Vehicle vehicleByPlateID;

    private final CreateCheckUpController controller;

    public CheckUpRegisterUI() {
        controller = new CreateCheckUpController();
    }

    private CreateCheckUpController getController() {
        return controller;
    }


    public void run() {
        System.out.println("\n\n--- Register Vehicle Check Up ------------------------");

        requestVehiclePlateID();
        vehicleByPlateID = getVehicleByPlateID(plateID);

        if (vehicleByPlateID != null) {
            System.out.println("Vehicle PlateID: " + plateID);
            requestData();

            submitData();

        } else {
            System.out.println("No vehicle was found for plateID: " + plateID);
        }


    }

    private void requestVehiclePlateID() {
        Scanner input = new Scanner(System.in);
        System.out.print("Plate ID in the format: __-__-__: ");

        plateID = input.nextLine();

    }

    private Vehicle getVehicleByPlateID(String plateID) {
        return controller.getVehicleByPlateID(plateID);
    }


    private void requestData() {
        checkUpDate = requestCheckUpDate();

        checkUpKms = requestCheckUpKms();
    }

    private Data requestCheckUpDate() {
        System.out.print("\n-- Check-Up date --\n");
        return getData();
    }

    private Data getData() {
        Data data;
        while (true) {
            int year = requestYear();
            int month = requestMonth();
            int day = requestDay();
            try {
                data = new Data(year, month, day);
                return data;
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            }
        }
    }

    private int requestDay() {
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


    private float requestCheckUpKms() {
        float resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Check-Up Kilometers (KM): ");
        while (true) {
            try {
                resposta = input.nextFloat();
                return resposta;
            } catch (InputMismatchException e) {
                // Clear the invalid input
                System.out.print(ANSI_BRIGHT_RED + "Invalid Current Kilometers! Enter a new one: " + ANSI_RESET);
                // Prompt the user again for a valid input
                input.nextLine();
            }
        }
    }

    private void submitData() {
        // Attempt to register the vehicle using the provided data
        try {

            Optional<CheckUp> checkUp = getController().registerCheckUp(vehicleByPlateID, checkUpKms, checkUpDate);

            // Check if the registration was successful
            if (checkUp.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "Check-Up successfully registered!" + ANSI_RESET);
            } else {
                // Print error message if vehicle is already registered
                System.out.println(ANSI_BRIGHT_RED + "Check-Up not registered - Already registered!" + ANSI_RESET);
            }

        } catch (IllegalArgumentException e) {
            // Catch IllegalArgumentException indicating invalid data
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            // Prompt user to repeat registration process
            //runException();
        }
    }

    private int confirmsPlateID() {
        int option = -1;
        Scanner input = new Scanner(System.in);

        while (option != 1) {
            display();
            option = input.nextInt();

            switch (option) {
                case 0:
                    System.out.println();
                    requestVehiclePlateID();
                    break;
                case 1:
                    System.out.println();
                    System.out.printf("Vehicle License Plate -> [%s]\n", plateID);
                    break;
                case 2:
                    System.out.println(ANSI_BRIGHT_RED + "LEAVING..." + ANSI_RESET);
                    return -1;
                default:
                    System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0 or 1 or 2." + ANSI_RESET);
            }
        }
        return option;
    }

    private void display() {

        System.out.printf("\nTyped License Plate -> [%s%s%s]\n",ANSI_GREEN,plateID,ANSI_RESET);
        System.out.print("Confirmation menu:\n 0 -> Change license plate\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }







}
