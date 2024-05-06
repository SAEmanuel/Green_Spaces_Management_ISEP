package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.Optional;
import java.util.Scanner;

public class RegisterVehicleUI implements Runnable{

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


    public RegisterVehicleUI(){
        controller = new VehicleController();
    }

    private VehicleController getController(){
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Register Vehicle ------------------------");

        requestVehicleInformation();
        submitData();

    }

    private void submitData() {
        Optional<Vehicle> vehicle = getController().registerVehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);

        if (vehicle.isPresent()) {
            System.out.println("Vehicle successfully registered!");
        } else {
            System.out.println("Vehicle not registered!");
        }
    }

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



    private String requestPlateID() {
        Scanner input = new Scanner(System.in);
        System.out.print("Plate ID: ");
        return input.next();
    }

    private String requestBrand() {
        Scanner input = new Scanner(System.in);
        System.out.print("Brand: ");
        return input.next();
    }

    private String requestModel() {
        Scanner input = new Scanner(System.in);
        System.out.print("Model: ");
        return input.next();
    }

    private String requestType() {
        Scanner input = new Scanner(System.in);
        System.out.print("Type: ");
        return input.next();
    }

    private float requestTareWeight() {
        Scanner input = new Scanner(System.in);
        System.out.print("Tare Weight (KG): ");
        return input.nextFloat();
    }

    private float requestGrossWeight() {
        Scanner input = new Scanner(System.in);
        System.out.print("Gross Weight (KG): ");
        return input.nextFloat();
    }

    private float requestCurrentKm() {
        Scanner input = new Scanner(System.in);
        System.out.print("Current Kilometers (KM): ");
        return input.nextFloat();
    }

    private float requestCheckUpFrequency() {
        Scanner input = new Scanner(System.in);
        System.out.print("Check up frequency (KM): ");
        return input.nextFloat();
    }

    private float requestLastCheckUp() {
        Scanner input = new Scanner(System.in);
        System.out.print("Last Check up (KM): ");
        return input.nextFloat();
    }

    private Data requestRegisterDate() {
        System.out.print("\n-- Register date --\n");
        return getData();
    }

    private Data requestAcquisitionDate() {
        System.out.print("-- Acquisition date --\n");
        return getData();
    }



//------------------INPUTS DATAS-------------------------------
private Data getData() {
    Scanner input = new Scanner(System.in);
    int year = requestYear();
    int month = requestMonth();
    int day = requestDay();
    return new Data(year, month, day);
}

    private int requestDay( ) {
        Scanner input = new Scanner(System.in);
        System.out.print("- Day: ");
        return input.nextInt();
    }

    private int requestMonth() {
        Scanner input = new Scanner(System.in);
        System.out.print("- Month: ");
        return input.nextInt();
    }

    private int requestYear() {
        Scanner input = new Scanner(System.in);
        System.out.print("- Year: ");
        return input.nextInt();
    }


}
