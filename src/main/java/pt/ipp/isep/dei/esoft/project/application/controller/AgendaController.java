package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.application.Mappers.AgendaMapper;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

/**
 * Controller class for managing the agenda operations.
 */
public class AgendaController {

    private AgendaRepository agendaRepository;
    private ToDoListRepository toDoListRepository;
    private TeamRepository teamRepository;
    private VehicleRepository vehicleRepository;

    /**
     * Constructor for AgendaController.
     * Initializes repositories.
     */
    public AgendaController() {
        this.agendaRepository = getAgenda();
        this.toDoListRepository = getToDoRepository();
        this.teamRepository = getTeamRepository();
        this.vehicleRepository = getVehicleRepository();
    }

    /**
     * Gets the vehicle repository.
     *
     * @return The vehicle repository instance.
     */
    private VehicleRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            Repositories repositories = Repositories.getInstance();
            vehicleRepository = repositories.getVehicleRepository();
        }
        return vehicleRepository;
    }

    /**
     * Gets the team repository.
     *
     * @return The team repository instance.
     */
    private TeamRepository getTeamRepository() {
        if (teamRepository == null) {
            Repositories repositories = Repositories.getInstance();
            teamRepository = repositories.getTeamRepository();
        }
        return teamRepository;
    }

    /**
     * Gets the to-do list repository.
     *
     * @return The to-do list repository instance.
     */
    private ToDoListRepository getToDoRepository() {
        if (toDoListRepository == null) {
            Repositories repositories = Repositories.getInstance();
            toDoListRepository = repositories.getToDoRepository();
        }
        return toDoListRepository;
    }

    /**
     * Gets the agenda repository.
     *
     * @return The agenda repository instance.
     */
    private AgendaRepository getAgenda() {
        if (agendaRepository == null) {
            Repositories repositories = Repositories.getInstance();
            agendaRepository = repositories.getAgenda();
        }
        return agendaRepository;
    }

    /**
     * Registers an entry in the agenda.
     *
     * @param toDoEntryOption The option of the to-do entry.
     * @param starting_Date   The starting date for the entry.
     * @return The registered agenda entry, if present.
     */
    public Optional<AgendaEntry> registerAgendaEntry(int toDoEntryOption, Data starting_Date) {
        ToDoEntry agendaEntry = searchForOption(toDoEntryOption);

        Optional<AgendaEntry> optionalAgenda = agendaRepository.registerAgendaEntry(agendaEntry, starting_Date);
        if (optionalAgenda.isPresent()) {
            toDoListRepository.getToDoList().remove(agendaEntry);
        }
        return optionalAgenda;
    }

    /**
     * Searches for a to-do entry based on the given option.
     *
     * @param toDoEntryOption The to-do entry option.
     * @return The to-do entry found.
     */
    private ToDoEntry searchForOption(int toDoEntryOption) {
        return getToDoEntryListForResponsible().get(toDoEntryOption);
    }

    /**
     * Postpones a task.
     *
     * @param agendaTaskID The ID of the agenda task.
     * @param postponeDate The new date to postpone the task to.
     * @param agendaEntry  The agenda entry to be postponed.
     * @return true if the task was postponed successfully, false otherwise.
     */
    public boolean postponeTask(int agendaTaskID, Data postponeDate, AgendaEntry agendaEntry) {
        return agendaRepository.postponeTask(agendaTaskID, postponeDate, agendaEntry);
    }

    /**
     * Assigns a team to a task in the agenda.
     *
     * @param teamID        The ID of the team.
     * @param agendaEntryID The ID of the agenda entry.
     * @param emailService  The email service to be used.
     * @param responsible   The responsible person's email.
     * @return true if the team was assigned successfully, false otherwise.
     */
    public boolean assignTeam(int teamID, int agendaEntryID, String emailService, String responsible) {
        List<Team> teams = teamRepository.getListTeam();
        SendEmail sendEmail = new SendEmail();

        if (agendaRepository.assignTeam(teams.get(teamID), agendaEntryID, responsible)) {
            try {
                if(!sendEmail.sendEmail(emailService, teams.get(teamID).getCollaboratorsEmail(), "Assigned to a Task", "You and your team members have been assigned to a task in an agenda")) {
                    System.out.println(ANSI_BRIGHT_RED + "Email not sent, invalid configuration file data" + ANSI_RESET);
                    throw new IllegalArgumentException("Email not sent, invalid configuration file data");
                }
            } catch (IOException e) {
                System.out.println("Email not sent, file not found or invalid configuration file data");
                throw new IllegalArgumentException("Email not sent, file not found or invalid configuration file data");
            }
            return true;
        }
        return false;
    }

    /**
     * Gets the available email services.
     *
     * @return A list of email services.
     */
    public List<String> getEmailServices() {
        try {
            SendEmail sendEmail = new SendEmail();
            return sendEmail.getEmailServices();
        } catch (IOException e) {
            System.out.println(ANSI_BRIGHT_RED + "\nConfiguration File not found!..Add one in \"/src/main/resource\" location" + ANSI_RESET);
            return null;
        }
    }

    /**
     * Cancels a task.
     *
     * @param agendaTaskID The ID of the agenda task.
     * @return true if the task was cancelled successfully, false otherwise.
     */
    public boolean cancelTask(int agendaTaskID) {
        return agendaRepository.cancelTask(agendaTaskID, getResponsible());
    }

    /**
     * Gets the responsible user.
     *
     * @return The email of the responsible user.
     */
    public String getResponsible() {
        return Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    }

    /**
     * Gets the list of to-do entries for the responsible user.
     *
     * @return The list of to-do entries.
     */
    private List<ToDoEntry> getToDoEntryListForResponsible() {
        return toDoListRepository.getToDoListForResponsible(getResponsible());
    }

    /**
     * Gets the list of to-do entries for the responsible user as DTOs.
     *
     * @return The list of to-do entry DTOs.
     */
    public List<ToDoEntryDTO> getToDoListDTOForResponsible() {
        AgendaMapper agendaMapper = new AgendaMapper();
        return agendaMapper.toDto(getToDoEntryListForResponsible());
    }

    /**
     * Gets the list of agenda entries for the responsible user.
     *
     * @return The list of agenda entries.
     */
    public List<AgendaEntry> getAgendaEntriesForResponsible() {
        return agendaRepository.getAgendaEntriesForResponsible(getResponsible());
    }
    public List<AgendaEntry> getAgendaEntriesForResponsibleTeam() {
        return agendaRepository.getAgendaEntriesForResponsibleTeam(getResponsible());
    }

    public List<AgendaEntry> getAgendaEntriesForResponsibleVehicle() {
        return agendaRepository.getAgendaEntriesForResponsibleVehicle(getResponsible());
    }

    /**
     * Gets the list of teams.
     *
     * @return The list of teams.
     */
    public List<Team> getTeams() {
        return teamRepository.getTeamList();
    }

    /**
     * Gets the list of all agenda entries.
     *
     * @return The list of all agenda entries.
     */
    public List<AgendaEntry> getAgendaEntries() {
        return agendaRepository.getAgendaEntries();
    }

    /**
     * Gets the list of available vehicles.
     *
     * @return The list of available vehicles.
     */
    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.getAvailableVehicles(getAgendaEntries());
    }

    /**
     * Assigns a vehicle to an agenda task.
     *
     * @param agendaTask The agenda task.
     * @param vehicle    The vehicle to be assigned.
     * @return true if the vehicle was assigned successfully, false otherwise.
     */
    public boolean assignVehicle(AgendaEntry agendaTask, Vehicle vehicle) {
        return agendaRepository.assignVehicle(agendaTask, vehicle);
    }

    /**
     * Gets the status options available.
     *
     * @return An array of status options.
     */
    public AgendaEntry.Status[] getStatus() {
        return agendaRepository.getStatus();
    }
    /**
     * Requests the list of tasks assigned to a collaborator.
     *
     * @param collaborator    The collaborator whose tasks are to be retrieved.
     * @param startDate       The start date of the task list.
     * @param endDate         The end date of the task list.
     * @param filterSelection The filter selection.
     * @return The list of tasks assigned to the collaborator.
     */
    public Optional<List<AgendaEntry>> requestColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        return agendaRepository.requestColabTaskList(collaborator, startDate, endDate, filterSelection);
    }

    /**
     * Requests the list of planned tasks assigned to a collaborator.
     *
     * @param collaborator    The collaborator whose tasks are to be retrieved.
     * @param startDate       The start date of the task list.
     * @param endDate         The end date of the task list.
     * @param filterSelection The filter selection.
     * @return The list of planned tasks assigned to the collaborator.
     */
    public Optional<List<AgendaEntry>> requestPlannedColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        return agendaRepository.requestColabPlannedTaskList(collaborator, startDate, endDate, filterSelection);
    }

    /**
     * Requests the list of tasks with changed status assigned to a collaborator.
     *
     * @param collaborator    The collaborator whose tasks are to be retrieved.
     * @param startDate       The start date of the task list.
     * @param endDate         The end date of the task list.
     * @param filterSelection The filter selection.
     * @param confirmation    The confirmation status.
     * @param selectedTask    The selected task ID.
     * @return The list of tasks with changed status assigned to the collaborator.
     */
    public Optional<List<AgendaEntry>> requestChangedStatusTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection, String confirmation, int selectedTask) {
        return agendaRepository.changedTaskStatusList(collaborator, startDate, endDate, filterSelection, confirmation, selectedTask);
    }

    /**
     * Requests the list of possible planned tasks assigned to a collaborator.
     *
     * @param collaborator    The collaborator whose tasks are to be retrieved.
     * @param startDate       The start date of the task list.
     * @param endDate         The end date of the task list.
     * @param filterSelection The filter selection.
     * @return The list of possible planned tasks assigned to the collaborator.
     */
    public Optional<List<AgendaEntry>> requestPossiblePlannedColabTaskList(Collaborator collaborator, Data startDate, Data endDate, int filterSelection) {
        return agendaRepository.requestPossibleColabPlannedTaskList(collaborator, startDate, endDate, filterSelection);
    }
}
