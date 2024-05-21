package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Agenda implements Serializable {

    private final List<AgendaEntry> agenda;


    public Agenda() {
        this.agenda =new ArrayList<>();
    }

    //------------------------------------ Add task to agenda --------------------------------

    public Optional<AgendaEntry> registerAgendaEntry(ToDoEntry agendaEntry, Data starting_Date) {

        Optional<AgendaEntry> optionalAgenda = Optional.empty();

        AgendaEntry entry = new AgendaEntry(agendaEntry,starting_Date);

        if (addAgendaEntry(entry)) {
            optionalAgenda = Optional.of(entry);
        }

        return optionalAgenda;
    }


    private boolean addAgendaEntry(AgendaEntry entry) {
        boolean sucess = false;

        if (validateEntry(entry)) {
            sucess = agenda.add(entry);
        }
        return sucess;
    }


    private boolean validateEntry(AgendaEntry entry) {
        for (AgendaEntry agendaEntry : agenda) {
            if (agendaEntry.equals(entry)) {
                return false;
            }
        }
        return true;
    }


    //------------------------------------ Postpone task --------------------------------

    public boolean postponeTask(int agendaTaskID,Data postponeDate, AgendaEntry agendaEntry) {
        List<AgendaEntry> agendaTasks = getAgendaEntriesForResponsible(agendaEntry.getResponsible());
        AgendaEntry taskInAgenda =  getTaskByResposible(agendaTaskID,agendaTasks);

        if (!validPostponeDate(taskInAgenda,postponeDate)) {
            taskInAgenda.setStarting_Date(postponeDate);
            return true;
        }
        return false;

    }

    private boolean validPostponeDate(AgendaEntry task, Data postponeDate) {
        return task.getStarting_Date().isGreaterOrEquals(postponeDate);
    }

    private AgendaEntry getTaskByResposible(int agendaTaskID, List<AgendaEntry> agendaTasks) {
        return agendaTasks.get(agendaTaskID);
    }

    //------------------------------------ Assign team to task in agenda --------------------------------

    public boolean assignTeam(Team team, int agendaEntryID) {
        AgendaEntry task = getAgendaEntryByID(agendaEntryID);

        if (validateInfo(team,task)) {
            task.setTeam(team);
            System.out.println(task);
            return true;
        }
        return false;

    }


    private boolean validateInfo(Team team, AgendaEntry task) {
       return task.getTeam() == null;
    }


    private AgendaEntry getAgendaEntryByID(int agendaEntryID) {
        return agenda.get(agendaEntryID);
    }


    //--------------------------------------  Extra Methods -----------------------------

    public List<AgendaEntry> getAgendaEntriesForResponsible(String responsible) {
        List<AgendaEntry> agendaEntries = new ArrayList<>();
        for (AgendaEntry agendaEntry : agenda) {
            if (agendaEntry.getResponsible().equals(responsible)) {
                agendaEntries.add(agendaEntry);
            }
        }
        return agendaEntries;
    }

}
