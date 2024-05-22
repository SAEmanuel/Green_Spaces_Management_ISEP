package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class GreenSpaceRepository implements Serializable {
    private final List<GreenSpace> greenSpacesList; // List to store green spaces

    /**
     * Constructs a new {@code GreenSpaceRepository} with an empty list of green spaces.
     */
    public GreenSpaceRepository() {
        this.greenSpacesList = new ArrayList<>();
    }

    //------------------------------ Register Green Space ---------------------------------
    /**
     * Registers a new green space if it does not already exist in the repository.
     *
     * @param name the name of the green space
     * @param size the size of the green space (0 for GARDEN, 1 for MEDIUM, 2 for LARGE)
     * @param area the area of the green space
     * @param address the address of the green space
     * @param responsible the responsible person for the green space
     * @return an {@code Optional} containing the registered green space if successful, otherwise empty
     */
    public Optional<GreenSpace> registerGreenSpace(String name, int size, float area, String address, String responsible) {

        Optional<GreenSpace> optionalGreenSpace = Optional.empty();

        GreenSpace greenSpace = new GreenSpace(name.trim(), size, area, address.trim(), responsible);

        if (addGreenSpace(greenSpace)) {
            optionalGreenSpace = Optional.of(greenSpace.clone());
        }

        return optionalGreenSpace;
    }


    /**
     * Adds a green space to the repository if it is valid.
     *
     * @param greenSpace the green space to add
     * @return {@code true} if the green space was added successfully, otherwise {@code false}
     */
    private boolean addGreenSpace(GreenSpace greenSpace) {
        boolean success = false;

        // Validate the vehicle before adding
        if (validate(greenSpace)) {
            success = greenSpacesList.add(greenSpace);
        }

        return success;
    }

    /**
     * Validates a green space to ensure it does not already exist in the repository.
     *
     * @param greenSpace the green space to validate
     * @return {@code true} if the green space is valid, otherwise {@code false}
     */
    private boolean validate(GreenSpace greenSpace) {
        return greenSpaceListDoNotContainsByName(greenSpace.getName());
    }

    /**
     * Checks if a green space with the specified name does not exist in the repository.
     *
     * @param greenSpaceName the name of the green space to check
     * @return {@code true} if the green space does not exist, otherwise {@code false}
     */
    private boolean greenSpaceListDoNotContainsByName(String greenSpaceName) {
        for (GreenSpace g : greenSpacesList) {
            if ((g.getName().trim()).equalsIgnoreCase(greenSpaceName.trim())) {
                return false;
            }
        }
        return true;
    }

    //---------------------------------------------------------------------------------



    /**
     * Displays a list of green spaces available for a responsible person.
     *
     * @param greenSpacesAvailableByResponsible the list of green spaces
     */
    public void showGreenSpaces( List<GreenSpace> greenSpacesAvailableByResponsible) {
        if (greenSpacesAvailableByResponsible.isEmpty()) {
            System.out.println(ANSI_BRIGHT_RED + "No green spaces were found in the repository." + ANSI_RESET);
        } else {
            System.out.println("\n--List of Green Spaces--");
            for (int i = 0; i < greenSpacesAvailableByResponsible.size(); i++) {
                GreenSpace greenSpace = greenSpacesAvailableByResponsible.get(i);
                System.out.println("• Green Space: " + greenSpace.getName() + "\n" + ANSI_PURPLE + "   Option -> [" + (i + 1) + "]" + ANSI_RESET);
            }
            System.out.println("----------------");
        }
    }

    /**
     * Retrieves a list of green spaces managed by a specific responsible person.
     *
     * @param responsible the responsible person to search for
     * @return a list of green spaces managed by the specified person
     */
    public List<GreenSpace>  getGreenSpacesByResponsible (String responsible) {
        ArrayList<GreenSpace> greenSpaces = new ArrayList<>();

        for (GreenSpace greenSpace : greenSpacesList) {
            if (greenSpace.getResponsible().equals(responsible)) {
                greenSpaces.add(greenSpace);
            }
        }
        return greenSpaces;
    }

    //-------------------------------------- Extra Methods -----------------------------------------
    /**
     * Adds a green space to the repository.
     *
     * @param greenSpace the green space to add
     */
    public void add(GreenSpace greenSpace) {
        this.greenSpacesList.add(greenSpace);
    }

    /**
     * Retrieves the list of all green spaces in the repository.
     *
     * @return a list of all green spaces
     */
    public List<GreenSpace> getGreenSpacesList() {
        return clone();
    }

    /**
     * Creates a clone of the green spaces list.
     *
     * @return a cloned list of green spaces
     */
    public List<GreenSpace> clone() {
        return new ArrayList<>(this.greenSpacesList);
    }

    /**
     * Retrieves all possible sizes for green spaces.
     *
     * @return an array of all {@code GreenSpace.Size} values
     */
    public GreenSpace.Size[] getGreenSpacesSizes() {
        return GreenSpace.Size.values();
    }


}


