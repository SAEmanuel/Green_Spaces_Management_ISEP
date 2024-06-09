package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;
import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password.getPasswordInput;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class CreateCollaboratorUI implements Runnable {
    private final CreateCollaboratorController collaboratorController;
    private final RegisterJobController jobController;
    private String name;
    private String address;
    private String emailAddress;
    private String docNumber;
    private Data birthDate;
    private Data admissionDate;
    private Password password;
    private Job job;
    private int docType;
    private int phoneNumber;
    private int taxPayerNumber;

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
            Optional<Collaborator> collaborator = getController().registerCollaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, docNumber, job, password);

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
        Collaborator.DocType[] values = collaboratorController.getDocType();

        StringBuilder stringBuilder = new StringBuilder(String.format("Name: %s | Address: %s | Email: %s | Doc Type: %s | Doc Number: %s | Birth Date: %s | Admission Date: %s | Phone Number: %d | Tax Payer Number: %d | Job: %s | Password: %s",
                name, address, emailAddress, values[docType], docNumber, birthDate, admissionDate, phoneNumber, taxPayerNumber, job, password));
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
            docNumber = requestCollaboratorDocNumber();
            birthDate = requestBirthDate();
            admissionDate = requestAdmissionDate();
            password = requestPassword();
        }
    }

    /**
     * Prompts the user to enter a collaborator's password and provides specific password requirements.
     * The password must have 7 alphanumeric characters, including three capital letters and two digits.
     *
     * @return A Password object representing the entered password.
     */
    private Password requestPassword() {
        System.out.print("\n-- Collaborator's Password --\n");
        System.out.println(ANSI_BRIGHT_YELLOW + "(Password must have 7 alphanumeric characters, including three capital letters and two digits)" + ANSI_RESET);
        return getPassword();
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
     * Continuously prompts the user to enter a valid password until a valid password is provided.
     * The password is validated based on the rules defined in the Password class.
     * If an invalid password is entered, an error message is displayed and the user is prompted again.
     *
     * @return A Password object representing the valid entered password.
     */
    private Password getPassword() {
        Password password;
        while (true) {
            String pass = requestPass();
            try {
                password = new Password(pass);
                return password;
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            }
        }
    }

    /**
     * Requests and retrieves a password input from the user.
     *
     * @return A String representing the entered password.
     */
    private String requestPass() {
        return getPasswordInput();
    }

    /**
     * Requests year
     *
     * @return year
     */
    private int requestYear() {
        int answer;
        Scanner input = new Scanner(System.in);
        System.out.print("- Year: ");
        while (true) {
            try {
                answer = input.nextInt();
                return answer;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid DAY! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }


    /**
     * Prompts the user to select a document type for the collaborator from the available options.
     * Get the document type values from the collaborator controller
     * Display the document type options for the user
     *
     * @return The (index - 1) of the document type selected by the user.
     */
    private int requestCollaboratorDocType() {
        Collaborator.DocType[] values = collaboratorController.getDocType();
        Scanner input = new Scanner(System.in);

        System.out.print("\n-- Document type --\n");
        for (int i = 0; i < values.length; i++) {
            System.out.printf("• DocType: %s%n", values[i].toString());
            System.out.printf(ANSI_PURPLE + "   Option -> [%d]%n" + ANSI_RESET, i + 1);
        }
        System.out.println("----------------");

        int answer = 0;
        boolean flag = true;
        boolean leave = true;

        do {
            try {
                if (flag && leave) {
                    System.out.print("Enter the number corresponding to the document type: ");
                    answer = input.nextInt();
                    if (answer < 1 || answer > values.length) {
                        flag = false;
                    }
                } else {
                    System.out.printf(ANSI_BRIGHT_YELLOW + "Invalid number, enter a new one (between 1 and %d): " + ANSI_RESET, values.length);
                    input.next();
                    answer = input.nextInt();
                }
            } catch (InputMismatchException e) {
                leave = false;
            }
        } while (answer < 1 || answer > values.length);

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
     * Requests document number
     *
     * @return document number
     */
    private String requestCollaboratorDocNumber() {
        String resposta;
        Collaborator.DocType[] values = collaboratorController.getDocType();
        Scanner input = new Scanner(System.in);

        System.out.printf("Doc Type selected -> [" + ANSI_GREEN + "%s" + ANSI_RESET + "]%n", values[docType]);
        switch (values[docType]) {
            case PASSPORT:
                System.out.println(ANSI_BRIGHT_YELLOW + "Must have 6 digits and two letters in the beginning." + ANSI_RESET);
                break;
            case CITIZEN_CARD:
                System.out.println(ANSI_BRIGHT_YELLOW + "Must have 9 digits." + ANSI_RESET);
                break;
            case RESIDENCE_PERMIT:
                System.out.println(ANSI_BRIGHT_YELLOW + "Must have between 7 digits and two letters." + ANSI_RESET);
                break;
        }
        System.out.print("Document Number: ");
        while (true) {
            try {
                resposta = input.nextLine();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid document number! Enter a new one: " + ANSI_RESET);
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
                    answer = input.nextInt() - 1;
                    if (answer <= n - 1 && answer >= 0) {
                        Job job = jobController.getJobRepository().getJob(answer);
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

