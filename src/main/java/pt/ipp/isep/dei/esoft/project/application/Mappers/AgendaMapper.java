package pt.ipp.isep.dei.esoft.project.application.Mappers;

import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import java.util.ArrayList;
import java.util.List;

public class AgendaMapper {


    public AgendaMapper() {
    }

    //------------------------------------------------

    public List<ToDoEntryDTO> toDto(List<ToDoEntry> toDoEntries) {
        List<ToDoEntryDTO> agendaDTO = new ArrayList<>();
        for (ToDoEntry toDoEntry : toDoEntries) {
            ToDoEntryDTO obj =  toDto(toDoEntry);
            agendaDTO.add(obj);
        }
        return agendaDTO;
    }

    private ToDoEntryDTO toDto(ToDoEntry toDoEntry) {
        return new ToDoEntryDTO(toDoEntry);
    }
}
