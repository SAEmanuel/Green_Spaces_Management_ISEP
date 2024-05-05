package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

public class RegisterVehicleController {

    private VehicleRepository vehicleRepository;

    public RegisterVehicleController() {
        this.vehicleRepository = getVehicleRepository();
    }

    private VehicleRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();
            //Get the VehicleRepository
            vehicleRepository = repositories.getVehicleRepository();
        }
        return vehicleRepository;
    }

    public Optional<Vehicle> registerVehicle(String plateId, String brand, String model, String type, float tareWeight, float grossWeight, float currentKm, float checkUpFrequency, Data registerDate, Data acquisitionDate) {
        Optional<Vehicle> newVehicle;

        newVehicle = vehicleRepository.registerVehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, registerDate, acquisitionDate);

        return newVehicle;
    }

    public Optional<List<Vehicle>> requestList(String activation) {
        Optional<List<Vehicle>> request = Optional.empty();

        request = vehicleRepository.requestList(activation);
        return request;
    }
}
