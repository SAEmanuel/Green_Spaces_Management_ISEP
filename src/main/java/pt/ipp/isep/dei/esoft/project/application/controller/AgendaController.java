package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.application.Mappers.AgendaMapper;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;
import pt.ipp.isep.dei.esoft.project.repository.ToDoListRepository;

import java.util.List;
import java.util.Optional;

public class AgendaController {

    private Agenda agenda;
    private ToDoListRepository toDoListRepository;
    private TeamRepository teamRepository;

    public AgendaController() {
        this.agenda = getAgenda();
        this.toDoListRepository = getToDoRepository();
        this.teamRepository = getTeamRepository();
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
        List<ToDoEntry> toDoEntries = toDoListRepository.getToDoList();
        ToDoEntry agendaEntry = searchForOption(toDoEntryOption);

        Optional<AgendaEntry> optionalAgenda;

        optionalAgenda = agenda.registerAgendaEntry(agendaEntry, starting_Date);
        if (optionalAgenda.isPresent()) {
            toDoEntries.remove(agendaEntry);
        }


        return optionalAgenda;
    }

    private ToDoEntry searchForOption(int toDoEntryOption) {
        return getToDoListForResponsible().get(toDoEntryOption);
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

    private List<ToDoEntry> getToDoListForResponsible() {
        return toDoListRepository.getToDoListForResponsible(getResponsible());
    }

    public List<ToDoEntryDTO> getToDoListDTOForResponsible() {
        return toDTO(getToDoListForResponsible());
    }

    public List<AgendaEntry> getAgendaEntriesForResponsible() {
        return agenda.getAgendaEntriesForResponsible(getResponsible());
    }

    public List<Team> getTeams() {
        return teamRepository.getTeamList();
    }

    //-------------------------------------- Mapper -----------------------------

    private List<ToDoEntryDTO> toDTO(List<ToDoEntry> toDoEntries) {
        AgendaMapper agendaMapper = new AgendaMapper();
        return agendaMapper.listToDto(toDoEntries);
    }

    public Optional<List<AgendaEntry>> requestColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> request;

        request = agenda.requestColabTaskList(collaborator, startDate, endDate, filterSelection);
        return request;
    }

    public AgendaEntry.Status[] getStatus() {
        return agenda.getStatus();
    }

}
