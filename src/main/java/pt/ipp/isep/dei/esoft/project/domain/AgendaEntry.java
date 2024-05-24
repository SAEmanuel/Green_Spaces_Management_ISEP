package pt.ipp.isep.dei.esoft.project.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_MEDIUM_SPRING_GREEN;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class AgendaEntry implements Serializable {

    private ToDoEntry agendaEntry;

    private Team team;
    private List<Vehicle> vehicles;
    private Data starting_Date;
    private Data expected_end_Date;
    private Data real_end_Date;
    private final String responsible;

    public enum Status {
        PLANNED{
            @Override
            public String toString() {
                return "Planned";
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
        this.expected_end_Date = calculateEndDate(starting_Date,agendaEntry);
        this.real_end_Date = null;
        this.team = null;
        this.vehicles = new ArrayList<Vehicle>();
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
    public void setExpected_end_Date(Data expected_end_Date) {
        this.expected_end_Date = expected_end_Date;
    }

    public void setStarting_Date(Data starting_Date) {
        this.starting_Date = starting_Date;
        this.expected_end_Date = calculateEndDate(starting_Date, agendaEntry);
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setReal_end_Date(Data real_end_Date) {
        this.real_end_Date = real_end_Date;
    }

    public void cancelTask(){
        agendaEntry.setStatus(String.valueOf(Status.CANCELED));
    }

    // **********************

    // ********** Gets ************
    public String getResponsible() {
        return responsible;
    }

    public Data getExpected_end_Date() {
        return expected_end_Date;
    }

    public ToDoEntry getAgendaEntry() {
        return agendaEntry;
    }

    public Data getStartingDate() {
        return starting_Date;
    }

    public Team getTeam() {
        return team;
    }


    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public boolean addVehicle(Vehicle vehicle) {

        if (!vehicles.contains(vehicle)) {
            vehicles.add(vehicle);
            return true;
        }
        return false;
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
                && expected_end_Date.equals(otherAgenda.expected_end_Date);
    }


    public AgendaEntry clone() {
        return new AgendaEntry(agendaEntry, starting_Date);
    }

    @Override
    public String toString() {
        return String.format("  Information: %s \n  Status: %s%s%s \n  Team: %s \n  Vehicles: %s \n  Starting Date: %s \n  Expected End Date: %s \n  Real End Date: %s",agendaEntry,ANSI_MEDIUM_SPRING_GREEN,agendaEntry.getStatus(),ANSI_RESET,team,vehicles,starting_Date, expected_end_Date,real_end_Date);
    }
}
