package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GreenSpaceRepository {
    private final List<GreenSpace> greenSpacesList;

    public GreenSpaceRepository() {
        this.greenSpacesList = new ArrayList<>();
    }

    public Optional<GreenSpace> registerGreenSpace(String name, int size, float area) {

        Optional<GreenSpace> optionalGreenSpace = Optional.empty();

        GreenSpace greenSpace = new GreenSpace(name,size,area);

        if (addGreenSpace(greenSpace)) {
            optionalGreenSpace = Optional.of(greenSpace.clone());
        }

        return optionalGreenSpace;
    }

    private boolean addGreenSpace(GreenSpace greenSpace) {
        boolean success = false;

        // Validate the vehicle before adding
        if (validate(greenSpace)) {
            success = greenSpacesList.add(greenSpace);
        }

        return success;
    }

    private boolean validate(GreenSpace greenSpace) {
        return greenSpaceListDoNotContainsByName(greenSpace.getName());
    }

    private boolean greenSpaceListDoNotContainsByName(String greenSpaceName) {
        for (GreenSpace g : greenSpacesList) {
            if (g.getName().equalsIgnoreCase(greenSpaceName)) {
                return false;
            }
        }
        return true;
    }


}
