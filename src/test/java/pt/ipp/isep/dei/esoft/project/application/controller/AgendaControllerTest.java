package pt.ipp.isep.dei.esoft.project.application.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;
import pt.ipp.isep.dei.esoft.project.repository.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendaControllerTest {

    private AgendaController agendaController = new AgendaController();;
    private Repositories repositories = Repositories.getInstance();
    private AgendaRepository agendaRepository = repositories.getAgenda();;
    private ToDoListRepository toDoListRepository = repositories.getToDoRepository();;
    private TeamRepository teamRepository = repositories.getTeamRepository();;
    private VehicleRepository vehicleRepository = repositories.getVehicleRepository();;
    private AuthenticationRepository authenticationRepository = repositories.getAuthenticationRepository();;





    private final Job job1 = new Job("Marketing Coordinator");
    private final Job job2 = new Job("President of Sales");
    private final Collaborator colla1 = new Collaborator("Francisco", new Data(2004, 5, 20), new Data(2023, 6, 12), "Rua da pedra", 912809789, "xico@gmail.com", 123456744, 0, "123456711", job1, new Password("AAA12ab"), "xico@gmail.com");
    private final Collaborator colla2 = new Collaborator("Emanuel", new Data(2004, 4, 20), new Data(2023, 6, 12), "Rua da Mariana", 912809777, "ema@gmail.com", 123456755, 0, "123456722", job2, new Password("AAA12ac"), "ema@gmail.com");



    private final GreenSpace infanteDomHenrique = new GreenSpace("Infante Dom Henrique", 0, 100, "Porto", "gsm@this.app");
    private final GreenSpace palacioDeCristal = new GreenSpace("Palacio de Cristal", 1, 40, "Porto", "gsm@this.app");
    private final GreenSpace parqueDaCidade = new GreenSpace("Parque da Cidade", 2, 100, "Matosinhos", "gsm@this.app");
    private final GreenSpace quintaDoCovelo = new GreenSpace("Quinta do Covelo", 0, 100, "Porto", "gsm1@this.app");
    private final GreenSpace passeioAlegre = new GreenSpace("Passeio Alegre", 0, 11, "Porto", "gsm1@this.app");



    private final Data data1 = new Data(2023, 1, 1);
    private final Data data2 = new Data(2021, 1, 1);
    private final Vehicle vehicle1 = new Vehicle("AA-00-AA", "Toyota", "Camnry", 0, 1000, 2000, 10000, 2000, 9005, data2, data1);
    private final Vehicle vehicle2 = new Vehicle("BB-00-AA", "Toyota", "Tundra", 0, 5000, 7000, 10000, 2000, 8000, data2, data1);
    private final Vehicle vehicle3 = new Vehicle("CC-00-AA", "Toyota", "Yaris", 0, 500, 1000, 10000, 2000, 4000, data2, data1);



    private final ToDoEntry task1 = new ToDoEntry(infanteDomHenrique, "Cortar relva", "Nenhuma", 0, 12);
    private final ToDoEntry task2 = new ToDoEntry(palacioDeCristal, "Cortar folhas arvores", "Nenhuma", 1, 12);
    private final ToDoEntry task3 = new ToDoEntry(parqueDaCidade, "Limpar fontes", "Nenhuma", 2, 12);
    private final ToDoEntry task4 = new ToDoEntry(quintaDoCovelo, "Limpar bancos", "Nenhuma", 2, 12);
    private final ToDoEntry task5 = new ToDoEntry(passeioAlegre, "Limpar casas de banho", "Nenhuma", 1, 12);



    private final Team team1 = new Team(1);



    @BeforeEach
    void setUp() {
        vehicleRepository.add(vehicle1);
        vehicleRepository.add(vehicle2);
        vehicleRepository.add(vehicle3);

        toDoListRepository.add(task1);
        toDoListRepository.add(task2);
        toDoListRepository.add(task3);
        toDoListRepository.add(task4);
        toDoListRepository.add(task5);

        List<Collaborator> collaboratorList = new ArrayList<>();
        collaboratorList.add(colla1);
        collaboratorList.add(colla2);
        team1.setCollaborators(collaboratorList);
        teamRepository.add(team1);
    }

    @Test
    void registerAgendaEntry() {
        ToDoEntry toDoEntry = mock(ToDoEntry.class);
        Data startingDate = mock(Data.class);
        when(toDoListRepository.getToDoListForResponsible(anyString())).thenReturn(List.of(toDoEntry));
        when(agendaRepository.registerAgendaEntry(any(), any())).thenReturn(Optional.of(mock(AgendaEntry.class)));

        Optional<AgendaEntry> result = agendaController.registerAgendaEntry(0, startingDate);
        assertTrue(result.isPresent());
    }

    @Test
    void postponeTask() {
        int agendaTaskID = 1;
        Data postponeDate = mock(Data.class);
        AgendaEntry agendaEntry = mock(AgendaEntry.class);
        when(agendaRepository.postponeTask(anyInt(), any(), any())).thenReturn(true);

        boolean result = agendaController.postponeTask(agendaTaskID, postponeDate, agendaEntry);
        assertTrue(result);
    }

    @Test
    void assignTeam() {
        int teamID = 0;
        int agendaEntryID = 1;
        String emailService = "test@example.com";
        String responsible = "responsible@example.com";
        Team team = mock(Team.class);
        when(teamRepository.getListTeam()).thenReturn(List.of(team));
        when(agendaRepository.assignTeam(any(), anyInt(), anyString())).thenReturn(true);

        boolean result = agendaController.assignTeam(teamID, agendaEntryID, emailService, responsible);
        assertTrue(result);
    }

    @Test
    void getEmailServices() {
        try {
            when(new SendEmail().getEmailServices()).thenReturn(List.of("Service1", "Service2"));
        } catch (IOException e) {
            fail("IOException should not occur in test");
        }

        List<String> result = agendaController.getEmailServices();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void cancelTask() {
        int agendaTaskID = 1;
        when(agendaRepository.cancelTask(anyInt(), anyString())).thenReturn(true);

        boolean result = agendaController.cancelTask(agendaTaskID);
        assertTrue(result);
    }

    @Test
    void getToDoListDTOForResponsible() {
        ToDoEntry toDoEntry = mock(ToDoEntry.class);
        when(toDoListRepository.getToDoListForResponsible(anyString())).thenReturn(List.of(toDoEntry));

        List<ToDoEntryDTO> result = agendaController.getToDoListDTOForResponsible();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAgendaEntriesForResponsible() {
        AgendaEntry agendaEntry = mock(AgendaEntry.class);
        when(agendaRepository.getAgendaEntriesForResponsible(anyString())).thenReturn(List.of(agendaEntry));

        List<AgendaEntry> result = agendaController.getAgendaEntriesForResponsible();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getTeams() {
        Team team = mock(Team.class);
        when(teamRepository.getTeamList()).thenReturn(List.of(team));

        List<Team> result = agendaController.getTeams();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAgendaEntries() {
        AgendaEntry agendaEntry = mock(AgendaEntry.class);
        when(agendaRepository.getAgendaEntries()).thenReturn(List.of(agendaEntry));

        List<AgendaEntry> result = agendaController.getAgendaEntries();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAvailableVehicles() {
        Vehicle vehicle = mock(Vehicle.class);
        AgendaEntry agendaEntry = mock(AgendaEntry.class);
        when(agendaRepository.getAgendaEntries()).thenReturn(List.of(agendaEntry));
        when(vehicleRepository.getAvailableVehicles(anyList())).thenReturn(List.of(vehicle));

        List<Vehicle> result = agendaController.getAvailableVehicles();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void assignVehicle() {
        AgendaEntry agendaTask = mock(AgendaEntry.class);
        Vehicle vehicle = mock(Vehicle.class);
        when(agendaRepository.assignVehicle(any(), any())).thenReturn(true);

        boolean result = agendaController.assignVehicle(agendaTask, vehicle);
        assertTrue(result);
    }

    @Test
    void requestColabTaskList() {
        Collaborator collaborator = mock(Collaborator.class);
        Data startDate = mock(Data.class);
        Data endDate = mock(Data.class);
        when(agendaRepository.requestColabTaskList(any(), any(), any(), anyInt())).thenReturn(Optional.of(List.of(mock(AgendaEntry.class))));

        Optional<List<AgendaEntry>> result = agendaController.requestColabTaskList(collaborator, startDate, endDate, 0);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }

    @Test
    void requestPlannedColabTaskList() {
        Collaborator collaborator = mock(Collaborator.class);
        Data startDate = mock(Data.class);
        Data endDate = mock(Data.class);
        when(agendaRepository.requestColabPlannedTaskList(any(), any(), any(), anyInt())).thenReturn(Optional.of(List.of(mock(AgendaEntry.class))));

        Optional<List<AgendaEntry>> result = agendaController.requestPlannedColabTaskList(collaborator, startDate, endDate, 0);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }

    @Test
    void getStatus() {
        AgendaEntry.Status[] statuses = AgendaEntry.Status.values();
        when(agendaRepository.getStatus()).thenReturn(statuses);

        AgendaEntry.Status[] result = agendaController.getStatus();
        assertNotNull(result);
        assertEquals(statuses.length, result.length);
    }

    @Test
    void requestChangedStatusTaskList() {
        Collaborator collaborator = mock(Collaborator.class);
        Data startDate = mock(Data.class);
        Data endDate = mock(Data.class);
        when(agendaRepository.changedTaskStatusList(any(), any(), any(), anyInt(), anyString(), anyInt())).thenReturn(Optional.of(List.of(mock(AgendaEntry.class))));

        Optional<List<AgendaEntry>> result = agendaController.requestChangedStatusTaskList(collaborator, startDate, endDate, 0, "confirmed", 1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }
}
