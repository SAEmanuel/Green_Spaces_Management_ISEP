package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleCheckUpRepository;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

/**
 * The CreateCheckUpController class represents a controller for creating vehicle check-ups in the application.
 */
public class CreateCheckUpController {

    // Attributes
    private VehicleRepository vehicleRepository;
    private VehicleCheckUpRepository vehicleCheckUpRepository;

    /**
     * Constructor for creating a CreateCheckUpController object.
     */
    public CreateCheckUpController() {
        this.vehicleRepository = getVehicleRepository();
        this.vehicleCheckUpRepository = getVehicleCheckUpRepository();
    }

    // ---- GET REPOSITORY ------------------------------

    /**
     * Retrieves the VehicleCheckUpRepository.
     *
     * @return The VehicleCheckUpRepository.
     */
    private VehicleCheckUpRepository getVehicleCheckUpRepository() {
        if (vehicleCheckUpRepository == null) {
            vehicleCheckUpRepository = getRepositories().getVehicleCheckUpRepository();
        }
        return vehicleCheckUpRepository;
    }

    /**
     * Retrieves the VehicleRepository.
     *
     * @return The VehicleRepository.
     */
    private VehicleRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            vehicleRepository = getRepositories().getVehicleRepository();
        }
        return vehicleRepository;
    }

    /**
     * Retrieves the Repositories instance.
     *
     * @return The Repositories instance.
     */
    private Repositories getRepositories() {
        return Repositories.getInstance();
    }

    // ---- GET REPOSITORY ------------------------------

    /**
     * Creates a new check-up.
     *
     * @return The created CheckUp object.
     */
    public CheckUp createCheckUp() {
        return null; // Placeholder implementation
    }

    /**
     * Retrieves the list of vehicles.
     *
     * @return A list of Vehicle objects.
     */
    public List<Vehicle> getVehicles() {
        return vehicleRepository.getVehicleList();
    }

    /**
     * Retrieves a vehicle by its plate ID.
     *
     * @param plateID The plate ID of the vehicle to retrieve.
     * @return The Vehicle object corresponding to the given plate ID, or null if not found.
     */
    public Vehicle getVehicleByPlateID(String plateID) {
        return vehicleRepository.vehicleListContainsByPlate(plateID);
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
        return vehicleCheckUpRepository.registerCheckUp(vehicleByPlateID, checkUpKms, checkUpDate);
    }
}

