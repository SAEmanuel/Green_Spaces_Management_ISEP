package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobController;
import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

/**
 * UI class for registering jobs.
 */
public class RegisterJobUI implements Runnable {

    private final RegisterJobController controller;
    private String jobName;
    private Scanner scanner;

    /**
     * Constructs a RegisterJobUI.
     */
    public RegisterJobUI() {
        controller = new RegisterJobController();
    }

    /**
     * Retrieves the register job controller.
     *
     * @return the register job controller
     */
    public RegisterJobController getController() {
        return controller;
    }

    /**
     * Runs the register job UI.
     */
    public void run() {
        System.out.println("\n\n--- Register Job ------------------------");

        jobName = confirmationJobName();
        submitData();
    }

    /**
     * Submits the job registration data to the controller.
     */
    private void submitData() {
        try {
            Optional<Job> job = getController().registerJob(jobName);

            if (job.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "Job successfully registered!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_BRIGHT_RED + "Job not registered - Already registered!" + ANSI_RESET);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
        }

    }

    /**
     * Requests the job name from the user.
     *
     * @return the job name entered by the user
     */
    private String requestJobName() {
        scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        jobName = scanner.nextLine().trim();
        return jobName;
    }

    /**
     * Displays the typed job name.
     *
     * @param typedJobName the typed job name
     */
    private void displayTypedJob(String typedJobName) {
        System.out.printf("%nTyped name -> [" + ANSI_GREEN + "%s" + ANSI_RESET + "]%n", typedJobName);
    }

    /**
     * Prompts the user for job name confirmation.
     *
     * @return the confirmed job name
     */
    private String confirmationJobName() {
        Scanner scanner = new Scanner(System.in);
        int answer = -1;
        String newAnswer;
        String jobName = requestJobName();
        displayTypedJob(jobName);
        boolean flag = true;
        while (answer != 1 && flag) {
            try {
                System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
                if (scanner.hasNextInt()) {
                    answer = scanner.nextInt();
                    if (answer == 0) {
                        jobName = null;
                        newAnswer = requestJobName();
                        displayTypedJob(newAnswer);
                    } else if (answer == 2) {
                        System.out.println(ANSI_BRIGHT_RED + "LEAVING..." + ANSI_RESET);
                        return null;
                    } else if (answer != 1) {
                        System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
                    }
                } else {
                    scanner.next();
                    System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0, 1 or 2." + ANSI_RESET);
                }
            } catch (InputMismatchException e) {
                flag = false;
                System.out.println("Invalid input. Please try again.");
            }
        }
        return jobName;
    }

}
