package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;
import pt.ipp.isep.dei.esoft.project.repository.Agenda;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.ToDoListRepository;

import java.util.List;
import java.util.Optional;

public class AgendaController {

    private Agenda agenda;
    private ToDoListRepository toDoListRepository;

    public AgendaController() {
        this.agenda = getAgenda();
        this.toDoListRepository = getToDoRepository();
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


    public Optional<AgendaEntry> registerAgendaEntry(ToDoEntry agendaEntry, Data starting_Date, Data end_Date) {

        Optional<AgendaEntry> optionalAgenda;

        optionalAgenda = agenda.registerAgendaEntry(agendaEntry, starting_Date, end_Date);

        return optionalAgenda;
    }


    public List<ToDoEntry> getToDoList(){
        return toDoListRepository.getToDoList();
    }

}
