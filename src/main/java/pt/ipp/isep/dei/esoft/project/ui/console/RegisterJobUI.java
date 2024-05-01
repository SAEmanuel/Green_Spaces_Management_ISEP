package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobController;
import pt.ipp.isep.dei.esoft.project.domain.Job;


import java.util.Optional;
import java.util.Scanner;


public class RegisterJobUI {

    private final RegisterJobController controller;
    private String jobName;
    private Scanner scanner;


    public RegisterJobUI() {
        controller = new RegisterJobController();
    }

    public RegisterJobController getController() {
        return controller;
    }


    public void run() {
        System.out.println("\n\n--- Register Job ------------------------");

        requestJobName();
        jobName = confirmationJobName();
        submitData();

    }

    private void submitData() {
        Optional<Job> job = getController().registerJob(new Job(jobName));

        if (job.isPresent()) {
            System.out.println("Job successfully registered!");
        } else {
            System.out.println("Job not registered!");
        }
    }


    private String requestJobName() {
        scanner = new Scanner(System.in);
        System.out.print("Enter job name: ");
        return scanner.nextLine();
    }

    private void displayTypedJob() {
        System.out.println(requestJobName());

    }

    private void displayTypedJob(String typedJobName) {
        typedJobName = requestJobName();
        System.out.println(typedJobName);

    }

    private String confirmationJobName() {
        scanner = new Scanner(System.in);
        int answer = -1;
        String newAnswer;
        System.out.print("Entered job name: ");
        displayTypedJob();

        while (answer != 1) {
            System.out.print("Do you wish to continue with the name chosen? (0 -> no / 1 -> yes?): ");
            answer = scanner.nextInt();
            if (answer == 0) {
                newAnswer = requestJobName();
                displayTypedJob(newAnswer);
            } else if (answer != 1) {
                System.out.println("Invalid choice. Please enter 0 or 1.");
            }
        }
        return jobName;
    }
}
