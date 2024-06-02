package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaRepositoryTest {

    private AgendaRepository repository;
    private List<AgendaEntry> agenda;

    @BeforeEach
    public void setUp() {
        repository = new AgendaRepository();
        agenda = new ArrayList<>();
        List<Vehicle> vehicleList = new ArrayList<>();
        Job job = new Job("Marketing Coordinator");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 5, 20), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job, new Password("AAA12ab"), "xico@gmail.com");
        GreenSpace greenSpace = new GreenSpace("Infante Dom Henrique", 0, 100, "Porto", "gsm@this.app");
        ToDoEntry agendaEntry = new ToDoEntry(greenSpace, "Cortar relva", "description", 0, 8);
        Team team = new Team(1);
        Data data1 = new Data(2023, 1, 1);
        Data data2 = new Data(2021, 1, 1);
        Vehicle vehicle = new Vehicle("AA-00-AA", "Toyota", "Camnry", 0, 1000, 2000, 10000, 2000, 9005, data2, data1);
        vehicleList.add(vehicle);
        Data starting_Date = new Data(2025, 2, 2);
        Data expected_end_Date = new Data(2025, 2, 10);
        Data real_end_Date = new Data(2025, 2, 11);
        AgendaEntry agenda = new AgendaEntry(agendaEntry, starting_Date);
    }

    @Test
    public void testRequestColabTaskList() {
        List<AgendaEntry> taskList = new ArrayList<>();
        Job job = new Job("Marketing Coordinator");
        Data starting_Date = new Data(2025, 2, 2);
        Data expected_end_Date = new Data(2025, 2, 10);
        GreenSpace greenSpace = new GreenSpace("Infante Dom Henrique", 0, 100, "Porto", "gsm@this.app");
        ToDoEntry agendaEntry = new ToDoEntry(greenSpace, "Cortar relva", "description", 0, 8);

        Optional<List<AgendaEntry>> registered = repository.requestColabTaskList(new Collaborator("Francisco", new Data(2004, 5, 20), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job, new Password("AAA12ab"), "xico@gmail.com"), starting_Date, expected_end_Date,1);
        assertTrue(registered.isPresent());
        assertEquals(agendaEntry, registered.get().get(0).getAgendaEntry());
    }

    @Test
    public void testRequestColabPlannedTaskList() {
        Job job = new Job("Marketing Coordinator");
        Data starting_Date = new Data(2025, 2, 2);
        Data expected_end_Date = new Data(2025, 2, 10);
        GreenSpace greenSpace = new GreenSpace("Infante Dom Henrique", 0, 100, "Porto", "gsm@this.app");
        ToDoEntry agendaEntry = new ToDoEntry(greenSpace, "Cortar relva", "description", 0, 8);

        Optional<List<AgendaEntry>> registered = repository.requestColabPlannedTaskList(new Collaborator("Francisco", new Data(2004, 5, 20), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job, new Password("AAA12ab"), "xico@gmail.com"), starting_Date, expected_end_Date,1);
        System.out.println("resgistered: " + registered.isPresent());
        assertTrue(registered.isPresent());
        assertEquals(agendaEntry, registered.get().get(0).getAgendaEntry());
    }

    @Test
    public void testTaskPlannedListCreated() {
        List<AgendaEntry> taskList = new ArrayList<>();
        GreenSpace greenSpace = new GreenSpace("Infante Dom Henrique", 0, 100, "Porto", "gsm@this.app");
        ToDoEntry agendaEntry = new ToDoEntry(greenSpace, "Cortar relva", "description", 0, 8);
        AgendaEntry agenda = new AgendaEntry(agendaEntry, new Data(2025, 2, 2));
        Team team = new Team(1);
        Job job = new Job("Marketing Coordinator");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 5, 20), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job, new Password("AAA12ab"), "xico@gmail.com");
        team.addCollaborator(collaborator);
        agenda.setTeam(team);
        taskList.add(agenda);
        boolean result = taskList.contains(agenda);
        assertTrue(result);
    }

    @Test
    public void testGetTaskPlannedList() {
        List<AgendaEntry> plannedList = new ArrayList<>();
        Team team = new Team(1);
        Team team2 = new Team(2);
        Job job = new Job("Marketing Coordinator");
        Collaborator collaborator = new Collaborator("Francisco", new Data(2004, 5, 20), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job, new Password("AAA12ab"), "xico@gmail.com");
        Collaborator collaborator2 = new Collaborator("Emanuel", new Data(2004, 4, 20), new Data(2023, 6, 12), "Rua da Mariana", 912809777, "ema@gmail.com", 123456755, 0, "123456722", job, new Password("AAA12ac"), "ema@gmail.com");
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator2);
        GreenSpace greenSpace = new GreenSpace("Infante Dom Henrique", 0, 100, "Porto", "gsm@this.app");
        ToDoEntry agendaEntry = new ToDoEntry(greenSpace, "Cortar relva", "description", 0, 8);
        AgendaEntry agenda = new AgendaEntry(agendaEntry, new Data(2025, 2, 2));
        agenda.setTeam(team);
        GreenSpace greenSpace2 = new GreenSpace("Palacio de Cristal", 1, 40, "Porto", "gsm@this.app");
        ToDoEntry agendaEntry2 = new ToDoEntry(greenSpace2, "Cortar relva", "description", 0, 8);
        AgendaEntry agenda2 = new AgendaEntry(agendaEntry2, new Data(2025, 3, 2));
        agenda2.setTeam(team2);
        Data startDate = new Data(2022, 1, 1);
        Data endDate = new Data(2025,3,1);
        if (agenda.getTeam().hasCollaborator(collaborator)
                && agenda.getStartingDate().isGreaterOrEquals(startDate)
                && !agenda.getStartingDate().isGreater(endDate)
                && agenda.getAgendaEntry().getStatus().equals("Planned")) {
            plannedList.add(agenda);
        }
        if (agenda2.getTeam().hasCollaborator(collaborator)
                && agenda2.getStartingDate().isGreaterOrEquals(startDate)
                && !agenda2.getStartingDate().isGreater(endDate)
                && agenda2.getAgendaEntry().getStatus().equals("Planned")) {
            plannedList.add(agenda2);
        }
        assertTrue(plannedList.contains(agenda));
        assertFalse(plannedList.contains(agenda2));
    }
}
