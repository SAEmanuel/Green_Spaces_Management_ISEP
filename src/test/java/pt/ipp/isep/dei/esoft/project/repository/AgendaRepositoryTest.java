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

    private AgendaRepository repository = new AgendaRepository();
    private List<AgendaEntry> agenda = new ArrayList<>();
    private List<Vehicle> vehicleList = new ArrayList<>();
    private Vehicle vehicle;
    private Job job;
    private Collaborator collaborator;
    private GreenSpace greenSpace;
    private Team team;
    private Data starting_Date;
    private Data expected_end_Date;
    private Data real_end_Date;
    private ToDoEntry toDoEntry;
    private AgendaEntry agendaEntry;
    private Optional<List<AgendaEntry>> agendaEntryList;
    private Data startDate;
    private Data endDate;
    private List<AgendaEntry> plannedList;
    private Team team2;
    private Collaborator collaborator2;
    private GreenSpace greenSpace2;
    private ToDoEntry toDoEntry2;
    private AgendaEntry agendaEntry2;

    /*
    requestColabTaskList
    requestColabPlannedTaskList
    taskPlannedListCreated
    changedTaskStatusList
    getTaskList
    sortByStartDate (doable)
    getAgendaEntriesForResponsible
     */

    @BeforeEach
    public void setUp() {
        Data data1 = new Data(2023, 1, 1);
        Data data2 = new Data(2021, 1, 1);
        vehicle = new Vehicle("AA-00-AA", "Toyota", "Camnry", 0, 1000, 2000, 10000, 2000, 9005, data2, data1);
        vehicleList.add(vehicle);
        job = new Job("Marketing Coordinator");
        collaborator = new Collaborator("Francisco", new Data(2004, 5, 20), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job, new Password("AAA12ab"), "xico@gmail.com");
        greenSpace = new GreenSpace("Infante Dom Henrique", 0, 100, "Porto", "gsm@this.app");
        team = new Team(1);
        team.addCollaborator(collaborator);
        starting_Date = new Data(2025, 2, 2);
        expected_end_Date = new Data(2025, 2, 10);
        real_end_Date = new Data(2025, 2, 11);
        toDoEntry = new ToDoEntry(greenSpace, "Cortar relva", "description", 0, 8);
        agendaEntry = new AgendaEntry(toDoEntry, starting_Date);
        agendaEntry.setTeam(team);
        agendaEntryList = repository.requestColabTaskList(collaborator, starting_Date, expected_end_Date, 1);
        repository.registerAgendaEntry(toDoEntry, new Data(2025, 2, 3));
        startDate = new Data(2022, 1, 1);
        endDate = new Data(2025, 3, 1);

        plannedList = new ArrayList<>();
        team2 = new Team(2);
        collaborator2 = new Collaborator("Emanuel", new Data(2004, 4, 20), new Data(2023, 6, 12), "Rua da Mariana", 912809777, "ema@gmail.com", 123456755, 0, "123456722", job, new Password("AAA12ac"), "ema@gmail.com");
        team.addCollaborator(collaborator);
        team2.addCollaborator(collaborator2);
        agendaEntry.setTeam(team);
        greenSpace2 = new GreenSpace("Palacio de Cristal", 1, 40, "Porto", "gsm@this.app");
        toDoEntry2 = new ToDoEntry(greenSpace2, "Cortar relva", "description", 0, 8);
        agendaEntry2 = new AgendaEntry(toDoEntry2, new Data(2025, 3, 2));
        agendaEntry2.setTeam(team2);

        agenda.add(0, agendaEntry);
        agenda.add(1, agendaEntry2);
    }


    @Test
    public void testGetTaskPlannedList() {
        if (agendaEntry.getTeam().hasCollaborator(collaborator)
                && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                && !agendaEntry.getStartingDate().isGreater(endDate)
                && agendaEntry.getAgendaEntry().getStatus().equals("Planned")) {
            plannedList.add(agendaEntry);
        }
        if (agendaEntry2.getTeam().hasCollaborator(collaborator)
                && agendaEntry2.getStartingDate().isGreaterOrEquals(startDate)
                && !agendaEntry2.getStartingDate().isGreater(endDate)
                && agendaEntry2.getAgendaEntry().getStatus().equals("Planned")) {
            plannedList.add(agendaEntry2);
        }
        assertTrue(plannedList.contains(agendaEntry));
        assertFalse(plannedList.contains(agendaEntry2));
    }

    @Test
    public void testRegisterAgendaEntry() {
        Optional<AgendaEntry> registered = repository.registerAgendaEntry(toDoEntry, starting_Date);
        assertTrue(registered.isPresent());
    }

    @Test
    public void testAddAgendaEntry() {
        repository.addAgendaEntry(agendaEntry);
        assertTrue(repository.getAgendaEntries().contains(agendaEntry));
    }

    @Test
    public void testValidateEntry() {
        assertTrue(repository.validateEntry(agendaEntry));
    }

    @Test
    public void testInValidEntry() {
        repository.addAgendaEntry(agendaEntry);
        assertFalse(repository.validateEntry(agendaEntry));
    }

    @Test
    public void testCancelTask() {
        assertTrue(repository.cancelTask(agenda.indexOf(agendaEntry), "gsm@this.app"));
    }

    @Test
    public void testValidPostponeDate() {
        assertFalse(repository.validPostponeDate(agendaEntry, new Data(2027, 1, 1)));
    }

    @Test
    public void testInvalidPostponeDate() {
        assertTrue(repository.validPostponeDate(agendaEntry, new Data(2022, 1, 1)));
    }

    @Test
    public void testPostponeTask() {
        assertTrue(repository.postponeTask(0, new Data(2027, 1, 1), agendaEntry));
    }

    @Test
    public void testPostponeTaskCanceled() {
        agendaEntry.cancelTask();
        try {
            assertFalse(repository.postponeTask(0, new Data(2027, 1, 1), agendaEntry));
        } catch (IllegalArgumentException e) {
            System.out.println("Canceled task");
        }
    }

    @Test
    public void testPostponeTaskDone() {
        agendaEntry.getAgendaEntry().setStatus(String.valueOf(AgendaEntry.Status.DONE));
        try {
            assertFalse(repository.postponeTask(0, new Data(2022, 1, 1), agendaEntry));
        } catch (IllegalArgumentException e) {
            System.out.println("Task done");
        }
    }

    @Test
    public void testGetTaskByResponsible() {
        assertEquals(agendaEntry2.getResponsible(), repository.getTaskByResponsible(1, agenda).getResponsible());
    }

    @Test
    public void testInvalidStatus() {
        agendaEntry.getAgendaEntry().setStatus(String.valueOf(AgendaEntry.Status.DONE));
        try {
            assertTrue(repository.validatesStatus(agendaEntry));
        } catch (IllegalArgumentException e) {
            System.out.println("Agenda entry done, cannot change status to canceled");
        }
    }

    @Test
    public void testValidatesStatus() {
        agendaEntry.getAgendaEntry().setStatus(String.valueOf(AgendaEntry.Status.CANCELED));
        assertTrue(repository.validatesStatus(agendaEntry));
    }

    @Test
    public void testAssignTeam() {
        assertTrue(repository.assignTeam(team,agenda.indexOf(agendaEntry), agendaEntry.getResponsible()));
        assertEquals(agendaEntry.getTeam(), team);
    }

    @Test
    public void testValidateInfo() {
        assertFalse(repository.validateInfo(team, agendaEntry));
    }

    @Test
    public void testGetAgendaEntryByID() {
        AgendaEntry result = repository.getAgendaEntryByID(agenda.indexOf(agendaEntry), "gsm@this.app");
        assertEquals(result, repository.getAgendaEntryByID(agenda.indexOf(agendaEntry), "gsm@this.app"));
    }

    @Test
    public void testAssignVehicle() {
        assertTrue(repository.assignVehicle(agendaEntry,vehicle));
    }
}
