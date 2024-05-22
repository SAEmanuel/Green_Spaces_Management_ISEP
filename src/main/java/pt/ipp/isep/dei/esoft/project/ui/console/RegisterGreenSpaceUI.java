package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

/**
 * The {@code RegisterGreenSpaceUI} class handles the user interface for registering a new green space.
 * It interacts with the {@code GreenSpaceController} to perform operations and display appropriate messages.
 */
public class RegisterGreenSpaceUI implements Runnable {

    private final GreenSpaceController controller; // Controller to manage green spaces
    private String name;
    private String address;
    private int size;
    private float area;

    /**
     * Default constructor that initializes the {@code GreenSpaceController}.
     */
    public RegisterGreenSpaceUI() {
        controller = new GreenSpaceController();
    }

    /**
     * Gets the {@code GreenSpaceController} instance.
     *
     * @return The {@code GreenSpaceController} instance.
     */
    private GreenSpaceController getController() {
        return controller;
    }

    //-------------------------------------- Run if happy success -------------------------

    /**
     * Main method that executes the green space registration interface.
     */
    public void run() {
        System.out.println("\n\n--- Register a \"Green Space\" ------------------------");

        requestData();

        int continueApp = confirmsMenu();

        if (continueApp != 2) {
            submitData();
        }
    }

    /**
     * Submits the green space data to be registered.
     */
    private void submitData() {
        // Attempt to register the green space using the provided data
        try {
            Optional<GreenSpace> greenSpace = getController().registerGreenSpace(name, size, area, address);

            // Check if the registration was successful
            if (greenSpace.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "Green Space successfully registered!" + ANSI_RESET);
            } else {
                // Print error message if green space is already registered
                System.out.println(ANSI_BRIGHT_RED + "Green Space not registered - Already registered!" + ANSI_RESET);
            }

        } catch (IllegalArgumentException e) {
            // Catch IllegalArgumentException indicating invalid data
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            // Prompt user to repeat registration process
            runException();
        }
    }

    /**
     * Handles the registration process in case of an exception.
     */
    public void runException() {
        if (repeatProcess()) {
            requestData();
            int continueApp = confirmsMenu();

            if (continueApp != 2) {
                submitData();
            }
        }
    }

    /**
     * Asks the user whether to repeat the registration process.
     *
     * @return True if the user wants to register a new green space, false otherwise.
     */
    private boolean repeatProcess() {
        System.out.print("\nDo you wish to register new info? (y/n): ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                return true;
            }
            if (answer.equalsIgnoreCase("n")) {
                System.out.println(ANSI_BRIGHT_RED + "LEAVING..." + ANSI_RESET);
                return false;
            }
            System.out.println(ANSI_BRIGHT_RED + "WARNING - Enter a valid option..." + ANSI_RESET);
        }
    }

    /**
     * Asks the user to confirm the entered Green Space data.
     *
     * @return 2 if the user chooses to exit, otherwise returns 1.
     */
    private int confirmsMenu() {
        Scanner input = new Scanner(System.in);
        int option = -1;

        while (option != 1) {
            display();
            option = input.nextInt();

            if (option == 0) {
                System.out.println();
                requestData();
            } else if (option == 1) {
                System.out.println();
            } else if (option == 2) {
                System.out.println(ANSI_BRIGHT_RED + "LEAVING..." + ANSI_RESET);
                return option;
            } else {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0 or 1 or 2." + ANSI_RESET);
            }
        }
        return option;
    }

    /**
     * Displays the entered Green Space information and options for confirmation.
     */
    private void display() {
        GreenSpace.Size[] sizes = GreenSpace.Size.values();
        StringBuilder stringBuilder = new StringBuilder(String.format("Name: %s | Size: %s | Area: %.4f | Address: %s", name, sizes[size], area, address));
        System.out.printf("\nTyped data -> [%s%s%s]\n", ANSI_GREEN, stringBuilder, ANSI_RESET);
        System.out.print("Confirmation menu:\n 0 -> Change Green Space Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }

    //-------------------- Data Request -----------------------------------

    /**
     * Requests all necessary data for registering a green space.
     */
    private void requestData() {
        name = requestName();
        area = requestArea();
        address = requestAddress();
        size = requestSize();
    }

    /**
     * Requests the name of the green space.
     *
     * @return The entered name.
     */
    private String requestName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name: ");
        return input.nextLine();
    }

    /**
     * Requests the address of the green space.
     *
     * @return The entered address.
     */
    private String requestAddress() {
        Scanner input = new Scanner(System.in);
        System.out.print("Address: ");
        return input.nextLine();
    }

    /**
     * Requests the area of the green space.
     *
     * @return The entered area.
     */
    private float requestArea() {
        float response;
        Scanner input = new Scanner(System.in);

        System.out.print("Area (hectares): ");
        while (true) {
            try {
                response = input.nextFloat();
                return response;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid Area! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

    /**
     * Requests the size of the green space.
     *
     * @return The entered size option.
     */
    private int requestSize() {
        int answer;
        Scanner input = new Scanner(System.in);
        GreenSpace.Size[] sizes = controller.getGreenSpacesSizes();
        int n = sizes.length;
        showSizes(sizes);

        System.out.print("Sizes options: ");
        while (true) {
            try {
                answer = input.nextInt() - 1;
                if (answer <= n - 1 && answer >= 0) {
                    return answer;
                }
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid data inputted! Enter a new one: " + ANSI_RESET + "\n");
                input.nextLine();
            }
            System.out.print(ANSI_BRIGHT_YELLOW + "Invalid size option, enter a new one: " + ANSI_RESET);
        }
    }

    /**
     * Displays the list of possible sizes for green spaces.
     *
     * @param sizes The array of green space sizes.
     */
    private void showSizes(GreenSpace.Size[] sizes) {
        System.out.println("\n--List of Sizes--");
        for (int i = 0; i < sizes.length; i++) {
            System.out.println("• Size: " + sizes[i] + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]" + ANSI_RESET);
        }
        System.out.println("----------------");
    }
}
