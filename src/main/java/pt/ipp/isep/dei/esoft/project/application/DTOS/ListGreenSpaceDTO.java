package pt.ipp.isep.dei.esoft.project.application.DTOS;

import java.util.ArrayList;
import java.util.List;

public class ListGreenSpaceDTO {

    private List<GreenSpaceDTO> listGreenSpaceDto;

    public ListGreenSpaceDTO() {
        this.listGreenSpaceDto = new ArrayList<>();
    }

    public void addObjDto(GreenSpaceDTO greenSpaceDTO) {
        listGreenSpaceDto.add(greenSpaceDTO);
    }

    public List<GreenSpaceDTO> getListGreenSpaceDto() {
        return listGreenSpaceDto;
    }
}
