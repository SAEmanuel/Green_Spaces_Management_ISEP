package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class AgendaUI implements Runnable {
    
    private final AgendaController controller;

    private ToDoEntry agendaEntry;
    private Data starting_Date;
    private Data ending_Date;


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
//        Vehicle.Type[] types = controller.showTypes();
//        StringBuilder stringBuilder = new StringBuilder(String.format("Plate: %s | Brand: %s | Model: %s | Type: %s | Tare Weight: %.2f | Gross Weight: %.2f \n               Current Km: %.2f | Check Up Frequency (Km): %.2f | Last Check Up (Km): %.2f\n               Register Date: %s | Acquisition Date: %s", plateId, brand, model,types[type],tareWeight,grossWeight,currentKm,checkUpFrequency,lastCheckUp,registerDate,acquisitionDate));
//        System.out.printf("\nTyped data -> [%s%s%s]\n", ANSI_GREEN, stringBuilder, ANSI_RESET);
        System.out.print("Confirmation menu:\n 0 -> Change Entry Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }


    private void submitData() {
        // Attempt to register the vehicle using the provided data
        try {

            Optional<AgendaEntry> entry = getController().registerAgendaEntry(agendaEntry, starting_Date, ending_Date);

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
            ending_Date = requestEndDate();
            return true;
        }
        return false;
        
    }

    private Data requestEndDate() {
        System.out.print("-- End Date --\n");
        return getData();
    }

    private Data requestStartDate() {
        System.out.print("-- Starting Date --\n");
        return getData();
    }



    private Data getData() {
        Data data;
        while (true) {
            int year = requestYear();
            int month = requestMonth();
            int day = requestDay();
            try {
                data = new Data(year, month, day);
                return data;
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            }
        }
    }

    /**
     * Prompts the user to input a day.
     * This method prompts the user to input a day as an integer.
     * It handles any invalid inputs gracefully and keeps prompting
     * until a valid day is provided.
     *
     * @return The day input by the user.
     */
    private int requestDay() {
        int resposta;
        Scanner input = new Scanner(System.in);
        System.out.print("- Day: ");
        while (true) {
            try {
                resposta = input.nextInt();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid DAY! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }


    /**
     * Prompts the user to input a month.
     * This method prompts the user to input a month as an integer.
     * It handles any invalid inputs gracefully and keeps prompting
     * until a valid month is provided.
     *
     * @return The month input by the user.
     */
    private int requestMonth() {
        int resposta;
        Scanner input = new Scanner(System.in);
        System.out.print("- Month: ");
        while (true) {
            try {
                resposta = input.nextInt();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid MONTH! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

    /**
     * Prompts the user to input a year.
     * This method prompts the user to input a year as an integer.
     * It handles any invalid inputs gracefully and keeps prompting
     * until a valid year is provided.
     *
     * @return The year input by the user.
     */
    private int requestYear() {
        int resposta;
        Scanner input = new Scanner(System.in);
        System.out.print("- Year: ");
        while (true) {
            try {
                resposta = input.nextInt();
                return resposta;
            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid DAY! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }
    
    
    
    
    
    
    
    
    
    

    private ToDoEntry requestAgendaEntry() {
        int answer;
        Scanner input = new Scanner(System.in);
        List<ToDoEntry> toDoList = controller.getToDoList();
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
        System.out.println("Title -> Description -> Urgency -> Spectated duration -> Park");
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println("• Task: " + toDoList.get(i).toString() + "\n" + ANSI_PURPLE + "   Option -> [" + (i+1) + "]" + ANSI_RESET);
        }
        System.out.println("----------------");
    }

}
