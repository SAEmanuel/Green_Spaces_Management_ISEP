package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;


public class RegisterSkillUI implements Runnable{

    private final RegisterSkillController controller;
    private String skillName;
    private String skillDescription;

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

        int option = requestOption();

        requestSkillName();

        if (option == 1) {
            requestSkillDescription();
            submitDataOption1();
        } else {
            submitDataElse();
        }


    }

    /**
     * Submits the skill data to the controller and displays a success or failure message.
     */
    private void submitDataElse() {
        Optional<Skill> skill = getController().registerSkill(skillName, skillDescription);

        // Verify
        if (skill.isPresent()) {
            System.out.println("Skill successfully registered!");
        } else {
            System.out.println("Skill not registered!");
        }
    }


    /**
     * Submits the skill data without description to the controller and displays a success or failure message.
     */
    private void submitDataOption1() {
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

    /**
     * Requests the skill description from the user.
     */
    private void requestSkillDescription() {
        Scanner input = new Scanner(System.in);
        System.out.print("Skill Description: ");
        skillDescription = input.nextLine();
    }

    /**
     * Requests the option from the user for skill registration.
     *
     * @return The option selected by the user.
     */
    private int requestOption() {
        Scanner input = new Scanner(System.in);
        System.out.print("Select an option:\n");
        System.out.print(" (1) - Create a skill (name and description) \n");
        System.out.print(" (_) - Create a skill (name) \n");

        while (true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for option selected. Please enter a valid option (1 or _):");
                input.nextLine();
            }
        }
    }
}
