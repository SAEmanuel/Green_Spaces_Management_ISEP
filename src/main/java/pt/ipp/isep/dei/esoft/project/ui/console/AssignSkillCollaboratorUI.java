package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AssignSkillCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;


/**
 * The AssignSkillCollaboratorUI class represents a console-based user interface
 * for assigning skills to collaborators in a project management system.
 * It implements the Runnable interface to enable concurrent execution.
 */
public class AssignSkillCollaboratorUI implements Runnable {

    // Attributes
    private int collaboratorTaxNumber;
    private String skillName;
    private final AssignSkillCollaboratorController controller;
    private final CreateCollaboratorController controllerCollaborator;

    /**
     * Default constructor that initializes the AssignSkillCollaboratorUI.
     */
    public AssignSkillCollaboratorUI() {
        controller = new AssignSkillCollaboratorController();
        controllerCollaborator = new CreateCollaboratorController();
    }

    /**
     * Executes the user interface logic for assigning skills to collaborators.
     * This method is invoked when an instance of AssignSkillCollaboratorUI is run.
     */
    @Override
    public void run() {
        System.out.println("\n\n--- Register Skill to Collaborator ------------------------");
        boolean isValidTaxNumber = false;
        int collaboratorMenuOption;

        if(!isCollaboratorListEmpty()) {
            do {
                requestCollaboratorTaxNumber();
                collaboratorMenuOption = confirmsCollaboratorTaxNumber();

                if (collaboratorMenuOption == 1) {
                    isValidTaxNumber = containsCollaboratorByTaxNumber();
                }

                if (!isValidTaxNumber && collaboratorMenuOption != -1) {
                    System.out.println(ANSI_BRIGHT_RED + "Collaborator Tax Number not found." + ANSI_RESET);
                    System.out.println();
                }

            } while (!isValidTaxNumber && collaboratorMenuOption != -1);

            // Only enters the loop if valid (even if choosing the leaving option in the menu)
            if (containsCollaboratorByTaxNumber()) {
                System.out.println("Collaborator Name: " + getCollaboratorName());
                if (getSkillList()) {
                    boolean moreThanOneSkill = true;

                    while (moreThanOneSkill) {

                        if (requestSkillToCollaborator()) {
                            assignSkillCollaboratorByTaxNumber(collaboratorTaxNumber, skillName);

                            moreThanOneSkill = confirmsMoreThanOneSkill();
                        }
                    }

                } else {
                    System.out.println("There is no skill created.");
                }
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Error - There is no collaborator created." + ANSI_RESET);
        }
    }

    /**
     * Assigns a skill to a collaborator based on tax number and skill name.
     *
     * @param collaboratorTaxNumber The tax number of the collaborator.
     * @param skillName             The name of the skill to assign.
     */
    private void assignSkillCollaboratorByTaxNumber(int collaboratorTaxNumber, String skillName) {
        boolean success = controller.assignSkillCollaboratorByTaxNumber(collaboratorTaxNumber, skillName);

        if (success) {
            System.out.println(ANSI_BRIGHT_GREEN + "Skill successfully assigned." + ANSI_RESET);
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Skill could not be assigned. Skill already assigned." + ANSI_RESET);
        }

    }

    /**
     * Prompts the user to confirm the collaborator's tax number.
     *
     * @return The user's choice.
     */
    private int confirmsCollaboratorTaxNumber() {
        int option = -1;
        Scanner input = new Scanner(System.in);

        while (option != 1) {
            display();
            option = input.nextInt();

            switch (option) {
                case 0:
                    System.out.println();
                    requestCollaboratorTaxNumber();
                    break;
                case 1:
                    System.out.println();
                    System.out.printf("Collaborator Tax Number chosen -> [%s]\n", collaboratorTaxNumber);
                    break;
                case 2:
                    System.out.println(ANSI_BRIGHT_RED + "LEAVING..." + ANSI_RESET);
                    return -1;
                default:
                    System.out.println(ANSI_BRIGHT_RED + "Invalid choice. Please enter 0 or 1 or 2." + ANSI_RESET);
            }
        }
        return option;
    }

    /**
     * Displays the options for changing the collaborator tax number.
     */
    private void display() {

        System.out.printf("\nTyped Collaborator Tax Number -> [%s%s%s]\n", ANSI_GREEN, collaboratorTaxNumber, ANSI_RESET);
        System.out.print("Options:\n 0 -> Change collaborator tax number\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }

    /**
     * Retrieves and displays the list of available skills.
     *
     * @return True if the skill list is not empty, false otherwise.
     */
    private boolean getSkillList() {
        List<Skill> skillList;

        skillList = controller.getSkillList();

        if (!skillList.isEmpty()) {
            System.out.println("-- Available skills --");
            for (Skill skill : skillList) {
                System.out.println(skill);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Asks the user if they want to register more skills.
     *
     * @return True if the user wants to register more skills, false otherwise.
     */
    private boolean confirmsMoreThanOneSkill() {
        Scanner input = new Scanner(System.in);
        String option;

        System.out.print("\nDo you wish to register new info? (y/n): ");

        while (true) {

            option = input.nextLine();

            if (option.equalsIgnoreCase("y")) {
                return true;
            }
            if (option.equalsIgnoreCase("n")) {
                System.out.println(ANSI_BRIGHT_RED + "LEAVING..." + ANSI_RESET);
                return false;
            }
            System.out.println(ANSI_BRIGHT_RED + "WARNING - Enter a valid option..." + ANSI_RESET);
        }

    }

    /**
     * Prompts the user to enter the collaborator's tax number.
     */
    private void requestCollaboratorTaxNumber() {
        boolean success = false;
        Scanner input = new Scanner(System.in);

        System.out.print("Please enter the collaborator Tax Number: ");

        while (!success)
            try {
                collaboratorTaxNumber = input.nextInt();
                System.out.println();
                success = true;

            } catch (InputMismatchException e) {
                System.out.println(ANSI_BRIGHT_RED + "Error - Only numbers are accepted" + ANSI_RESET);
                input.next();
                System.out.println();
                System.out.print("Please enter a valid collaborator Tax Number: ");
            }
    }

    //    private boolean isCollaboratorTaxNumberValid() {
//        return controller.isCollaboratorIDValid(collaboratorTaxNumber);
//    }

    /**
     * Checks if the collaborator list contains a collaborator with the given tax number.
     *
     * @return True if the collaborator list contains the tax number, false otherwise.
     */
    private boolean containsCollaboratorByTaxNumber() {
        List <Collaborator> collaboratorList = controllerCollaborator.getCollaboratorRepository2().getCollaboratorList();

        if (!collaboratorList.isEmpty()) {
            for (Collaborator collaborator : collaboratorList) {
                if (collaborator.getTaxPayerNumber() == collaboratorTaxNumber) {
                    return true;
                }
            }
        } else {
            System.out.println(ANSI_BRIGHT_RED + "Collaborator List is Empty." + ANSI_RESET);
            System.out.println();
            return false;
        }

        return false;
    }

    /**
     * Checks if the entered skill name is valid.
     *
     * @return True if the skill name is valid, false otherwise.
     */
    private boolean isSkillValid() {
        return controller.isSkillNameValid(skillName);
    }

    /**
     * Checks if the collaborator list is empty.
     *
     * @return True if the collaborator list is empty, false otherwise.
     */
    private boolean isCollaboratorListEmpty(){
        return controller.isCollaboratorListEmpty();
    }

    /**
     * Retrieves the name of the collaborator with the given tax number.
     *
     * @return The name of the collaborator.
     */
    private String getCollaboratorName() {
        return controller.getCollaboratorName(collaboratorTaxNumber);
    }

    /**
     * Prompts the user to enter the skill name.
     *
     * @return True if the skill name is valid, false otherwise.
     */
    private boolean requestSkillToCollaborator() {
        boolean isValid;
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter the skill name: ");

        skillName = input.nextLine().trim();

        isValid = isSkillValid();

        while (!isValid) {
            System.out.print(ANSI_BRIGHT_RED + "Invalid skill name! Enter a new one: " + ANSI_RESET);
            skillName = input.nextLine();
            isValid = isSkillValid();
        }

        return isValid;

    }
}

