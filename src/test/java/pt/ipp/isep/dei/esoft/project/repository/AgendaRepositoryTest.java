package pt.ipp.isep.dei.esoft.project.repository;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaRepositoryTest {

    private AgendaRepository agendaRepository;

    @BeforeEach
    public void setUp() {
        agendaRepository = new AgendaRepository();
    }

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

    @Test
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

    /*
    @Test
    public void testGetAgendaEntriesTeam() {
        Repositories repositories = Repositories.getInstance();
        Job job1 = new Job("Jardineiro");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 7, 6), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
        Collaborator collaborator2 = new Collaborator("Romeu", new Data(2000, 7, 26), new Data(2020, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xic@gmail.com");
        Collaborator collaborator3 = new Collaborator("Xu", new Data(2000, 7, 26), new Data(2020, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xuc@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Work", "description", 1, 8);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Working", "description", 1, 8);
        Team team = new Team(1);
        Team team2 = new Team(2);
        Team team3 = new Team(3);
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator2);
        team3.addCollaborator(collaborator3);
        AgendaEntry agendaEntry = new AgendaEntry(toDoEntry, new Data(2023, 6, 5));
        AgendaEntry agendaEntry2 = new AgendaEntry(toDoEntry2, new Data(2023, 6, 1));
        agendaEntry.addTeam(team);
        agendaEntry2.addTeam(team2);
        agendaEntry.addTeam(team3);
        List<AgendaEntry> list = new ArrayList<>();
        list.add(agendaEntry);
        list.add(agendaEntry2);

        List<AgendaEntry> result = repositories.getAgenda().getAgendaEntriesTeam("test@greenspace.com", list);
        assertEquals(1, result.size());
    }

     */
}
