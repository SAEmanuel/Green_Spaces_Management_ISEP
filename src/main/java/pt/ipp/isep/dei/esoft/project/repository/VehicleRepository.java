package pt.ipp.isep.dei.esoft.project.repository;


import java.util.List;
import java.util.Optional;

public class VehicleRepository {

    private static final float percentageOfKm = 0.05f;

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
