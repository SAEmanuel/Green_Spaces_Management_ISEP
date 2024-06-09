package pt.ipp.isep.dei.esoft.project.application.DTOS;

import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

public class ToDoEntryDTO {

    private ToDoEntry objDto;

    /**
     * Constructor for ToDoEntryDTO.
     * @param toDoEntry The ToDoEntry object to wrap.
     */
    public ToDoEntryDTO(ToDoEntry toDoEntry) {
        this.objDto = toDoEntry;
    }

    /**
     * Gets the wrapped ToDoEntry object.
     * @return The ToDoEntry object.
     */
    public ToDoEntry getObjDto() {
        return objDto;
    }

    /**
     * Overrides the toString method to return the string representation of the wrapped ToDoEntry object.
     * @return The string representation of the wrapped ToDoEntry object.
     */
    @Override
    public String toString() {
        return String.format(objDto.toString());
    }
}
