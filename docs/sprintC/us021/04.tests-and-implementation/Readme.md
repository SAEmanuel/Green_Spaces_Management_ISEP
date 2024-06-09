# US021 - Adding a To-Do List Entry for Green Space Management

## 4. Tests

**Test 1:**

    @Test
    void testConstructorValidParameters() {
        String title = "Test Title";
        String description = "Test Description";
        int urgency = 1;
        int expectedDuration = 100;

        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, title, description, urgency, expectedDuration);

        assertEquals(greenSpace, toDoEntry.getGreenSpace());
        assertEquals(title, toDoEntry.getTitle());
        assertEquals(description, toDoEntry.getDescription());
        assertEquals(ToDoEntry.Urgency.MEDIUM, toDoEntry.getUrgency());
        assertEquals(expectedDuration, toDoEntry.getExpectedDuration());
        assertEquals("Pending", toDoEntry.getStatus());
        assertEquals("gsm@this.app", toDoEntry.getResponsible());
    }


**Test 2:**

    @Test
    void testConstructorInvalidTitle() {
        String invalidTitle = "";

        Executable executable = () -> new ToDoEntry(greenSpace, invalidTitle, "Description", 1, 100);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Title and Description cannot be null or empty.", exception.getMessage());
    }


**Test 3:**

    @Test
    void testConstructorInvalidUrgency() {
        Executable executable = () -> new ToDoEntry(greenSpace, "Title", "Description", 4, 100);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Invalid urgency level!", exception.getMessage());
    }

**Test 4:**

    @Test
    void testConstructorNegativeExpectedDuration() {
        Executable executable = () -> new ToDoEntry(greenSpace, "Title", "Description", 1, -1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Expected duration cannot be negative!", exception.getMessage());
    }

**Test 5:**

    @Test
    void testConstructorExpectedDurationExceedsLimit() {
        Executable executable = () -> new ToDoEntry(greenSpace, "Title", "Description", 1, 1000);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Expected duration cannot be greater than 730 days", exception.getMessage());
    }

**Test 6:**

     @Test
    void testSetStatus() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        toDoEntry.setStatus("Completed");

        assertEquals("Completed", toDoEntry.getStatus());
    }

**Test 7:**

     @Test
    void testSetExpectedDuration() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        toDoEntry.setExpectedDuration(200);

        assertEquals(200, toDoEntry.getExpectedDuration());
    }

**Test 8:**

    @Test
    void testClone() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        ToDoEntry clonedToDoEntry = toDoEntry.clone();

        assertNotSame(toDoEntry, clonedToDoEntry);
        assertEquals(toDoEntry, clonedToDoEntry);
    }

**Test 9:**

    @Test
    void testEqualsSameObject() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);

        assertTrue(toDoEntry.equals(toDoEntry));
    }

**Test 10:**

    @Test
    void testEqualsDifferentObjectSameValues() {
        ToDoEntry toDoEntry1 = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);

        assertTrue(toDoEntry1.equals(toDoEntry2));
    }

**Test 11:**

    @Test
    void testEqualsDifferentObjectDifferentValues() {
        ToDoEntry toDoEntry1 = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 100);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Title2", "Description2", 2, 200);

        assertFalse(toDoEntry1.equals(toDoEntry2));
    }

**Test 12:**

    @Test
    void testToString() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        String expectedString = "[Title | Description | Medium | 100 | Parque]";

        assertEquals(expectedString, toDoEntry.toString());
    }

**Test 13:**

    @Test
    void testIsValidUrgency() {
        assertDoesNotThrow(() -> new ToDoEntry(greenSpace, "Title", "Description", 0, 100));
        assertDoesNotThrow(() -> new ToDoEntry(greenSpace, "Title", "Description", 1, 100));
        assertDoesNotThrow(() -> new ToDoEntry(greenSpace, "Title", "Description", 2, 100));
        assertThrows(IllegalArgumentException.class, () -> new ToDoEntry(greenSpace, "Title", "Description", 3, 100));
    }

**Test 14:**

    @Test
    void testValidateString() {
        assertTrue(Validations.validateString("Valid String"));
        assertFalse(Validations.validateString(""));
    }

**Test 15:**

    @Test
    void testGetToDoList() {
        List<ToDoEntry> toDoList = repository.getToDoList();
        assertNotNull(toDoList);
        assertTrue(toDoList.isEmpty());
    }

**Test 16:**

    @Test
    void testGetToDoListClone() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        repository.add(toDoEntry);
        List<ToDoEntry> clonedList = repository.getToDoListClone();
        assertNotSame(repository.getToDoList(), clonedList);
        assertEquals(1, clonedList.size());
        assertEquals(toDoEntry, clonedList.get(0));
    }

**Test 17:**

    @Test
    void testRegisterToDoEntry() {
        String title = "Title1";
        String description = "Description1";
        int urgency = 1;
        int expectedDuration = 60;

        Optional<ToDoEntry> optionalToDoEntry = repository.registerToDoEntry(greenSpace, title, description, urgency, expectedDuration);
        assertTrue(optionalToDoEntry.isPresent());
        ToDoEntry toDoEntry = optionalToDoEntry.get();
        assertEquals(title, toDoEntry.getTitle());
        assertEquals(description, toDoEntry.getDescription());
        assertEquals(urgency, toDoEntry.getUrgency().ordinal());
        assertEquals(expectedDuration, toDoEntry.getExpectedDuration());
    }

**Test 18:**

     @Test
    void testAdd() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        repository.add(toDoEntry);
        assertEquals(1, repository.getToDoList().size());
        assertTrue(repository.getToDoList().contains(toDoEntry));
    }

**Test 19:**

    @Test
    void testAddToDoEntry() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        assertTrue(repository.addToDoEntry(toDoEntry));
        assertEquals(1, repository.getToDoList().size());
        assertTrue(repository.getToDoList().contains(toDoEntry));
    }

**Test 20:**

    @Test
    void testAddToDoEntryFailsIfDuplicate() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        repository.addToDoEntry(toDoEntry);
        assertFalse(repository.addToDoEntry(toDoEntry)); // Adding the same entry should fail
        assertEquals(1, repository.getToDoList().size());
    }

**Test 21:**

    @Test
    void testValidate() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        assertTrue(repository.validate(toDoEntry));
        repository.add(toDoEntry);
        assertFalse(repository.validate(toDoEntry)); // Should return false for duplicate entry
    }

**Test 22:**

    @Test
    void testToDoListDoesNotContainToDoEntry() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        assertTrue(repository.toDoListDoesNotContainToDoEntry(toDoEntry));
        repository.add(toDoEntry);
        assertFalse(repository.toDoListDoesNotContainToDoEntry(toDoEntry));
    }

**Test 23:**

    @Test
    void testGetUrgency() {
        ToDoEntry.Urgency[] urgencies = repository.getUrgency();
        assertNotNull(urgencies);
        assertEquals(ToDoEntry.Urgency.values().length, urgencies.length);
    }

## 5. Construction (Implementation)

### Class ToDoListController

```java
    public ToDoListController() {
    this.greenSpaceRepository = getGreenSpaceRepository();
    this.toDoListRepository = getToDoListRepository();
}
```

```java
    private GreenSpaceRepository getGreenSpaceRepository() {
    if (greenSpaceRepository == null) {
        greenSpaceRepository = getRepositories().getGreenSpaceRepository();
    }
    return greenSpaceRepository;
}
```

```java
    private ToDoListRepository getToDoListRepository() {
    if (toDoListRepository == null) {
        toDoListRepository = getRepositories().getToDoRepository();
    }
    return toDoListRepository;
}
```
```java
    private String getResponsible() {
        return repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
}
```
```java
   public Optional<ToDoEntry> registerToDoEntry(int greenSpaceID, String title, String description, int urgency, int expectedDuration) {
    GreenSpace greenSpace = searchForGreenSpaceID(greenSpaceID);
    return toDoListRepository.registerToDoEntry(greenSpace, title, description, urgency, expectedDuration);
}
```

```java
   private GreenSpace searchForGreenSpaceID(int greenSpaceID) {
    return getGreenSpacesByResponsible().get(greenSpaceID);
}
```

```java
   public List<GreenSpace> getGreenSpacesByResponsible() {
    return greenSpaceRepository.getGreenSpacesByResponsible(getResponsible());
}
```

### Class ToDoListRepository

```java
     public ToDoListRepository() {
    toDoList = new ArrayList<>();
}
```

```java
   public Optional<ToDoEntry> registerToDoEntry(GreenSpace greenSpace, String title, String description, int urgency, int expectedDuration) {
    Optional<ToDoEntry> optionalToDoEntry = Optional.empty();

    ToDoEntry toDoEntry = new ToDoEntry(greenSpace, title, description, urgency, expectedDuration);

    if (addToDoEntry(toDoEntry)) {
        optionalToDoEntry = Optional.of(toDoEntry.clone());
    }

    return optionalToDoEntry;
    }

```

```java
    boolean addToDoEntry(ToDoEntry toDoEntry) {
    boolean success = false;

    if (validate(toDoEntry)) {
        success = toDoList.add(toDoEntry);
    }

    return success;
    }

```

```java
   boolean validate(ToDoEntry toDoEntry) {
    return toDoListDoesNotContainToDoEntry(toDoEntry);
    }

```

```java
    boolean toDoListDoesNotContainToDoEntry(ToDoEntry toDoEntry) {
    return !toDoList.contains(toDoEntry);
    }

```

```java
    public List<ToDoEntry> getToDoListForResponsible(String responsible) {
    List<ToDoEntry> entry = new ArrayList<>();

    for (ToDoEntry toDoEntry : toDoList) {
        if (toDoEntry.getResponsible().equals(responsible)) {
            entry.add(toDoEntry);
        }
    }
    return entry;
    }
```

## 6. Integration and Demo

* None


## 7. Observations

n/a