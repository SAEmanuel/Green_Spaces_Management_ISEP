package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.CheckUp;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class VehicleCheckUpRepositoryTest {
    Vehicle v1;
    Data data;
    Float kms;


    @BeforeEach
    void setUp() {
        v1 = new Vehicle("12-AB-34", "AUDI", "A1", "NA", 120, 200, 2000, 500, 1655, new Data(2010, 4, 20), new Data(2010, 4, 20));
        data = new Data(2024, 4, 26);
        kms = 2100f;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerCheckUpShouldAddCheckUpToListWhenValid() {
        // Arrange
        VehicleCheckUpRepository vehicleCheckUpRepository = new VehicleCheckUpRepository();

        // Act
        Optional<CheckUp> result = vehicleCheckUpRepository.registerCheckUp(v1, kms, data);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, vehicleCheckUpRepository.clone().size());
    }

    @Test
    void registerCheckUpShouldNotAddCheckUpToListWhenInvalid() {
        // Arrange
        VehicleCheckUpRepository vehicleCheckUpRepository = new VehicleCheckUpRepository();
        vehicleCheckUpRepository.registerCheckUp(v1, kms, data);

        // Act
        Optional<CheckUp> result = vehicleCheckUpRepository.registerCheckUp(v1, kms, data);

        // Assert
        assertFalse(result.isPresent());
        assertEquals(1, vehicleCheckUpRepository.clone().size()); // The list should not increase in size since it was not added
    }


    @Test
    void ensureClonedCheckUpListIsIndependentFromOriginal() {
        // Arrange
        VehicleCheckUpRepository vehicleCheckUpRepository = new VehicleCheckUpRepository();
        vehicleCheckUpRepository.registerCheckUp(v1, kms, data);

        // Act
        vehicleCheckUpRepository.clone().clear();

        // Assert
        assertEquals(1, vehicleCheckUpRepository.clone().size());
    }
}