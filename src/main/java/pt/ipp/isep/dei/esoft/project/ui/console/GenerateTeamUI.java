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

    public GenerateTeamUI() {
        this.controller = new GenerateTeamController();
    }

    public GenerateTeamController getController(){ return controller; }

    public void run() {
        System.out.println("\n\n--- Generate Team ------------------------");

        if(requestData()){
            submitData();
        }
    }

    private void submitData() {
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
    }

    private boolean requestData() {

        if(!displayAndSelectSkills())
            return false;

        minCollaborators = inputMinCollab();
        maxCollaborators = inputMaxCollab();
        return true;
    }

    private int inputMinCollab(){
        Scanner input = new Scanner(System.in);

        System.out.print("Select the minimum Collaborators: ");
        return input.nextInt();
    }

    private int inputMaxCollab(){
        Scanner input = new Scanner(System.in);

        System.out.print("Select the maximum Collaborators: ");
        return input.nextInt();
    }

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
