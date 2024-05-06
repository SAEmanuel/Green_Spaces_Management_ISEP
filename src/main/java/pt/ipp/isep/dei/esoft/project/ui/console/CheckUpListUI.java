package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CheckUpListUI implements Runnable {

    private final RegisterVehicleController controller;

    /**
     * Constructor for the class.
     */
    public CheckUpListUI() {
        controller = new RegisterVehicleController();
    }

    /**
     * Method to get the controller.
     *
     * @return The RegisterVehicleController object.
     */
    public RegisterVehicleController getController() {
        return controller;
    }

    /**
     * Main method to run the interface.
     */
    public void run() {
        System.out.println("\n\n--- Request Vehicles check-up list ------------------------");

        submitData();
    }

    /**
     * Method to submit the data.
     */
    private void submitData() {
        Optional<List<Vehicle>> checkUp = Optional.empty();

        if (continueProcess()) {
            checkUp = getController().requestList("y");
        }

        if (checkUp.isPresent()) {
            System.out.println("Check-up list successfully generated!");
            printCheckUpList(checkUp.get());
        } else {
            System.out.println("Check-up list not generated!");
        }
    }

    /**
     * Method to request activation from the user.
     *
     * @return The user's activation input.
     */
    private String requestActivation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to generate the Vehicle's Check-up list? (y/n): ");
        String activation = scanner.nextLine().toLowerCase();

        while (!activation.equals("y") && !activation.equals("n")) {
            System.out.print("Please enter 'y' or 'n': ");
            activation = scanner.nextLine().toLowerCase();
        }

        return activation;
    }

    /**
     * Method to check if the process should continue.
     *
     * @return True if the process should continue, false otherwise.
     */
    private boolean continueProcess() {
        return requestActivation().equals("y");
    }

    /**
     * Method to print the check-up vehicle list.
     *
     * @param checkUp The list of check-up vehicles to print.
     */
    private void printCheckUpList(List<Vehicle> checkUp) {
        for (Vehicle vehicle : checkUp) {
            System.out.println(vehicle);
        }
    }
}
