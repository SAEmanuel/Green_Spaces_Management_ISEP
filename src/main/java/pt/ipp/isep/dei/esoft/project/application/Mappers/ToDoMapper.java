package pt.ipp.isep.dei.esoft.project.application.Mappers;

import pt.ipp.isep.dei.esoft.project.application.DTOS.GreenSpaceDTO;
import pt.ipp.isep.dei.esoft.project.application.DTOS.ListGreenSpaceDTO;
import pt.ipp.isep.dei.esoft.project.application.DTOS.ListToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import java.util.List;

public class ToDoMapper {
    private ListGreenSpaceDTO listGreenSpaceDTO;

    public ToDoMapper() {
        listGreenSpaceDTO = new ListGreenSpaceDTO();
    }

    //------------------------------------------------

    public List<GreenSpaceDTO> listToDto(List<GreenSpace> greenSpaceList) {
        for (GreenSpace g : greenSpaceList) {
            GreenSpaceDTO obj =  toDto(g);
            listGreenSpaceDTO.addObjDto(obj);
        }
        return listGreenSpaceDTO.getListGreenSpaceDto();
    }

    private GreenSpaceDTO toDto(GreenSpace greenSpace) {
        return new GreenSpaceDTO(greenSpace);
    }
}


