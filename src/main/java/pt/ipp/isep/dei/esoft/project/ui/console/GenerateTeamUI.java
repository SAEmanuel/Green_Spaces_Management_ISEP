package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.SkillList;
import pt.ipp.isep.dei.esoft.project.domain.Task;
import pt.ipp.isep.dei.esoft.project.domain.TaskCategory;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GenerateTeamUI {

    private final GenerateTeamController controller;
    private int minCollaborators;
    private int maxCollaborators;

    public GenerateTeamUI() {
        this.controller = new GenerateTeamController();
    }

    public GenerateTeamController getController(){ return controller; }

    public void run() {
        System.out.println("\n\n--- Generate Team ------------------------");

        requestData();
        submitData();
    }

    private void submitData() {
//        Optional<TeamRepository> team = getController().generateTeam(minCollaborators, maxCollaborators);
//
//        if (team.isPresent()) {
//            System.out.println("\nTask successfully created!");
//        } else {
//            System.out.println("\nTask not created!");
//        }
    }

    private void requestData() {

        displayAndSelectSkills();

        minCollaborators = inputMinCollab();
        maxCollaborators = inputMaxCollab();
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

    private void displayAndSelectSkills() {
        //Display the list of task categories
        List<Skill> skillList = controller.getSkillList();

        int listSize = skillList.size();
        int answerList = -1;
        int answerAdd = -1;
        boolean addSkills = true;

        Scanner input = new Scanner(System.in);

        while(addSkills) {
            while (answerList < 1 || answerList > listSize) {
                displaySkillListOptions(skillList);
                System.out.print("Select a skill: ");
                answerList = input.nextInt();
            }

            controller.addSkill(new Skill(skillList.get(answerList - 1).getSkillName()));

            while(answerAdd != 1 || answerAdd != 0){
                System.out.print("Wanna add more skills? [Y -> 1]  [N -> 0]");
                answerAdd = input.nextInt();
            }
        }
    }

    private void displaySkillListOptions(List<Skill> skillList) {
        //display the task categories as a menu with number options to select
        int i = 1;
        for (Skill s : skillList) {
            System.out.println("  " + i + " - " + s.getSkillName());
            i++;
        }
    }
}
