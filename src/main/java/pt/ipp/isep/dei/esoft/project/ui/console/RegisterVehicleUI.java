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


    public RegisterVehicleUI() {
        controller = new VehicleController();
    }

    private VehicleController getController() {
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Register Vehicle ------------------------");

        requestVehicleInformation();
        int continueApp = confirmsData();

        if (continueApp != 2) {
            submitData();
        }

    }
    public void runException() {

        if (repeatProcess()) {
            requestVehicleInformation();
            int continueApp = confirmsData();

            if (continueApp != 2) {
                submitData();
            }
        }

    }

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
     * Confirms the skill data entered by the user.
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
     * Displays the typed skill name and options for confirmation.
     */
    private void display() {
        StringBuilder stringBuilder = new StringBuilder(String.format("Plate: %s | Brand: %s | Model: %s | Current Km: %.2f | Register Date: %s | Acquisition Date: %s", plateId, brand, model, currentKm, registerDate, acquisitionDate));
        System.out.printf("\nTyped data -> [%s%s%s]\n",ANSI_GREEN,stringBuilder,ANSI_RESET);
        System.out.print("Confirmation menu:\n 0 -> Change Vehicle Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }



    private void submitData() {
        try{

            Optional<Vehicle> vehicle = getController().registerVehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);

            if (vehicle.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN+"Vehicle successfully registered!"+ ANSI_RESET);
            } else {
                System.out.println(ANSI_BRIGHT_RED +"Vehicle not registered - Already registered!"+ ANSI_RESET);
            }

        }catch (IllegalArgumentException e){
            System.out.println(ANSI_BRIGHT_RED +e.getMessage()+ANSI_RESET);
            runException();
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

    private float requestCurrentKm() {
        float resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Current Kilometers (KM): ");
        while (true) {
            try {
                resposta = input.nextFloat();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid Current Kilometers! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

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
