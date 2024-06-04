package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleRepository implements Serializable {

    private final List<Vehicle> vehicleList;

    private static final float PERCENTAGE_OF_KM = 0.05f;

    /**
     * Constructor for the class.
     */
    public VehicleRepository() {
        this.vehicleList = new ArrayList<>();
    }


    /**
     * Registers a new vehicle in the repository.
     *
     * @param plateId          The plate ID of the vehicle.
     * @param brand            The brand of the vehicle.
     * @param model            The model of the vehicle.
     * @param type             The type of the vehicle.
     * @param tareWeight       The tare weight of the vehicle.
     * @param grossWeight      The gross weight of the vehicle.
     * @param currentKm        The current kilometers of the vehicle.
     * @param checkUpFrequency The check-up frequency of the vehicle.
     * @param lastCheckUp      The date of the last check-up of the vehicle.
     * @param registerDate     The registration date of the vehicle.
     * @param acquisitionDate  The acquisition date of the vehicle.
     * @return An Optional containing the registered Vehicle if successful, otherwise empty.
     */
    public Optional<Vehicle> registerVehicle(String plateId, String brand, String model, int type, float tareWeight, float grossWeight, float currentKm, float checkUpFrequency, float lastCheckUp, Data registerDate, Data acquisitionDate) {

        Optional<Vehicle> optionalVehicle = Optional.empty();

        Vehicle vehicle = new Vehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);

        if (addVehicle(vehicle)) {
            optionalVehicle = Optional.of(vehicle.clone());
        }

        return optionalVehicle;
    }


    /**
     * Adds a vehicle to the repository.
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
     * skillList.add(skill);
     * <p>
     * Validates a vehicle before adding it to the repository.
     *
     * @param vehicle The vehicle to be validated.
     * @return True if the vehicle is valid, otherwise false.
     */
    private boolean validate(Vehicle vehicle) {
        return vehicleListDoNotContainsByPlate(vehicle);
    }


    /**
     * Retrieves the list of vehicles in the repository.
     *
     * @return The list of vehicles.
     */
    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    /**
     * Checks if the vehicle list does not contain a specific vehicle by plate ID.
     *
     * @param vehicle The vehicle to be checked.
     * @return True if the vehicle list does not contain the vehicle, otherwise false.
     */
    private boolean vehicleListDoNotContainsByPlate(Vehicle vehicle) {
        for (Vehicle v : vehicleList) {
            if (v.getPlateId().equals(vehicle.getPlateId())) {
                return false;
            }
        }
        return true;
    }

    public Vehicle vehicleListContainsByPlate(String plateID) {
        for (Vehicle v : vehicleList) {
            if (v.getPlateId().equals(plateID)) {
                return v;
            }
        }
        return null;
    }


    /**
     * Requests a list of vehicles needing check-up.
     *
     * @return An Optional containing the list of requested vehicles if successful, otherwise empty.
     */
    public Optional<List<Vehicle>> requestList() {

        Optional<List<Vehicle>> optionalValue = Optional.empty();

        List<Vehicle> vehiclesForCheckUp = getVehiclesNeedingCheckUp();

        if (checkUpListCreated()) {
            optionalValue = Optional.of(vehiclesForCheckUp);
        }
        return optionalValue;
    }


    /**
     * Checks if the check-up list was created.
     *
     * @return True if the check-up list was created, otherwise false.
     */
    public boolean checkUpListCreated() {
        List<Vehicle> vehiclesForCheckUp = getVehiclesNeedingCheckUp();
        return !vehiclesForCheckUp.isEmpty();
    }


    /**
     * Retrieves a list of vehicles needing check-up.
     *
     * @return The list of vehicles needing check-up.
     */
    public List<Vehicle> getVehiclesNeedingCheckUp() {
        List<Vehicle> vehiclesForCheckUp = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) {
            if ((vehicle.getCurrentKm() - vehicle.getLastCheckUp()) >= vehicle.getCheckUpFrequency() * (1 - PERCENTAGE_OF_KM)) {
                vehiclesForCheckUp.add(vehicle);
            }

        }
        return vehiclesForCheckUp;
    }

    public Vehicle.Type[] showType() {
        return Vehicle.Type.values();
    }


    //----------------------- Serialization methods -------------------------------

    public List<Vehicle> getVehicleListSerialization() {
        return vehicleList;
    }

    public void serializationInput(List<Vehicle> vehicleList) {
        this.vehicleList.clear();
        this.vehicleList.addAll(vehicleList);
    }

    //-------------------------------------------------------------------------------


    //---------------------- Boostrap methods ---------------------------------------

    public void add(Vehicle vehicle) {
        vehicleList.add(vehicle);
    }

    //-------------------------------------------------------------------------------

    public List<Vehicle> getAvailableVehicles(List<AgendaEntry> tasks) {
        List<Vehicle> availableVehicles = getVehicleList();

        for (AgendaEntry task : tasks) {
            System.out.println(task);
            List<Vehicle> taskVehicle = task.getVehicles();
            if (taskVehicle != null) {
                for (Vehicle vehicle : taskVehicle) {
                    availableVehicles.remove(vehicle);
                }
            }
        }

        return availableVehicles;

    }
}
