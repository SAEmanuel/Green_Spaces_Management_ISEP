package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;



public class RegisterVehicleUI implements Runnable {

    private final VehicleController controller;

    private String plateId;
    private String brand;
    private String model;
    private String type;

    private float tareWeight;
    private float grossWeight;
    private float currentKm;
    private float checkUpFrequency;
    private float lastCheckUp;

    private Data registerDate;
    private Data acquisitionDate;


    /**
     * Constructor for the class.
     */
    public RegisterVehicleUI() {
        controller = new VehicleController();
    }


    /**
     * Retrieves the VehicleController instance.
     *
     * @return The VehicleController instance.
     */
    private VehicleController getController() {
        return controller;
    }


    /**
     * Executes the user interface logic for registering a vehicle.
     */
    public void run() {
        System.out.println("\n\n--- Register Vehicle ------------------------");

        requestVehicleInformation();
        int continueApp = confirmsData();

        if (continueApp != 2) {
            submitData();
        }

    }


    /**
     * Executes the user interface logic for registering a vehicle in case of an exception.
     */
    public void runException() {

        if (repeatProcess()) {
            requestVehicleInformation();
            int continueApp = confirmsData();

            if (continueApp != 2) {
                submitData();
            }
        }

    }


    /**
     * Asks the user whether to repeat the registration process.
     *
     * @return True if the user wants to register a new vehicle, false otherwise.
     */
    private boolean repeatProcess() {
        System.out.print("\nDo you wish to register new info? (y/n): ");
        Scanner scanner = new Scanner(System.in);
        while(true){
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                return true;
            }
            if (answer.equalsIgnoreCase("n")) {
                System.out.println(ANSI_BRIGHT_RED+"LEAVING..."+ANSI_RESET);
                return false;
            }
            System.out.println(ANSI_BRIGHT_RED+"WARNING - Enter a valid option..."+ANSI_RESET);
        }
    }


    /**
     * Asks the user to confirm the entered vehicle data.
     *
     * @return 2 if the user chooses to exit, otherwise returns 1.
     */
    private int confirmsData() {
        Scanner input = new Scanner(System.in);
        int option = -1;


        while(option != 1){
            display();
            option = input.nextInt();

            if (option == 0) {
                System.out.println();
                requestVehicleInformation();
            }else if (option == 1) {
                System.out.println();
            } else if (option == 2) {
                System.out.println(ANSI_BRIGHT_RED+"LEAVING..."+ANSI_RESET);
                return option;
            } else {
                System.out.println(ANSI_BRIGHT_RED+ "Invalid choice. Please enter 0 or 1 or 2."+ ANSI_RESET);
            }
        }
        return option;
    }


    /**
     * Displays the typed vehicle information and options for confirmation.
     */
    private void display() {
        StringBuilder stringBuilder = new StringBuilder(String.format("Plate: %s | Brand: %s | Model: %s | Current Km: %.2f | Register Date: %s | Acquisition Date: %s", plateId, brand, model, currentKm, registerDate, acquisitionDate));
        System.out.printf("\nTyped data -> [%s%s%s]\n",ANSI_GREEN,stringBuilder,ANSI_RESET);
        System.out.print("Confirmation menu:\n 0 -> Change Vehicle Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }


    /**
     * Submits the entered vehicle data for registration.
     *
     *
     * This method invokes the registration process for the vehicle using the
     * provided data. It calls the corresponding method in the VehicleController
     * to register the vehicle in the repository. If the registration is successful,
     * it prints a success message. If the registration fails due to duplicate
     * registration (i.e., the vehicle is already registered), it prints an error
     * message.
     *
     * If an IllegalArgumentException occurs during the registration process,
     * indicating invalid data, it catches the exception, prints the error message,
     * and prompts the user to repeat the registration process.
     */
    private void submitData() {
        // Attempt to register the vehicle using the provided data
        try{

            Optional<Vehicle> vehicle = getController().registerVehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);

            // Check if the registration was successful
            if (vehicle.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN+"Vehicle successfully registered!"+ ANSI_RESET);
            } else {
                // Print error message if vehicle is already registered
                System.out.println(ANSI_BRIGHT_RED +"Vehicle not registered - Already registered!"+ ANSI_RESET);
            }

        }catch (IllegalArgumentException e){
            // Catch IllegalArgumentException indicating invalid data
            System.out.println(ANSI_BRIGHT_RED +e.getMessage()+ANSI_RESET);
            // Prompt user to repeat registration process
            runException();
        }
    }

    /**
     * Requests vehicle information from the user.
     */
    private void requestVehicleInformation() {

        plateId = requestPlateID();

        brand = requestBrand();

        model = requestModel();

        type = requestType();

        tareWeight = requestTareWeight();

        grossWeight = requestGrossWeight();

        currentKm = requestCurrentKm();

        checkUpFrequency = requestCheckUpFrequency();

        lastCheckUp = requestLastCheckUp();

        registerDate = requestRegisterDate();

        acquisitionDate = requestAcquisitionDate();
    }

    /**
     * Requests the plate ID of the vehicle from the user.
     *
     * @return The plate ID entered by the user.
     */
    private String requestPlateID() {
        Scanner input = new Scanner(System.in);
        System.out.print("Plate ID: ");
        return input.next();
    }


    /**
     * Requests the brand of the vehicle from the user.
     *
     * @return The brand entered by the user.
     */
    private String requestBrand() {
        Scanner input = new Scanner(System.in);
        System.out.print("Brand: ");
        return input.next();
    }

    /**
     * Requests the model of the vehicle from the user.
     *
     * @return The model entered by the user.
     */
    private String requestModel() {
        Scanner input = new Scanner(System.in);
        System.out.print("Model: ");
        return input.next();
    }

    /**
     * Requests the type of the vehicle from the user.
     *
     * @return The type entered by the user.
     */
    private String requestType() {
        Scanner input = new Scanner(System.in);
        System.out.print("Type: ");
        return input.next();
    }


    /**
     * Requests the tare weight of the vehicle from the user.
     *
     * @return The tare weight entered by the user.
     */
    private float requestTareWeight() {
        float resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Tare Weight (KG): ");
        while (true) {
            try {
                resposta = input.nextFloat();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid tare weight! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

    /**
     * Requests the gross weight of the vehicle from the user.
     *
     * @return The gross weight entered by the user.
     */
    private float requestGrossWeight() {
        float resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Gross Weight (KG): ");
        while (true) {
            try {
                resposta = input.nextFloat();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid Gross Weight! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }


    /**
     * Requests the current kilometers of the vehicle from the user.
     *
     * @return The current kilometers entered by the user.
     */
    private float requestCurrentKm() {
        float resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Current Kilometers (KM): ");
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


    /**
     * Prompts the user to input the frequency of check-ups in kilometers.
     * <p>
     * This method continuously prompts the user to enter the frequency
     * of check-ups in kilometers until a valid floating-point number is provided.
     *
     * @return The frequency of check-ups in kilometers input by the user.
     */
    private float requestCheckUpFrequency() {
        float resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Check up frequency (KM): ");
        while (true) {
            try {
                resposta = input.nextFloat();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid Check up frequency! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }


    /**
     * Prompts the user to input the distance traveled since the last check-up.
     * <p>
     * This method continuously prompts the user to enter the distance
     * traveled since the last check-up until a valid floating-point number is provided.
     *
     * @return The distance traveled since the last check-up input by the user.
     */
    private float requestLastCheckUp() {
        float resposta;
        Scanner input = new Scanner(System.in);
        System.out.print("Last Check up (KM): ");
        while (true) {
            try {
                resposta = input.nextFloat();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid Check up frequency! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }


    /**
     * Prompts the user to input the registration date.
     * <p>
     * This method prompts the user to input the registration date
     * by asking for the year, month, and day separately. It handles
     * any invalid inputs gracefully and keeps prompting until a valid date is provided.
     *
     * @return The registration date input by the user.
     */
    private Data requestRegisterDate() {
        System.out.print("\n-- Register date --\n");
        return getData();
    }


    /**
     * Prompts the user to input the acquisition date.
     * This method prompts the user to input the acquisition date
     * by asking for the year, month, and day separately. It handles
     * any invalid inputs gracefully and keeps prompting until a valid date is provided.
     *
     * @return The acquisition date input by the user.
     */
    private Data requestAcquisitionDate() {
        System.out.print("-- Acquisition date --\n");
        return getData();
    }


    //------------------INPUTS DATAS-------------------------------
    /**
     * Prompts the user to input a date (year, month, and day).
     * This method prompts the user to input a date by asking for
     * the year, month, and day separately. It handles any invalid inputs
     * gracefully and keeps prompting until a valid date is provided.
     *
     * @return The date input by the user.
     */
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

    /**
     * Prompts the user to input a day.
     * This method prompts the user to input a day as an integer.
     * It handles any invalid inputs gracefully and keeps prompting
     * until a valid day is provided.
     *
     * @return The day input by the user.
     */
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


}
