package pt.ipp.isep.dei.esoft.project.repository;


import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class VehicleRepository {

    private final List<Vehicle> vehicleList;

    private static final float PERCENTAGEOFKM = 0.05f;



    public VehicleRepository() {
        this.vehicleList = new ArrayList<>(); ;
    }


    public Optional<Vehicle> registerVehicle(String plateId, String brand, String model, String type, int tareWeight, int grossWeight, float currentKm, float checkUpFrequency, Data registerDate, Data acquisitionDate) {

        Optional<Vehicle> optionalVehicle = Optional.empty();

        Vehicle vehicle = new Vehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, registerDate, acquisitionDate);

        if ( addVehicle(vehicle) ){
            optionalVehicle = Optional.of(vehicle);
        }

        return optionalVehicle;

    }

    private boolean addVehicle(Vehicle vehicle) {
        boolean success = false;

        if ( validate(vehicle) ) {
            success = vehicleList.add(vehicle);
        }

        return success;
    }

    private boolean validate(Vehicle vehicle) {
        return vehicleListDoNotContains(vehicle);
    }

    private boolean vehicleListDoNotContains(Vehicle vehicle) {
        return !vehicleList.contains(vehicle);
    }


    //----------------------------XICO-----------------------------
    public Optional<List<Vehicle>> requestList(String activation) {

        Optional<List<Vehicle>> optionalValue = Optional.empty();

        List<Vehicle> vehicles = getVehiclesNeedingCheckUp();

        if (checkUpListCreated()) {
            optionalValue = Optional.of(vehicles);
        }
        return optionalValue;
    }

    private boolean checkUpListCreated() {
        return true;
    }

    private List<Vehicle> getVehiclesNeedingCheckUp() {
        return null;
    }
}
