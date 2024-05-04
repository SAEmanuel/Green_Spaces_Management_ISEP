package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.VehicleListController;
import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CheckUpListUI {

    private final VehicleListController controller;
    private String activation;

    public CheckUpListUI() {
        controller = new VehicleListController();
    }

    public VehicleListController getController() {
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Request Vehicles check-up list ------------------------");
        requestActivation();
        activation = confirmationActivation();
        submitData();
    }

    private void submitData() {
        Optional<List> checkUp = getController().requestList(activation);

        if (checkUp.isPresent()) {
            System.out.println("Check-up list successfully generated!");
        } else {
            System.out.println("Check-up list not generated!");
        }
    }

    private String requestActivation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter job name: ");
        activation = scanner.nextLine().toLowerCase();
        return activation;
    }


}
