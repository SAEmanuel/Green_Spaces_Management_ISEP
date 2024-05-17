package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.*;

import java.util.List;
import java.util.Optional;

public class ToDoListController {

    private GreenSpaceRepository greenSpaceRepository;
    private ToDoListRepository toDoListRepository;

    public ToDoListController() {
        this.greenSpaceRepository = getGreenSpaceRepository();
        this.toDoListRepository = getToDoListRepository();
    }

    // ---- GET REPOSITORY ------------------------------

    public GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            greenSpaceRepository = getRepositories().getGreenSpaceRepository();
        }
        return greenSpaceRepository;
    }

    private ToDoListRepository getToDoListRepository() {
        if (toDoListRepository == null) {
            toDoListRepository = getRepositories().getToDoRepository();
        }
        return toDoListRepository;
    }

    private Repositories getRepositories() {
        return Repositories.getInstance();
    }

    // ---- GET REPOSITORY ------------------------------

    public Optional<ToDoEntry> registerToDoEntry(GreenSpace greenSpace, String title, String description, int urgency, int expectedDuration) {
        return toDoListRepository.registerToDoEntry(greenSpace, title, description, urgency, expectedDuration);
    }

    public List<GreenSpace> getGreenSpaces() {
        return greenSpaceRepository.getGreenSpacesList();
    }

    public ToDoEntry.Urgency[] getUrgency() {
        return toDoListRepository.getUrgency();
    }











}
