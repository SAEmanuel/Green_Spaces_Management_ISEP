package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

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

        int continueApp = confirmsData();

        if (continueApp != 2) {
            submitData();
        }

    }

    private int confirmsData() {
        Scanner input = new Scanner(System.in);
        int option = -1;


        while(option != 1){
            display();
            option = input.nextInt();

            if (option == 0) {
                System.out.println();
                requestSkillName();
            }else if (option == 1) {
                option = 1;
                System.out.println();
                System.out.printf("Skill name chosen -> [%s]\n",skillName);
            } else if (option == 2) {
                return option;
            } else {
                System.out.println("Invalid choice. Please enter 0 or 1 or 2.");
            }
        }
        return option;
    }

    private void display() {

        System.out.printf("\nTyped name -> [%s]\n",skillName.trim());
        System.out.print("Options:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
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
