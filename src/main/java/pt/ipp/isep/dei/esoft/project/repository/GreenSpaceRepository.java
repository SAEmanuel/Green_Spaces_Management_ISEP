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
    private final List<GreenSpace> greenSpacesList;

    public GreenSpaceRepository() {
        this.greenSpacesList = new ArrayList<>();
    }

    public Optional<GreenSpace> registerGreenSpace(String name, int size, float area,String address,String resposible) {

        Optional<GreenSpace> optionalGreenSpace = Optional.empty();

        GreenSpace greenSpace = new GreenSpace(name,size,area,address,resposible);

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

    public void add(GreenSpace greenSpace){
        this.greenSpacesList.add(greenSpace);
    }

    public List<GreenSpace> getGreenSpacesList() {
        return clone();
    }

    public List<GreenSpace> clone(){
        return new ArrayList<>(this.greenSpacesList);
    }

    public void showGreenSpaces() {
        if (greenSpacesList.isEmpty()) {
            System.out.println(ANSI_BRIGHT_RED + "No green spaces were found in the repository." + ANSI_RESET);
        } else {
            System.out.println("\n--List of Green Spaces--");
            for (int i = 0; i < greenSpacesList.size(); i++) {
                GreenSpace greenSpace = greenSpacesList.get(i);
                System.out.println("• Green Space: " + greenSpace.getName() + "\n"+ANSI_PURPLE+"   Option -> [" + (i+1) + "]"+ ANSI_RESET);
            }
            System.out.println("----------------");
        }
    }


}


