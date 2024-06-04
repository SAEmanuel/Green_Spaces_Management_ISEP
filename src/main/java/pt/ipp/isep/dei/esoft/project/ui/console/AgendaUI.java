package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.domain.Extras.ConfirmationMenu.ConfirmationMenu;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.GetDatesFromUsers;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class AgendaUI implements Runnable {

    private final AgendaController controller;
    private ToDoEntryDTO agendaEntry;
    private int toDoEntryOption;
    private Data starting_Date;

    /**
     * Constructor for AgendaUI.
     */
    public AgendaUI() {
        controller = new AgendaController();
    }

    /**
     * Gets the controller for managing agenda operations.
     *
     * @return the AgendaController instance.
     */
    private AgendaController getController() {
        return controller;
    }

    /**
     * Entry point to run the agenda UI operations.
     */
    @Override
    public void run() {
        System.out.println("\n\n--- Add Agenda Entry ------------------------");

        if (requestEntryInformation()) {
            int continueApp = confirmsData();

            if (continueApp != 2) {
                submitData();
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Does not exist task in the TODO list... Add some" + ANSI_RESET);
        }
    }

    /**
     * Displays the confirmation menu and handles the user's choice.
     *
     * @return the chosen option.
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
     * Displays the current data entered by the user.
     */
    private void display() {
        System.out.printf("\nTyped data -> [%s]\n", String.format(ANSI_GREEN + "Task: " + ANSI_RESET + "%s |" + ANSI_GREEN + " Starting Date: " + ANSI_RESET + "%s", "\"" + agendaEntry.getObjDto().getTitle() + "\" in \"" + agendaEntry.getObjDto().getGreenSpace().getName() + "\"", starting_Date));
        System.out.print("Confirmation menu:\n 0 -> Change Entry Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }

    /**
     * Submits the entered data to register an agenda entry.
     */
    private void submitData() {
        try {
            Optional<AgendaEntry> entry = getController().registerAgendaEntry(toDoEntryOption, starting_Date);

            if (entry.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "Agenda entry successfully added!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_BRIGHT_RED + "Agenda entry not added - Already exist!" + ANSI_RESET);
            }

        } catch (IllegalArgumentException e) {
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
        }
    }

    /**
     * Requests entry information from the user.
     *
     * @return true if entry information is valid, false otherwise.
     */
    private boolean requestEntryInformation() {
        toDoEntryOption = requestAgendaEntry();
        if (toDoEntryOption != -1) {
            starting_Date = requestStartDate();
            return true;
        }
        return false;
    }

    /**
     * Requests the starting date from the user.
     *
     * @return the starting date entered by the user.
     */
    private Data requestStartDate() {
        System.out.print("-- Starting Date --\n");
        return GetDatesFromUsers.getData();
    }

    /**
     * Requests the agenda entry option from the user.
     *
     * @return the chosen agenda entry option, or -1 if no entries exist.
     */
    private int requestAgendaEntry() {
        int answer;
        List<ToDoEntryDTO> toDoList = controller.getToDoListDTOForResponsible();
        int n = toDoList.size();

        if (n != 0) {
            showToDoList(toDoList);
            answer = getAnswer(n);
            agendaEntry = toDoList.get(answer);
            return answer;
        }
        return -1;
    }

    /**
     * Gets the user's answer for the agenda entry option.
     *
     * @param n the number of available options.
     * @return the chosen option.
     */
    private int getAnswer(int n) {
        int answer;
        Scanner input = new Scanner(System.in);
        System.out.print("Type ID Option: ");
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
     * Displays the list of to-do entries.
     *
     * @param toDoList the list of to-do entries.
     */
    private void showToDoList(List<ToDoEntryDTO> toDoList) {
        System.out.println("\n-- TODO List --");
        System.out.println("\n[Title -> Description -> Urgency -> Spectated duration -> Park]\n");
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println("• Task: " + toDoList.get(i).toString() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]\n" + ANSI_RESET);
        }
        System.out.println("----------------");
    }
}
