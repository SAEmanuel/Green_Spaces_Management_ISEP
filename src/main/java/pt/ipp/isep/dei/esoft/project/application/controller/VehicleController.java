package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class VehicleController {

    private VehicleRepository vehicleRepository;

    /**
     * Constructor for the class.
     */
    public VehicleController() {
        this.vehicleRepository = getVehicleRepository();
    }


    /**
     * Retrieves the vehicle repository.
     *
     * @return The VehicleRepository object.
     */
    private VehicleRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();
            vehicleRepository = repositories.getVehicleRepository();
        }
        return vehicleRepository;
    }

    /**
     * Registers a new vehicle.
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
    public Optional<Vehicle> registerVehicle(String plateId, String brand, String model, int type, float tareWeight, float grossWeight, float currentKm, float checkUpFrequency, float lastCheckUp, Data registerDate, Data acquisitionDate) {
        Optional<Vehicle> newVehicle;

        newVehicle = vehicleRepository.registerVehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);

        return newVehicle;
    }

    /**
     * Requests a list of vehicles.
     *
     * @return An Optional containing the list of requested vehicles if successful, otherwise empty.
     */
    public Optional<List<Vehicle>> requestList() {
        Optional<List<Vehicle>> request;

        request = vehicleRepository.requestList();
        return request;
    }

    public List<Vehicle> getVehicles() {
        return getVehicleRepository().getVehicleList();
    }

    public Vehicle.Type[] showTypes(){
        return vehicleRepository.showType();
    }
}
