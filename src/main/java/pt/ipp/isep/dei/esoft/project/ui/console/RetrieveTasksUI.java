package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.GetDatesFromUsers;

import java.util.*;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

/**
 * This class represents the User Interface for retrieving tasks assigned to a collaborator.
 * It implements the Runnable interface to allow running in a separate thread.
 */
public class RetrieveTasksUI implements Runnable {

    private final AgendaController controller;
    private final Collaborator collaborator;
    private Data startDate;
    private Data endDate;
    private int filterSelection;
    private String confirmation = "n";
    private int selectedTask;

    private static final int PLANNED_TASKS = 2;

    /**
     * Constructor for the RetrieveTasksUI class.
     *
     * @param collaborator The collaborator whose tasks are to be retrieved.
     */
    public RetrieveTasksUI(Collaborator collaborator) {
        controller = new AgendaController();
        this.collaborator = collaborator;
    }

    /**
     * Returns the controller associated with this UI.
     *
     * @return The AgendaController instance.
     */
    public AgendaController getController() {
        return controller;
    }

    /**
     * The main method to run the UI, which handles the flow of retrieving tasks.
     */
    @Override
    public void run() {
        System.out.println("\n\n--- Tasks Assigned ------------------------");
        System.out.println("How do you want to search for your tasks?");
        requestInfo();
        submitData();
        printData();
        askConfirmation();
        submitDataForPlanned();
    }

    /**
     * Requests the user to select a task for completion and returns the selected task ID.
     *
     * @return The selected task ID.
     */
    private int requestCompletionTask() {
        Optional<List<AgendaEntry>> taskList = getController().requestPlannedColabTaskList(collaborator, startDate, endDate, filterSelection);
        Optional<List<AgendaEntry>> possibleTaskList = getController().requestPossiblePlannedColabTaskList(collaborator, startDate, endDate, filterSelection);
        if (taskList.isPresent()) {
            Scanner scanner = new Scanner(System.in);
            int id;
            System.out.println();

            if (confirmation.equalsIgnoreCase("y")) {
                if (possibleTaskList.isPresent()) {
                    if (!possibleTaskList.get().isEmpty()) {
                        printPossibleDataForPlanned();
                        while (true) {
                            System.out.print("Select the task you want to choose: ");
                            try {
                                id = scanner.nextInt();
                                if (id < 1 || id > possibleTaskList.get().size()) {
                                    System.out.printf(ANSI_BRIGHT_RED + "Invalid task id. Must be a number between 1 and %d.%n" + ANSI_RESET, possibleTaskList.get().size());
                                } else {
                                    break;
                                }
                            } catch (InputMismatchException e) {
                                System.out.printf(ANSI_BRIGHT_RED + "Invalid task id. Must be a number between 1 and %d.%n" + ANSI_RESET, possibleTaskList.get().size());
                                scanner.next();
                            }
                        }
                        return id - 1;
                    }
                }
            } else {
                printDataForPlanned();
            }
        }
        return -1;
    }

    /**
     * Asks the user for confirmation if they want to register a task completion.
     */
    private void askConfirmation() {
        Optional<List<AgendaEntry>> plannedTaskList = getController().requestPlannedColabTaskList(collaborator, startDate, endDate, filterSelection);
        if (plannedTaskList.isPresent()) {
            if (filterSelection == PLANNED_TASKS && !plannedTaskList.get().isEmpty()) {
                confirmation = requestConfirmation();
                selectedTask = requestCompletionTask();
            }
        }
    }

    /**
     * Requests the user to provide information for task retrieval.
     */
    private void requestInfo() {
        filterSelection = selectFilter();
        requestDates();
    }

    /**
     * Prompts the user to select a filter for task retrieval.
     *
     * @return The selected filter option.
     */
    private int selectFilter() {
        Scanner scanner = new Scanner(System.in);
        int option;

        System.out.println("• No filter");
        System.out.println(ANSI_PURPLE + "   Option -> [0]" + ANSI_RESET);
        for (int i = 0; i < controller.getStatus().length; i++) {
            System.out.println("• " + controller.getStatus()[i]);
            System.out.printf(ANSI_PURPLE + "   Option -> [%d]" + ANSI_RESET + "%n", i + 1);
        }

        System.out.print("Select a filter: ");

        do {
            try {
                option = scanner.nextInt();
                if (option < 0 || option > controller.getStatus().length) {
                    System.out.print(ANSI_BRIGHT_RED + "Invalid option! Select a number between 0 and " + (controller.getStatus().length) + ": " + ANSI_RESET);
                }
            } catch (InputMismatchException e) {
                option = -1;
                System.out.println(ANSI_BRIGHT_RED + "Invalid input! Please enter a valid number." + ANSI_RESET);
                System.out.print("Select a filter: ");
                scanner.nextLine();
            }
        } while (option < 0 || option > controller.getStatus().length);

        return option + 1;
    }

    /**
     * Requests the user to enter the start and end dates for task retrieval.
     */
    private void requestDates() {
        do {
            System.out.println("Enter start date: ");
            startDate = GetDatesFromUsers.getData();
            System.out.println("Enter end date: ");
            endDate = GetDatesFromUsers.getData();
            if (startDate.isGreater(endDate)) {
                System.out.println(ANSI_BRIGHT_RED + "Start date can't be greater than the end date!" + ANSI_RESET);
            }
        } while (startDate.isGreater(endDate));
    }

    /**
     * Prompts the user for confirmation to register a task completion.
     *
     * @return The confirmation input from the user.
     */
    private String requestConfirmation() {
        Scanner scanner = new Scanner(System.in);
        String confirmation;
        System.out.print("Do you want to register a task completion? [y/n]: ");
        do {
            confirmation = scanner.nextLine();
            if (!confirmation.equalsIgnoreCase("y") && !confirmation.equalsIgnoreCase("n")) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid option! Select 'y' or 'n':" + ANSI_RESET);
                confirmation = scanner.nextLine();
            }
        } while (!confirmation.equalsIgnoreCase("y") && !confirmation.equalsIgnoreCase("n"));

        return confirmation;
    }

    /**
     * Submits the data for task retrieval based on the provided information.
     */
    private void submitData() {
        try {
            Optional<List<AgendaEntry>> taskList = getController().requestColabTaskList(collaborator, startDate, endDate, filterSelection);

            if (filterSelection != PLANNED_TASKS) {
                if (taskList.isPresent()) {
                    System.out.println(ANSI_BRIGHT_GREEN + "Task list successfully generated!" + ANSI_RESET);
                } else {
                    System.out.printf(ANSI_BRIGHT_RED + "No tasks assigned to you %s or for those filters!" + ANSI_RESET, collaborator.getName());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.print("");
        }
    }

    /**
     * Submits the data for registering a planned task completion.
     */
    private void submitDataForPlanned() {
        try {
            if (filterSelection == PLANNED_TASKS) {
                Optional<List<AgendaEntry>> plannedTaskList = getController().requestChangedStatusTaskList(collaborator, startDate, endDate, filterSelection, confirmation, selectedTask);

                if (plannedTaskList.isPresent()) {
                    System.out.println(ANSI_BRIGHT_GREEN + "Task completion successfully registered!" + ANSI_RESET);
                } else if (!plannedTaskList.isPresent() && confirmation.equalsIgnoreCase("y")) {
                    System.out.printf(ANSI_BRIGHT_RED + "No tasks assigned to you %s or your planned tasks haven't started yet!" + ANSI_RESET, collaborator.getName());

                }
            }
        } catch (IllegalArgumentException e) {
            System.out.print("");
        }
    }

    /**
     * Prints the retrieved tasks to the console.
     */
    private void printData() {
        Optional<List<AgendaEntry>> taskList = getController().requestColabTaskList(collaborator, startDate, endDate, filterSelection);
        printList(taskList);
    }

    /**
     * Prints the planned tasks to the console.
     */
    private void printDataForPlanned() {
        Optional<List<AgendaEntry>> plannedTaskList = getController().requestPlannedColabTaskList(collaborator, startDate, endDate, filterSelection);
        printList(plannedTaskList);
        if (plannedTaskList.isEmpty()) {
            System.out.printf(ANSI_BRIGHT_RED + "No tasks assigned to you %s or for those filters!" + ANSI_RESET, collaborator.getName());
        }
    }

    /**
     * Prints the possible planned tasks to the console.
     */
    private void printPossibleDataForPlanned() {
        Optional<List<AgendaEntry>> possiblePlannedTaskList = getController().requestPossiblePlannedColabTaskList(collaborator, startDate, endDate, filterSelection);
        printList(possiblePlannedTaskList);
        if (possiblePlannedTaskList.isEmpty()) {
            System.out.printf(ANSI_BRIGHT_RED + "No tasks assigned to you %s or for those filters!" + ANSI_RESET, collaborator.getName());
        }
    }

    /**
     * Prints a list of tasks to the console.
     *
     * @param taskList The list of tasks to be printed.
     */
    private void printList(Optional<List<AgendaEntry>> taskList) {
        int counter = 1;
        if (taskList.isPresent() && confirmation.equalsIgnoreCase("y") && filterSelection == PLANNED_TASKS) {
            System.out.println(ANSI_BRIGHT_YELLOW + "-----" + ANSI_RESET);
            for (AgendaEntry task : taskList.get()) {
                System.out.printf(ANSI_CORAL + "•Task: %d%n" + ANSI_RESET, counter);
                System.out.println(task);
                System.out.printf(ANSI_PURPLE + "   Option -> [%d]%n" + ANSI_RESET, counter++);
                System.out.println(ANSI_BRIGHT_YELLOW + "-----" + ANSI_RESET);

            }
        } else if (taskList.isPresent()) {
            System.out.println(ANSI_BRIGHT_YELLOW + "-----" + ANSI_RESET);
            for (AgendaEntry task : taskList.get()) {
                System.out.printf(ANSI_CORAL + "•Task: %d%n" + ANSI_RESET, counter++);
                System.out.println(task);
                System.out.println(ANSI_BRIGHT_YELLOW + "-----" + ANSI_RESET);
            }

        } else{
            System.out.printf(ANSI_BRIGHT_RED + "No tasks assigned to you %s or for those filters!" + ANSI_RESET, collaborator.getName());
        }
    }
}
