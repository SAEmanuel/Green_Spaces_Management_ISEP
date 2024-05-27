package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.ShowGreenSpacesByManagerController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.io.IOException;
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
        int answerList = -2;
        Scanner input = new Scanner(System.in);

        while (answerList < 1 || answerList > listSize + 1) {
            displaySortTypesListOptions(sortTypes);
            System.out.print("Select a team: ");
            answerList = input.nextInt();
        }

        if (answerList != listSize + 1) {
            sortType = sortTypes.get(answerList-1);
        }else
            return false;

        return true;
    }

    private void displaySortTypesListOptions(List<String> sortTypes) {
        int i = 1;
        for (String s : sortTypes) {
            System.out.println("• Type: " + s);
            System.out.println("\n" + ANSI_PURPLE + "   Option -> [" + i + "]" + ANSI_RESET);
            i++;
        }
        System.out.println();
        System.out.println("• Type: " + " - Exit" + "\n" + ANSI_PURPLE + "   Option -> [" + i + "]" + ANSI_RESET);
    }
}
