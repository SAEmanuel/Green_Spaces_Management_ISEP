package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;


public class RegisterSkillUI implements Runnable{

    private final RegisterSkillController controller;
    private String skillName;

    /**
     * Default constructor that initializes the RegisterSkillController.
     */
    public RegisterSkillUI() {
        controller = new RegisterSkillController();
    }

    /**
     * Gets the RegisterSkillController instance.
     *
     * @return The RegisterSkillController instance.
     */
    private RegisterSkillController getController() {
        return controller;
    }

    /**
     * Main method that executes the skill registration interface.
     */
    public void run() {
        System.out.println("\n\n--- Register Skill ------------------------");

        requestSkillName();

        submitData();

    }



    /**
     * Submits the skill data without description to the controller and displays a success or failure message.
     */
    private void submitData() {
        Optional<Skill> skill = getController().registerSkill(skillName);

        // Verify
        if (skill.isPresent()) {
            System.out.println("Skill successfully registered!");
        } else {
            System.out.println("Skill not registered!");
        }
    }


    /**
     * Requests the skill name from the user.
     */
    private void requestSkillName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Skill Name: ");
        skillName = input.nextLine();
    }


}
