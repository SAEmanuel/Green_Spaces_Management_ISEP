package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleCheckUpRepository {

    private final List<CheckUp> vehicleCheckUpsList;

    public VehicleCheckUpRepository() {
        vehicleCheckUpsList = new ArrayList<>();
    }

    public List<CheckUp> getCheckUpsList() {
        return clone();
    }

    public List<CheckUp> clone(){
        return new ArrayList<>(this.vehicleCheckUpsList);
    }

    public Optional<CheckUp> registerCheckUp(Vehicle vehicleByPlateID, float checkUpKms, Data checkUpDate) {

        Optional<CheckUp> optionalCheckUp = Optional.empty();

        CheckUp checkUp = new CheckUp(vehicleByPlateID, checkUpKms, checkUpDate);

        if (addCheckUp(checkUp)) {
            optionalCheckUp = Optional.of(checkUp.clone());

            setLastKmsCheckUp(vehicleByPlateID, checkUpKms);
        }

        return optionalCheckUp;
    }

    private boolean addCheckUp(CheckUp checkUp) {
        boolean success = false;

        // Validate the Check-Up before adding
        if (validate(checkUp)) {
            success = vehicleCheckUpsList.add(checkUp);
        }

        return success;
    }

    private boolean validate(CheckUp checkUp) {
        return CheckListDoNotContainsCheckUp(checkUp);
    }

    private boolean CheckListDoNotContainsCheckUp(CheckUp checkUp) {
        return !vehicleCheckUpsList.contains(checkUp);
    }

    private void setLastKmsCheckUp(Vehicle vehicleByPlateID, float checkUpKms) {
        vehicleByPlateID.setLastCheckUp(checkUpKms);
    }


}
