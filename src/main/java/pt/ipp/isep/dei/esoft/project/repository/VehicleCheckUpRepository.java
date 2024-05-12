package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The VehicleCheckUpRepository class represents a repository of vehicle check-ups in the project.
 */
public class VehicleCheckUpRepository {
    // Attributes
    private final List<CheckUp> vehicleCheckUpsList;

    /**
     * Constructor for creating a VehicleCheckUpRepository object.
     */
    public VehicleCheckUpRepository() {
        vehicleCheckUpsList = new ArrayList<>();
    }

    /**
     * Retrieves a clone of the list of check-ups.
     *
     * @return A list of CheckUp objects.
     */
    public List<CheckUp> getCheckUpsList() {
        return clone();
    }

    /**
     * Clones the list of check-ups.
     *
     * @return A new list of CheckUp objects.
     */
    public List<CheckUp> clone() {
        return new ArrayList<>(this.vehicleCheckUpsList);
    }

    /**
     * Registers a new check-up for a vehicle.
     *
     * @param vehicleByPlateID The vehicle associated with the check-up.
     * @param checkUpKms       The kilometers at the time of the check-up.
     * @param checkUpDate      The date of the check-up.
     * @return An Optional containing the registered check-up if successful, empty otherwise.
     */
    public Optional<CheckUp> registerCheckUp(Vehicle vehicleByPlateID, float checkUpKms, Data checkUpDate) {
        Optional<CheckUp> optionalCheckUp = Optional.empty();

        CheckUp checkUp = new CheckUp(vehicleByPlateID, checkUpKms, checkUpDate);

        if (addCheckUp(checkUp)) {
            optionalCheckUp = Optional.of(checkUp.clone());
            setLastKmsCheckUp(vehicleByPlateID, checkUpKms);
        }

        return optionalCheckUp;
    }

    /**
     * Adds a check-up to the repository.
     *
     * @param checkUp The check-up to be added.
     * @return True if the check-up was successfully added, false otherwise.
     */
    private boolean addCheckUp(CheckUp checkUp) {
        boolean success = false;

        // Validate the Check-Up before adding
        if (validate(checkUp)) {
            success = vehicleCheckUpsList.add(checkUp);
        }

        return success;
    }

    /**
     * Validates a check-up before adding it to the repository.
     *
     * @param checkUp The check-up to be validated.
     * @return True if the check-up is valid, false otherwise.
     */
    private boolean validate(CheckUp checkUp) {
        return checkListDoesNotContainCheckUp(checkUp);
    }

    /**
     * Checks if the repository already contains a specific check-up.
     *
     * @param checkUp The check-up to be checked.
     * @return True if the repository does not contain the check-up, false otherwise.
     */
    private boolean checkListDoesNotContainCheckUp(CheckUp checkUp) {
        return !vehicleCheckUpsList.contains(checkUp);
    }

    /**
     * Sets the last check-up kilometers for a vehicle.
     *
     * @param vehicleByPlateID The vehicle associated with the check-up.
     * @param checkUpKms       The kilometers at the time of the check-up.
     */
    private void setLastKmsCheckUp(Vehicle vehicleByPlateID, float checkUpKms) {
        vehicleByPlateID.setLastCheckUp(checkUpKms);
    }
}
