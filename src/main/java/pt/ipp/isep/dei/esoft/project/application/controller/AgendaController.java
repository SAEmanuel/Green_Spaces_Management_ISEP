package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.application.DTOS.ToDoEntryDTO;
import pt.ipp.isep.dei.esoft.project.application.Mappers.AgendaMapper;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;
import pt.ipp.isep.dei.esoft.project.repository.ToDoListRepository;

//import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AgendaController {

    private Agenda agenda;
    private ToDoListRepository toDoListRepository;
    private TeamRepository teamRepository;
    private SendEmail sendEmail;

    public AgendaController() {
        this.agenda = getAgenda();
        this.toDoListRepository = getToDoRepository();
        this.teamRepository = getTeamRepository();
        this.sendEmail = getSendEmail();
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

    private SendEmail getSendEmail() {
        if (sendEmail == null) {
            Repositories repositories = Repositories.getInstance();
            sendEmail = repositories.getSendEmail();
        }
        return sendEmail;
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
        if(agenda.assignTeam(teams.get(teamID), agendaEntryID)){
            try {
                String[] emails = new String[1];
                emails[0] = "1231498@isep.ipp.pt";
                sendEmail.sendEmail("src/main/resources/config.properties", "isep_ipp_pt", emails, "Assunto", "Texto do email");
//            } catch (MessagingException e) {
//                System.out.println("Team added but not message sent(messaginException)");
//                return false;
            } catch (IOException e) {
                System.out.println("Team added but not message sent(IOException)");
                return false;
            }
            return true;
        }
        return false;
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
