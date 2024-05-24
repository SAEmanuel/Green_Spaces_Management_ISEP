package pt.ipp.isep.dei.esoft.project.application.DTOS;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

public class GreenSpaceDTO {
    private GreenSpace objDto;

    public GreenSpaceDTO(GreenSpace greenSpace) {
        this.objDto = greenSpace;
    }

    public GreenSpace getObjDto() {
        return objDto;
    }



    @Override
    public String toString() {
        return String.format(objDto.toString());
    }
}
