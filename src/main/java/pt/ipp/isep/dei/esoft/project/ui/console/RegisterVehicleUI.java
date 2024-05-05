package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterVehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.Optional;
import java.util.Scanner;

public class RegisterVehicleUI {

    private final RegisterVehicleController controller;

    private String plateId;
    private String brand;
    private String model;
    private String type;

    private float tareWeight;
    private float grossWeight;
    private float currentKm;
    private float checkUpFrequency;

    private Data registerDate;
    private Data acquisitionDate;


    public RegisterVehicleUI(){
        controller = new RegisterVehicleController();
    }

    private RegisterVehicleController getController(){
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Register Vehicle ------------------------");

        requestVehicleInformation();
        submitData();

    }

    private void submitData() {
        Optional<Vehicle> vehicle = getController().registerVehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, registerDate, acquisitionDate);

        if (vehicle.isPresent()) {
            System.out.println("Vehicle successfully registered!");
        } else {
            System.out.println("Vehicle not registered!");
        }
    }

    private void requestVehicleInformation() {
        Scanner input = new Scanner(System.in);

        plateId = requestPlateID(input);

        brand = requestBrand(input);

        model = requestModel(input);

        type = requestType(input);

        tareWeight = requestTareWeight(input);

        grossWeight = requestGrossWeight(input);

        currentKm = requestCurrentKm(input);

        checkUpFrequency = requestCheckUpFrequency(input);

        registerDate = requestRegisterDate(input);

        acquisitionDate = requestAcquisitionDate(input);
    }


    private String requestPlateID(Scanner input) {
        System.out.print("Plate ID: ");
        return input.next();
    }

    private String requestBrand(Scanner input) {
        System.out.print("Brand: ");
        return input.next();
    }

    private String requestModel(Scanner input) {
        System.out.print("Model: ");
        return input.next();
    }

    private String requestType(Scanner input) {
        System.out.print("Type: ");
        return input.next();
    }

    private float requestTareWeight(Scanner input) {
        System.out.print("Tare Weight (KG): ");
        return input.nextFloat();
    }

    private float requestGrossWeight(Scanner input) {
        System.out.print("Gross Weight (KG): ");
        return input.nextFloat();
    }

    private float requestCurrentKm(Scanner input) {
        System.out.print("Current Kilometers (KM): ");
        return input.nextFloat();
    }

    private float requestCheckUpFrequency(Scanner input) {
        System.out.print("Check up frequency: ");
        return input.nextFloat();
    }

    private Data requestRegisterDate(Scanner input) {
        //TODO : TRYS E CATCHS FAZEMOS?
        System.out.print("-- Register date --");
        return getData(input);
    }

    private Data requestAcquisitionDate(Scanner input) {
        System.out.print("-- Acquisition date --");
        return getData(input);
    }



//------------------INPUTS DATAS-------------------------------
private Data getData(Scanner input) {
    int year = requestYear(input);
    int month = requestMonth(input);
    int day = requestDay(input);
    return new Data(year, month, day);
}

    private int requestDay(Scanner input) {
        System.out.print("\n- Day: ");
        return input.nextInt();
    }

    private int requestMonth(Scanner input) {
        System.out.print("\n- Month: ");
        return input.nextInt();
    }

    private int requestYear(Scanner input) {
        System.out.print("\n- Year: ");
        return input.nextInt();
    }


}
