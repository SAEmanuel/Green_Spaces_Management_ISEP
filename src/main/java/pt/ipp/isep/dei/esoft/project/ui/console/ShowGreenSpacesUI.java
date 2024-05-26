package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.application.controller.ShowGreenSpacesByManagerController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;

import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class ShowGreenSpacesUI implements Runnable {

    private ShowGreenSpacesByManagerController controller;
    public ShowGreenSpacesUI(){
        controller = new ShowGreenSpacesByManagerController();
    }

    //-------------------------------------- Run if happy success -------------------------

    public void run() {
        System.out.println("\n\n--- Current managed green spaces ------------------------");

        submitData();
    }

    private void submitData() {

        List<GreenSpace> result = getController().showGreenSpaces();

        if (result != null) {
            for(GreenSpace gs : result)
                System.out.println(ANSI_BRIGHT_BLUE + gs.getName() + ANSI_RESET);
        } else {
            System.out.printf(ANSI_BRIGHT_RED + "\nGreen Spaces not found" + ANSI_RESET);
        }
    }


    private ShowGreenSpacesByManagerController getController() {
        return controller;
    }
}
