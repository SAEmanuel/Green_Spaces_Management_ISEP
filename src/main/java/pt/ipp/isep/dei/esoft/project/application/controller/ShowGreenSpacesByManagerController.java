package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.List;
import java.util.Optional;

public class ShowGreenSpacesByManagerController {
    private GreenSpaceRepository greenSpaceRepository; // Repository to manage green spaces
    private Repositories repositories = Repositories.getInstance(); // Singleton instance to access various repositories

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
    public List<GreenSpace> showGreenSpaces() {
        String resposible = getResponsible();

        return greenSpaceRepository.getGreenSpacesByResponsible(resposible);
    }

    //------------------------------ Extra Methods ---------------
    private String getResponsible() {
        return repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    }
}
