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

    /**
     * Initializes the greenSpaceRepository if it is null, otherwise returns the existing instance.
     *
     * @return the GreenSpaceRepository instance
     */
    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;
    }

    //--------------------------- Show Green Spaces ------------------------------------
    /**
     * Retrieves and sorts the green spaces managed by the currently logged-in user.
     *
     * @param sortOrder the order in which to sort the green spaces
     * @return a sorted list of GreenSpace objects managed by the current user, or null if none are found
     */
    public List<GreenSpace> showGreenSpaces(String sortOrder) {
        String resposible = getResponsible();

        List<GreenSpace> greenSpaces = greenSpaceRepository.getGreenSpacesByResponsible(resposible);
        if(greenSpaces == null || greenSpaces.isEmpty()){
            System.out.printf(ANSI_BRIGHT_RED + "\nGreen Spaces list not found or empty" + ANSI_RESET);
            return null;
        }

        try {
            greenSpaces = sortGreenSpaces.sortGreenSpaces(greenSpaces);
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "\nConfiguration File not found!..Add one in \"/src/main/resource\" location" + ANSI_RESET);
            return null;
        }
        return greenSpaces;
    }

    //------------------------------ Extra Methods ---------------
    /**
     * Retrieves the email of the currently logged-in user from the authentication repository.
     *
     * @return the email of the current user
     */
    private String getResponsible() {
        return repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    }
}
