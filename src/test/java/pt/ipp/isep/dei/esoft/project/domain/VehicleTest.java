package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;

public class VehicleTest {

    // Plate ID related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdIsNull() {
        // Arrange
        Data registerDate = new Data(2023, 5, 1);
        Data acquisitionDate = new Data(2023, 5, 15);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle(null, "Toyota", "Yaris", "Car", 1000f, 1200f, 0f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdIsEmpty() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("", "Toyota", "Yaris", "Car", 1000f, 1200f, 0f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureValidatePlateAfterHighest() {
        // Arrange
        Data registerDate = new Data(2023, 5, 1);
        Data acquisitionDate = new Data(2023, 5, 15);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("00-00-AA", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureValidatePlateAfterMiddle() {
        // Arrange
        Data registerDate = new Data(2015, 5, 1);
        Data acquisitionDate = new Data(2023, 5, 15);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AA-00-00", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureValidatePlateAfterLowest() {
        // Arrange
        Data registerDate = new Data(1999, 5, 1);
        Data acquisitionDate = new Data(2023, 5, 15);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AA-00-00", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureValidatePlateWithMalformedId() {
        // Arrange
        Data registerDate = new Data(2023, 5, 1);
        Data acquisitionDate = new Data(2023, 5, 15);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("00-00", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdExceedsMaxLength() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("ABCD123456", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdHasInvalidFormat() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB_00_AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    // Vehicle brand related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenBrandIsNull() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", null, "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenBrandContainsSpecialCharacters() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toy@ta", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenBrandContainsNumbers() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toy0ta", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenBrandIsEmpty() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    // Vehicle model related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenModelIsNull() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", null, "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenModelContainsSpecialCharacters() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Y@ris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenModelIsEmpty() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    // Vehicle type related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenTypeIsNull() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", null, 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenTypeContainsSpecialCharacters() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "C@r", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenTypeContainsNumbers() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car2", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenTypeIsEmpty() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    // Vehicle weight related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenTareWeightIsGreaterThanGrossWeight() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1200f, 1000f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenTareWeightIsNegative() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", -1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenGrossWeightIsNegative() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, -1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    // Vehicle mileage registration related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenCurrentKmIsLessThanLastCheckUp() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 3000f, registerDate, acquisitionDate));
    }

    @Test
    void ensureVehicleIsNotRegisteredWhenCurrentKmIsNegative() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, -2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

    // Maintenance frequency related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenCheckUpFrequencyIsNegative() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, -5000f, 0f, registerDate, acquisitionDate));
    }

    // Last check-up related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenLastCheckUpIsNegative() {
        // Arrange
        Data registerDate = new Data(2023, 5, 15);
        Data acquisitionDate = new Data(2023, 5, 1);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, -1000f, registerDate, acquisitionDate));
    }
}
