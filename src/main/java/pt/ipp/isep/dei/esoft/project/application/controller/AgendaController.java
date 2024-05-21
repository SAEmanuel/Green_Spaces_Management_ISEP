package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;
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
    public Optional<AgendaEntry> registerAgendaEntry(ToDoEntry agendaEntry, Data starting_Date) {

        Optional<AgendaEntry> optionalAgenda;

        optionalAgenda = agenda.registerAgendaEntry(agendaEntry, starting_Date);

        return optionalAgenda;
    }

    //------------------------------------ Postpone task --------------------------------
    public boolean postponeTask(int agendaTaskID,Data postponeDate, AgendaEntry agendaEntry) {
        return agenda.postponeTask(agendaTaskID,postponeDate,agendaEntry);
    }

    //------------------------------------ Assign team to task in agenda --------------------------------
    public boolean assignTeam(int teamID, int agendaEntryID) {
        List<Team> teams = teamRepository.getListTeam();
        return agenda.assignTeam(teams.get(teamID),agendaEntryID);
    }



    //--------------------------------------  Extra Methods -----------------------------

    public String getResponsible() {
        return Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    }

    public List<ToDoEntry> getToDoListForResponsible(){
        return toDoListRepository.getToDoListForResponsible(getResponsible());
    }

    public List<AgendaEntry> getAgendaEntriesForResponsible(){
        return agenda.getAgendaEntriesForResponsible(getResponsible());
    }

    public List<Team> getTeams(){
        return teamRepository.getTeamList();
    }


}
