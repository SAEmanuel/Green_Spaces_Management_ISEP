# US028 and US029 - Consulting Tasks and Task Completion Recording


## 4. Tests

**Test 1:** Agenda Entry Registration Test

```java
@Test
public void registerAgendaEntry_ValidEntry_SuccessfullyRegistered() {
    // Arrange
    AgendaRepository agendaRepository = new AgendaRepository();
    GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
    ToDoEntry todoEntry = new ToDoEntry(greenSpace, "Test Task", "None", 0, 10);
    Data startDate = new Data(2024, 6, 4);
    
    // Act
    Optional<AgendaEntry> registeredEntry = agendaRepository.registerAgendaEntry(todoEntry, startDate);
    
    // Assert
    assertTrue(registeredEntry.isPresent());
}
```

**Test 2:** Task Cancellation Test
``` java
@Test
public void cancelTask_ValidTaskId_SuccessfullyCancelled() {
// Arrange
AgendaRepository agendaRepository = new AgendaRepository();
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
```

**Test 3:** Task Postponement Test
```java
@Test
public void postponeTask_ValidTaskIdAndDate_SuccessfullyPostponed() {
    // Arrange
    AgendaRepository agendaRepository = new AgendaRepository();
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
```

**Test 4:** Team Assignment Test
```java
@Test
public void assignTeam_ValidTeam_AssignedSuccessfully() {
    // Arrange
    AgendaRepository agendaRepository = new AgendaRepository();
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
```
**Test 5:** Vehicle Assignment Test
```java
@Test
public void assignVehicle_ValidVehicle_AssignedSuccessfully() {
// Arrange
AgendaRepository agendaRepository = new AgendaRepository();
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
```
**Test 6:** Task Planned List Retrieval Test

```java
@Test
    public void testGetTaskPlannedList() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        Team team2 = new Team(2);
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator);
        toDoEntry.setStatus("Planned");
        toDoEntry2.setStatus("Canceled");
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        AgendaEntry agendaEntry2 = new AgendaEntry(toDoEntry, new Data(2023, 7, 1));
        agendaEntry.addTeam(team);
        agendaEntry2.addTeam(team2);
        repositories.getAgenda().addAgendaEntry(agendaEntry);
        repositories.getAgenda().addAgendaEntry(agendaEntry2);

        List<AgendaEntry> result = repositories.getAgenda().getTaskPlannedList(collaborator, new Data(2023, 6, 1), new Data(2023, 6, 30), 2);
        assertEquals(1, result.size());
        assertEquals(new Data(2023, 6, 5), result.get(0).getStartingDate());
    }
```
**Test 7:** Possible Done Task Planned List Retrieval Test
```java
 @Test
    public void testGetPossibleDoneTaskPlannedList() {
        Repositories repositories = Repositories.getInstance();
        repositories.getAgenda().createNewAgendaList();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        Team team2 = new Team(2);
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator);
        toDoEntry.setStatus("Planned");
        toDoEntry2.setStatus("Canceled");
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        AgendaEntry agendaEntry2 = new AgendaEntry(toDoEntry, new Data(2023, 7, 1));
        agendaEntry.addTeam(team);
        agendaEntry2.addTeam(team2);
        repositories.getAgenda().addAgendaEntry(agendaEntry);
        repositories.getAgenda().addAgendaEntry(agendaEntry2);

        List<AgendaEntry> result = repositories.getAgenda().getTaskPlannedList(collaborator, new Data(2023, 6, 1), new Data(2023, 6, 30), 2);
        assertEquals(1, result.size());
        assertEquals(new Data(2023, 6, 5), result.get(0).getStartingDate());
    }
```
**Test 8:** Task List Filtering Test
```java
 @Test
    public void testGetTaskListFilter0() {
        Repositories repositories = Repositories.getInstance();
        repositories.getAgenda().createNewAgendaList();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        Team team2 = new Team(2);
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator);
        toDoEntry.setStatus("Planned");
        toDoEntry2.setStatus("Canceled");
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        AgendaEntry agendaEntry2 = new AgendaEntry(toDoEntry, new Data(2023, 9, 1));
        agendaEntry.addTeam(team);
        agendaEntry2.addTeam(team2);
        repositories.getAgenda().addAgendaEntry(agendaEntry);
        repositories.getAgenda().addAgendaEntry(agendaEntry2);

        List<AgendaEntry> result = repositories.getAgenda().getTaskList(collaborator, new Data(2023,5,1), new Data(2023,8,30), 1);
        assertEquals(1, result.size());
        assertTrue(result.contains(agendaEntry));
        assertFalse(result.contains(agendaEntry2));
    }
```
**Test 9:** Task List Filtering Test
```java @Test
    public void testGetTaskListFilter2() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        Team team2 = new Team(2);
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        AgendaEntry agendaEntry2 = new AgendaEntry(toDoEntry2, new Data(2023, 6, 1));
        agendaEntry2.getAgendaEntry().setStatus(String.valueOf(AgendaEntry.Status.CANCELED));
        agendaEntry.addTeam(team);
        agendaEntry2.addTeam(team2);
        repositories.getAgenda().addAgendaEntry(agendaEntry);
        repositories.getAgenda().addAgendaEntry(agendaEntry2);

        List<AgendaEntry> result = repositories.getAgenda().getTaskList(collaborator, new Data(2023,5,1), new Data(2023,8,30), 3);
        assertEquals(1, result.size());
        assertFalse(result.contains(agendaEntry));
        assertTrue(result.contains(agendaEntry2));
    }
```

**Test 10:**  Valid Agenda Entry Addition Test
```java 
@Test
    public void testAddValidAgendaEntry() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        Team team2 = new Team(2);
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        AgendaEntry agendaEntry2 = new AgendaEntry(toDoEntry2, new Data(2023, 6, 1));
        agendaEntry.addTeam(team);
        agendaEntry2.addTeam(team2);
        boolean result = repositories.getAgenda().addAgendaEntry(agendaEntry);
        assertTrue(result);
    }
```

**Test 11:** Invalid Agenda Entry Addition Test
```java
@Test
    public void testAddInvalidAgendaEntry() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        Team team2 = new Team(2);
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        AgendaEntry agendaEntry2 = new AgendaEntry(toDoEntry2, new Data(2023, 6, 1));
        agendaEntry.addTeam(team);
        agendaEntry2.addTeam(team2);
        repositories.getAgenda().addAgendaEntry(agendaEntry);
        boolean result = repositories.getAgenda().addAgendaEntry(agendaEntry);
        assertFalse(result);
    }
```
**Test 12:** Valid Agenda Backup Addition Test

```java
@Test
    public void testAddValidAgendaBackup() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        Team team2 = new Team(2);
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        AgendaEntry agendaEntry2 = new AgendaEntry(toDoEntry2, new Data(2023, 6, 1));
        agendaEntry.addTeam(team);
        agendaEntry2.addTeam(team2);
        boolean result = repositories.getAgenda().addAgendaBackup(agendaEntry);
        assertTrue(result);
    }
```

**Test 13:** Invalid Agenda Backup Addition Test
```java
 @Test
    public void testAddInvalidAgendaBackup() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        Team team2 = new Team(2);
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        AgendaEntry agendaEntry2 = new AgendaEntry(toDoEntry2, new Data(2023, 6, 1));
        agendaEntry.addTeam(team);
        agendaEntry2.addTeam(team2);
        repositories.getAgenda().addAgendaBackup(agendaEntry);
        boolean result = repositories.getAgenda().addAgendaBackup(agendaEntry);
        assertFalse(result);
    }
```
**Test 14:** Valid Entry Validation Test

```java
@Test
    public void testValidEntry() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        team.addCollaborator(collaborator);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        agendaEntry.addTeam(team);

        boolean result = repositories.getAgenda().validateEntry(agendaEntry);
        assertTrue(result);
    }
```

**Test 15:** Invalid Entry Validation Test
```java
  @Test
    public void testInvalidEntry() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        team.addCollaborator(collaborator);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        agendaEntry.addTeam(team);
        repositories.getAgenda().addAgendaEntry(agendaEntry);
        boolean result = repositories.getAgenda().validateEntry(agendaEntry);
        assertFalse(result);
    }
```
**Test 16:** Valid Entry Validation Test (Backup)

```java
@Test
    public void testValidBackUp() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        team.addCollaborator(collaborator);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        agendaEntry.addTeam(team);

        boolean result = repositories.getAgenda().validateBackUp(agendaEntry);
        assertTrue(result);
    }
```

**Test 17:** Invalid Entry Validation Test (Backup)
```java
@Test
    public void testInvalidBackUp() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        Team team = new Team(1);
        team.addCollaborator(collaborator);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        agendaEntry.addTeam(team);
        repositories.getAgenda().addAgendaBackup(agendaEntry);
        boolean result = repositories.getAgenda().validateBackUp(agendaEntry);
        assertFalse(result);
    }
```

## 5. Construction (Implementation)

### Class AgendaController
```java
/**
     * Gets the status options available.
     *
     * @return An array of status options.
     */
    public AgendaEntry.Status[] getStatus() {
        return agendaRepository.getStatus();
    }
    /**
     * Requests the list of tasks assigned to a collaborator.
     *
     * @param collaborator    The collaborator whose tasks are to be retrieved.
     * @param startDate       The start date of the task list.
     * @param endDate         The end date of the task list.
     * @param filterSelection The filter selection.
     * @return The list of tasks assigned to the collaborator.
     */
    public Optional<List<AgendaEntry>> requestColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        return agendaRepository.requestColabTaskList(collaborator, startDate, endDate, filterSelection);
    }

    /**
     * Requests the list of planned tasks assigned to a collaborator.
     *
     * @param collaborator    The collaborator whose tasks are to be retrieved.
     * @param startDate       The start date of the task list.
     * @param endDate         The end date of the task list.
     * @param filterSelection The filter selection.
     * @return The list of planned tasks assigned to the collaborator.
     */
    public Optional<List<AgendaEntry>> requestPlannedColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        return agendaRepository.requestColabPlannedTaskList(collaborator, startDate, endDate, filterSelection);
    }

    /**
     * Requests the list of tasks with changed status assigned to a collaborator.
     *
     * @param collaborator    The collaborator whose tasks are to be retrieved.
     * @param startDate       The start date of the task list.
     * @param endDate         The end date of the task list.
     * @param filterSelection The filter selection.
     * @param confirmation    The confirmation status.
     * @param selectedTask    The selected task ID.
     * @return The list of tasks with changed status assigned to the collaborator.
     */
    public Optional<List<AgendaEntry>> requestChangedStatusTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection, String confirmation, int selectedTask) {
        return agendaRepository.changedTaskStatusList(collaborator, startDate, endDate, filterSelection, confirmation, selectedTask);
    }

    /**
     * Requests the list of possible planned tasks assigned to a collaborator.
     *
     * @param collaborator    The collaborator whose tasks are to be retrieved.
     * @param startDate       The start date of the task list.
     * @param endDate         The end date of the task list.
     * @param filterSelection The filter selection.
     * @return The list of possible planned tasks assigned to the collaborator.
     */
    public Optional<List<AgendaEntry>> requestPossiblePlannedColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        return agendaRepository.requestPossibleColabPlannedTaskList(collaborator, startDate, endDate, filterSelection);
    }
```

### Class AgendaRepository
```java
 /**
     * Requests the task list for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return An optional list of agenda entries.
     */
    public Optional<List<AgendaEntry>> requestColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> optionalValue = Optional.empty();

        List<AgendaEntry> taskList = getTaskList(collaborator, startDate, endDate, filterSelection);

        if (taskListCreated(collaborator, startDate, endDate, filterSelection)) {
            optionalValue = Optional.of(taskList);
        }
        return optionalValue;
    }

    /**
     * Requests the planned task list for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return An optional list of planned agenda entries.
     */
    public Optional<List<AgendaEntry>> requestColabPlannedTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> optionalValue = Optional.empty();

        List<AgendaEntry> taskList = getTaskPlannedList(collaborator, startDate, endDate, filterSelection);

        if (taskPlannedListCreated(collaborator, startDate, endDate, filterSelection)) {
            optionalValue = Optional.of(taskList);
        }
        return optionalValue;
    }

    /**
     * Requests the possible planned task list for a collaborator within a specified date range and filter selection.
     * This method retrieves a list of planned tasks that have the potential to be marked as done, considering the provided
     * collaborator, date range, and filter selection.
     *
     * @param collaborator    The collaborator whose tasks are being requested.
     * @param startDate       The start date of the range within which tasks are being requested.
     * @param endDate         The end date of the range within which tasks are being requested.
     * @param filterSelection The filter selection used to determine which tasks to include in the list.
     * @return An optional list of possible planned agenda entries for the specified collaborator within the given date range and filter selection.
     * If the list is empty, the optional will be empty as well.
     */
    public Optional<List<AgendaEntry>> requestPossibleColabPlannedTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> optionalValue = Optional.empty();

        List<AgendaEntry> taskList = getPossibleDoneTaskPlannedList(collaborator, startDate, endDate, filterSelection);

        if (taskPlannedListCreated(collaborator, startDate, endDate, filterSelection)) {
            optionalValue = Optional.of(taskList);
        }
        return optionalValue;
    }

    /**
     * Checks if a planned task list was created for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return true if the task list was created, false otherwise.
     */
    private boolean taskPlannedListCreated(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> taskList = getTaskPlannedList(collaborator, startDate, endDate, filterSelection);
        return !taskList.isEmpty();
    }

    /**
     * Gets the planned task list for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return A list of planned agenda entries.
     */
    public List<AgendaEntry> getTaskPlannedList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> plannedList = new ArrayList<>();

        if ((filterSelection - 1) == 1) {
            for (AgendaEntry agendaEntry : agenda) {
                try {
                    if (agendaEntry.getTeam().hasCollaborator(collaborator)
                            && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                            && !agendaEntry.getStartingDate().isGreater(endDate)
                            && agendaEntry.getAgendaEntry().getStatus().equals("Planned")) {
                        plannedList.add(agendaEntry);
                    }
                } catch (NullPointerException e) {
                    System.out.print("");
                }

            }
        }
        return sortByStartDate(plannedList);
    }

    /**
     * Retrieves a list of planned agenda entries for a collaborator within the specified date range and filter selection
     * that have the potential to be marked as done. This method considers only those tasks that are currently planned and
     * have not started yet, and are within the provided date range.
     *
     * @param collaborator    The collaborator for whom the tasks are being retrieved.
     * @param startDate       The start date of the range within which tasks are being retrieved.
     * @param endDate         The end date of the range within which tasks are being retrieved.
     * @param filterSelection The filter selection used to determine which tasks to include in the list.
     * @return A list of planned agenda entries for the specified collaborator within the given date range and filter selection,
     * which have the potential to be marked as done. The list is sorted by start date.
     */
    public List<AgendaEntry> getPossibleDoneTaskPlannedList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> plannedList = new ArrayList<>();

        if ((filterSelection - 1) == 1) {
            for (AgendaEntry agendaEntry : agenda) {
                try {
                    if (agendaEntry.getTeam().hasCollaborator(collaborator)
                            && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                            && !agendaEntry.getStartingDate().isGreater(endDate)
                            && agendaEntry.getAgendaEntry().getStatus().equals("Planned")
                            && !agendaEntry.getStartingDate().isGreater(Data.currentDate())) {
                        plannedList.add(agendaEntry);
                    }
                } catch (NullPointerException e) {
                    System.out.print("");
                }
            }
        }
        return sortByStartDate(plannedList);
    }

    /**
     * Requests the list of tasks with changed status for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @param confirmation    The confirmation status.
     * @param selectedTask    The selected task ID.
     * @return An optional list of tasks with changed status.
     */
    public Optional<List<AgendaEntry>> changedTaskStatusList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection, String confirmation, int selectedTask) {
        List<AgendaEntry> taskList = getPossibleDoneTaskPlannedList(collaborator, startDate, endDate, filterSelection);

        if (confirmation.equalsIgnoreCase("y") && !taskList.isEmpty() && selectedTask >= 0 && selectedTask < taskList.size()) {

            int i = 0;
            taskList.get(selectedTask).setReal_end_Date(Data.currentDate());
            taskList.get(selectedTask).getAgendaEntry().setStatus(String.valueOf(AgendaEntry.Status.DONE));


            if (!taskList.get(selectedTask).getVehicles().isEmpty() || taskList.get(selectedTask).getTeam() != null) {
                for (AgendaEntry agendaEntry : agendaBackUp) {

                    if (agendaEntry.equals(taskList.get(selectedTask))) {
                        agendaEntry.setReal_end_Date(Data.currentDate());
                        List<Vehicle> removedVehicles = new ArrayList<>(taskList.get(selectedTask).getVehicles());
                        taskList.get(selectedTask).getVehicles().clear();
                        agendaBackUp.get(i).addVehicles(removedVehicles);
                        Team removedTeam = taskList.get(selectedTask).getTeam();
                        taskList.get(selectedTask).setTeam(null);
                        agendaBackUp.get(i).addTeam(removedTeam);
                    }
                    i++;
                }
            }

            return Optional.of(taskList);
        }

        return Optional.empty();
    }

    /**
     * Gets the task list for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return A list of agenda entries.
     */
    public List<AgendaEntry> getTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> taskList = new ArrayList<>();

        switch (filterSelection - 1) {
            case 0:
                for (AgendaEntry agendaEntry : agenda) {
                    try {
                        if (agendaEntry.getTeam().hasCollaborator(collaborator)
                                && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                                && !agendaEntry.getStartingDate().isGreater(endDate)) {
                            taskList.add(agendaEntry);
                        }
                    } catch (NullPointerException e) {
                        System.out.print("");
                    }
                }
                for (AgendaEntry agendaEntry : agendaBackUp) {
                    try {
                        if (agendaEntry.getTeam().hasCollaborator(collaborator)
                                && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                                && !agendaEntry.getStartingDate().isGreater(endDate) && !taskList.contains(agendaEntry)) {
                            taskList.add(agendaEntry);
                        }
                    } catch (NullPointerException e) {
                        System.out.print("");
                    }
                }
                break;
            case 2:
                for (AgendaEntry agendaEntry : agenda) {
                    try {
                        if (agendaEntry.getTeam().hasCollaborator(collaborator)
                                && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                                && !agendaEntry.getStartingDate().isGreater(endDate)
                                && agendaEntry.getAgendaEntry().getStatus().equals(String.valueOf(AgendaEntry.Status.CANCELED))) {
                            taskList.add(agendaEntry);
                        }
                    } catch (NullPointerException e) {
                        System.out.print("");
                    }
                }
                break;
            case 3:
                for (AgendaEntry agendaEntry : agendaBackUp) {
                    try {
                        if (agendaEntry.getTeam().hasCollaborator(collaborator)
                                && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                                && !agendaEntry.getStartingDate().isGreater(endDate)
                                && agendaEntry.getAgendaEntry().getStatus().equals(String.valueOf(AgendaEntry.Status.DONE))) {
                            taskList.add(agendaEntry);
                        }
                    } catch (NullPointerException e) {
                        System.out.print("");
                    }
                }
                break;
        }

        return sortByStartDate(taskList);
    }

    /**
     * Checks if a task list was created for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return true if the task list was created, false otherwise.
     */
    public boolean taskListCreated(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> taskList = getTaskList(collaborator, startDate, endDate, filterSelection);
        return !taskList.isEmpty();
    }

    /**
     * Sorts a list of agenda entries by start date.
     *
     * @param taskList The list of agenda entries.
     * @return The sorted list of agenda entries.
     */
    public List<AgendaEntry> sortByStartDate(List<AgendaEntry> taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            for (int j = i + 1; j < taskList.size(); j++) {
                if (taskList.get(i).getStartingDate().isGreater(taskList.get(j).getStartingDate())) {
                    AgendaEntry temp = taskList.get(i);
                    taskList.set(i, taskList.get(j));
                    taskList.set(j, temp);
                }
            }
        }
        return taskList;
    }

    /**
     * Gets the possible status values for agenda entries.
     *
     * @return An array of status values.
     */
    public AgendaEntry.Status[] getStatus() {
        return AgendaEntry.Status.values();
    }
```