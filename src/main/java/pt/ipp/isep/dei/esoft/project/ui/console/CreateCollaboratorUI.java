package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class CreateCollaboratorUI {
    private Scanner scan = new Scanner(System.in);
    private final CreateCollaboratorController collaboratorController;
    private final RegisterJobController jobController;
    private String name;
    private Data birthDate;
    private Data admissionDate;
    private String address;
    private int phoneNumber;
    private String emailAddress;
    private int taxPayerNumber;
    private String docType;
    private int job;

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

        this.job = confirmationCollaboratorJob();
        this.name = confirmationCollaboratorName();
        this.birthDate = confirmationCollaboratorBirthDate();
        this.admissionDate = confirmationCollaboratorAdmissionDate();
        this.address = confirmationCollaboratorAddress();
        this.phoneNumber = confirmationCollaboratorPhoneNumber();
        this.emailAddress = confirmationCollaboratorEmailAddress();
        this.taxPayerNumber = confirmationCollaboratorTaxPayNumber();
        this.docType = confirmationCollaboratorDocType();
        submitData();
    }

    // resquest---------------------------------------------------------------
    private int requestCollaboratorJob() {
        getJobController().getJobRepository().showJobs();
        job = scan.nextInt();
        return job;
    }
    private String requestCollaboratorDocType() {
        System.out.println("Enter document type: ");
        docType = scan.nextLine().trim();
        return docType;
    }
    private int requestCollaboratorTaxPayerNumber() {
        System.out.println("Enter tax payer number: ");
        taxPayerNumber = scan.nextInt();
        return taxPayerNumber;
    }
    private String requestCollaboratorEmailAddress() {
        System.out.println("Enter email address: ");
        emailAddress = scan.nextLine().trim();
        return emailAddress;
    }
    private int requestCollaboratorPhoneNumber() {
        System.out.println("Enter phone number: ");
        phoneNumber = scan.nextInt();
        return phoneNumber;
    }
    private String requestCollaboratorAddress() {
        System.out.println("Enter address: ");
        address = scan.nextLine().trim();
        return address;
    }
    private Data requestCollaboratorDate() {
        int day, month, year;
        System.out.print("Enter day: ");
        day = scan.nextInt();
        System.out.print("Enter month: ");
        month = scan.nextInt();
        System.out.print("Enter year: ");
        year = scan.nextInt();
        return new Data(year, month, day);
    }
    private String requestCollaboratorName() {
        System.out.print("Enter name: ");
        name = scan.nextLine().trim();
        return name;
    }

    //display------------------------------------------------------------------
    private void displayTypedCollaboratorJob(int typedCollaboratorJob) {
        System.out.printf(ANSI_GREEN + "%nJob selected: %s%n" + ANSI_RESET, getJobController().getJobRepository().getJob(typedCollaboratorJob));
    }
    private void displayTypedCollaboratorDocType(String typedCollaboratorDocType) {
        System.out.printf(ANSI_GREEN + "%nDocument type written: %s%n" + ANSI_RESET, typedCollaboratorDocType);
    }
    private void displayTypedCollaboratorTaxPayerNumber(int typedCollaboratorTaxPayerNumber) {
        System.out.printf(ANSI_GREEN + "%nTax payer number written: %s%n" + ANSI_RESET, typedCollaboratorTaxPayerNumber);
    }
    private void displayTypedCollaboratorEmailAddress(String typedCollaboratorEmailAddress) {
        System.out.printf(ANSI_GREEN + "%nEmail address written: %s%n" + ANSI_RESET, typedCollaboratorEmailAddress);
    }
    private void displayTypedCollaboratorPhoneNumber(int typedCollaboratorPhoneNumber) {
        System.out.printf(ANSI_GREEN + "%nPhone number written: %s%n" + ANSI_RESET, typedCollaboratorPhoneNumber);
    }
    private void displayTypedCollaboratorAddress(String typedCollaboratorAddress) {
        System.out.printf(ANSI_GREEN + "%nAddress written: %s%n" + ANSI_RESET, typedCollaboratorAddress);
    }
    private void displayTypedCollaboratorDate(Data typedCollaboratorBirthDate) {
        System.out.printf(ANSI_GREEN + "%nDate written: %s%n" + ANSI_RESET, typedCollaboratorBirthDate);
    }

    private void displayTypedCollaboratorName(String typedCollaboratorName) {
        System.out.printf(ANSI_GREEN + "%nName written: %s%n" + ANSI_RESET, typedCollaboratorName);
    }

    //confirmation--------------------------------------------------------------------
    private int confirmationCollaboratorJob() {
        int answer = -1;
        int job = requestCollaboratorJob();
        displayTypedCollaboratorJob(job);

        while (answer != 1) {
            System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
            answer = scan.nextInt();
            if (answer == 0) {
                job = requestCollaboratorJob();
                displayTypedCollaboratorJob(job);
            } else if (answer == 2) {
                System.out.println(ANSI_BRIGHT_RED + "No changes made!" + ANSI_RESET);
                return 0;
            } else if (answer != 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
            }
        }
        return job;
    }
    private String confirmationCollaboratorDocType() {
        int answer = -1;
        String docType = requestCollaboratorDocType();
        displayTypedCollaboratorDocType(docType);

        while (answer != 1) {
            System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
            answer = scan.nextInt();
            if (answer == 0) {
                docType = requestCollaboratorDocType();
                displayTypedCollaboratorDocType(docType);
            } else if (answer == 2) {
                System.out.println(ANSI_BRIGHT_RED + "No changes made!" + ANSI_RESET);
                return null;
            } else if (answer != 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
            }
        }
        return docType;
    }
    private int confirmationCollaboratorTaxPayNumber() {
        int answer = -1;
        int taxPayerNumber = requestCollaboratorTaxPayerNumber();
        displayTypedCollaboratorTaxPayerNumber(taxPayerNumber);

        while (answer != 1) {
            System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
            answer = scan.nextInt();
            if (answer == 0) {
                taxPayerNumber = requestCollaboratorTaxPayerNumber();
                displayTypedCollaboratorTaxPayerNumber(taxPayerNumber);
            } else if (answer == 2) {
                System.out.println(ANSI_BRIGHT_RED + "No changes made!" + ANSI_RESET);
                return 0;
            } else if (answer != 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
            }
        }
        return taxPayerNumber;
    }
    private String confirmationCollaboratorEmailAddress() {
        int answer = -1;
        String emailAddress = requestCollaboratorEmailAddress();
        displayTypedCollaboratorEmailAddress(emailAddress);

        while (answer != 1) {
            System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
            answer = scan.nextInt();
            if (answer == 0) {
                emailAddress = requestCollaboratorEmailAddress();
                displayTypedCollaboratorEmailAddress(emailAddress);
            } else if (answer == 2) {
                System.out.println(ANSI_BRIGHT_RED + "No changes made!" + ANSI_RESET);
                return null;
            } else if (answer != 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
            }
        }
        return emailAddress;
    }
    private int confirmationCollaboratorPhoneNumber() {
        int answer = -1;
        int phoneNumber = requestCollaboratorPhoneNumber();
        displayTypedCollaboratorPhoneNumber(phoneNumber);

        while (answer != 1) {
            System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
            answer = scan.nextInt();
            if (answer == 0) {
                phoneNumber = requestCollaboratorPhoneNumber();
                displayTypedCollaboratorPhoneNumber(phoneNumber);
            } else if (answer == 2) {
                System.out.println(ANSI_BRIGHT_RED + "No changes made!" + ANSI_RESET);
                return 0;
            } else if (answer != 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
            }
        }
        return phoneNumber;
    }
    private String confirmationCollaboratorAddress() {
        int answer = -1;
        String address = requestCollaboratorAddress();
        displayTypedCollaboratorAddress(address);

        while (answer != 1) {
            System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
            answer = scan.nextInt();
            if (answer == 0) {
                address = requestCollaboratorAddress();
                displayTypedCollaboratorAddress(address);
            } else if (answer == 2) {
                System.out.println(ANSI_BRIGHT_RED + "No changes made!" + ANSI_RESET);
                return null;
            } else if (answer != 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
            }
        }
        return address;
    }
    private Data confirmationCollaboratorAdmissionDate() {
        int answer = -1;
        Data admissionDate = requestCollaboratorDate();
        displayTypedCollaboratorDate(admissionDate);

        while (answer != 1) {
            System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
            answer = scan.nextInt();
            if (answer == 0) {
                admissionDate = requestCollaboratorDate();
                displayTypedCollaboratorDate(admissionDate);
            } else if (answer == 2) {
                System.out.println(ANSI_BRIGHT_RED + "No changes made!" + ANSI_RESET);
                return null;
            } else if (answer != 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
            }
        }
        return admissionDate;
    }

    private Data confirmationCollaboratorBirthDate() {
        int answer = -1;
        Data birthDate = requestCollaboratorDate();
        displayTypedCollaboratorDate(birthDate);

        while (answer != 1) {
            System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
            answer = scan.nextInt();
            if (answer == 0) {
                birthDate = requestCollaboratorDate();
                displayTypedCollaboratorDate(birthDate);
            } else if (answer == 2) {
                System.out.println(ANSI_BRIGHT_RED + "No changes made!" + ANSI_RESET);
                return null;
            } else if (answer != 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
            }
        }
        return birthDate;
    }

    private String confirmationCollaboratorName() {
        int answer = -1;
        String name = requestCollaboratorName();
        displayTypedCollaboratorName(name);

        while (answer != 1) {
            System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
            answer = scan.nextInt();
            if (answer == 0) {
                name = requestCollaboratorName();
                displayTypedCollaboratorName(name);
            } else if (answer == 2) {
                System.out.println(ANSI_BRIGHT_RED + "No changes made!" + ANSI_RESET);
                return null;
            } else if (answer != 1) {
                System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
            }
        }
        return name;
    }

    /*
    private void submitData() {
        try {
            Optional<Collaborator> collaborator = getCollaboratorController(jobName);

            if (job.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "Job successfully registered!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_BRIGHT_RED + "Duplicated name!" + ANSI_RESET);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(ANSI_BRIGHT_RED + "Job not registered!" + ANSI_RESET);
        }

    }

     */
    //implementar runnable
}
