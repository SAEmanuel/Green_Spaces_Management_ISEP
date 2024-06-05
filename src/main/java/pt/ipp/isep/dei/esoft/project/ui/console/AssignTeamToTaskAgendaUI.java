package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Extras.ConfirmationMenu.ConfirmationMenu;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class AssignTeamToTaskAgendaUI implements Runnable {

    private final AgendaController controller;
    private int teamID;
    private int agendaEntryID;
    private String emailService;

    public AssignTeamToTaskAgendaUI() {
        this.controller = new AgendaController();
    }

    private AgendaController getController() {
        return controller;
    }


    //-------------------------------------- Run if happy success -------------------------

    /**
     * Executes the user interface logic for assigning a team to a task in the agenda.
     */
    public void run() {
        System.out.println("\n\n--- Postpone a task in agenda ------------------------");


        if (requestEntryInformation()) {
            int continueApp = confirmsData();

            if (continueApp != 2) {
                submitData();
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Does not exist task in the AGENDA or no TEAM in the system or the isn't any email service available ... Add some" + ANSI_RESET);
        }
    }

    /**
     * Submits the data for assigning a team to a task in the agenda.
     */
    private void submitData() {

        boolean result = getController().assignTeam(teamID,agendaEntryID, emailService, controller.getResponsible());

        if (result) {
            System.out.println(ANSI_BRIGHT_GREEN + "\nTeam successfully assigned!" + ANSI_RESET);
        } else {
            System.out.printf(ANSI_BRIGHT_RED + "\nTeam not assigned - This task already have a team assigned!" + ANSI_RESET);
        }
    }

    /**
     * Displays a confirmation menu and handles the user's selection.
     *
     * @return the selected option
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
     * Displays the confirmation menu options.
     */
    private void display() {
        System.out.print("\nConfirmation menu:\n 0 -> Change Entry Info\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }

    //--------------------------------------------------------------------------------

    //----------------------------------- Requests ----------------------------------

    /**
     * Requests entry information from the user.
     *
     * @return true if the information was successfully requested, false otherwise
     */
    private boolean requestEntryInformation() {
        agendaEntryID = requestAgendaEntryID();
        if (agendaEntryID != -1) {
            teamID = requestTeamID();
            if(teamID != -1){
                emailService = requestEmailService();
                return emailService != null;
            }
        }
        return false;
    }

    /**
     * Requests the agenda entry ID from the user.
     *
     * @return the selected agenda entry ID, or -1 if none is selected
     */
    private int requestAgendaEntryID() {
        List<AgendaEntry> agendaTasks = controller.getAgendaEntriesForResponsibleTeam();
        int n = agendaTasks.size();
        if (n != 0) {
            showToDoList(agendaTasks);
            System.out.print("Type ID Option: ");
            return getAnswer(n);
        }
        return -1;
    }

    /**
     * Displays the list of agenda entries.
     *
     * @param agendaTasks the list of agenda entries to display
     */
    private void showToDoList(List<AgendaEntry> agendaTasks) {
        System.out.println("\n-- Agenda --");
        for (int i = 0; i < agendaTasks.size(); i++) {
            System.out.println(ANSI_CORAL+"•Task: "+(i+1)+ANSI_RESET+ "\n" + agendaTasks.get(i).toString() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]\n" + ANSI_RESET);
        }
        System.out.println("----------------");
    }

    /**
     * Requests the team ID from the user.
     *
     * @return the selected team ID, or -1 if none is selected
     */
    private int requestTeamID() {
        List<Team> teams = controller.getTeams();
        int n = teams.size();
        if (n != 0) {
            showTeams(teams);
            System.out.print("Type ID Option: ");
            return getAnswer(n);
        }
        return -1;
    }

    /**
     * Displays the list of teams.
     *
     * @param teams the list of teams to display
     */
    private void showTeams(List<Team> teams) {
        System.out.println("\n-- Teams --");
        for (int i = 0; i < teams.size(); i++) {
            System.out.println(ANSI_CORAL+"•Team: "+(i+1)+ANSI_RESET+ "\n" + teams.get(i).toString() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]\n" + ANSI_RESET);
        }
        System.out.println("----------------");
    }

    /**
     * Gets the user's answer for selecting an option.
     *
     * @param n the number of options available
     * @return the selected option
     */
    private int getAnswer( int n) {
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
     * Requests the email service from the user.
     *
     * @return the selected email service, or null if none is selected
     */
    private String requestEmailService() {
        List<String > emailServices = controller.getEmailServices();
        int n = emailServices.size();
        if (n != 0) {
            showEmailServices(emailServices);
            System.out.print("Type ID Option: ");
            return emailServices.get(getAnswer(n));
        }
        return null;
    }

    /**
     * Displays the list of email services.
     *
     * @param emailServices the list of email services to display
     */
    private void showEmailServices(List<String> emailServices) {
        System.out.println("\n-- Email Services --");
        for (int i = 0; i < emailServices.size(); i++) {
            System.out.println(ANSI_CORAL+"•Service: "+(i+1)+ANSI_RESET+ "\n" + emailServices.get(i).toString() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]\n" + ANSI_RESET);
        }
        System.out.println("----------------");
    }
}
