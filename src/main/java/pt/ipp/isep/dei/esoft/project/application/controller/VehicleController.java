package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

public class VehicleController {

    private VehicleRepository vehicleRepository;

    public VehicleController() {
        this.vehicleRepository = getVehicleRepository();
    }

    private VehicleRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();
            //Get the JobRepository
            vehicleRepository = repositories.getVehicleRepository();
        }
        return vehicleRepository;
    }


    public Optional<List<Vehicle>> requestList(String activation) {
        Optional<List<Vehicle>> request = Optional.empty();

        request = vehicleRepository.requestList(activation);
        return request;
    }
}
