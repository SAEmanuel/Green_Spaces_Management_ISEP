package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Agenda {

    private final List<AgendaEntry> agenda;




    public Agenda() {
        this.agenda =new ArrayList<>();
    }



    public Optional<AgendaEntry> registerAgendaEntry(ToDoEntry agendaEntry, Data starting_Date, Data end_Date) {

        Optional<AgendaEntry> optionalAgenda = Optional.empty();

        AgendaEntry entry = new AgendaEntry(agendaEntry,starting_Date,end_Date);

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


}
