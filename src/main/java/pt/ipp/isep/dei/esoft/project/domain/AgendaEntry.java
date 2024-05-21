package pt.ipp.isep.dei.esoft.project.domain;


import java.io.Serializable;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_MEDIUM_SPRING_GREEN;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class AgendaEntry implements Serializable {

    private final ToDoEntry agendaEntry;

    private Team team;
    private List<Vehicle> vehicles;
    private Data starting_Date;
    private Data end_Date;
    private final String responsible;

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



    public AgendaEntry(ToDoEntry agendaEntry, Data starting_Date) {
//        validationAgendaEntry(starting_Date,end_Date);

        this.agendaEntry = agendaEntry;
        agendaEntry.setStatus(String.valueOf(Status.PLANNED));
        this.starting_Date = starting_Date;
        this.end_Date = calculateEndDate(starting_Date,agendaEntry);
        this.team = null;
        this.vehicles = null;
        this.responsible = agendaEntry.getResponsible();
    }


    private void validationAgendaEntry(Data startingDate, Data endDate) {
        validateDates(startingDate,endDate);
    }

    private void validateDates(Data startingDate, Data endDate) {
        if (startingDate.isGreater(endDate)){
            throw new IllegalArgumentException("Start date cannot be greater than end date.");
        }
    }



    private Data calculateEndDate(Data startingDate, ToDoEntry agendaEntry) {
        return startingDate.calculateData(agendaEntry.getExpectedDuration());
    }


    //------------------------------------ Sets and Gets ----------------------------

    // ********** Sets ************
    public void setEnd_Date(Data end_Date) {
        this.end_Date = end_Date;
    }

    public void setStarting_Date(Data starting_Date) {
        this.starting_Date = starting_Date;
        this.end_Date = calculateEndDate(starting_Date, agendaEntry);
        agendaEntry.setStatus(String.valueOf(Status.POSTPONED));
    }

    // **********************

    // ********** Gets ************
    public String getResponsible() {
        return responsible;
    }

    public Data getEnd_Date() {
        return end_Date;
    }

    public ToDoEntry getAgendaEntry() {
        return agendaEntry;
    }

    public Data getStarting_Date() {
        return starting_Date;
    }

    // **********************

    //------------------------------------- JAVA -------------------------------------
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;

        if (otherObject == null || getClass() != otherObject.getClass())
            return false;

        AgendaEntry otherAgenda = (AgendaEntry) otherObject;
        return agendaEntry.equals(otherAgenda.agendaEntry) && starting_Date.equals(otherAgenda.starting_Date)
                && end_Date.equals(otherAgenda.end_Date) && team.equals(otherAgenda.team) && vehicles.equals(otherAgenda.vehicles);
    }


    public AgendaEntry clone() {
        return new AgendaEntry(agendaEntry, starting_Date);
    }

    @Override
    public String toString() {
        return String.format("  Information: %s \n  Status: %s%s%s \n  Team: %s \n  Vehicles: %s \n  Starting Date: %s \n  End Date: %s",agendaEntry,ANSI_MEDIUM_SPRING_GREEN,agendaEntry.getStatus(),ANSI_RESET,team,vehicles,starting_Date,end_Date);
    }
}
