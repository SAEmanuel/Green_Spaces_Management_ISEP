package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.*;

import java.util.*;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class GenerateTeamUI implements Runnable {

    private final GenerateTeamController controller;
    private int minCollaborators;
    private int maxCollaborators;

    /**
     * Constructs a new GenerateTeamUI with a GenerateTeamController.
     */
    public GenerateTeamUI() {
        this.controller = new GenerateTeamController();
    }

    public GenerateTeamController getController(){ return controller; }

    /**
     * Runs the Generate Team UI.
     */
    public void run() {
        System.out.println("\n\n--- Generate Team ------------------------");

        Team team = new Team(0);

        if(requestData()){
            team = submitData();

            if(team != null) {
                if (!teamConfirmation()) {
                    while (true) {
                        if (team != null && teamGenerationConfirmation()) {
                            team = submitData();
                        } else {
                            displayGeneratedList();
                            break;
                        }
                    }
                } else {
                    controller.addTeam(team.getTeamId());
                    System.out.println(ANSI_BRIGHT_GREEN + "Team Accepted\n" + ANSI_RESET);
                }
            }
        }

        getController().cleanSkillList();
        getController().cleanTeamListAux();
    }

    /**
     * Displays a list of generated teams and prompts the user to select one of the teams.
     * If the list is empty, returns false.
     * If the user selects a valid team, the team is added using the controller.
     *
     * @return true if the list of teams is not empty and a team was selected, false otherwise.
     */
    private boolean displayGeneratedList() {
        List<Team> teamList = controller.generatedList();

        if (teamList.isEmpty()) {
            return false;
        }

        int listSize = teamList.size();
        int answerList = -2;
        Scanner input = new Scanner(System.in);

        while (answerList < 1 || answerList > listSize + 1) {
            displayGeneratedTeamListOptions(teamList);
            System.out.print("Select a team: ");
            answerList = input.nextInt();
        }

        if (answerList != listSize + 1) {
            controller.addTeam(teamList.get(answerList - 1).getTeamId());
        }

        return true;
    }

    /**
     * Displays the list of generated team options for the user to select from.
     * Each team and its collaborators are printed, followed by an option number.
     * An additional option to exit is also provided.
     *
     * @param teamList the list of teams to display
     */
    private void displayGeneratedTeamListOptions(List<Team> teamList) {
        int i = 1;
        for (Team s : teamList) {
            System.out.println("• Team: ");
            for (Collaborator c : s.getCollaborators()) {
                System.out.println(c.getName());
            }
            System.out.println("\n" + ANSI_PURPLE + "   Option -> [" + i + "]" + ANSI_RESET);
            i++;
        }
        System.out.println();
        System.out.println("• Type: - Exit\n" + ANSI_PURPLE + "   Option -> [" + i + "]" + ANSI_RESET);
    }

    /**
     * Submits the data to generate a team and displays the result.
     * @return The ID of the generated team.
     */
    private Team submitData() {
        try{
            Optional<Team> team = getController().generateTeam(minCollaborators, maxCollaborators);

            if (team.isPresent()) {
                System.out.println(ANSI_BLUE+"\nTeam");
                for (Collaborator collaborator : team.get().getCollaborators()) {
                    System.out.println(collaborator.getName());
                }
                System.out.println(ANSI_BRIGHT_GREEN +"\nTeam successfully created!"+ANSI_RESET);

                return team.get();
            } else {
                System.out.println(ANSI_BRIGHT_RED+"\nTeam not created!"+ANSI_RESET);
                return null;
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Asks the user for confirmation to accept the generated team.
     * @return True if the user accepts the team, otherwise false.
     */
    private boolean teamConfirmation(){
        Scanner input = new Scanner(System.in);
        String answerAdd = "";

        while(!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n") ){
            System.out.println();
            System.out.print("Do you want to accept the team ? [y / n]");
            answerAdd = input.nextLine();

            if(!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n")) {
                System.out.println();
                System.out.println(ANSI_BRIGHT_RED + "Wrong answer, try again" + ANSI_RESET);
            }
        }

        return answerAdd.equalsIgnoreCase("y");
    }

    /**
     * Prompts the user to confirm if they want to generate another team.
     * Continuously asks the user until a valid answer ('y' or 'n') is provided.
     *
     * @return true if the user wants to generate another team, false otherwise.
     */
    private boolean teamGenerationConfirmation() {
        Scanner input = new Scanner(System.in);
        String answerAdd = "";

        while (!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n")) {
            System.out.println();
            System.out.print("Do you want to generate another team? [y / n] ");
            answerAdd = input.nextLine();

            if (!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n")) {
                System.out.println();
                System.out.println(ANSI_BRIGHT_RED + "Wrong answer, try again" + ANSI_RESET);
            }
        }

        return answerAdd.equalsIgnoreCase("y");
    }

    /**
     * Requests data from the user for team generation.
     * @return True if data is successfully collected, otherwise false.
     */
    private boolean requestData() {

        if(!displayAndSelectSkills())
            return false;

        minCollaborators = inputMinCollab();
        maxCollaborators = inputMaxCollab();
        return true;
    }

    /**
     * Requests input from the user for the minimum number of collaborators.
     * @return The minimum number of collaborators.
     */
    private int inputMinCollab(){
        int minCollab = 0;
        while(minCollab <= 0) {
            try {
                Scanner input = new Scanner(System.in);

                System.out.print("\nSelect the minimum Collaborators: ");
                minCollab = input.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println(ANSI_BRIGHT_RED+"Invalid data input"+ANSI_RESET);
            }

            if(minCollab <= 0)
                System.out.println(ANSI_BRIGHT_RED+"Invalid minimum collaborator! Need to be greater than 0"+ANSI_RESET);
        }

        return minCollab;
    }

    /**
     * Requests input from the user for the maximum number of collaborators.
     * @return The maximum number of collaborators.
     */
    private int inputMaxCollab(){
        int maxCollab = 0;
        while(maxCollab <= 0) {
            try {
                Scanner input = new Scanner(System.in);

                System.out.print("\nSelect the maximum Collaborators: ");
                maxCollab = input.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println(ANSI_BRIGHT_RED+"Invalid data input"+ANSI_RESET);
            }

            if(maxCollab <= 0)
                System.out.println(ANSI_BRIGHT_RED+"Invalid maximum collaborator! Need to be greater than 0"+ANSI_RESET);
        }

        return maxCollab;
    }

    /**
     * Displays the list of skills and allows the user to select them for team generation.
     * @return True if skills are successfully selected, otherwise false.
     */
    private boolean displayAndSelectSkills() {
        //Display the list of task categories
        List<Skill> skillList = controller.getSkillList();

        if(skillList.isEmpty())
            return false;

        int listSize = skillList.size();
        int answerList = -2;
        String answerAdd = "";
        boolean addSkills = true;
        Scanner input = new Scanner(System.in);

        while(addSkills) {
            while (answerList < 0 || answerList > listSize) {
                displaySkillListOptions(skillList);
                System.out.print("Select a skill: ");
                answerList = input.nextInt();
            }

            if(answerList != 0){
                controller.addSkill(new Skill(skillList.get(answerList - 1).getSkillName()));
            }

            input.nextLine();

            if(answerList != 0) {
                while (!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n")) {
                    System.out.print("Wanna add more skills? [y / n]: ");
                    answerAdd = input.nextLine();

                    if (!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n")){
                        System.out.println(ANSI_BRIGHT_RED+"Wrong answer, try again"+ANSI_RESET);
                        System.out.println();
                    }
                }
                if (answerAdd.equalsIgnoreCase("n")) {
                    addSkills = false;
                }
            }

            if (answerList == 0) {
                getController().cleanSkillList();
                return false;
            }
            answerList= -2;
            answerAdd = "";
        }
        return true;
    }

    /**
     * Displays the list of skills as options for selection.
     * @param skillList The list of skills to display.
     */
    private void displaySkillListOptions(List<Skill> skillList) {
        //display the task categories as a menu with number options to select
        int i = 1;
        for (Skill s : skillList) {
            System.out.println("• Type: " + s.getSkillName() + "\n" + ANSI_PURPLE + "   Option -> [" + i + "]" + ANSI_RESET);
            i++;
        }
        System.out.println();
        System.out.println("• Type: " + " - Cancelar geração de team" + "\n" + ANSI_PURPLE + "   Option -> [" + 0 + "]" + ANSI_RESET);
    }
}
