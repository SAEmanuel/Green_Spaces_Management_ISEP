# US025 - Cancel entry in Agenda

## 4. Tests

**Test 1: Agenda Entry Creation**

    @Test
    public void testAgendaEntryCreation() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);

        assertEquals("Planned", agendaEntry.getAgendaEntry().getStatus());
        assertEquals(data, agendaEntry.getStartingDate());
        assertEquals(data.calculateData(task1.getExpectedDuration()), agendaEntry.getExpected_end_Date());
        assertNull(agendaEntry.getReal_end_Date());
        assertNull(agendaEntry.getTeam());
        assertTrue(agendaEntry.getVehicles().isEmpty());
        assertEquals("gsm@this.app", agendaEntry.getResponsible());
    }
**Test 2: Set Expected End Date**

    @Test
    public void testSetExpectedEndDate() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);
        Data newEndDate = new Data(2023, 1, 5);

        agendaEntry.setExpected_end_Date(newEndDate);

        assertEquals(newEndDate, agendaEntry.getExpected_end_Date());
    }

**Test 3: Set Starting Date**

    @Test
    public void testSetStartingDate() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);
        Data newStartDate = new Data(2025, 1, 5);

        agendaEntry.setStarting_Date(newStartDate);

        System.out.println(agendaEntry.getExpected_end_Date());

        assertEquals(newStartDate, agendaEntry.getStartingDate());
        assertEquals(new Data(2025,01,7), agendaEntry.getExpected_end_Date());
    }

**Test 4: Set Real End Date**

    @Test
    public void testSetRealEndDate() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);
        Data realEndDate = new Data(2023, 1, 15);

        agendaEntry.setReal_end_Date(realEndDate);

        assertEquals(realEndDate, agendaEntry.getReal_end_Date());
    }
**Test 5: Cancel Task**

    @Test
    public void testCancelTask() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);

        agendaEntry.cancelTask();

        assertEquals("Canceled", agendaEntry.getAgendaEntry().getStatus());
    }

**Test 6: Add Vehicle**

    @Test
    public void testAddVehicle() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);

        assertTrue(agendaEntry.addVehicle(vehicle1));
        assertEquals(1, agendaEntry.getVehicles().size());
        assertTrue(agendaEntry.getVehicles().contains(vehicle1));

        // Adding the same vehicle again should not change the size of the vehicle list
        assertFalse(agendaEntry.addVehicle(vehicle1));
        assertEquals(1, agendaEntry.getVehicles().size());
    }

**Test 7: Register Agenda Entry Valid Entry**

    @Test
    public void registerAgendaEntry_ValidEntry_SuccessfullyRegistered() {
        // Arrange
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry todoEntry = new ToDoEntry(greenSpace, "Test Task", "None", 0, 10);
        Data startDate = new Data(2024, 6, 4);

        // Act
        Optional<AgendaEntry> registeredEntry = agendaRepository.registerAgendaEntry(todoEntry, startDate);

        // Assert
        assertTrue(registeredEntry.isPresent());
    }
**Test 8: Cancel Task Valid TaskId**

    @Test
    public void cancelTask_ValidTaskId_SuccessfullyCancelled() {
        // Arrange
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry todoEntry = new ToDoEntry(greenSpace, "Test Task", "None", 0, 10);
        Data startDate = new Data(2024, 6, 4);
        Optional<AgendaEntry> registeredEntry = agendaRepository.registerAgendaEntry(todoEntry, startDate);
        AgendaEntry entry = registeredEntry.orElse(null);
        assertNotNull(entry);

        // Act
        boolean cancelled = agendaRepository.cancelTask(0, entry.getResponsible());

        // Assert
        assertTrue(cancelled);
    }

**Test 9: Postpone Task Valid Task Id And Date**

    @Test
    public void postponeTask_ValidTaskIdAndDate_SuccessfullyPostponed() {
        // Arrange
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry todoEntry = new ToDoEntry(greenSpace, "Test Task", "None", 0, 10);
        Data startDate = new Data(2024, 6, 4);
        Optional<AgendaEntry> registeredEntry = agendaRepository.registerAgendaEntry(todoEntry, startDate);
        AgendaEntry entry = registeredEntry.orElse(null);
        assertNotNull(entry);
        Data postponeDate = new Data(2024, 6, 5);

        // Act
        boolean postponed = agendaRepository.postponeTask(0, postponeDate, entry);

        // Assert
        assertTrue(postponed);
    }
**Test 10: Assign Team Valid Team**

    @Test
    public void assignTeam_ValidTeam_AssignedSuccessfully() {
        // Arrange
        Team team = new Team(1);
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry todoEntry = new ToDoEntry(greenSpace, "Test Task", "None", 0, 10);
        Data startDate = new Data(2024, 6, 4);
        Optional<AgendaEntry> registeredEntry = agendaRepository.registerAgendaEntry(todoEntry, startDate);
        AgendaEntry entry = registeredEntry.orElse(null);
        assertNotNull(entry);

        // Act
        boolean assigned = agendaRepository.assignTeam(team, 0, entry.getResponsible());

        // Assert
        assertTrue(assigned);
    }

**Test 11: Assign Vehicle Valid Vehicle**

    @Test
    public void assignVehicle_ValidVehicle_AssignedSuccessfully() {
        // Arrange
        Vehicle vehicle = new Vehicle("AA-00-AA", "Toyota", "Camnry", 0, 1000, 2000, 10000, 2000, 9005, new Data(2021, 1, 1), new Data(2023, 1, 1));
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry todoEntry = new ToDoEntry(greenSpace, "Test Task", "None", 0, 10);
        Data startDate = new Data(2024, 6, 4);
        Optional<AgendaEntry> registeredEntry = agendaRepository.registerAgendaEntry(todoEntry, startDate);
        AgendaEntry entry = registeredEntry.orElse(null);
        assertNotNull(entry);

        // Act
        boolean assigned = agendaRepository.assignVehicle(entry, vehicle);

        // Assert
        assertTrue(assigned);
    }


## 5. Construction (Implementation)

### Class AgendaController

```java
public AgendaController() {
    this.agendaRepository = getAgenda();
}
```

```java
    private AgendaRepository getAgenda() {
    if (agendaRepository == null) {
        Repositories repositories = Repositories.getInstance();
        agendaRepository = repositories.getAgenda();
    }
    return agendaRepository;
}
```

```java
    public boolean cancelTask(int agendaTaskID) {
    return agendaRepository.cancelTask(agendaTaskID, getResponsible());
}
```




### Class AgendaRepository

```java
    public AgendaRepository() {
        this.agenda = new ArrayList<>();
    }
```

```java
    public boolean cancelTask(int agendaTaskID, String responsible) {
    List<AgendaEntry> agendaTasks = getAgendaEntriesForResponsible(responsible);
    AgendaEntry taskInAgenda = getTaskByResponsible(agendaTaskID, agendaTasks);

    if (validatesStatus(taskInAgenda)) {
        return false;
    }
    taskInAgenda.cancelTask();
    return true;
}
```

```java
    public List<AgendaEntry> getAgendaEntriesForResponsible(String responsible) {
    List<AgendaEntry> agendaEntries = new ArrayList<>();
    for (AgendaEntry agendaEntry : agenda) {
        if (agendaEntry.getTeam() != null) {

            if (agendaEntry.getResponsible().equals(responsible) && !agendaEntry.containsTeam(agendaEntry.getTeam())) {
                agendaEntries.add(agendaEntry);
            }
        } else if (agendaEntry.getTeam() == null && agendaEntry.getResponsible().equals(responsible)) {
            agendaEntries.add(agendaEntry);
        }
    }
    return agendaEntries;
}
```

```java
    public AgendaEntry getTaskByResponsible(int agendaTaskID, List<AgendaEntry> agendaTasks) {
    return agendaTasks.get(agendaTaskID);
}
```

```java
    public boolean validatesStatus(AgendaEntry taskInAgenda) {
    if (taskInAgenda.getAgendaEntry().getStatus().equals(STATUS_DONE)) {
        throw new IllegalArgumentException("Done agenda entry - can't cancel this task.");
    }
    return taskInAgenda.getAgendaEntry().getStatus().equals(STATUS_CANCELLED);
}
```

## 6. Integration and Demo

* None


## 7. Observations

n/a