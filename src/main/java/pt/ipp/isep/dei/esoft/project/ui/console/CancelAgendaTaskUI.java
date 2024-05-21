package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Extras.ConfirmationMenu.ConfirmationMenu;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class CancelAgendaTaskUI implements Runnable{

    private final AgendaController controller;
    private int agendaTaskID;

    public CancelAgendaTaskUI() {
        this.controller = new AgendaController();
    }

    private AgendaController getController() {
        return controller;
    }




    //-------------------------------------- Run if happy success -------------------------

    /**
     * Executes the user interface logic for registering a vehicle.
     */
    public void run() {
        System.out.println("\n\n--- Postpone a task in agenda ------------------------");


        if (requestEntryInformation()) {
            int continueApp = confirmsData();

            if (continueApp != 2) {
                submitData();
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Does not exist tasks in the agenda available for this GSM..." + ANSI_RESET);
        }

    }


    private void submitData() {

        boolean result = getController().cancelTask(agendaTaskID);

        if (result) {
            System.out.println(ANSI_BRIGHT_GREEN + "Task successfully canceled!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Task not canceled - task already have this status!" + ANSI_RESET);
        }


    }


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

    private void display() {
        System.out.print("\nConfirmation menu:\n 0 -> Change Entry Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }

    //--------------------------------------------------------------------------------

    private boolean requestEntryInformation() {
        agendaTaskID = requestAgendaEntry();
        return agendaTaskID != -1;

    }

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

    private int getAnswer(List<AgendaEntry> agendaTasks, int n) {
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

    private void showToDoList(List<AgendaEntry> agendaTasks) {
        System.out.println("\n-- Agenda --");
        for (int i = 0; i < agendaTasks.size(); i++) {
            System.out.println(ANSI_CORAL+"•Task: "+(i+1)+ANSI_RESET+ "\n" + agendaTasks.get(i).toString() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]\n" + ANSI_RESET);
        }
        System.out.println("----------------");
    }
}
