package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CreateCheckUpController;
import pt.ipp.isep.dei.esoft.project.domain.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

public class CheckUpRegisterUI implements Runnable  {

    private Data checkUpDate;
    private int checkUpKms;

    private final CreateCheckUpController controller;

    public CheckUpRegisterUI() {
        controller = new CreateCheckUpController();
    }

    private CreateCheckUpController getController() {
        return controller;
    }


    public void run() {
        System.out.println("\n\n--- Register Vehicle Check Up ------------------------");


    }

    private void submitData(Vehicle vehicle) {
        CheckUp checkUp = getController().createCheckUp();

        if (checkUp!=null) {
            System.out.println("\nCheck up successfully scheduled!");
        } else {
            System.out.println("\nCheck up not scheduled!");
        }
    }


    private void requestData(Vehicle vehicle) {
        checkUpDate = requestCheckUpDate();

        checkUpKms = requestCheckUpKms(vehicle);
    }


    private Data requestCheckUpDate() {
        return controller.validateDate();
    }

    private int requestCheckUpKms(Vehicle vehicle) {
        return controller.validateKms(vehicle);
    }

}
