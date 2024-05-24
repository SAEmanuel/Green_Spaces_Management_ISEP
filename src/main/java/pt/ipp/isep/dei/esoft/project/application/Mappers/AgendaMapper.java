package pt.ipp.isep.dei.esoft.project.application.Mappers;

import pt.ipp.isep.dei.esoft.project.application.DTOS.ListToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import java.util.List;

public class AgendaMapper {


    public AgendaMapper() {
    }

    //------------------------------------------------

    public List<ToDoEntryDTO> toDto(List<ToDoEntry> toDoEntries) {
        ListToDoEntryDTO agendaDTO = new ListToDoEntryDTO();
        for (ToDoEntry toDoEntry : toDoEntries) {
            ToDoEntryDTO obj =  toDto(toDoEntry);
            agendaDTO.addObjDto(obj);
        }
        return agendaDTO.getListToDoEntryDto();
    }

    private ToDoEntryDTO toDto(ToDoEntry toDoEntry) {
        return new ToDoEntryDTO(toDoEntry);
    }
}
