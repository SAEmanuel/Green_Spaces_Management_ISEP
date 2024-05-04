package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.Optional;
import java.util.Scanner;


public class RegisterSkillUI {

    private final RegisterSkillController controller;
    private String skillName;


    public RegisterSkillUI(){
         controller = new RegisterSkillController();
    }

    public RegisterSkillController getController(){
        return controller;
    }

    public void run() {
        System.out.println("\n\n--- Register Skill ------------------------");

        requestSkillName();
        submitData();

    }

    private void submitData() {
        Optional<Skill> skill = getController().registerSkill(skillName);

        if (skill.isPresent()) {
            System.out.println("Job successfully registered!");
        } else {
            System.out.println("Job not registered!");
        }
    }

    private void requestSkillName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Skill Name: ");
        skillName = input.nextLine();
    }

}
