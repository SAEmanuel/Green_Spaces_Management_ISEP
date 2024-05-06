package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VehicleRepositoryTest {

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdIsNull() {
        // Arrange
        VehicleRepository vehicleRepository = new VehicleRepository();
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> vehicleRepository.registerVehicle(null, "Toyota", "Yaris", "Car", 1000f, 1200f, 0f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdIsEmpty() {
        // Arrange
        VehicleRepository vehicleRepository = new VehicleRepository();
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> vehicleRepository.registerVehicle("", "Toyota", "Yaris", "Car", 1000f, 1200f, 0f, 5000f, 0f, registerDate, acquisitionDate));
    }


}

