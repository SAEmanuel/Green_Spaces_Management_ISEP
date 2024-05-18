package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AssignSkillCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
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
    private Skill skill;
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
        boolean isValidTaxNumber, continueOption = false;

        if (!isCollaboratorListEmpty()) {
            do {
                requestCollaboratorTaxNumber();
                isValidTaxNumber = containsCollaboratorByTaxNumber();

                if (!isValidTaxNumber) {
                    System.out.println(ANSI_BRIGHT_RED + "Collaborator Tax Number not found." + ANSI_RESET);
                    continueOption = askIfTryAgain();

                }

            } while (!isValidTaxNumber && continueOption);

            // Only enters the loop if valid (even if choosing the leaving option in the menu)
            if (isValidTaxNumber) {
                System.out.println("-- Collaborator Info --");
                System.out.println(ANSI_BRIGHT_YELLOW + "Name: " + getCollaboratorName() + ANSI_RESET);
                System.out.println(ANSI_BRIGHT_YELLOW + "Email: " + getCollaboratorEmail() + ANSI_RESET);
                System.out.println(ANSI_BRIGHT_YELLOW + "Birth Date: " + getCollaboratorBirthDate() + ANSI_RESET);

                if (!controller.getSkillList().isEmpty()) {
                    boolean moreThanOneSkill = true;

                    while (moreThanOneSkill) {

                        getSkillListOption();
                        assignSkillCollaboratorByTaxNumber(collaboratorTaxNumber, skill);
                        moreThanOneSkill = confirmsMoreThanOneSkill();
                    }

                } else {
                    System.out.println(ANSI_BRIGHT_RED + "Error - There is no skill created." + ANSI_RESET);
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
     * @param skill                 The name of the skill to assign.
     */
    private void assignSkillCollaboratorByTaxNumber(int collaboratorTaxNumber, Skill skill) {
        boolean success = controller.assignSkillCollaboratorByTaxNumber(collaboratorTaxNumber, skill);

        if (success) {
            System.out.println();
            System.out.println(ANSI_BRIGHT_GREEN + "Skill successfully assigned." + ANSI_RESET);
        } else {
            System.out.println();
            System.out.println(ANSI_BRIGHT_RED + "Skill could not be assigned. Skill already assigned." + ANSI_RESET);
        }

    }

    /**
     * Retrieves and displays the list of available skills.
     *
     * @return True if the skill list is not empty, false otherwise.
     */

    public Skill getSkillListOption() {
        Scanner input = new Scanner(System.in);
        int answer;
        int n = controller.getSkillList().size();


        controller.showSkills();

        if (n != 0) {
            System.out.print("Skill ID: ");
            while (true) {
                try {
                    answer = input.nextInt() - 1;
                    if (answer <= n - 1 && answer >= 0) {
                        skill = controller.getSkill(answer);
                        return skill;
                    }
                } catch (InputMismatchException e) {
                    System.out.print(ANSI_BRIGHT_RED + "Invalid Skill ID number! Enter a new one: " + ANSI_RESET + "\n");
                    input.nextLine();
                }
                System.out.print(ANSI_BRIGHT_YELLOW + "Invalid Skill ID, enter a new one: " + ANSI_RESET);

            }
        }
        return null;
    }

    private boolean askIfTryAgain() {
        Scanner input = new Scanner(System.in);
        String option;

        System.out.print("\nDo you wish to try again? (y/n): ");

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

    /**
     * Checks if the collaborator list contains a collaborator with the given tax number.
     *
     * @return True if the collaborator list contains the tax number, false otherwise.
     */
    private boolean containsCollaboratorByTaxNumber() {
        List<Collaborator> collaboratorList = controllerCollaborator.returnCollaboratorRepository().getCollaboratorList();

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
     * Checks if the collaborator list is empty.
     *
     * @return True if the collaborator list is empty, false otherwise.
     */
    private boolean isCollaboratorListEmpty() {
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
     * Retrieves the email of the collaborator with the given tax number.
     *
     * @return The email of the collaborator.
     */
    private String getCollaboratorEmail() {
        return controller.getCollaboratorEmail(collaboratorTaxNumber);
    }

    /**
     * Retrieves the birth date of the collaborator with the given tax number.
     *
     * @return The birth date of the collaborator.
     */
    private Data getCollaboratorBirthDate() {
        return controller.getCollaboratorBirthDate(collaboratorTaxNumber);
    }


}

