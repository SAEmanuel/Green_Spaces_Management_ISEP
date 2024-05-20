package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.GetDatasFromUsers;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class AgendaUI implements Runnable {
    
    private final AgendaController controller;

    private ToDoEntry agendaEntry;
    private Data starting_Date;


    public AgendaUI(){
        controller = new AgendaController();
    }

    private AgendaController getController() {
        return controller;
    }


    /**
     * Executes the user interface logic for registering a vehicle.
     */
    public void run() {
        System.out.println("\n\n--- Add Agenda Entry ------------------------");

        if (requestEntryInformation()) {
            int continueApp = confirmsData();

            if (continueApp != 2) {
                submitData();
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED+ "Does not exist task in the TODO list... Add some"+ANSI_RESET);
        }

    }


    private int confirmsData() {
        Scanner input = new Scanner(System.in);
        int option;

        while(true){
            try {
                display();
                option = input.nextInt();

                if (option == 0) {
                    System.out.println();
                    requestEntryInformation();
                }else if (option == 1) {
                    System.out.println();
                    return option;
                } else if (option == 2) {
                    System.out.println(ANSI_BRIGHT_RED+"LEAVING..."+ANSI_RESET);
                    return option; // Exit the registration process
                } else {
                    System.out.println(ANSI_BRIGHT_RED+ "Invalid choice. Please enter 0 or 1 or 2."+ ANSI_RESET);
                }
            }catch (InputMismatchException e){
                System.out.println(ANSI_BRIGHT_RED+ "Invalid choice. Please enter 0 or 1 or 2."+ ANSI_RESET);
                input.nextLine();
            }
        }
    }

    private void display() {
        StringBuilder stringBuilder = new StringBuilder(String.format("Task: %s | Starting Date: %s", "\""+ agendaEntry.getTitle() + "\" in \"" + agendaEntry.getGreenSpace().getName()+"\"", starting_Date));
        System.out.printf("\nTyped data -> [%s%s%s]\n", ANSI_GREEN, stringBuilder, ANSI_RESET);
        System.out.print("Confirmation menu:\n 0 -> Change Entry Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }


    private void submitData() {
        // Attempt to register the vehicle using the provided data
        try {

            Optional<AgendaEntry> entry = getController().registerAgendaEntry(agendaEntry, starting_Date);

            // Check if the registration was successful
            if (entry.isPresent()) {
                System.out.println(ANSI_BRIGHT_GREEN + "Agenda entry successfully added!" + ANSI_RESET);
            } else {
                // Print error message if vehicle is already registered
                System.out.println(ANSI_BRIGHT_RED + "VAgenda entry not added - Already exist!" + ANSI_RESET);
            }

        } catch (IllegalArgumentException e) {
            // Catch IllegalArgumentException indicating invalid data
            System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            // Prompt user to repeat registration process
            runException();
        }
    }

    /**
     * Executes the user interface logic for registering a vehicle in case of an exception.
     */
    public void runException() {

        if (repeatProcess()) {
            requestEntryInformation();
            int continueApp = confirmsData();

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

    private boolean requestEntryInformation() {
        agendaEntry = requestAgendaEntry();
        if (agendaEntry != null) {
            starting_Date = requestStartDate();
            return true;
        }
        return false;
        
    }


    private Data requestStartDate() {
        System.out.print("-- Starting Date --\n");
        return GetDatasFromUsers.getData();
    }



    private ToDoEntry requestAgendaEntry() {
        int answer;
        Scanner input = new Scanner(System.in);
        List<ToDoEntry> toDoList = controller.getToDoListForResponsible();
        int n = toDoList.size();
        if (n != 0) {
            showToDoList(toDoList);
            System.out.print("Type ID Option: ");

            while (true) {
                try {
                    answer = input.nextInt() - 1;
                    if (answer <= n - 1 && answer >= 0) {
                        return toDoList.get(answer);
                    }
                } catch (InputMismatchException e) {
                    System.out.print(ANSI_BRIGHT_RED + "Invalid ID number! Enter a new one: " + ANSI_RESET +"\n");
                    input.nextLine();
                }
                System.out.print(ANSI_BRIGHT_YELLOW+"Invalid ID, enter a new one: "+ANSI_RESET);
            }
        }
        return null;
    }

    private void showToDoList(List<ToDoEntry> toDoList) {
        System.out.println("\n-- TODO List --");
        System.out.println("Title -> Description -> Urgency -> Spectated duration -> Park\n");
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println("• Task: " + toDoList.get(i).toString() + "\n" + ANSI_PURPLE + "   Option -> [" + (i+1) + "]\n" + ANSI_RESET);
        }
        System.out.println("----------------");
    }

}
