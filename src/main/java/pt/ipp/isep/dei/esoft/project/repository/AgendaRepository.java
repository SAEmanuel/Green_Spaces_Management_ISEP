package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing agenda entries.
 */
public class AgendaRepository implements Serializable {

    private List<AgendaEntry> agenda;
    private List<AgendaEntry> agendaBackUp;
    private TeamRepository teamRepository;


    private static final String STATUS_CANCELLED = "Canceled";
    private static final String STATUS_DONE = "Done";

    /**
     * Constructor for AgendaRepository.
     * Initializes the agenda list.
     */
    public AgendaRepository() {
        this.agenda = new ArrayList<>();
        this.agendaBackUp = new ArrayList<>();
    }

    /**
     * Requests the task list for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return An optional list of agenda entries.
     */
    public Optional<List<AgendaEntry>> requestColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> optionalValue = Optional.empty();

        List<AgendaEntry> taskList = getTaskList(collaborator, startDate, endDate, filterSelection);

        if (taskListCreated(collaborator, startDate, endDate, filterSelection)) {
            optionalValue = Optional.of(taskList);
        }
        return optionalValue;
    }

    /**
     * Requests the planned task list for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return An optional list of planned agenda entries.
     */
    public Optional<List<AgendaEntry>> requestColabPlannedTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> optionalValue = Optional.empty();

        List<AgendaEntry> taskList = getTaskPlannedList(collaborator, startDate, endDate, filterSelection);

        if (taskPlannedListCreated(collaborator, startDate, endDate, filterSelection)) {
            optionalValue = Optional.of(taskList);
        }
        return optionalValue;
    }

    /**
     * Requests the possible planned task list for a collaborator within a specified date range and filter selection.
     * This method retrieves a list of planned tasks that have the potential to be marked as done, considering the provided
     * collaborator, date range, and filter selection.
     *
     * @param collaborator    The collaborator whose tasks are being requested.
     * @param startDate       The start date of the range within which tasks are being requested.
     * @param endDate         The end date of the range within which tasks are being requested.
     * @param filterSelection The filter selection used to determine which tasks to include in the list.
     * @return An optional list of possible planned agenda entries for the specified collaborator within the given date range and filter selection.
     * If the list is empty, the optional will be empty as well.
     */
    public Optional<List<AgendaEntry>> requestPossibleColabPlannedTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        Optional<List<AgendaEntry>> optionalValue = Optional.empty();

        List<AgendaEntry> taskList = getPossibleDoneTaskPlannedList(collaborator, startDate, endDate, filterSelection);

        if (taskPlannedListCreated(collaborator, startDate, endDate, filterSelection)) {
            optionalValue = Optional.of(taskList);
        }
        return optionalValue;
    }

    /**
     * Checks if a planned task list was created for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return true if the task list was created, false otherwise.
     */
    private boolean taskPlannedListCreated(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> taskList = getTaskPlannedList(collaborator, startDate, endDate, filterSelection);
        return !taskList.isEmpty();
    }

    /**
     * Gets the planned task list for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return A list of planned agenda entries.
     */
    public List<AgendaEntry> getTaskPlannedList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> plannedList = new ArrayList<>();

        if ((filterSelection - 1) == 1) {
            for (AgendaEntry agendaEntry : agenda) {
                try {
                    if (agendaEntry.getTeam().hasCollaborator(collaborator)
                            && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                            && !agendaEntry.getStartingDate().isGreater(endDate)
                            && agendaEntry.getAgendaEntry().getStatus().equals("Planned")) {
                        plannedList.add(agendaEntry);
                    }
                } catch (NullPointerException e) {
                    System.out.print("");
                }

            }
        }
        return sortByStartDate(plannedList);
    }

    /**
     * Retrieves a list of planned agenda entries for a collaborator within the specified date range and filter selection
     * that have the potential to be marked as done. This method considers only those tasks that are currently planned and
     * have not started yet, and are within the provided date range.
     *
     * @param collaborator    The collaborator for whom the tasks are being retrieved.
     * @param startDate       The start date of the range within which tasks are being retrieved.
     * @param endDate         The end date of the range within which tasks are being retrieved.
     * @param filterSelection The filter selection used to determine which tasks to include in the list.
     * @return A list of planned agenda entries for the specified collaborator within the given date range and filter selection,
     * which have the potential to be marked as done. The list is sorted by start date.
     */
    public List<AgendaEntry> getPossibleDoneTaskPlannedList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> plannedList = new ArrayList<>();

        if ((filterSelection - 1) == 1) {
            for (AgendaEntry agendaEntry : agenda) {
                try {
                    if (agendaEntry.getTeam().hasCollaborator(collaborator)
                            && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                            && !agendaEntry.getStartingDate().isGreater(endDate)
                            && agendaEntry.getAgendaEntry().getStatus().equals("Planned")
                            && !agendaEntry.getStartingDate().isGreater(Data.currentDate())) {
                        plannedList.add(agendaEntry);
                    }
                } catch (NullPointerException e) {
                    System.out.print("");
                }
            }
        }
        return sortByStartDate(plannedList);
    }

    /**
     * Requests the list of tasks with changed status for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @param confirmation    The confirmation status.
     * @param selectedTask    The selected task ID.
     * @return An optional list of tasks with changed status.
     */
    public Optional<List<AgendaEntry>> changedTaskStatusList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection, String confirmation, int selectedTask) {
        List<AgendaEntry> taskList = getTaskPlannedList(collaborator, startDate, endDate, filterSelection);

        if (confirmation.equalsIgnoreCase("y") && !taskList.isEmpty() && selectedTask >= 0 && selectedTask < taskList.size()) {

            taskList.get(selectedTask).setReal_end_Date(Data.currentDate());
            taskList.get(selectedTask).getAgendaEntry().setStatus(String.valueOf(AgendaEntry.Status.DONE));


            if (!taskList.get(selectedTask).getVehicles().isEmpty() || taskList.get(selectedTask).getTeam() != null) {
               for (AgendaEntry agendaEntry : agendaBackUp) {
                   if (agendaEntry.equals(taskList.get(selectedTask))) {
                       agendaEntry.setReal_end_Date(Data.currentDate());
                   }
               }
                List<Vehicle> removedVehicles = new ArrayList<>(taskList.get(selectedTask).getVehicles());
                taskList.get(selectedTask).getVehicles().clear();
                agendaBackUp.get(selectedTask).addVehicles(removedVehicles);
                Team removedTeam = taskList.get(selectedTask).getTeam();
                taskList.get(selectedTask).setTeam(null);
                agendaBackUp.get(selectedTask).addTeam(removedTeam);
            }

            return Optional.of(taskList);
        }

        return Optional.empty();
    }

    /**
     * Gets the task list for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return A list of agenda entries.
     */
    private List<AgendaEntry> getTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> taskList = new ArrayList<>();

        switch (filterSelection - 1) {
            case 0:
                for (AgendaEntry agendaEntry : agenda) {
                    try {
                        if (agendaEntry.getTeam().hasCollaborator(collaborator)
                                && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                                && !agendaEntry.getStartingDate().isGreater(endDate)) {
                            taskList.add(agendaEntry);
                        }
                    } catch (NullPointerException e) {
                        System.out.print("");
                    }
                }
                for (AgendaEntry agendaEntry : agendaBackUp) {
                    try {
                        if (agendaEntry.getTeam().hasCollaborator(collaborator)
                                && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                                && !agendaEntry.getStartingDate().isGreater(endDate) && !taskList.contains(agendaEntry)) {
                            taskList.add(agendaEntry);
                        }
                    } catch (NullPointerException e) {
                        System.out.print("");
                    }
                }
                break;
            case 2:
                for (AgendaEntry agendaEntry : agenda) {
                    try {
                        if (agendaEntry.getTeam().hasCollaborator(collaborator)
                                && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                                && !agendaEntry.getStartingDate().isGreater(endDate)
                                && agendaEntry.getAgendaEntry().getStatus().equals(String.valueOf(AgendaEntry.Status.CANCELED))) {
                            taskList.add(agendaEntry);
                        }
                    } catch (NullPointerException e) {
                        System.out.print("");
                    }
                }
                break;
            case 3:
                for (AgendaEntry agendaEntry : agendaBackUp) {
                    try {
                        if (agendaEntry.getTeam().hasCollaborator(collaborator)
                                && agendaEntry.getStartingDate().isGreaterOrEquals(startDate)
                                && !agendaEntry.getStartingDate().isGreater(endDate)
                                && agendaEntry.getAgendaEntry().getStatus().equals(String.valueOf(AgendaEntry.Status.DONE))) {
                            taskList.add(agendaEntry);
                        }
                    } catch (NullPointerException e) {
                        System.out.print("");
                    }
                }
                break;
        }

        return sortByStartDate(taskList);
    }

    /**
     * Checks if a task list was created for a collaborator within a date range and filter.
     *
     * @param collaborator    The collaborator.
     * @param startDate       The start date of the range.
     * @param endDate         The end date of the range.
     * @param filterSelection The filter selection.
     * @return true if the task list was created, false otherwise.
     */
    public boolean taskListCreated(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        List<AgendaEntry> taskList = getTaskList(collaborator, startDate, endDate, filterSelection);
        return !taskList.isEmpty();
    }

    /**
     * Sorts a list of agenda entries by start date.
     *
     * @param taskList The list of agenda entries.
     * @return The sorted list of agenda entries.
     */
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

    /**
     * Gets the possible status values for agenda entries.
     *
     * @return An array of status values.
     */
    public AgendaEntry.Status[] getStatus() {
        return AgendaEntry.Status.values();
    }

    /**
     * Registers an agenda entry.
     *
     * @param agendaEntry  The to-do entry to be registered.
     * @param startingDate The starting date for the entry.
     * @return The registered agenda entry, if present.
     */
    public Optional<AgendaEntry> registerAgendaEntry(ToDoEntry agendaEntry, Data startingDate) {
        Optional<AgendaEntry> optionalAgenda = Optional.empty();

        AgendaEntry entry = new AgendaEntry(agendaEntry, startingDate);

        if (addAgendaEntry(entry) && addAgendaBackup(entry)) {
            optionalAgenda = Optional.of(entry);
        }

        return optionalAgenda;
    }

    /**
     * Adds an agenda entry.
     *
     * @param entry The agenda entry to add.
     * @return true if the entry was added successfully, false otherwise.
     */
    public boolean addAgendaEntry(AgendaEntry entry) {
        boolean success = false;

        if (validateEntry(entry)) {
            success = agenda.add(entry);

        }
        return success;
    }

    /**
     * Adds a backup copy of the provided agenda entry to the backup agenda list.
     *
     * @param entry The agenda entry to be added to the backup list.
     * @return true if the backup copy of the agenda entry was successfully added to the backup list,
     * false otherwise (e.g., if the provided entry is not valid for backup).
     */
    public boolean addAgendaBackup(AgendaEntry entry) {
        boolean success = false;
        AgendaEntry entryCopy = new AgendaEntry(entry.getAgendaEntry(), entry.getStartingDate());

        if (validateBackUp(entryCopy)) {
            success = agendaBackUp.add(entryCopy);
        }
        return success;
    }

    /**
     * Validates an agenda entry.
     *
     * @param entry The agenda entry to validate.
     * @return true if the entry is valid, false otherwise.
     */
    public boolean validateEntry(AgendaEntry entry) {
        for (AgendaEntry agendaEntry : agenda) {
            if (agendaEntry.equals(entry)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates whether the provided agenda entry is unique in the backup agenda list.
     * This method checks if an agenda entry with the same details as the provided entry already exists
     * in the backup agenda list.
     *
     * @param entry The agenda entry to be validated.
     * @return true if the provided agenda entry is unique and can be added to the backup list,
     * false if there is already an identical entry in the backup list.
     */
    public boolean validateBackUp(AgendaEntry entry) {
        for (AgendaEntry agendaEntry : agendaBackUp) {
            if (agendaEntry.equals(entry)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Postpones a task in the agenda.
     *
     * @param agendaTaskID The ID of the agenda task.
     * @param postponeDate The new date to postpone the task to.
     * @param agendaEntry  The agenda entry to be postponed.
     * @return true if the task was postponed successfully, false otherwise.
     */
    public boolean postponeTask(int agendaTaskID, Data postponeDate, AgendaEntry agendaEntry) {
        if (agendaEntry.getAgendaEntry().getStatus().equals(STATUS_CANCELLED)) {
            throw new IllegalArgumentException("Cancelled agenda entry - can't postpone this task.");
        }
        if (agendaEntry.getAgendaEntry().getStatus().equals(STATUS_DONE)) {
            throw new IllegalArgumentException("Done agenda entry - can't postpone this task.");
        }
        List<AgendaEntry> agendaTasks = getAgendaEntriesForResponsibleTeam(agendaEntry.getResponsible());
        AgendaEntry taskInAgenda = getTaskByResponsible(agendaTaskID, agendaTasks);

        if (!validPostponeDate(taskInAgenda, postponeDate)) {
            taskInAgenda.setStarting_Date(postponeDate);
            return true;
        }
        return false;

    }

    /**
     * Checks if the postpone date is valid.
     *
     * @param task         The task to check.
     * @param postponeDate The postpone date to validate.
     * @return true if the postpone date is valid, false otherwise.
     */
    public boolean validPostponeDate(AgendaEntry task, Data postponeDate) {
        return task.getStartingDate().isGreaterOrEquals(postponeDate);
    }

    /**
     * Gets a task by its responsible collaborator.
     *
     * @param agendaTaskID The ID of the agenda task.
     * @param agendaTasks  The list of agenda tasks.
     * @return The agenda task.
     */
    public AgendaEntry getTaskByResponsible(int agendaTaskID, List<AgendaEntry> agendaTasks) {
        return agendaTasks.get(agendaTaskID);
    }

    /**
     * Cancels a task in the agenda.
     *
     * @param agendaTaskID The ID of the agenda task.
     * @param responsible  The responsible collaborator.
     * @return true if the task was cancelled successfully, false otherwise.
     */
    public boolean cancelTask(int agendaTaskID, String responsible) {
        List<AgendaEntry> agendaTasks = getAgendaEntriesForResponsibleTeam(responsible);
        AgendaEntry taskInAgenda = getTaskByResponsible(agendaTaskID, agendaTasks);

        if (validatesStatus(taskInAgenda)) {
            return false;
        }
        taskInAgenda.cancelTask();
        return true;
    }

    /**
     * Validates the status of a task.
     *
     * @param taskInAgenda The task to validate.
     * @return true if the task status is valid, false otherwise.
     */
    public boolean validatesStatus(AgendaEntry taskInAgenda) {
        if (taskInAgenda.getAgendaEntry().getStatus().equals(STATUS_DONE)) {
            throw new IllegalArgumentException("Done agenda entry - can't cancel this task.");
        }
        return taskInAgenda.getAgendaEntry().getStatus().equals(STATUS_CANCELLED);
    }

    /**
     * Assigns a team to a task in the agenda.
     *
     * @param team          The team to assign.
     * @param agendaEntryID The ID of the agenda entry.
     * @return true if the team was assigned successfully, false otherwise.
     */
    public boolean assignTeam(Team team, int agendaEntryID, String responsible) {
        AgendaEntry task = getAgendaEntryByID(agendaEntryID, responsible);
        AgendaEntry taskBackUp = getAgendaBackUpByID(agendaEntryID, responsible);

        if (validateInfo(team, task)) {
            task.setTeam(team);
            taskBackUp.setTeam(team);
            System.out.println(task);
            return true;
        }
        return false;
    }

    /**
     * Validates the information of a team and task.
     *
     * @param team The team to validate.
     * @param task The task to validate.
     * @return true if the information is valid, false otherwise.
     */
    public boolean validateInfo(Team team, AgendaEntry task) {
        return task.getTeam() == null;
    }

    /**
     * Gets an agenda entry by its ID.
     *
     * @param agendaEntryID The ID of the agenda entry.
     * @return The agenda entry.
     */
    public AgendaEntry getAgendaEntryByID(int agendaEntryID, String responsible) {
        List<AgendaEntry> agenda = getAgendaEntriesForResponsibleTeam(responsible);
        return agenda.get(agendaEntryID);
    }

    /**
     * Retrieves an agenda entry from the backup agenda list based on its ID and responsible collaborator.
     * This method retrieves the agenda entry with the specified ID from the backup agenda list for the given responsible collaborator.
     *
     * @param agendaEntryID The ID of the agenda entry to retrieve.
     * @param responsible   The responsible collaborator associated with the agenda entry.
     * @return The agenda entry with the specified ID and responsible collaborator from the backup agenda list.
     */
    public AgendaEntry getAgendaBackUpByID(int agendaEntryID, String responsible) {
        List<AgendaEntry> agenda = getAgendaEntriesBackUp(responsible);
        return agenda.get(agendaEntryID);
    }

    /**
     * Gets the agenda entries for a specific responsible collaborator.
     *
     * @param responsible The responsible collaborator.
     * @return A list of agenda entries.
     */
    public List<AgendaEntry> getAgendaEntriesForResponsibleTeam(String responsible) {
        return getAgendaEntriesTeam(responsible, agenda);
    }

    public List<AgendaEntry> getAgendaEntriesForResponsibleVehicle(String responsible) {
        return getAgendaEntriesVehicle(responsible, agenda);
    }

    /**
     * Retrieves the backup agenda entries associated with a specific responsible collaborator.
     * This method returns a list of backup agenda entries for the given responsible collaborator.
     *
     * @param responsible The responsible collaborator for whom to retrieve backup agenda entries.
     * @return A list of backup agenda entries associated with the specified responsible collaborator.
     */
    public List<AgendaEntry> getAgendaEntriesBackUp(String responsible) {
        // Delegate the retrieval of backup agenda entries to the private method
        return getAgendaEntriesTeam(responsible, agendaBackUp);
    }

    /**
     * Retrieves agenda entries associated with a specific responsible collaborator from a given list of agenda entries.
     * This method filters and returns agenda entries for the specified responsible collaborator from the provided list,
     * excluding those that are already associated with a team and those with a status of 'Done'.
     *
     * @param responsible The responsible collaborator for whom to retrieve agenda entries.
     * @param agenda      The list of agenda entries to filter.
     * @return A list of agenda entries associated with the specified responsible collaborator.
     */
    private List<AgendaEntry> getAgendaEntriesTeam(String responsible, List<AgendaEntry> agenda) {
        List<AgendaEntry> agendaEntries = new ArrayList<>();
        for (AgendaEntry agendaEntry : agenda) {
            if (agendaEntry.getTeam() != null) {
                if (agendaEntry.getResponsible().equals(responsible) && !agendaEntry.containsTeam(agendaEntry.getTeam()) &&
                        !agendaEntry.getAgendaEntry().getStatus().equals(String.valueOf(AgendaEntry.Status.DONE))) {
                    agendaEntries.add(agendaEntry);
                }
            } else if (agendaEntry.getTeam() == null && agendaEntry.getResponsible().equals(responsible) &&
                    !agendaEntry.getAgendaEntry().getStatus().equals(String.valueOf(AgendaEntry.Status.DONE))) {
                agendaEntries.add(agendaEntry);
            }
        }
        return agendaEntries;
    }

    private List<AgendaEntry> getAgendaEntriesVehicle(String responsible, List<AgendaEntry> agenda) {
        List<AgendaEntry> agendaEntries = new ArrayList<>();
        for (AgendaEntry agendaEntry : agenda) {
            if (agendaEntry.getVehicles() != null && agendaEntry.getResponsible().equals(responsible) &&
                    !agendaEntry.getAgendaEntry().getStatus().equals(String.valueOf(AgendaEntry.Status.DONE)))
                agendaEntries.add(agendaEntry);
        }
        return agendaEntries;
    }

    /**
     * Gets all agenda entries.
     *
     * @return A list of all agenda entries.
     */
    public List<AgendaEntry> getAgendaEntriesTeam() {
        return agenda;
    }

    /**
     * Assigns a vehicle to an agenda task.
     *
     * @param agendaTask The agenda task.
     * @param vehicle    The vehicle to assign.
     * @return true if the vehicle was assigned successfully, false otherwise.
     */
    public boolean assignVehicle(AgendaEntry agendaTask, Vehicle vehicle) {
        return agendaTask.addVehicle(vehicle);
    }

    /**
     * Retrieves a list of teams that are currently in use in the agenda.
     * This method returns a list of teams associated with agenda entries.
     *
     * @return A list of teams currently in use in the agenda.
     */
    public List<Team> getTeamsInUse() {
        // Initialize a list to store teams currently in use
        List<Team> teams = new ArrayList<>();
        // Iterate through the agenda entries
        for (AgendaEntry agendaEntry : agenda) {
            // Check if the agenda entry has a team associated with it
            if (agendaEntry.getTeam() != null) {
                // Add the team to the list of teams in use
                teams.add(agendaEntry.getTeam());
            }
        }
        // Return the list of teams in use
        return teams;
    }

    /**
     * Retrieves the team repository.
     * This method ensures that the team repository is initialized and returns it.
     *
     * @return The team repository.
     */
    private TeamRepository getTeamRepository() {
        if (teamRepository == null) {
            Repositories repositories = Repositories.getInstance();
            teamRepository = repositories.getTeamRepository();
        }
        return teamRepository;
    }

}
