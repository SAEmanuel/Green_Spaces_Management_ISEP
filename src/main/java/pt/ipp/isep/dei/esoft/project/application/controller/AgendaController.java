package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.application.Mappers.AgendaMapper;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AgendaController {

    private AgendaRepository agendaRepository;
    private ToDoListRepository toDoListRepository;
    private TeamRepository teamRepository;
    private VehicleRepository vehicleRepository;

    public AgendaController() {
        this.agendaRepository = getAgenda();
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

    private AgendaRepository getAgenda() {
        if (agendaRepository == null) {
            Repositories repositories = Repositories.getInstance();
            agendaRepository = repositories.getAgenda();
        }
        return agendaRepository;
    }

//----------------------------------- Register an entry in agenda --------------------------------------
    public Optional<AgendaEntry> registerAgendaEntry(int toDoEntryOption, Data starting_Date) {
        ToDoEntry agendaEntry = searchForOption(toDoEntryOption);

        Optional<AgendaEntry> optionalAgenda;


        optionalAgenda = agendaRepository.registerAgendaEntry(agendaEntry, starting_Date);
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
        return agendaRepository.postponeTask(agendaTaskID, postponeDate, agendaEntry);
    }

    //------------------------------------ Assign team to task in agenda --------------------------------
    public boolean assignTeam(int teamID, int agendaEntryID) {
        List<Team> teams = teamRepository.getListTeam();
        SendEmail sendEmail = new SendEmail();

        if(agendaRepository.assignTeam(teams.get(teamID), agendaEntryID)){
            try {
                String[] emails = new String[1];
                emails[0] = "belinha@this.app";
                sendEmail.sendEmail("src/main/resources/config.properties", "gmail", emails, "Whats", "Passa ai o whats novinha");
                return true;
            }
            catch (IOException e){
                System.out.println("Email not sent");
            }
        }
        return false;
    }


    //--------------------------------------  Cancel Task -----------------------------

    public boolean cancelTask(int agendaTaskID) {
        return agendaRepository.cancelTask(agendaTaskID, getResponsible());
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
        return agendaRepository.getAgendaEntriesForResponsible(getResponsible());
    }

    public List<Team> getTeams() {
        return teamRepository.getTeamList();
    }


    public List<AgendaEntry> getAgendaEntries(){
        return agendaRepository.getAgendaEntries();
    }

    //--------------------------------------  Assign Vehicle -----------------------------

    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.getAvailableVehicles(getAgendaEntries());
    }

    public boolean assignVehicle(AgendaEntry agendaTask, Vehicle vehicle) {
        return agendaRepository.assignVehicle(agendaTask, vehicle);
    }

    //-------------------------------------- Mapper -----------------------------------


    public Optional<List<AgendaEntry>> requestColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> request;

        request = agendaRepository.requestColabTaskList(collaborator, startDate, endDate, filterSelection);
        return request;
    }

    public AgendaEntry.Status[] getStatus() {
        return agendaRepository.getStatus();
    }

}
