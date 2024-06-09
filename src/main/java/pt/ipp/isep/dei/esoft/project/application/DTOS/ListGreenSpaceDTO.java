package pt.ipp.isep.dei.esoft.project.application.DTOS;

import java.util.ArrayList;
import java.util.List;

public class ListGreenSpaceDTO {

    private List<GreenSpaceDTO> listGreenSpaceDto;

    /**
     * Constructor for ListGreenSpaceDTO.
     */
    public ListGreenSpaceDTO() {
        this.listGreenSpaceDto = new ArrayList<>();
    }

    /**
     * Adds a GreenSpaceDTO object to the list.
     * @param greenSpaceDTO The GreenSpaceDTO object to add.
     */
    public void addObjDto(GreenSpaceDTO greenSpaceDTO) {
        listGreenSpaceDto.add(greenSpaceDTO);
    }

    /**
     * Gets the list of GreenSpaceDTO objects.
     * @return The list of GreenSpaceDTO objects.
     */
    public List<GreenSpaceDTO> getListGreenSpaceDto() {
        return listGreenSpaceDto;
    }
}
