package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleCheckUpRepository;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.Calendar;
import java.util.List;

public class CreateCheckUpController {

    private VehicleRepository vehicleRepository;
    private VehicleCheckUpRepository vehicleCheckUpRepository;


    public CreateCheckUpController() {
        this.vehicleRepository = getVehicleRepository();
        this.vehicleCheckUpRepository = getVehicleCheckUpRepository();
    }

    // ---- GET REPOSITORY ------------------------------

    private VehicleCheckUpRepository getVehicleCheckUpRepository() {
        if (vehicleCheckUpRepository == null) {
            vehicleCheckUpRepository = getRepositories().getVehicleCheckUpRepository();
        }
        return vehicleCheckUpRepository;
    }

    private VehicleRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            vehicleRepository = getRepositories().getVehicleRepository();
        }
        return vehicleRepository;
    }

    private Repositories getRepositories() {
        return Repositories.getInstance();
    }

    // ---- GET REPOSITORY ------------------------------

    public CheckUp createCheckUp() {
        return null;
    }

    public List<Vehicle> getVehicles() {
        return vehicleRepository.getVehicleList();
    }


    public Data validateDate() {
        return CheckUp.validateDate();
    }

    public int validateKms(Vehicle vehicle) {
        int lastCheckUpKms = 2;
        int currentKms = 3;

        return CheckUp.validateKms(lastCheckUpKms, currentKms);
    }


}
