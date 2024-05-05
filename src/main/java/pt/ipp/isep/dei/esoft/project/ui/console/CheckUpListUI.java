package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CheckUpListUI implements Runnable{

    private final RegisterVehicleController controller;

    public CheckUpListUI() {
        controller = new RegisterVehicleController();
    }

    public RegisterVehicleController getController() {
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Request Vehicles check-up list ------------------------");
        if (shouldContinueProcessing()) {
            submitData();
        } else {
            System.out.println("\n\n--- Vehicles' check-up list not generated ------------------------");
        }
    }

    private void submitData() {
        Optional<List<Vehicle>> checkUp = getController().requestList("y");

        if (checkUp.isPresent()) {
            System.out.println("Check-up list successfully generated!");
        } else {
            System.out.println("Check-up list not generated!");
        }
    }

    private boolean shouldContinueProcessing() {
        String answer = requestActivation();
        return answer.equals("y");
    }

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
}
