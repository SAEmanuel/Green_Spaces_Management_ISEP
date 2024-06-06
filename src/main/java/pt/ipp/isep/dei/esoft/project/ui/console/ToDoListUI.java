package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.DTOS.GreenSpaceDTO;
import pt.ipp.isep.dei.esoft.project.application.controller.ToDoListController;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import java.util.*;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

/**
 * The ToDoListUI class provides a console-based user interface for adding new entries to a to-do list.
 * It interacts with the user to gather information about the to-do entry and submits the entry to the controller.
 */
public class ToDoListUI implements Runnable {

    private GreenSpaceDTO greenSpaceDTO;
    private int greenSpaceId;
    private String title;
    private String description;
    private int urgency;
    private int expectedDuration;

    private final ToDoListController controller;

    /**
     * Constructs a new ToDoListUI instance and initializes the controller.
     */
    public ToDoListUI() {
        controller = new ToDoListController();
    }

    /**
     * Gets the ToDoListController instance.
     *
     * @return the controller instance
     */
    private ToDoListController getController() {
        return controller;
    }

    /**
     * Runs the to-do list UI, prompting the user to add a new entry.
     */
    @Override
    public void run() {
        System.out.println("\n\n--- Add New Entry To-Do List ------------------------");
        List<GreenSpaceDTO> greenSpacesAvailableByResponsible;

        greenSpacesAvailableByResponsible = controller.getGreenSpaceDTOByResponsible();

        if (!greenSpacesAvailableByResponsible.isEmpty()) {
            getGreenSpaceListOption(greenSpacesAvailableByResponsible);
            requestData();
            int continueApp = confirmsMenu();
            if (continueApp != 2) {
                submitData();
            }
        } else {
            System.out.println("You are not responsible for any green space");
        }
    }

    /**
     * Prompts the user to select a green space from the list of available green spaces.
     *
     * @param greenSpacesAvailableByResponsible the list of green spaces
     * @return 0 if a valid green space is selected, -1 otherwise
     */
    public int getGreenSpaceListOption(List<GreenSpaceDTO> greenSpacesAvailableByResponsible) {
        Scanner input = new Scanner(System.in);
        int answer;
        int n = greenSpacesAvailableByResponsible.size();

        showGreenSpaces(greenSpacesAvailableByResponsible);

        if (n != 0) {
            System.out.print("Green Space ID: ");
            while (true) {
                try {
                    answer = input.nextInt() - 1;
                    if (answer <= n - 1 && answer >= 0) {
                        greenSpaceDTO = greenSpacesAvailableByResponsible.get(answer);
                        greenSpaceId = answer;
                        return 0;
                    }
                } catch (InputMismatchException e) {
                    System.out.print(ANSI_BRIGHT_RED + "Invalid Green Space ID number! Enter a new one: " + ANSI_RESET + "\n");
                    input.nextLine();
                }
                System.out.print(ANSI_BRIGHT_YELLOW + "Invalid Green Space ID, enter a new one: " + ANSI_RESET);
            }
        }
        return -1;
    }

    /**
     * Displays a list of green spaces available for a responsible person.
     *
     * @param greenSpacesAvailableByResponsible the list of green spaces
     */
    public void showGreenSpaces(List<GreenSpaceDTO> greenSpacesAvailableByResponsible) {
        if (greenSpacesAvailableByResponsible.isEmpty()) {
            System.out.println(ANSI_BRIGHT_RED + "No green spaces were found in the repository." + ANSI_RESET);
        } else {
            System.out.println("\n--List of Green Spaces--");
            for (int i = 0; i < greenSpacesAvailableByResponsible.size(); i++) {
                GreenSpaceDTO greenSpace = greenSpacesAvailableByResponsible.get(i);
                System.out.println("• Green Space: " + greenSpace.getObjDto().getName() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]" + ANSI_RESET);
            }
            System.out.println("----------------");
        }
    }

    /**
     * Prompts the user to enter the details of the to-do entry.
     */
    private void requestData() {
        title = requestTitle();
        description = requestDescription();
        urgency = requestUrgency();
        expectedDuration = requestExpectedDuration();
    }

    /**
     * Prompts the user to enter the urgency level for the to-do entry.
     *
     * @return the urgency level
     */
    private int requestUrgency() {
        ToDoEntry.Urgency[] values = controller.getUrgency();
        Scanner input = new Scanner(System.in);

        System.out.print("\n-- Urgency --\n");
        for (int i = 0; i < values.length; i++) {
            System.out.printf("• Level: %s%n", values[i].toString());
            System.out.printf(ANSI_PURPLE + "   Option -> [%d]%n" + ANSI_RESET, i + 1);
        }
        System.out.println("----------------");

        int answer = 0;
        boolean flag = true, leave = true;

        do {
            try {
                if (flag && leave) {
                    System.out.print("Enter the number corresponding to the urgency level: ");
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
     * Prompts the user to enter the title for the to-do entry.
     *
     * @return the title
     */
    private String requestTitle() {
        Scanner input = new Scanner(System.in);
        System.out.print("Title: ");
        return input.nextLine();
    }

    /**
     * Prompts the user to enter the description for the to-do entry.
     *
     * @return the description
     */
    private String requestDescription() {
        Scanner input = new Scanner(System.in);
        System.out.print("Description: ");
        return input.nextLine();
    }

    /**
     * Prompts the user to enter the expected duration for the to-do entry.
     *
     * @return the expected duration in days
     */
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

    /**
     * Submits the gathered data to the controller for creating a new to-do entry.
     */
    private void submitData() {
        // Attempt to register the to-do entry using the provided data
        try {
            Optional<ToDoEntry> toDoEntry = getController().registerToDoEntry(greenSpaceId, title, description, urgency, expectedDuration);

            // Check if the registration was successful
            if (toDoEntry.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "To-Do entry successfully registered!" + ANSI_RESET);
            } else {
                // Print error message if to-do entry is already registered
                System.out.println(ANSI_BRIGHT_RED + "To-Do entry not registered - Already registered!" + ANSI_RESET);
            }
        } catch (IllegalArgumentException e) {
            // Catch IllegalArgumentException indicating invalid data
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            // Prompt user to repeat registration process
            runException();
        }
    }

    /**
     * Handles the case when there is an exception during data submission, prompting the user to retry.
     */
    public void runException() {
        if (repeatProcess()) {
            requestData();
            int continueApp = confirmsMenu();
            if (continueApp != 2) {
                submitData();
            }
        }
    }

    /**
     * Prompts the user to decide whether to repeat the registration process.
     *
     * @return true if the user wants to repeat the process, false otherwise
     */
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

/**
 * Displays the confirmation menu and prompts the user to confirm the entered
 * information.
 *
 * @return the selected option from the confirmation menu
 */
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

    /**
     * Displays the entered information for review and presents the confirmation menu.
     */
    private void display() {
        ToDoEntry.Urgency[] urgencies = ToDoEntry.Urgency.values();
        System.out.println("\n\n-- To-Do Entry Review --");
        System.out.println(ANSI_BRIGHT_YELLOW + "Title: " + title + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW + "Description: " + description + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW + "Urgency: " + urgencies[urgency] + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW + "Expected Duration: " + expectedDuration + " day(s)" + ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW + "Green Space: " + greenSpaceDTO.getObjDto().getName() + ANSI_RESET);
        System.out.print("\nConfirmation menu:\n 0 -> Change Green Space Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }
}
