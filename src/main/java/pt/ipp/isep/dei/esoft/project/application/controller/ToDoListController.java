package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.DTOS.GreenSpaceDTO;
import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.application.Mappers.AgendaMapper;
import pt.ipp.isep.dei.esoft.project.application.Mappers.ToDoMapper;
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

    private GreenSpaceRepository getGreenSpaceRepository() {
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

    public Optional<ToDoEntry> registerToDoEntry(int greenSpaceID, String title, String description, int urgency, int expectedDuration) {

        GreenSpace greenSpace = getGreenSpacesByResponsible().get(greenSpaceID);
        return toDoListRepository.registerToDoEntry(greenSpace, title, description, urgency, expectedDuration);
    }

    public List<GreenSpace> getGreenSpaces() {
        return greenSpaceRepository.getGreenSpacesList();
    }

    public ToDoEntry.Urgency[] getUrgency() {
        return toDoListRepository.getUrgency();
    }

    private String getResponsible() {
        return getRepositories().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    }

    public List<GreenSpace> getGreenSpacesByResponsible() {
        return greenSpaceRepository.getGreenSpacesByResponsible(getResponsible());
    }



    public List<GreenSpaceDTO> getToDoListDTOForResponsible(){
        return toDTO(getGreenSpacesByResponsible());
    }

    private List<GreenSpaceDTO> toDTO(List<GreenSpace> greenSpaceList){
        ToDoMapper toDoMapper = new ToDoMapper();
        return toDoMapper.listToDto(greenSpaceList);
    }


}
