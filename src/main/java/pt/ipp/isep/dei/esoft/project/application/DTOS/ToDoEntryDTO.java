package pt.ipp.isep.dei.esoft.project.application.DTOS;

import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

public class ToDoEntryDTO {

    private ToDoEntry objDto;

    public ToDoEntryDTO(ToDoEntry toDoEntry) {
        this.objDto = toDoEntry;
    }

    public ToDoEntry getObjDto() {
        return objDto;
    }

    @Override
    public String toString() {
        return String.format(objDto.toString());
    }
}
