package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleRepository {

    private final List<Vehicle> vehicleList;

    private static final float PERCENTAGE_OF_KM = 0.05f;

    /**
     * Constructor for the class.
     */
    public VehicleRepository() {
        this.vehicleList = new ArrayList<>();
    }

    /**
     * Method to register a new vehicle.
     *
     * @param plateId        The plate ID of the vehicle.
     * @param brand          The brand of the vehicle.
     * @param model          The model of the vehicle.
     * @param type           The type of the vehicle.
     * @param tareWeight     The tare weight of the vehicle.
     * @param grossWeight    The gross weight of the vehicle.
     * @param currentKm      The current kilometers of the vehicle.
     * @param checkUpFrequency The check-up frequency of the vehicle.
     * @param lastCheckUp      The date of the last check-up of the vehicle.
     * @param registerDate     The registration date of the vehicle.
     * @param acquisitionDate  The acquisition date of the vehicle.
     * @return An Optional containing the registered Vehicle if successful, otherwise empty.
     */
    public Optional<Vehicle> registerVehicle(String plateId, String brand, String model, String type, float tareWeight, float grossWeight, float currentKm, float checkUpFrequency, float lastCheckUp, Data registerDate, Data acquisitionDate) {

        Optional<Vehicle> optionalVehicle = Optional.empty();

        Vehicle vehicle = new Vehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);

        if (addVehicle(vehicle)) {
            optionalVehicle = Optional.of(vehicle.clone());
        }

        return optionalVehicle;
    }

    /**
     * Method to add a vehicle to the repository.
     *
     * @param vehicle The vehicle to be added.
     * @return True if the vehicle was successfully added, otherwise false.
     */
    private boolean addVehicle(Vehicle vehicle) {
        boolean success = false;

        // Validate the vehicle before adding
        if (validate(vehicle)) {
            success = vehicleList.add(vehicle);
        }

        return success;
    }

    /**
     * Method to validate a vehicle before adding it to the repository.
     *
     * @param vehicle The vehicle to be validated.
     * @return True if the vehicle is valid, otherwise false.
     */
    private boolean validate(Vehicle vehicle) {
        return vehicleListDoNotContains(vehicle);
    }

    /**
     * Method to check if the vehicle list does not contain a specific vehicle.
     *
     * @param vehicle The vehicle to be checked.
     * @return True if the vehicle list does not contain the vehicle, otherwise false.
     */
    private boolean vehicleListDoNotContains(Vehicle vehicle) {
        return !vehicleList.contains(vehicle);
    }

    /**
     * Method to request a list of vehicles needing check-up.
     *
     * @param activation The activation signal for the request.
     * @return An Optional containing the list of requested vehicles if successful, otherwise empty.
     */
    public Optional<List<Vehicle>> requestList(String activation) {

        Optional<List<Vehicle>> optionalValue = Optional.empty();

        List<Vehicle> vehiclesForCheckUp = getVehiclesNeedingCheckUp();

        if (checkUpListCreated()) {
            optionalValue = Optional.of(vehiclesForCheckUp);
        }
        return optionalValue;
    }

    /**
     * Method to check if the check-up list was created.
     *
     * @return True if the check-up list was created, otherwise false.
     */
    private boolean checkUpListCreated() {
        List<Vehicle> vehiclesForCheckUp = getVehiclesNeedingCheckUp();
        return !vehiclesForCheckUp.isEmpty();
    }

    /**
     * Method to get a list of vehicles needing check-up.
     *
     * @return The list of vehicles needing check-up.
     */
    private List<Vehicle> getVehiclesNeedingCheckUp() {
        List<Vehicle> vehiclesForCheckUp = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) {
            if ((vehicle.getCurrentKm() - vehicle.getLastCheckUp()) * (1 - PERCENTAGE_OF_KM) >= vehicle.getCheckUpFrequency()) {
                vehiclesForCheckUp.add(vehicle);
            }

        }
        return vehiclesForCheckUp;
    }
}
