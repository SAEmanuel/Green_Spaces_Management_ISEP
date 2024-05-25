package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgendaRepository implements Serializable {

    private List<AgendaEntry> agenda;

    private static final String STATUS_CANCELLED = "Canceled";


    public AgendaRepository() {
        this.agenda = new ArrayList<>();
    }

    public Optional<List<AgendaEntry>> requestColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> optionalValue = Optional.empty();

        List<AgendaEntry> taskList = getTaskList(collaborator, startDate, endDate, filterSelection);

        if (taskListCreated(collaborator, startDate, endDate, filterSelection)) {
            optionalValue = Optional.of(taskList);
        }
        return optionalValue;
    }

    private List<AgendaEntry> getTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> taskList = new ArrayList<>();
        switch (filterSelection - 1) {
            case 0:
                for (AgendaEntry agendaEntry : agenda) {
                    if (agendaEntry.getTeam().hasCollaborator(collaborator)
                            && agendaEntry.getStartingDate().isGreater(startDate)
                            && !agendaEntry.getStartingDate().isGreater(endDate)) {

                        taskList.add(agendaEntry);
                    }

                }
                break;
            case 1:
                for (AgendaEntry agendaEntry : agenda) {
                    if (agendaEntry.getTeam().hasCollaborator(collaborator)
                            && agendaEntry.getStartingDate().isGreater(startDate)
                            && !agendaEntry.getStartingDate().isGreater(endDate)
                            && agendaEntry.getAgendaEntry().getStatus().equals("Planned")) {
                        taskList.add(agendaEntry);
                    }

                }
                break;
            case 2:
                for (AgendaEntry agendaEntry : agenda) {
                    if (agendaEntry.getTeam().hasCollaborator(collaborator)
                            && agendaEntry.getStartingDate().isGreater(startDate)
                            && !agendaEntry.getStartingDate().isGreater(endDate)
                            && agendaEntry.getAgendaEntry().getStatus().equals("Postponed")) {
                        taskList.add(agendaEntry);
                    }

                }
                break;
            case 3:
                for (AgendaEntry agendaEntry : agenda) {
                    if (agendaEntry.getTeam().hasCollaborator(collaborator)
                            && agendaEntry.getStartingDate().isGreater(startDate)
                            && !agendaEntry.getStartingDate().isGreater(endDate)
                            && agendaEntry.getAgendaEntry().getStatus().equals("Canceled")) {
                        taskList.add(agendaEntry);
                    }

                }
                break;
            case 4:
                for (AgendaEntry agendaEntry : agenda) {
                    if (agendaEntry.getTeam().hasCollaborator(collaborator)
                            && agendaEntry.getStartingDate().isGreater(startDate)
                            && !agendaEntry.getStartingDate().isGreater(endDate)
                            && agendaEntry.getAgendaEntry().getStatus().equals("Done")) {
                        taskList.add(agendaEntry);
                    }

                }
                break;


        }


        return sortByStartDate(taskList);
    }

    public boolean taskListCreated(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> taskList = getTaskList(collaborator, startDate, endDate, filterSelection);
        return !taskList.isEmpty();
    }

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

    public AgendaEntry.Status[] getStatus() {
        return AgendaEntry.Status.values();
    }

    //------------------------------------ Add task to agenda --------------------------------

    public Optional<AgendaEntry> registerAgendaEntry(ToDoEntry agendaEntry, Data starting_Date) {

        Optional<AgendaEntry> optionalAgenda = Optional.empty();

        AgendaEntry entry = new AgendaEntry(agendaEntry, starting_Date);

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

    public boolean postponeTask(int agendaTaskID, Data postponeDate, AgendaEntry agendaEntry) {
        List<AgendaEntry> agendaTasks = getAgendaEntriesForResponsible(agendaEntry.getResponsible());
        AgendaEntry taskInAgenda = getTaskByResposible(agendaTaskID, agendaTasks);

        if (!validPostponeDate(taskInAgenda, postponeDate)) {
            taskInAgenda.setStarting_Date(postponeDate);
            return true;
        }
        return false;

    }

    private boolean validPostponeDate(AgendaEntry task, Data postponeDate) {
        return task.getStartingDate().isGreaterOrEquals(postponeDate);
    }

    private AgendaEntry getTaskByResposible(int agendaTaskID, List<AgendaEntry> agendaTasks) {
        return agendaTasks.get(agendaTaskID);
    }

    //------------------------------------ Cancel Task --------------------------------

    public boolean cancelTask(int agendaTaskID, String responsible) {
        List<AgendaEntry> agendaTasks = getAgendaEntriesForResponsible(responsible);
        AgendaEntry taskInAgenda = getTaskByResposible(agendaTaskID, agendaTasks);

        if (validatesStatus(taskInAgenda)) {
            return false;
        }
        taskInAgenda.cancelTask();
        return true;
    }

    private boolean validatesStatus(AgendaEntry taskInAgenda) {
        return taskInAgenda.getAgendaEntry().getStatus().equals(STATUS_CANCELLED);
    }

    //------------------------------------ Assign team to task in agenda --------------------------------

    public boolean assignTeam(Team team, int agendaEntryID) {
        AgendaEntry task = getAgendaEntryByID(agendaEntryID);

        if (validateInfo(team, task)) {
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

    public List<AgendaEntry> getAgendaEntries() {
        return agenda;
    }

    //--------------------------------------  Assign Vehicle -----------------------------

    public boolean assignVehicle(AgendaEntry agendaTask, Vehicle vehicle) {
        return agendaTask.addVehicle(vehicle);
    }


}
