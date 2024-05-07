package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VehicleRepositoryTest {
    private static final Data registerDate = new Data(2023, 5, 1);
    private static final Data acquisitionDate = new Data(2023, 5, 15);


    // Test Vehicle List
    @Test
    void registerVehicleShouldAddVehicleToListWhenValid() {
        // Arrange
        VehicleRepository vehicleRepository = new VehicleRepository();

        // Act
        Optional<Vehicle> result = vehicleRepository.registerVehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 500f, 100f, registerDate, acquisitionDate);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, vehicleRepository.getVehicleList().size());
    }

    @Test
    void registerVehicleShouldNotAddVehicleToListWhenInvalid() {
        // Arrange
        VehicleRepository vehicleRepository = new VehicleRepository();

        //Adds a valid vehicle to the list
        vehicleRepository.registerVehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 500f, 100f, registerDate, acquisitionDate);

        // Act
        Optional<Vehicle> result = vehicleRepository.registerVehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 500f, 100f, registerDate, acquisitionDate);

        // Assert
        assertFalse(result.isPresent());
        assertEquals(1, vehicleRepository.getVehicleList().size()); // The list should not increase in size since it was not added
    }

    // Test CheckUp List

    @Test
    void checkUpListCreatedShouldReturnTrueWhenNotEmpty() {
        // Arrange
        VehicleRepository vehicleRepository = new VehicleRepository();
        vehicleRepository.registerVehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 500f, 100f, registerDate, acquisitionDate);

        // Act
        boolean result = vehicleRepository.checkUpListCreated();

        // Assert
        assertTrue(result);
    }

    @Test
    void checkUpListCreatedShouldReturnFalseWhenEmpty() {
        // Arrange
        VehicleRepository vehicleRepository = new VehicleRepository();

        // Act
        boolean result = vehicleRepository.checkUpListCreated();

        // Assert
        assertFalse(result);
    }
    @Test
    void getVehiclesNeedingCheckUpShouldReturnCorrectList() {
        // Arrange
        VehicleRepository vehicleRepository = new VehicleRepository();
        Data registerDate1 = new Data(2023, 5, 1);
        Data acquisitionDate1 = new Data(2023, 5, 15);
        Vehicle vehicle1 = new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 500f, 100f, registerDate1, acquisitionDate1);
        vehicleRepository.registerVehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 500f, 100f, registerDate1, acquisitionDate1);

        Data registerDate2 = new Data(2023, 5, 2);
        Data acquisitionDate2 = new Data(2023, 5, 16);
        Vehicle vehicle2 = new Vehicle("CD-00-AB", "Honda", "Civic", "Car", 2000f, 2300f, 2500f, 6000f, 200f, registerDate2, acquisitionDate2);
        vehicleRepository.registerVehicle("CD-00-AB", "Honda", "Civic", "Car", 2000f, 2300f, 2500f, 6000f, 200f, registerDate2, acquisitionDate2);

        // Act
        List<Vehicle> result = vehicleRepository.getVehiclesNeedingCheckUp();

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(vehicle1));
        assertFalse(result.contains(vehicle2));
    }

}
