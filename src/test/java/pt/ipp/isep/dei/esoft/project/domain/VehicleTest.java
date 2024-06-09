package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VehicleTest {

    private static final Data registerDate = new Data(2023, 5, 1);
    private static final Data acquisitionDate = new Data(2023, 5, 15);

    // Plate ID related tests

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdIsEmpty() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("", "Toyota", "Yaris", 0, 1000f, 1200f, 0f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureValidatePlateAfterHighest() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("00-00-AA", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureValidatePlateAfterMiddle() {
        Data registerDate1 = new Data(2018, 5, 1);
        Data acquisitionDate1 = new Data(2018, 5, 12);
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AA-00-00", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate1, acquisitionDate1));
    }

    @Test
    void ensureValidatePlateAfterLowest() {
        Data registerDate2 = new Data(2008, 5, 1);
        Data acquisitionDate2 = new Data(2008, 5, 12);
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AA-00-00", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureValidatePlateWithMalformedId() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("00-00", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdExceedsMaxLength() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("ABCD123456", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdHasInvalidFormat() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB_00_AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    // Vehicle brand related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenBrandIsNull() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", null, "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenBrandContainsSpecialCharacters() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toy@ta", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenBrandContainsNumbers() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toy0ta", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenBrandIsEmpty() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    // Vehicle model related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenModelIsNull() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", null, 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenModelContainsSpecialCharacters() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Y@ris", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenModelIsEmpty() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "", 0, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }


//    // Vehicle type related tests
//    @Test
//    void ensureVehicleIsNotRegisteredWhenTypeIsInvalid() {
//        // Act and Assert
//        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", 5, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
//    }

    // Vehicle weight related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenTareWeightIsGreaterThanGrossWeight() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1200f, 1000f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenTareWeightIsNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, -1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenGrossWeightIsNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, -10000f, -1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    // Vehicle mileage registration related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenCurrentKmIsLessThanLastCheckUp() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 3000f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenCurrentKmIsNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, -2000f, 5000f, -3000f, registerDate, acquisitionDate));
    }

    // Maintenance frequency related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenCheckUpFrequencyIsNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, -5000f, 0f, registerDate, acquisitionDate));
    }

    // Last check-up related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenLastCheckUpIsNegative() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, -1000f, registerDate, acquisitionDate));
    }

    // Validations for Method Equals
    @Test
    void comparingVehiclesSameRef() {
        Vehicle vehicle1 = new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 1000f, registerDate, acquisitionDate);
        Vehicle vehicle2 = vehicle1;
        assertTrue(vehicle1.equals(vehicle2));
    }

    @Test
    void comparingVehicleWithDifferentObjectInstance_1() {
        Vehicle vehicle1 = new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 1000f, registerDate, acquisitionDate);
        Object object = new Object();
        assertFalse(vehicle1.equals(object));
    }

    @Test
    void comparingVehicleWithDifferentObjectInstance_2() {
        Vehicle vehicle1 = new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 1000f, registerDate, acquisitionDate);
        Job job = new Job("Gardener");
        assertFalse(vehicle1.equals(job));
    }


    @Test
    void comparingVehicles_sameVehicles() {
        Vehicle vehicle1 = new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 1000f, registerDate, acquisitionDate);
        Vehicle vehicle2 = new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 1000f, registerDate, acquisitionDate);
        assertTrue(vehicle1.equals(vehicle2));
    }

    @Test
    void comparingVehicles_differentVehicles() {
        Vehicle vehicle1 = new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 1000f, registerDate, acquisitionDate);
        Vehicle vehicle2 = new Vehicle("AA-11-BB", "Mercedes", "GLE", 0, 1000f, 1200f, 2000f, 5000f, 1000f, registerDate, acquisitionDate);
        assertFalse(vehicle1.equals(vehicle2));
    }

    // Validations Method Clone
    @Test
    void differentRefForSkill() {
        Vehicle vehicle1 = new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 1000f, registerDate, acquisitionDate);
        Vehicle vehicle2 = vehicle1.clone();
        boolean sameRef = vehicle1 == vehicle2;
        assertFalse(sameRef);
    }

    // Date validation
    @Test
    void ensureRegisterDateBiggerAcquisitionDate() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", 0, 1000f, 1200f, 2000f, 5000f, 0f, acquisitionDate, registerDate));
    }
}
