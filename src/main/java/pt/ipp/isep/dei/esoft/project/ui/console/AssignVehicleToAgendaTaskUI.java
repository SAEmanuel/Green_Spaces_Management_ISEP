package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Extras.ConfirmationMenu.ConfirmationMenu;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_PURPLE;

/**
 * The AssignVehicleToAgendaTaskUI class is responsible for the user interface
 * that allows assigning a vehicle to an agenda task.
 * It interacts with the user to collect information about the agenda task
 * and the vehicle, and then attempts to assign the selected vehicle to the selected task.
 */
public class AssignVehicleToAgendaTaskUI implements Runnable {

    private final AgendaController controller;
    private AgendaEntry agendaTask;
    private Vehicle vehicle;

    /**
     * Constructs a new AssignVehicleToAgendaTaskUI instance.
     * Initializes the AgendaController.
     */
    public AssignVehicleToAgendaTaskUI() {
        this.controller = new AgendaController();
    }

    /**
     * Gets the controller.
     *
     * @return the AgendaController
     */
    private AgendaController getController() {
        return controller;
    }

    /**
     * Runs the UI for assigning a vehicle to an agenda task.
     * Collects user input and processes the assignment.
     */
    @Override
    public void run() {
        System.out.println("\n\n--- Assign a Vehicle to a Task in Agenda ------------------------");

        if (requestAgendaTaskInformation()) {
            if (requestVehicleInformation()) {
                int continueApp = confirmsData();

                if (continueApp != 2) {
                    submitData();
                }
            } else {
                System.out.println(ANSI_BRIGHT_RED + "There is no available vehicle..." + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Does not exist tasks in the agenda available for this GSM..." + ANSI_RESET);
        }
    }

    /**
     * Requests information about the vehicle.
     *
     * @return true if a vehicle is selected, false otherwise
     */
    private boolean requestVehicleInformation() {
        vehicle = requestVehicleID();
        return vehicle != null;
    }

    /**
     * Requests the ID of the vehicle.
     *
     * @return the selected Vehicle, or null if no vehicles are available
     */
    private Vehicle requestVehicleID() {
        List<Vehicle> availableVehicles = getController().getAvailableVehicles();
        int n = availableVehicles.size();
        if (n != 0) {
            showAvailableVehiclesList(availableVehicles);
            System.out.print("Type ID Option: ");
            return availableVehicles.get(getAnswer(n));
        }
        return null;
    }

    /**
     * Displays the list of available vehicles.
     *
     * @param availableVehicles the list of available vehicles
     */
    private void showAvailableVehiclesList(List<Vehicle> availableVehicles) {
        System.out.println("\n-- Available Vehicle(s) --");
        for (int i = 0; i < availableVehicles.size(); i++) {
            System.out.println(ANSI_CORAL + "•Vehicle: " + (i + 1) + ANSI_RESET + "\n" + availableVehicles.get(i).toStringTask() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]\n" + ANSI_RESET);
        }
        System.out.println("----------------");
    }

    /**
     * Requests information about the agenda task.
     *
     * @return true if an agenda task is selected, false otherwise
     */
    private boolean requestAgendaTaskInformation() {
        agendaTask = requestAgendaEntry();
        return agendaTask != null;
    }

    /**
     * Requests the ID of the agenda entry.
     *
     * @return the selected AgendaEntry, or null if no tasks are available
     */
    private AgendaEntry requestAgendaEntry() {
        List<AgendaEntry> agendaTasks = getController().getAgendaEntriesForResponsibleVehicle();
        int n = agendaTasks.size();
        if (n != 0) {
            showToDoList(agendaTasks);
            System.out.print("Type ID Option: ");
            return agendaTasks.get(getAnswer(n));
        }
        return null;
    }

    /**
     * Gets the user's answer and validates it.
     *
     * @param n the number of options available
     * @return the validated answer
     */
    private int getAnswer(int n) {
        Scanner input = new Scanner(System.in);
        int answer;
        while (true) {
            try {
                answer = input.nextInt() - 1;
                if (answer <= n - 1 && answer >= 0) {
                    return answer;
                }
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid ID number! Enter a new one: " + ANSI_RESET + "\n");
                input.nextLine();
            }
            System.out.print(ANSI_BRIGHT_YELLOW + "Invalid ID, enter a new one: " + ANSI_RESET);
        }
    }

    /**
     * Displays the list of agenda tasks.
     *
     * @param agendaTasks the list of agenda tasks
     */
    private void showToDoList(List<AgendaEntry> agendaTasks) {
        System.out.println("\n-- Agenda --");
        for (int i = 0; i < agendaTasks.size(); i++) {
            System.out.println(ANSI_CORAL + "•Task: " + (i + 1) + ANSI_RESET + "\n" + agendaTasks.get(i).toString() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]\n" + ANSI_RESET);
        }
        System.out.println("----------------");
    }

    /**
     * Confirms the data with the user.
     *
     * @return the selected option from the confirmation menu
     */
    private int confirmsData() {
        int option;
        display();

        while (true) {
            option = ConfirmationMenu.confirmsData();
            switch (option) {
                case 0:
                    System.out.println();
                    requestAgendaTaskInformation();
                    System.out.println();
                    requestVehicleInformation();
                case 2:
                    return option;
                case 1:
                    System.out.println();
                    return option;
            }
        }
    }

    /**
     * Displays the confirmation menu.
     */
    private void display() {
        System.out.print("\nConfirmation menu:\n 0 -> Change Entry Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }

    /**
     * Submits the data and attempts to assign the vehicle to the agenda task.
     * Displays the result of the operation.
     */
    private void submitData() {
        boolean result = getController().assignVehicle(agendaTask, vehicle);

        if (result) {
            System.out.println(ANSI_BRIGHT_GREEN + "Vehicle successfully assigned to a Task!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Vehicle not assigned." + ANSI_RESET);
        }
    }
}
