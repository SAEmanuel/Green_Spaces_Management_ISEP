# US007 - Register the check-up of a vehicle

## 4. Tests 

**Test 1:** This test ensures that a CheckUp object can be created with valid parameters.

	@Test
    void ensureCheckUpNameIsValid_1() {
        CheckUp checkUp = new CheckUp(v1, kms, data);
    }
	

**Test 2:** This test ensures that CheckUp constructor throws a NullPointerException if any of its parameters is null.

	 @Test
    void ensureCheckUpIsNotNull() {
        //Act and Assert
        assertThrows(NullPointerException.class, () -> new CheckUp(null, null, null));
    }

**Test 3:** This test ensures that CheckUp constructor throws an IllegalArgumentException if the kilometers parameter is negative.

	@Test
    void ensureCheckUpIsInvalid_1() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new CheckUp(v1, -1000f, data));
    }


**Test 4:** This test ensures that CheckUp constructor throws an IllegalArgumentException if the date parameter is in the future.

	@Test
    void ensureCheckUpIsInvalid_2() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new CheckUp(v1, kms, new Data(2030, 4, 29)));
    }

**Test 5:** This test ensures that CheckUp constructor throws an IllegalArgumentException if the kilometers parameter is less than a predefined value.

	@Test
    void ensureCheckUpIsInvalid_3() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new CheckUp(v1, 1500f , data));
    }

**Test 6:** This test ensures that the equals method returns true when comparing two CheckUp objects referencing the same memory location.

	@Test
    void comparingCheckUpSameRef() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        CheckUp ck2 = ck1;
        assertTrue(ck1.equals(ck2));
    }

**Test 7:** This test ensures that the equals method returns false when comparing a CheckUp object with a different class instance.

	@Test
    void comparingCheckUpWithDifferentObjectInstance_1() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        Object object = new Object();
        assertFalse(ck1.equals(object));
    }

**Test 8:** This test ensures that the equals method returns false when comparing a CheckUp object with an instance of a different class.

	@Test
    void comparingCheckUpWithDifferentObjectInstance_2() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        Skill skill = new Skill("Java Developer");
        assertFalse(ck1.equals(skill));
    }

**Test 9:** This test ensures that the equals method returns true when comparing two CheckUp objects with the same attributes.

	   @Test
    void comparingCheckUp_sameCheckUp() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        CheckUp ck2 = new CheckUp(v1, kms, data);
        assertTrue(ck1.equals(ck2));
    }

**Test 10:** This test ensures that the equals method returns false when comparing two CheckUp objects with different attributes.

	@Test
    void comparingCheckUps_differentCheckUp() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        CheckUp ck2 = new CheckUp(v1, 2000f, data);
        assertFalse(ck1.equals(ck2));
    }

**Test 11:** This test ensures that the clone method creates a new instance of CheckUp with different memory reference.

	@Test
    void differentRefForJob() {
        CheckUp checkUp1 = new CheckUp(v1, kms, data);
        CheckUp checkUp2 = checkUp1.clone();
        boolean sameRef = checkUp2 == checkUp1;
        assertFalse(sameRef);
    }

_It is also recommended to organize this content by subsections._ 


## 5. Construction (Implementation)

### Class CreateTaskController 

```java
public CheckUp(Vehicle vehicle, Float checkUpKms, Data checkUpDate) {
    validateCheckUp(vehicle, checkUpKms, checkUpDate);

    this.vehicle = vehicle;
    this.checkUpKms = checkUpKms;
    this.checkUpDate = checkUpDate;
    
}
```

### Class Organization

```java
public Optional<CheckUp> registerCheckUp(Vehicle vehicleByPlateID, float checkUpKms, Data checkUpDate) {
    return vehicleCheckUpRepository.registerCheckUp(vehicleByPlateID, checkUpKms, checkUpDate);
}
```
```java
public Optional<CheckUp> registerCheckUp(Vehicle vehicleByPlateID, float checkUpKms, Data checkUpDate) {
    Optional<CheckUp> optionalCheckUp = Optional.empty();

    CheckUp checkUp = new CheckUp(vehicleByPlateID, checkUpKms, checkUpDate);

    if (addCheckUp(checkUp)) {
        optionalCheckUp = Optional.of(checkUp.clone());
        setLastKmsCheckUp(vehicleByPlateID, checkUpKms);
    }

    return optionalCheckUp;
}
```


## 6. Integration and Demo 

* For demo purposes some tasks are bootstrapped while system starts.

## 7. Observations

n/a