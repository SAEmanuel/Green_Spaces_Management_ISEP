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

        submitData();
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


}
