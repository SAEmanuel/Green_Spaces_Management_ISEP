package pt.ipp.isep.dei.esoft.project.application.Mappers;

import pt.ipp.isep.dei.esoft.project.application.DTOS.GreenSpaceDTO;
import pt.ipp.isep.dei.esoft.project.application.DTOS.ListGreenSpaceDTO;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;

import java.util.List;

public class ToDoMapper {
    private ListGreenSpaceDTO listGreenSpaceDTO;

    /**
     * Constructs a new ToDoMapper instance.
     */
    public ToDoMapper() {
        listGreenSpaceDTO = new ListGreenSpaceDTO();
    }

    //------------------------------------------------
    /**
     * Converts a list of GreenSpace objects to a list of GreenSpaceDTO objects.
     *
     * @param greenSpaceList The list of GreenSpace objects.
     * @return The list of GreenSpaceDTO objects.
     */
    public List<GreenSpaceDTO> listToDto(List<GreenSpace> greenSpaceList) {
        for (GreenSpace g : greenSpaceList) {
            GreenSpaceDTO obj =  toDto(g);
            listGreenSpaceDTO.addObjDto(obj);
        }
        return listGreenSpaceDTO.getListGreenSpaceDto();
    }

    /**
     * Converts a single GreenSpace object to a GreenSpaceDTO object.
     *
     * @param greenSpace The GreenSpace object.
     * @return The GreenSpaceDTO object.
     */
    private GreenSpaceDTO toDto(GreenSpace greenSpace) {
        return new GreenSpaceDTO(greenSpace);
    }
}


