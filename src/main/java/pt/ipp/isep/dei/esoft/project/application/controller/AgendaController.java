package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.application.Mappers.AgendaMapper;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgendaController {

    private Agenda agenda;
    private ToDoListRepository toDoListRepository;
    private TeamRepository teamRepository;
    private VehicleRepository vehicleRepository;

    public AgendaController() {
        this.agenda = getAgenda();
        this.toDoListRepository = getToDoRepository();
        this.teamRepository = getTeamRepository();
        this.vehicleRepository = getVehicleRepository();
    }

    private VehicleRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();
            vehicleRepository = repositories.getVehicleRepository();
        }
        return vehicleRepository;
    }

    private TeamRepository getTeamRepository() {
        if (teamRepository == null) {
            Repositories repositories = Repositories.getInstance();
            teamRepository = repositories.getTeamRepository();
        }
        return teamRepository;
    }

    private ToDoListRepository getToDoRepository() {
        if (toDoListRepository == null) {
            Repositories repositories = Repositories.getInstance();
            toDoListRepository = repositories.getToDoRepository();
        }
        return toDoListRepository;
    }

    private Agenda getAgenda() {
        if (agenda == null) {
            Repositories repositories = Repositories.getInstance();
            agenda = repositories.getAgenda();
        }
        return agenda;
    }

//----------------------------------- Register an entry in agenda --------------------------------------
    public Optional<AgendaEntry> registerAgendaEntry(int toDoEntryOption, Data starting_Date) {
        ToDoEntry agendaEntry = searchForOption(toDoEntryOption);

        Optional<AgendaEntry> optionalAgenda;


        optionalAgenda = agenda.registerAgendaEntry(agendaEntry, starting_Date);
        if (optionalAgenda.isPresent()) {
            toDoListRepository.getToDoList().remove(agendaEntry);
        }
        return optionalAgenda;
    }

    private ToDoEntry searchForOption(int toDoEntryOption) {
        return getToDoEntryListForResponsible().get(toDoEntryOption);
    }

    //------------------------------------ Postpone task --------------------------------
    public boolean postponeTask(int agendaTaskID, Data postponeDate, AgendaEntry agendaEntry) {
        return agenda.postponeTask(agendaTaskID, postponeDate, agendaEntry);
    }

    //------------------------------------ Assign team to task in agenda --------------------------------
    public boolean assignTeam(int teamID, int agendaEntryID) {
        List<Team> teams = teamRepository.getListTeam();
        return agenda.assignTeam(teams.get(teamID), agendaEntryID);
    }


    //--------------------------------------  Cancel Task -----------------------------

    public boolean cancelTask(int agendaTaskID) {
        return agenda.cancelTask(agendaTaskID, getResponsible());
    }

    //--------------------------------------  Extra Methods -----------------------------

    public String getResponsible() {
        return Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    }

    private List<ToDoEntry> getToDoEntryListForResponsible() {
        List<ToDoEntry> listToDoEntry = toDoListRepository.getToDoListForResponsible(getResponsible());
        return listToDoEntry;
    }

    public List<ToDoEntryDTO> getToDoListDTOForResponsible() {
        AgendaMapper agendaMapper = new AgendaMapper();
        return agendaMapper.toDto(getToDoEntryListForResponsible());
    }

    public List<AgendaEntry> getAgendaEntriesForResponsible() {
        return agenda.getAgendaEntriesForResponsible(getResponsible());
    }

    public List<Team> getTeams() {
        return teamRepository.getTeamList();
    }


    public List<AgendaEntry> getAgendaEntries(){
        return agenda.getAgendaEntries();
    }

    //--------------------------------------  Assign Vehicle -----------------------------

    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.getAvailableVehicles(getAgendaEntries());
    }

    public boolean assignVehicle(AgendaEntry agendaTask, Vehicle vehicle) {
        return agenda.assignVehicle(agendaTask, vehicle);
    }

    //-------------------------------------- Mapper -----------------------------------


    public Optional<List<AgendaEntry>> requestColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> request;

        request = agenda.requestColabTaskList(collaborator, startDate, endDate, filterSelection);
        return request;
    }

    public AgendaEntry.Status[] getStatus() {
        return agenda.getStatus();
    }

}
