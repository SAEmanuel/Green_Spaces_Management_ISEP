package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.Optional;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;


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

    /**
     * Confirms the skill data entered by the user.
     *
     * @return 2 if the user chooses to exit, otherwise returns 1.
     */
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
                System.out.println();
                System.out.printf("Skill name chosen -> [%s]\n",skillName);
            } else if (option == 2) {
                System.out.println(ANSI_BRIGHT_RED+"LEAVING..."+ANSI_RESET);
                return option;
            } else {
                System.out.println(ANSI_BRIGHT_RED+ "Invalid choice. Please enter 0 or 1 or 2."+ ANSI_RESET);
            }
        }
        return option;
    }

    /**
     * Displays the typed skill name and options for confirmation.
     */
    private void display() {

        System.out.printf("\nTyped name -> [%s%s%s]\n",ANSI_GREEN,skillName.trim(),ANSI_RESET);
        System.out.print("Confirmation menu:\n 0 -> Change name\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }


    /**
     * Submits the skill data without description to the controller and displays a success or failure message.
     */
    private void submitData() {
        Optional<Skill> skill = getController().registerSkill(skillName);

        // Verify
        if (skill.isPresent()) {
            System.out.println(ANSI_BRIGHT_GREEN+"Skill successfully registered!"+ ANSI_RESET);
        } else {
            System.out.println(ANSI_BRIGHT_RED +"Skill not registered - Already registered!"+ ANSI_RESET);
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
