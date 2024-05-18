package pt.ipp.isep.dei.esoft.project.domain;


import pt.ipp.isep.dei.esoft.project.domain.validations.Validations;

import java.util.List;

public class AgendaEntry {

    private final ToDoEntry agendaEntry;

    private Team team;

    private List<Vehicle> vehicles;

    private final Data starting_Date;
    private Data end_Date;


    private enum Status {
        PLANNED{
            @Override
            public String toString() {
                return "Planned";
            }
        },
        POSTPONED{
            @Override
            public String toString() {
                return "Postponed";
            }
        },
        CANCELED{
            @Override
            public String toString() {
                return "Canceled";
            }
        },
        DONE{
            @Override
            public String toString() {
                return "Done";
            }
        }
    }

    public AgendaEntry(ToDoEntry agendaEntry, Data starting_Date, Data end_Date) {
        validationAgendaEntry(starting_Date,end_Date);

        this.agendaEntry = agendaEntry;
        agendaEntry.setStatus(String.valueOf(Status.PLANNED));
        this.starting_Date = starting_Date;
        this.end_Date = end_Date;
        this.team = null;
        this.vehicles = null;
    }

    private void validationAgendaEntry(Data startingDate, Data endDate) {
        validateDates(startingDate,endDate);
    }

    private void validateDates(Data startingDate, Data endDate) {
        if (startingDate.isGreater(endDate)){
            throw new IllegalArgumentException("Start date cannot be greater than end date.");
        }
    }

    public AgendaEntry clone() {
        return new AgendaEntry(agendaEntry, starting_Date, end_Date);
    }

    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;

        if (otherObject == null || getClass() != otherObject.getClass())
            return false;

        AgendaEntry otherAgenda = (AgendaEntry) otherObject;
        return agendaEntry.equals(otherAgenda.agendaEntry) && starting_Date.equals(otherAgenda.starting_Date)
                && end_Date.equals(otherAgenda.end_Date) && team.equals(otherAgenda.team) && vehicles.equals(otherAgenda.vehicles);
    }


    @Override
    public String toString() {
        return String.format(" ---- Agenda Entry Inf ----\n •Task information: %s \n •Team: %s \n •Vehicles: %s \n •Starting Date: %s \n •End Date: %s",agendaEntry,team,vehicles,starting_Date,end_Date);
    }
}
