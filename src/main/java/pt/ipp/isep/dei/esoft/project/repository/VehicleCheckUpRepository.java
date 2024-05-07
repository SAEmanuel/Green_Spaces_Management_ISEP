package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.CheckUp;

import java.util.ArrayList;
import java.util.List;

public class VehicleCheckUpRepository {

    private final List<CheckUp> vehicleCheckUpsList;


    public VehicleCheckUpRepository() {
        vehicleCheckUpsList = new ArrayList<>();
    }

    public void createVehicleCheckUp(CheckUp checkUp) {
        //TODO: VALIDACAO
        addCheckUp(checkUp);
    }

    private void addCheckUp(CheckUp checkUp) {
        vehicleCheckUpsList.add(checkUp.clone());
    }

    public List<CheckUp> getCheckUpsList() {
        return clone();
    }

    public List<CheckUp> clone(){
        return new ArrayList<>(this.vehicleCheckUpsList);
    }

}
