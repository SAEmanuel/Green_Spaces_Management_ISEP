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
    private int docType;
    private Data birthDate;
    private Data admissionDate;
    private int phoneNumber;
    private int taxPayerNumber;
    private Job job;

    /**
     * Default constructor that initializes the CreateCollaboratorController and RegisterJobController
     */
    public CreateCollaboratorUI() {
        this.collaboratorController = new CreateCollaboratorController();
        this.jobController = new RegisterJobController();
    }

    /**
     * Gets the RegisterJobController instance.
     *
     * @return The RegisterJobController instance.
     */
    public RegisterJobController getJobController() {
        return jobController;
    }

    /**
     * Gets the CreateCollaboratorController instance.
     *
     * @return The CreateCollaboratorController instance.
     */
    private CreateCollaboratorController getController() {
        return collaboratorController;
    }

    /**
     * Main method that executes the collaborator creation interface.
     */
    public void run() {
        System.out.println("\n\n--- Create Collaborator ------------------------");
        requestCollaboratorData();

        if (job != null) {
            int continueApp = confirmsData();
            if (continueApp != 2) {
                submitData();
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Register a Job first!" + ANSI_RESET);
        }
    }

    /**
     * Submits the data to the controller and displays a success or failure message.
     */
    private void submitData() {
        try {
            Optional<Collaborator> collaborator = getController().registerCollaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, job);

            if (collaborator.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "Collaborator successfully created!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_BRIGHT_RED + "Collaborator not created - Already created!" + ANSI_RESET);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            runException();
        }
    }

    /**
     * Asks for confirmation of the data to register and requests the data
     */
    public void runException() {
        if (repeatProcess()) {
            requestCollaboratorData();
            int continueApp = confirmsData();

            if (continueApp != 2) {
                submitData();
            }
        }
    }

    /**
     * Asks for the confirmation for the register of the new information
     *
     * @return true if yes and false otherwise
     */
    private boolean repeatProcess() {
        System.out.print("\nDo you wish to register new information? (y/n): ");
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
     * Confirms the data entered by the user.
     *
     * @return 2 if the user chooses to exit, otherwise returns 1.
     */
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

    /**
     * Displays the typed name, address, email, docType, birthdate, admission date,
     * phone number, taxpayer number, job and options for confirmation.
     */
    private void display() {
        StringBuilder stringBuilder = new StringBuilder(String.format("Name: %s | Address: %s | Email: %s | Doc Type: %s | Birth Date: %s | Admission Date: %s | Phone Number: %d | Tax Payer Number: %d | Job: %s",
                name, address, emailAddress, docType, birthDate, admissionDate, phoneNumber, taxPayerNumber, job));
        System.out.printf("\nTyped data -> [%s%s%s]\n", ANSI_GREEN, stringBuilder, ANSI_RESET);
        System.out.print("Confirmation menu:\n 0 -> Change Collaborator Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }

    /**
     * Requests all the data needed for creation of collaborator
     */
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


    /**
     * Requests admission date
     *
     * @return admission date
     */
    private Data requestAdmissionDate() {
        System.out.print("\n-- Admission date --\n");
        return getData();
    }

    /**
     * Requests birthdate
     *
     * @return birthdate
     */
    private Data requestBirthDate() {
        System.out.print("\n-- Birth date --\n");
        return getData();
    }

    /**
     * Requests data
     *
     * @return data
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
     * Requests year
     *
     * @return year
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


    private int requestCollaboratorDocType() {
        Collaborator.DocType[] values = collaboratorController.getDocType();
        Scanner input = new Scanner(System.in);

        System.out.println("Select a document type:");
        for (int i = 0; i < values.length - 1; i++) {
            System.out.printf("%d -> %s\n", i + 1, values[i].toString());
        }

        int answer;
        do {
            System.out.printf("Enter the number corresponding to the document type (between 1 and %d): ", values.length - 1);
            answer = input.nextInt();
        } while (answer < 1 || answer > values.length - 1);

        return answer - 1;
    }


    /**
     * Requests month
     *
     * @return month
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
     * Requests day
     *
     * @return day
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
     * Requests name
     *
     * @return name
     */
    private String requestCollaboratorName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Name: ");
        return input.next();
    }

    /**
     * Requests address
     *
     * @return address
     */
    private String requestCollaboratorAddress() {
        Scanner input = new Scanner(System.in);
        System.out.print("Address: ");
        return input.nextLine();
    }

    /**
     * Requests email address
     *
     * @return email address
     */
    private String requestCollaboratorEmailAddress() {
        Scanner input = new Scanner(System.in);
        System.out.print("Email: ");
        return input.next().toLowerCase();
    }



    /**
     * Requests phone number
     *
     * @return phone number
     */
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

    /**
     * Requests taxpayer number
     *
     * @return taxpayer number
     */
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

    /**
     * Requests job
     *
     * @return job
     */
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
                        Job job = jobController.getJobRepository().getJob(answer - 1);
                        return job;
                    }
                } catch (InputMismatchException e) {
                    System.out.print(ANSI_BRIGHT_RED + "Invalid jobID number! Enter a new one: " + ANSI_RESET + "\n");
                    input.nextLine();
                }
                System.out.print(ANSI_BRIGHT_YELLOW + "Invalid Job ID, enter a new one: " + ANSI_RESET);

            }
        }
        return null;
    }
}
