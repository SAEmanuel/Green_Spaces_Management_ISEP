package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.ShowGreenSpacesByManagerController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class ShowGreenSpacesUI implements Runnable {
    private String sortType;
    private ShowGreenSpacesByManagerController controller;
    public ShowGreenSpacesUI(){
        controller = new ShowGreenSpacesByManagerController();
    }

    private ShowGreenSpacesByManagerController getController() {
        return controller;
    }

    //-------------------------------------- Run if happy success -------------------------

    public void run() {
        System.out.println("\n\n--- Current managed green spaces ------------------------");

        if(displaySortTypesList()){
            submitData();
        }
    }

    private void submitData() {
        List<GreenSpace> result = getController().showGreenSpaces(sortType);

        if (result != null) {
            for(GreenSpace gs : result)
                System.out.println(ANSI_BRIGHT_BLUE + gs.getName() + ANSI_RESET);
        } else {
            System.out.printf(ANSI_BRIGHT_RED + "\nGreen Spaces couldn't be shown" + ANSI_RESET);
        }
    }

    private boolean displaySortTypesList() {
        List<String> sortTypes = controller.getSortTypes();

        if(sortTypes.isEmpty())
            return false;

        int listSize = sortTypes.size();
        Scanner input = new Scanner(System.in);

        int answer;
        displaySortTypesListOptions(sortTypes);
        System.out.println("Select a Team: ");
        while (true) {
            try {
                answer = input.nextInt();
                if (answer <= listSize && answer > 0) {
                    sortType = sortTypes.get(answer-1);
                    return true;
                }
                else
                    if(answer == 0)
                        return false;
                    else
                        System.out.print(ANSI_BRIGHT_YELLOW + "Invalid ID, enter a new one: " + ANSI_RESET);

            } catch (InputMismatchException e) {
                System.out.print(ANSI_BRIGHT_RED + "Invalid ID number! Enter a new one: " + ANSI_RESET);
                input.nextLine();
            }
        }
    }

    private void displaySortTypesListOptions(List<String> sortTypes) {
        int i = 1;
        for (String s : sortTypes) {
            System.out.println("• Type: " + s);
            System.out.println(ANSI_PURPLE + "   Option -> [" + i + "]" + ANSI_RESET + "\n");
            i++;
        }
        System.out.println();
        System.out.println("• Type: " + " - Exit" + "\n" + ANSI_PURPLE + "   Option -> [" + 0 + "]" + ANSI_RESET);
    }
}
