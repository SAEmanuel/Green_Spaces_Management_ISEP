package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AssignSkillCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class AssignSkillCollaboratorUI implements Runnable {

    private int collaboratorTaxNumber;
    private String skillName;

    //APENAS TESTES
    //Collaborator collaborator1 = new Collaborator("Emanuel Almeida", new Data(2004, 4, 20), new Data(2024, 4, 22), "Casa do Caralho", 911529355, "xico@viado.com", 123456789, "BI", new Job("Sugador de Xereca"));
    //Skill skill1 = new Skill("Programar em Java");

    private final AssignSkillCollaboratorController controller;

    /**
     * Default constructor that initializes the AssignSkillCollaboratorUI.
     */
    public AssignSkillCollaboratorUI() {
        controller = new AssignSkillCollaboratorController();
    }

    private AssignSkillCollaboratorController getController() {
        return controller;
    }


    @Override
    public void run() {
        System.out.println("\n\n--- Register Skill to Collaborator ------------------------");

        int collaboratorMenuOption;
        do {
            requestCollaboratorTaxNumber();
            collaboratorMenuOption = confirmsCollaboratorTaxNumber();
            System.out.println(collaboratorMenuOption);

            if (collaboratorMenuOption == 1) {
               isCollaboratorTaxNumberValid();

            }

            if (!isCollaboratorTaxNumberValid() && collaboratorMenuOption != -1) {
                System.out.println(ANSI_BRIGHT_RED + "Collaborator Tax Number not found." + ANSI_RESET);
                System.out.println();
            }

        } while (!isCollaboratorTaxNumberValid() && collaboratorMenuOption != -1);

        //só entra no loop se for valido (mesmo se escolher a opcao leaving no menu)
        if (isCollaboratorTaxNumberValid()) {
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


    }

    private void assignSkillCollaboratorByTaxNumber(int collaboratorTaxNumber, String skillName) {
        boolean success = controller.assignSkillCollaboratorByTaxNumber(collaboratorTaxNumber, skillName);

        if (success) {
            System.out.println("Skill successfully assigned.");
        } else {
            System.err.println("Skill could not be assigned. Skill already assigned.");
        }

    }

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

    private void display() {

        System.out.printf("\nTyped Collaborator Tax Number -> [%s%s%s]\n", ANSI_GREEN, collaboratorTaxNumber, ANSI_RESET);
        System.out.print("Options:\n 0 -> Change collaborator tax number\n 1 -> Continue\n 2 -> Exit\nSelected option: ");
    }

    private boolean getSkillList() {
        List<Skill> skillList;

        skillList = controller.getSkillList();

        if (!skillList.isEmpty()) {
            for (Skill skill : skillList) {
                System.out.println(skill);
            }
            return true;
        } else {
            return false;
        }
    }

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

    private boolean isCollaboratorTaxNumberValid() {
        return controller.isCollaboratorIDValid(collaboratorTaxNumber);
    }

    private boolean isSkillValid() {
        return controller.isSkillNameValid(skillName);
    }

    private String getCollaboratorName() {
        return controller.getCollaboratorName(collaboratorTaxNumber);
    }

    private boolean requestSkillToCollaborator() {
        boolean isValid;
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the skill name: ");

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

