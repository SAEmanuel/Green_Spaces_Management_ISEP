package pt.ipp.isep.dei.esoft.project.application.Mappers;

import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import java.util.ArrayList;
import java.util.List;

public class AgendaMapper {

    /**
     * Default constructor for AgendaMapper.
     */
    public AgendaMapper() {
    }

    //------------------------------------------------
    /**
     * Converts a list of ToDoEntry objects to a list of ToDoEntryDTO objects.
     *
     * @param toDoEntries The list of ToDoEntry objects.
     * @return The list of ToDoEntryDTO objects.
     */
    public List<ToDoEntryDTO> toDto(List<ToDoEntry> toDoEntries) {
        List<ToDoEntryDTO> agendaDTO = new ArrayList<>();
        for (ToDoEntry toDoEntry : toDoEntries) {
            ToDoEntryDTO obj =  toDto(toDoEntry);
            agendaDTO.add(obj);
        }
        return agendaDTO;
    }

    /**
     * Converts a single ToDoEntry object to a ToDoEntryDTO object.
     *
     * @param toDoEntry The ToDoEntry object.
     * @return The ToDoEntryDTO object.
     */
    private ToDoEntryDTO toDto(ToDoEntry toDoEntry) {
        return new ToDoEntryDTO(toDoEntry);
    }
}
