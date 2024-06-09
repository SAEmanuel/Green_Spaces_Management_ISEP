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
    private ShowGreenSpacesByManagerController controller;

    /**
     * Constructor for ShowGreenSpacesUI.
     * Initializes the controller.
     */
    public ShowGreenSpacesUI(){
        controller = new ShowGreenSpacesByManagerController();
    }

    /**
     * Gets the controller for showing green spaces by manager.
     *
     * @return the controller instance
     */
    private ShowGreenSpacesByManagerController getController() {
        return controller;
    }

    //-------------------------------------- Run if happy success -------------------------

    /**
     * Executes the user interface logic for showing the current managed green spaces.
     */
    public void run() {
        System.out.println("\n\n--- Current managed green spaces ------------------------");

        submitData();
    }

    /**
     * Submits the data to show the green spaces managed by the current user.
     */
    private void submitData() {
        List<GreenSpace> result = getController().showGreenSpaces();

        if (result != null) {
            for(GreenSpace gs : result)
                System.out.println(ANSI_BRIGHT_BLUE + gs.getName() + ANSI_RESET);
        } else {
            System.out.printf(ANSI_BRIGHT_RED + "\nGreen Spaces couldn't be shown" + ANSI_RESET);
        }
    }
}
