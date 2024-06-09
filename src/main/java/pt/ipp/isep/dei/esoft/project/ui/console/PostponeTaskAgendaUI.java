package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Extras.ConfirmationMenu.ConfirmationMenu;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.GetDatesFromUsers;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_PURPLE;

/**
 * User interface for postponing a task in the agenda.
 */
public class PostponeTaskAgendaUI implements Runnable {

    private final AgendaController controller;

    private AgendaEntry agendaEntry;
    private int agendaTaskID;
    private Data postponedDate;

    /**
     * Constructor for PostponeTaskAgendaUI.
     * Initializes the controller.
     */
    public PostponeTaskAgendaUI() {
        this.controller = new AgendaController();
    }

    /**
     * Gets the controller instance.
     *
     * @return The AgendaController instance.
     */
    private AgendaController getController() {
        return controller;
    }

    /**
     * Executes the user interface logic for postponing a task in the agenda.
     */
    public void run() {
        System.out.println("\n\n--- Postpone a task in agenda ------------------------");

        if (requestEntryInformation()) {
            int continueApp = confirmsData();

            if (continueApp != 2) {
                submitData();
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Does not exist task in the AGENDA... Add some" + ANSI_RESET);
        }
    }

    /**
     * Submits the data for postponing a task.
     */
    private void submitData() {
        try {
            boolean result = getController().postponeTask(agendaTaskID, postponedDate, agendaEntry);

            if (result) {
                System.out.println(ANSI_BRIGHT_GREEN + "Task successfully postponed!" + ANSI_RESET);
            } else {
                System.out.printf(ANSI_BRIGHT_RED + "Task not postponed - Make sure date inputted is greater than: [%s]!" + ANSI_RESET, agendaEntry.getStartingDate());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
        }
    }

    /**
     * Confirms the data with the user.
     *
     * @return The user's option.
     */
    private int confirmsData() {
        int option;
        display();

        while (true) {
            option = ConfirmationMenu.confirmsData();
            switch (option) {
                case 0:
                    System.out.println();
                    requestEntryInformation();
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
     * Requests entry information from the user.
     *
     * @return true if the information was successfully requested, false otherwise.
     */
    private boolean requestEntryInformation() {
        agendaTaskID = requestAgendaEntry();
        if (agendaTaskID != -1) {
            postponedDate = requestPostponedData();
            return true;
        }
        return false;
    }

    /**
     * Requests the postponed date from the user.
     *
     * @return The postponed date.
     */
    private Data requestPostponedData() {
        System.out.print("-- Postponed Date --\n");
        return GetDatesFromUsers.getData();
    }

    /**
     * Requests the agenda entry from the user.
     *
     * @return The agenda task ID.
     */
    private int requestAgendaEntry() {
        List<AgendaEntry> agendaTasks = controller.getAgendaEntriesForResponsible();
        int n = agendaTasks.size();
        if (n != 0) {
            showToDoList(agendaTasks);
            System.out.print("Type ID Option: ");
            return getAnswer(agendaTasks, n);
        }
        return -1;
    }

    /**
     * Gets the user's answer for the agenda task ID.
     *
     * @param agendaTasks The list of agenda tasks.
     * @param n The number of agenda tasks.
     * @return The user's selected task ID.
     */
    private int getAnswer(List<AgendaEntry> agendaTasks, int n) {
        Scanner input = new Scanner(System.in);
        int answer;
        while (true) {
            try {
                answer = input.nextInt() - 1;
                if (answer <= n - 1 && answer >= 0) {
                    agendaEntry = agendaTasks.get(answer);
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
     * Displays the list of agenda tasks to the user.
     *
     * @param agendaTasks The list of agenda tasks.
     */
    private void showToDoList(List<AgendaEntry> agendaTasks) {
        System.out.println("\n-- Agenda --");
        for (int i = 0; i < agendaTasks.size(); i++) {
            System.out.println(ANSI_CORAL + "•Task: " + i + ANSI_RESET + "\n" + agendaTasks.get(i).toString() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]\n" + ANSI_RESET);
        }
        System.out.println("----------------");
    }
}
