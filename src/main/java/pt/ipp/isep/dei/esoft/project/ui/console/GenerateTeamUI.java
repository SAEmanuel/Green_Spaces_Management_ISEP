package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;

import java.sql.SQLOutput;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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

        int teamId = 0;

        if(requestData()){
            teamId = submitData();
        }

        if(!teamConfirmation())
            getController().removeTeam(teamId);
        else
            System.out.println("Team Accepted");
    }
    /**
     * Submits the data to generate a team and displays the result.
     * @return The ID of the generated team.
     */
    private int submitData() {
        Optional<Team> team = getController().generateTeam(minCollaborators, maxCollaborators);

        if (team.isPresent()) {
            System.out.println("Team");
            for (Collaborator collaborator : team.get().getCollaborators()) {
                System.out.println(collaborator.getName());
            }

            System.out.println("\nTask successfully created!");
        } else {
            System.out.println("\nTask not created!");
        }

        getController().cleanSkillList();
        return team.get().getTeamId();
    }
    /**
     * Asks the user for confirmation to accept the generated team.
     * @return True if the user accepts the team, otherwise false.
     */
    private boolean teamConfirmation(){
        Scanner input = new Scanner(System.in);
        String answerAdd = "";

        while(!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n") ){
            System.out.print("Do you want to accept the team ? [y / n]");
            answerAdd = input.nextLine();

            if(!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n"))
                System.out.println("Wrong answer, try again!");
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
        Scanner input = new Scanner(System.in);

        System.out.print("Select the minimum Collaborators: ");
        return input.nextInt();
    }
    /**
     * Requests input from the user for the maximum number of collaborators.
     * @return The maximum number of collaborators.
     */
    private int inputMaxCollab(){
        Scanner input = new Scanner(System.in);

        System.out.print("Select the maximum Collaborators: ");
        return input.nextInt();
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

            if(answerList != -1)
                controller.addSkill(new Skill(skillList.get(answerList - 1).getSkillName()));

            input.nextLine();

            while(!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n") ){
                System.out.print("Wanna add more skills? [y / n]");
                answerAdd = input.nextLine();

                if(!answerAdd.equalsIgnoreCase("y") && !answerAdd.equalsIgnoreCase("n"))
                    System.out.println("Wrong answer, try again!");
            }
            if(answerAdd.equalsIgnoreCase("n")) {
                addSkills = false;
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
            System.out.println("  " + i + " - " + s.getSkillName());
            i++;
        }
        System.out.println("  " + 0 + " - Exit");
    }
}
