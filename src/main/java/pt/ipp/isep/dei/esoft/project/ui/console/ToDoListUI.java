package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.ToDoListController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class ToDoListUI implements Runnable {

    private GreenSpace greenSpace;
    private String title;
    private String description;
    private int urgency;
    private int expectedDuration;

    private final ToDoListController controller;

    public ToDoListUI() {
        controller = new ToDoListController();
    }

    private ToDoListController getController() {
        return controller;
    }


    @Override
    public void run() {
        System.out.println("\n\n--- Add New Entry To-Do List ------------------------");

        if(!controller.getGreenSpaces().isEmpty()){
            getGreenSpaceListOption();

            requestData();

            int continueApp = confirmsMenu();

            if (continueApp != 2) {
                submitData();
            }

        } else {
            System.out.println("There are no green spaces created");
        }

    }

    public GreenSpace getGreenSpaceListOption() {
        Scanner input = new Scanner(System.in);

        int answer;
        int n = controller.getGreenSpaces().size();


        controller.getGreenSpaceRepository().showGreenSpaces();

        if (n != 0) {
            System.out.print("Green Space ID: ");
            while (true) {
                try {
                    answer = input.nextInt() - 1;
                    if (answer <= n - 1 && answer >= 0) {
                        greenSpace = controller.getGreenSpaces().get(answer);
                        return greenSpace;
                    }
                } catch (InputMismatchException e) {
                    System.out.print(ANSI_BRIGHT_RED + "Invalid Green Space ID number! Enter a new one: " + ANSI_RESET + "\n");
                    input.nextLine();
                }
                System.out.print(ANSI_BRIGHT_YELLOW + "Invalid Green Space ID, enter a new one: " + ANSI_RESET);

            }
        }
        return null;
    }

    private void requestData() {
       title = requestTitle();
       description = requestDescription();
       urgency = requestUrgency();
       expectedDuration = requestExpectedDuration();

    }

    private int requestUrgency() {
        ToDoEntry.Urgency[] values = controller.getUrgency();
        Scanner input = new Scanner(System.in);


        System.out.print("\n-- Urgency --\n");
        for (int i = 0; i < values.length; i++) {
            System.out.printf("• Level: %s%n", values[i].toString());
            System.out.printf(ANSI_PURPLE + "   Option -> [%d]%n" + ANSI_RESET, i + 1);
        }
        System.out.println("----------------");

        int answer;
        boolean flag = true;
        do {
            if (flag) {
                System.out.print("Enter the number corresponding to the urgency level: ");
                answer = input.nextInt();
                if (answer < 1 || answer > values.length) {
                    flag = false;
                }
            } else {
                System.out.printf(ANSI_BRIGHT_YELLOW + "Invalid number, enter a new one (between 1 and %d): " + ANSI_RESET, values.length);
                answer = input.nextInt();
            }
        } while (answer < 1 || answer > values.length);

        return answer - 1;
    }

    private String requestTitle() {
        Scanner input = new Scanner(System.in);
        System.out.print("Title: ");
        return input.nextLine();
    }

    private String requestDescription() {
        Scanner input = new Scanner(System.in);
        System.out.print("Description: ");
        return input.nextLine();
    }

    private int requestExpectedDuration() {
        int resposta;
        Scanner input = new Scanner(System.in);

        System.out.print("Expected duration (in DAYS): ");
        while (true) {
            try {
                resposta = input.nextInt();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid number! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

    private void submitData() {
        // Attempt to register the vehicle using the provided data
        try {

            Optional<ToDoEntry> toDoEntry = getController().registerToDoEntry(greenSpace, title, description, urgency, expectedDuration);

            // Check if the registration was successful
            if (toDoEntry.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "To-Do entry successfully registered!" + ANSI_RESET);
            } else {
                // Print error message if vehicle is already registered
                System.out.println(ANSI_BRIGHT_RED + "To-Do entry not registered - Already registered!" + ANSI_RESET);
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
            requestData();
            int continueApp = confirmsMenu();

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

    private int confirmsMenu() {
        Scanner input = new Scanner(System.in);
        int option = -1;


        while (option != 1) {
            display();
            option = input.nextInt();

            if (option == 0) {
                System.out.println();
                requestData();
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
        ToDoEntry.Urgency[] urgencies = ToDoEntry.Urgency.values();
        System.out.println("\n\n-- To-Do Entry Review --");
        System.out.println(ANSI_BRIGHT_YELLOW + "Title: " + title + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW + "Description: " + description + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW + "Urgency: " + urgencies[urgency] + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW + "Expected Duration: " + expectedDuration + " day(s)" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW + "Green Space: " + greenSpace.getName() + ANSI_RESET);
        System.out.print("\nConfirmation menu:\n 0 -> Change Green Space Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }




}
