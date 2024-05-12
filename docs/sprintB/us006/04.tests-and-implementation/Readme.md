# US006 - Registering Vehicles

## 4. Tests 


**Test 1:** Ensure Vehicle Is Not Registered When Plate Id Is Null

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdIsNull() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle(null, "Toyota", "Yaris", "Car", 1000f, 1200f, 0f, 5000f, 0f, registerDate, acquisitionDate));
    }
**Test 2:** Ensure Vehicle Is Not Registered When PlateId Is Empty

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdIsEmpty() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("", "Toyota", "Yaris", "Car", 1000f, 1200f, 0f, 5000f, 0f, registerDate, acquisitionDate));
    }
**Test 3:** Ensure Validate Plate After Highest.

    @Test
    void ensureValidatePlateAfterHighest() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("00-00-AA", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }
**Test 4:** Ensure Validate Plate After Middle.

    @Test
    void ensureValidatePlateAfterMiddle() {
        Data registerDate1 = new Data(2018, 5, 1);
        Data acquisitionDate1 = new Data(2018, 5, 12);
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AA-00-00", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate1, acquisitionDate1));
    }
**Test 5:** Ensure Validate Plate After Lowest

    @Test
    void ensureValidatePlateAfterLowest() {
        Data registerDate2 = new Data(2008, 5, 1);
        Data acquisitionDate2 = new Data(2008, 5, 12);
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AA-00-00", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }
**Test 6:** Ensure Validate Plate With Mal formed Id.

    @Test
    void ensureValidatePlateWithMalformedId() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("00-00", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }
**Test 7:** Ensure Vehicle Is Not Registered When PlateId Exceeds Max Length.

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdExceedsMaxLength() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("ABCD123456", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }
**Test 8:** Check that it is not possible to create an instance of the Task class with null values.

    @Test
    void ensureVehicleIsNotRegisteredWhenPlateIdHasInvalidFormat() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB_00_AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

**Test 9:** Ensure Vehicle Is Not Registered When Brand Is Null.

    // Vehicle brand related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenBrandIsNull() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", null, "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

**Test 10:** Check that it is not possible to create an instance of the Task class with null values.

    // Vehicle model related tests
    @Test
    void ensureVehicleIsNotRegisteredWhenModelIsNull() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", null, "Car", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }


**Test 11:** Ensure Vehicle Is Not Registered When Type Contains Special Characters.

    @Test
    void ensureVehicleIsNotRegisteredWhenTypeContainsSpecialCharacters() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "C@r", 1000f, 1200f, 2000f, 5000f, 0f, registerDate, acquisitionDate));
    }

**Test 12:** Different Ref For Skill.

    // Validations Method Clone
    @Test
    void differentRefForSkill() {
        Vehicle vehicle1 = new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 1000f, registerDate, acquisitionDate);
        Vehicle vehicle2 = vehicle1.clone();
        boolean sameRef = vehicle1 == vehicle2;
        assertFalse(sameRef);
    }

**Test 13:** Ensure Register Date Bigger Acquisition Date.

    // Date validation
    @Test
    void ensureRegisterDateBiggerAcquisitionDate() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Vehicle("AB-00-AC", "Toyota", "Yaris", "Car", 1000f, 1200f, 2000f, 5000f, 0f, acquisitionDate, registerDate));
    }


## 5. Construction (Implementation)

### Class VehicleController 

```java
    public Optional<Vehicle> registerVehicle(String plateId, String brand, String model, String type, float tareWeight, float grossWeight, float currentKm, float checkUpFrequency, float lastCheckUp, Data registerDate, Data acquisitionDate) {
        Optional<Vehicle> newVehicle;

        newVehicle = vehicleRepository.registerVehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);

        return newVehicle;
    }
```




## 6. Integration and Demo 

* None


## 7. Observations

n/a