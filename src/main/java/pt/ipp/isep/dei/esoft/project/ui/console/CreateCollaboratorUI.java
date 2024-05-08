package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class CreateCollaboratorUI implements Runnable {


    private final CreateCollaboratorController collaboratorController;
    private final RegisterJobController jobController;

    private String name;
    private String address;
    private String emailAddress;
    private String docType;
    private Data birthDate;
    private Data admissionDate;
    private int phoneNumber;
    private int taxPayerNumber;
    private Job job;

    public CreateCollaboratorUI() {
        this.collaboratorController = new CreateCollaboratorController();
        this.jobController = new RegisterJobController();
    }

    public RegisterJobController getJobController() {
        return jobController;
    }

    public CreateCollaboratorController getCollaboratorController() {
        return collaboratorController;
    }

    public void run() {
        System.out.println("\n\n--- Create Collaborator ------------------------");
        requestCollaboratorData();

        if (job != null) {
            int continueApp = confirmsData();
            if (continueApp != 2) {
                submitData();
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED+"Register a Job first!"+ANSI_RESET);
        }

    }

    private CreateCollaboratorController getController() {
        return collaboratorController;
    }


    private void submitData() {
        // Attempt to register the vehicle using the provided data
        try {
            Optional<Collaborator> collaborator = getController().registerCollaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, job);

            // Check if the registration was successful
            if (collaborator.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "Collaborator successfully created!" + ANSI_RESET);
            } else {
                // Print error message if vehicle is already registered
                System.out.println(ANSI_BRIGHT_RED + "Collaborator not created - Already created!" + ANSI_RESET);
            }

        } catch (IllegalArgumentException e) {
            // Catch IllegalArgumentException indicating invalid data
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            // Prompt user to repeat registration process
            runException();
        }
    }

    public void runException() {

        if (repeatProcess()) {
            requestCollaboratorData();
            int continueApp = confirmsData();

            if (continueApp != 2) {
                submitData();
            }
        }

    }

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


    private int confirmsData() {
        Scanner input = new Scanner(System.in);
        int option = -1;


        while (option != 1) {
            display();
            option = input.nextInt();

            if (option == 0) {
                System.out.println();
                requestCollaboratorData();
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

    private void display() {
        StringBuilder stringBuilder = new StringBuilder(String.format("Name: %s | Address: %s | Email: %s | Doc Type: %s | Birth Date: %s | Admission Date: %s | Phone Number: %d | Tax Payer Number: %d | Job: %s",
                name, address, emailAddress, docType, birthDate, admissionDate, phoneNumber, taxPayerNumber, job));
        System.out.printf("\nTyped data -> [%s%s%s]\n", ANSI_GREEN, stringBuilder, ANSI_RESET);
        System.out.print("Confirmation menu:\n 0 -> Change Collaborator Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }

    // resquest---------------------------------------------------------------
    private void requestCollaboratorData() {
        job = requestCollaboratorJob();
        if (job != null) {
            name = requestCollaboratorName();
            address = requestCollaboratorAddress();
            phoneNumber = requestCollaboratorPhoneNumber();
            emailAddress = requestCollaboratorEmailAddress();
            taxPayerNumber = requestCollaboratorTaxPayerNumber();
            docType = requestCollaboratorDocType();
            birthDate = requestBirthDate();
            admissionDate = requestAdmissionDate();
        }
    }


    private Data requestAdmissionDate() {
        System.out.print("\n-- Admission date --\n");
        return getData();
    }


    private Data requestBirthDate() {
        System.out.print("\n-- Birth date --\n");
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


    private String requestCollaboratorName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name: ");
        return input.next();
    }

    private String requestCollaboratorAddress() {
        Scanner input = new Scanner(System.in);
        System.out.print("Address: ");
        return input.nextLine();
    }

    private String requestCollaboratorEmailAddress() {
        Scanner input = new Scanner(System.in);
        System.out.print("Email: ");
        return input.next();
    }

    private String requestCollaboratorDocType() {
        Scanner input = new Scanner(System.in);
        System.out.print("Doc Type: ");
        return input.next();
    }

    private int requestCollaboratorPhoneNumber() {
        int resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Phone Number: ");
        while (true) {
            try {
                resposta = input.nextInt();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid phone number! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

    private int requestCollaboratorTaxPayerNumber() {
        int resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Tax Payer Number: ");
        while (true) {
            try {
                resposta = input.nextInt();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid tax payer number! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

    private Job requestCollaboratorJob() {
        Scanner input = new Scanner(System.in);
        int answer;
        int n = jobController.getJobRepository().numberCollaborators();
        jobController.getJobRepository().showJobs();

        if (n != 0) {
            System.out.print("Job ID: ");
            while (true) {
                try {
                    answer = input.nextInt();
                    if (answer <= n - 1 && answer >= 0) {
                        Job job = jobController.getJobRepository().getJob(answer);
                        return job;
                    }
                } catch (InputMismatchException e) {
                    System.out.print(ANSI_BRIGHT_RED + "Invalid jobID number! Enter a new one: " + ANSI_RESET +"\n");
                    input.nextLine();
                }
                System.out.print(ANSI_BRIGHT_YELLOW+"Invalid Job ID, enter a new one: "+ANSI_RESET);

            }
        }
        return null;
    }

}
