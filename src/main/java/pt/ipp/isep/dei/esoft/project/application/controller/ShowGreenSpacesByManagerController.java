package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.SortGreenSpaces;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class ShowGreenSpacesByManagerController {
    private GreenSpaceRepository greenSpaceRepository; // Repository to manage green spaces
    private Repositories repositories = Repositories.getInstance(); // Singleton instance to access various repositories
    private SortGreenSpaces sortGreenSpaces = new SortGreenSpaces();

    public ShowGreenSpacesByManagerController(){
        this.greenSpaceRepository = getGreenSpaceRepository();
    }

    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;
    }

    //--------------------------- Show Green Spaces ------------------------------------
    public List<GreenSpace> showGreenSpaces(String sortOrder) {
        String resposible = getResponsible();

        List<GreenSpace> greenSpaces = greenSpaceRepository.getGreenSpacesByResponsible(resposible);
        if(greenSpaces == null || greenSpaces.isEmpty()){
            System.out.printf(ANSI_BRIGHT_RED + "\nGreen Spaces list not found or empty" + ANSI_RESET);
            return null;
        }

        try {
            greenSpaces = sortGreenSpaces.sortGreenSpaces( sortOrder, greenSpaces);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return greenSpaces;
    }

    public List<String> getSortTypes(){
        try {
            return sortGreenSpaces.getSortTypes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //------------------------------ Extra Methods ---------------
    private String getResponsible() {
        return repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    }
}
