package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GreenSpaceController {

    private GreenSpaceRepository greenSpaceRepository; // Repository to manage green spaces
    private Repositories repositories = Repositories.getInstance(); // Singleton instance to access various repositories


    /**
     * Constructs a new {@code GreenSpaceController} and initializes the green space repository.
     */
    public GreenSpaceController(){
        this.greenSpaceRepository = getGreenSpaceRepository();
    }

    /**
     * Retrieves the {@code GreenSpaceRepository} instance, initializing it if necessary.
     *
     * @return the {@code GreenSpaceRepository} instance
     */
    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;
    }


    //--------------------------- Register Green Space ------------------------------------
    /**
     * Registers a new green space with the specified details.
     *
     * @param name the name of the green space
     * @param size the size of the green space (0 for GARDEN, 1 for MEDIUM, 2 for LARGE)
     * @param area the area of the green space
     * @param address the address of the green space
     * @return an {@code Optional} containing the registered green space if successful, otherwise empty
     */
    public Optional<GreenSpace> registerGreenSpace(String name, int size, float area,String address) {
        Optional<GreenSpace> newGreenSpace;

        String resposible = getResponsible();

        newGreenSpace = greenSpaceRepository.registerGreenSpace(name,size,area,address,resposible);

        return newGreenSpace;
    }

    //------------------------------ Extra Methods ---------------
    /**
     * Retrieves the email of the current responsible user.
     *
     * @return the email of the current responsible user
     */
    private String getResponsible() {
        return repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    }

    /**
     * Retrieves all possible sizes for green spaces.
     *
     * @return an array of all {@code GreenSpace.Size} values
     */
    public GreenSpace.Size[] getGreenSpacesSizes() {
        return GreenSpace.Size.values();
    }

    public List<GreenSpace> getAllGreenSpaces() {
        return greenSpaceRepository.getGreenSpacesList();
    }
}
