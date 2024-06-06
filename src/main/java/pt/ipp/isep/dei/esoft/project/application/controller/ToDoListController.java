package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.DTOS.GreenSpaceDTO;
import pt.ipp.isep.dei.esoft.project.application.Mappers.ToDoMapper;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * The ToDoListController class manages the logic for handling to-do list entries,
 * interacting with repositories to perform CRUD operations on to-do entries and green spaces.
 */
public class ToDoListController {

    private GreenSpaceRepository greenSpaceRepository;
    private ToDoListRepository toDoListRepository;

    /**
     * Constructs a new ToDoListController instance and initializes repositories.
     */
    public ToDoListController() {
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.toDoListRepository = getToDoListRepository();
    }

    /**
     * Gets the GreenSpaceRepository instance.
     *
     * @return the GreenSpaceRepository instance
     */
    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            greenSpaceRepository = getRepositories().getGreenSpaceRepository();
        }
        return greenSpaceRepository;
    }

    /**
     * Gets the ToDoListRepository instance.
     *
     * @return the ToDoListRepository instance
     */
    private ToDoListRepository getToDoListRepository() {
        if (toDoListRepository == null) {
            toDoListRepository = getRepositories().getToDoRepository();
        }
        return toDoListRepository;
    }

    /**
     * Gets the Repositories instance.
     *
     * @return the Repositories instance
     */
    private Repositories getRepositories() {
        return Repositories.getInstance();
    }

    /**
     * Registers a new to-do entry in the repository.
     *
     * @param greenSpaceID the ID of the green space
     * @param title the title of the to-do entry
     * @param description the description of the to-do entry
     * @param urgency the urgency level of the to-do entry
     * @param expectedDuration the expected duration of the to-do entry
     * @return an Optional containing the registered to-do entry, or empty if registration fails
     */
    public Optional<ToDoEntry> registerToDoEntry(int greenSpaceID, String title, String description, int urgency, int expectedDuration) {
        GreenSpace greenSpace = searchForGreenSpaceID(greenSpaceID);
        return toDoListRepository.registerToDoEntry(greenSpace, title, description, urgency, expectedDuration);
    }

    /**
     * Searches for a green space by its ID.
     *
     * @param greenSpaceID the ID of the green space
     * @return the GreenSpace instance
     */
    private GreenSpace searchForGreenSpaceID(int greenSpaceID) {
        return getGreenSpacesByResponsible().get(greenSpaceID);
    }

    /**
     * Gets the list of all green spaces.
     *
     * @return the list of green spaces
     */
    public List<GreenSpace> getGreenSpaces() {
        return greenSpaceRepository.getGreenSpacesList();
    }

    /**
     * Gets the list of urgency levels.
     *
     * @return an array of urgency levels
     */
    public ToDoEntry.Urgency[] getUrgency() {
        return toDoListRepository.getUrgency();
    }

    /**
     * Gets the responsible user's email.
     *
     * @return the email of the responsible user
     */
    private String getResponsible() {
        return getRepositories().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    }

    /**
     * Gets the list of green spaces for which the current user is responsible.
     *
     * @return the list of green spaces by responsible
     */
    public List<GreenSpace> getGreenSpacesByResponsible() {
        return greenSpaceRepository.getGreenSpacesByResponsible(getResponsible());
    }

    /**
     * Gets the list of green spaces as GreenSpaceDTO objects for which the current user is responsible.
     *
     * @return the list of GreenSpaceDTO objects by responsible
     */
    public List<GreenSpaceDTO> getGreenSpaceDTOByResponsible() {
        return toDTO(getGreenSpacesByResponsible());
    }

    /**
     * Converts a list of GreenSpace objects to a list of GreenSpaceDTO objects.
     *
     * @param greenSpaceList the list of GreenSpace objects
     * @return the list of GreenSpaceDTO objects
     */
    private List<GreenSpaceDTO> toDTO(List<GreenSpace> greenSpaceList) {
        ToDoMapper toDoMapper = new ToDoMapper();
        return toDoMapper.listToDto(greenSpaceList);
    }

    /**
     * Gets the list of all to-do entries.
     *
     * @return the list of to-do entries
     */
    public List<ToDoEntry> getToDoEntries() {
        return toDoListRepository.getToDoList();
    }
}
