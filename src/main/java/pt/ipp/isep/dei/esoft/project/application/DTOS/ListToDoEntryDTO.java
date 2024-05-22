package pt.ipp.isep.dei.esoft.project.application.DTOS;


import java.util.ArrayList;
import java.util.List;

public class ListToDoEntryDTO {

    private List<ToDoEntryDTO> listToDoEntryDto;

    public ListToDoEntryDTO() {
        this.listToDoEntryDto = new ArrayList<>();
    }

    public void addObjDto(ToDoEntryDTO toDoEntryDTO) {
        listToDoEntryDto.add(toDoEntryDTO);
    }

    public List<ToDoEntryDTO> getListToDoEntryDto() {
        return listToDoEntryDto;
    }
}
