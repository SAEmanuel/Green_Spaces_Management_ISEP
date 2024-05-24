package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.GetDatesFromUsers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class RetrieveTasksUI implements Runnable {

    private final AgendaController controller;
    private final Collaborator collaborator;
    private Data startDate;
    private Data endDate;
    private int filterSelection;

    public RetrieveTasksUI(Collaborator collaborator) {
        controller = new AgendaController();
        this.collaborator = collaborator;
    }

    public AgendaController getController() {
        return controller;
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Tasks Assigned ------------------------");
        System.out.println("How do you want to search for your tasks?");
        requestInfo();
        submitData();
        printData();

    }

    private void requestInfo() {
        filterSelection = selectFilter();
        requestDates();
    }

    private int selectFilter() {
        Scanner scanner = new Scanner(System.in);
        int option;
        System.out.println("•No filter");
        System.out.println(ANSI_PURPLE + "   Option -> [0]" + ANSI_RESET);
        for (int i = 0; i < controller.getStatus().length; i++) {
            System.out.println("•" + controller.getStatus()[i]);
            System.out.printf(ANSI_PURPLE + "   Option -> [%d]" + ANSI_RESET + "%n", i + 1);

        }
        System.out.print("Select a filter: ");
        do {
            option = scanner.nextInt();
            if (option < 0 || option > controller.getStatus().length) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid option! Select a number between 0 and " + (controller.getStatus().length) + ": " + ANSI_RESET);
            }
        } while (option < 0 || option > controller.getStatus().length);

        return option + 1;
    }

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

    private void submitData() {
        try {
            Optional<List<AgendaEntry>> taskList = getController().requestColabTaskList(collaborator, startDate, endDate, filterSelection);

            if (taskList.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "Task list successfully generated!" + ANSI_RESET);
            } else {
                System.out.printf(ANSI_BRIGHT_RED + "No tasks assigned to you %s or for those filters!" + ANSI_RESET, collaborator.getName());
            }

        } catch (IllegalArgumentException e) {
            System.out.print("");
        }


    }

    private void printData() {
        Optional<List<AgendaEntry>> taskList = getController().requestColabTaskList(collaborator, startDate, endDate, filterSelection);
        if (taskList.isPresent()) {
            for (AgendaEntry task : taskList.get()) {
                System.out.println(task);
                System.out.println("-----");

            }
        }
    }

}
