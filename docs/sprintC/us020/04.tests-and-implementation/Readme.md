# US020 - Register a Green Space

## 4. Tests

**Test 1:** 

    @Test
    void testConstructorAndGetters() {
        GreenSpace greenSpace = new GreenSpace("Park", 2, 1000, "123 Main St", "John Doe");
            assertEquals("Park", greenSpace.getName());
            assertEquals(GreenSpace.Size.LARGE, greenSpace.getSize());
            assertEquals(1000, greenSpace.getArea());
            assertEquals("123 Main St", greenSpace.getAddress());
            assertEquals("John Doe", greenSpace.getResponsible());
    }


**Test 2:** 

    @Test
    void testConstructorWithInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () ->
        new GreenSpace("", 1, 1000, "123 Main St", "John Doe"));
            assertThrows(IllegalArgumentException.class, () ->
                    new GreenSpace("Park", 1, 0, "123 Main St", "John Doe"));
    
            assertThrows(IllegalArgumentException.class, () ->
                    new GreenSpace("Park", 1, -100, "123 Main St", "John Doe"));
    
            assertThrows(IllegalArgumentException.class, () ->
                    new GreenSpace("Park", 1, 1000, "", "John Doe"));
    }


**Test 3:** 

    @Test
    void testClone() {
        GreenSpace original = new GreenSpace("Park", 2, 1000, "123 Main St", "John Doe");
        GreenSpace clone = original.clone();

        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getSize(), clone.getSize());
        assertEquals(original.getArea(), clone.getArea());
        assertEquals(original.getAddress(), clone.getAddress());
        assertEquals(original.getResponsible(), clone.getResponsible());
        assertNotSame(original, clone);
    }

**Test 4:** 

    @Test
    void testValidation() {
        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace(null, 1, 1000, "123 Main St", "John Doe"));

        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace("Park", 1, -100, "123 Main St", "John Doe"));

        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace("Park", 1, 1000, null, "John Doe"));


    }

**Test 5:** 

    @Test
    void testRegisterGreenSpace() {
    GreenSpaceRepository repository = new GreenSpaceRepository();
    
            Optional<GreenSpace> optionalGreenSpace = repository.registerGreenSpace("Park", 2, 1000, "123 Main St", "John Doe");
    
            assertTrue(optionalGreenSpace.isPresent());
            assertEquals("Park", optionalGreenSpace.get().getName());
    }

**Test 6:**
   
     @Test
        void testRegisterGreenSpaceWithDuplicateName() {
        GreenSpaceRepository repository = new GreenSpaceRepository();

        repository.registerGreenSpace("Park", 2, 1000, "123 Main St", "John Doe");

        Optional<GreenSpace> optionalGreenSpace = repository.registerGreenSpace("Park", 2, 1000, "456 Elm St", "Jane Smith");

        assertFalse(optionalGreenSpace.isPresent());
    }

**Test 7:** 

    @Test
    void testGetGreenSpacesByResponsible() {
        GreenSpaceRepository repository = new GreenSpaceRepository();

        repository.registerGreenSpace("Park", 2, 1000, "123 Main St", "John Doe");
        repository.registerGreenSpace("Garden", 0, 500, "456 Elm St", "John Doe");
        repository.registerGreenSpace("Field", 1, 2000, "789 Oak St", "Jane Smith");

        List<GreenSpace> greenSpaces = repository.getGreenSpacesByResponsible("John Doe");

        assertEquals(2, greenSpaces.size());
        assertEquals("Park", greenSpaces.get(0).getName());
        assertEquals("Garden", greenSpaces.get(1).getName());
    }

**Test 8:** 

    @Test
    void testGetGreenSpacesList() {
        GreenSpaceRepository repository = new GreenSpaceRepository();

        repository.registerGreenSpace("Park", 2, 1000, "123 Main St", "John Doe");
        repository.registerGreenSpace("Garden", 0, 500, "456 Elm St", "John Doe");

        List<GreenSpace> greenSpaces = repository.getGreenSpacesList();

        assertEquals(2, greenSpaces.size());
        assertEquals("Park", greenSpaces.get(0).getName());
        assertEquals("Garden", greenSpaces.get(1).getName());
    }


## 5. Construction (Implementation)

### Class GreenSpaceController

```java
    public GreenSpaceController(){
        this.greenSpaceRepository = getGreenSpaceRepository();
}
```

```java
    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;
}
```

```java
    public Optional<GreenSpace> registerGreenSpace(String name, int size, float area,String address) {
        Optional<GreenSpace> newGreenSpace;
    
        String resposible = getResponsible();
    
        newGreenSpace = greenSpaceRepository.registerGreenSpace(name,size,area,address,resposible);
    
        return newGreenSpace;
}
```
```java
    private String getResponsible() {
        return repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
}
```
```java
    public GreenSpace.Size[] getGreenSpacesSizes() {
        return GreenSpace.Size.values();
}
```

### Class GreenSpaceRepository 

```java
    public GreenSpaceRepository() {
    this.greenSpacesList = new ArrayList<>();
}
```

```java
    public Optional<GreenSpace> registerGreenSpace(String name, int size, float area, String address, String responsible) {

        Optional<GreenSpace> optionalGreenSpace = Optional.empty();

        GreenSpace greenSpace = new GreenSpace(name.trim(), size, area, address.trim(), responsible);

        if (addGreenSpace(greenSpace)) {
            optionalGreenSpace = Optional.of(greenSpace.clone());
        }

        return optionalGreenSpace;
    }

```

```java
    private boolean addGreenSpace(GreenSpace greenSpace) {
        boolean success = false;

        // Validate the vehicle before adding
        if (validate(greenSpace)) {
            success = greenSpacesList.add(greenSpace);
        }

        return success;
    }

```

```java
    private boolean validate(GreenSpace greenSpace) {
        return validateInRepository(greenSpace.getName(), greenSpace.getAddress() );
    }

```

```java
    private boolean validateInRepository(String greenSpaceName, String address) {
        for (GreenSpace g : greenSpacesList) {
            if ((g.getName()).equalsIgnoreCase(greenSpaceName) || g.getAddress().equalsIgnoreCase(address)) {
                return false;
            }
        }
        return true;
    }

```

```java
    public List<GreenSpace>  getGreenSpacesByResponsible (String responsible) {
        ArrayList<GreenSpace> greenSpaces = new ArrayList<>();

        for (GreenSpace greenSpace : greenSpacesList) {
            if (greenSpace.getResponsible().equals(responsible)) {
                greenSpaces.add(greenSpace);
            }
        }
        return greenSpaces;
    }
```

## 6. Integration and Demo

* None


## 7. Observations

n/a