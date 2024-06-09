package pt.ipp.isep.dei.esoft.project.application.DTOS;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

public class GreenSpaceDTO {
    private GreenSpace objDto;

    /**
     * Constructor for GreenSpaceDTO.
     * @param greenSpace The GreenSpace object.
     */
    public GreenSpaceDTO(GreenSpace greenSpace) {
        this.objDto = greenSpace;
    }

    /**
     * Gets the GreenSpace object.
     * @return The GreenSpace object.
     */
    public GreenSpace getObjDto() {
        return objDto;
    }

    /**
     * Overrides the toString method to represent GreenSpaceDTO as a string.
     * @return String representation of GreenSpaceDTO.
     */
    @Override
    public String toString() {
        return String.format(objDto.toString());
    }
}
