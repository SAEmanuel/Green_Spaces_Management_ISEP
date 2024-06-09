package pt.ipp.isep.dei.esoft.project.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_MEDIUM_SPRING_GREEN;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

/**
 * Class representing an entry in the agenda.
 */
public class AgendaEntry implements Serializable {

    private ToDoEntry agendaEntry;
    private Team team;
    private List<Vehicle> vehicles;
    private Data starting_Date;
    private Data expected_end_Date;
    private Data real_end_Date;
    private final String responsible;
    private String stringVehicle;

    public Data getReal_end_Date() {
        return real_end_Date;
    }


    public StringProperty taskNameProperty() {
        return new SimpleStringProperty(agendaEntry.getTitle());
    }

    public StringProperty teamNameProperty() {
        return new SimpleStringProperty(team != null ? team.toString() : "No Team");
    }

    public StringProperty vehicleNameProperty() {
        return new SimpleStringProperty(getStringVehicle());
    }

    public StringProperty startDateProperty() {
        return new SimpleStringProperty(starting_Date != null ? starting_Date.toString() : "");
    }

    public StringProperty expectedEndDateProperty() {
        return new SimpleStringProperty(expected_end_Date != null ? expected_end_Date.toString() : "");
    }

    public StringProperty realEndDateProperty() {
        return new SimpleStringProperty(real_end_Date != null ? real_end_Date.toString() : "");
    }

    public StringProperty statusProperty() {
        return new SimpleStringProperty(getStatus());
    }

    public String getTaskName(){
        return agendaEntry.toString();
    }





    /**
     * Enumeration representing the status of an agenda entry.
     */
    public enum Status {
        PLANNED {
            @Override
            public String toString() {
                return "Planned";
            }
        },
        CANCELED {
            @Override
            public String toString() {
                return "Canceled";
            }
        },
        DONE {
            @Override
            public String toString() {
                return "Done";
            }
        }
    }

    /**
     * Constructor for AgendaEntry.
     *
     * @param agendaEntry   The to-do entry for this agenda entry.
     * @param starting_Date The starting date of the agenda entry.
     */
    public AgendaEntry(ToDoEntry agendaEntry, Data starting_Date) {
//        validationAgendaEntry(starting_Date);

        this.agendaEntry = agendaEntry;
        agendaEntry.setStatus(String.valueOf(Status.PLANNED));
        this.starting_Date = starting_Date;
        this.expected_end_Date = calculateEndDate(starting_Date, agendaEntry);
        this.real_end_Date = null;
        this.team = null;
        this.vehicles = new ArrayList<>();
        this.responsible = agendaEntry.getResponsible();
        setVehiclesString();
    }

    /**
     * Calculates the end date based on the starting date and the expected duration of the to-do entry.
     *
     * @param startingDate The starting date.
     * @param agendaEntry  The to-do entry.
     * @return The calculated end date.
     */
    private Data calculateEndDate(Data startingDate, ToDoEntry agendaEntry) {
        return startingDate.calculateData(agendaEntry.getExpectedDuration());
    }

    /**
     * Checks if the given team is assigned to this agenda entry.
     *
     * @param team The team to check.
     * @return true if the team is assigned, false otherwise.
     */
    public boolean containsTeam(Team team) {
        return this.team.equals(team);
    }

    // ------------------------------------ Sets and Gets ----------------------------

    // ********** Sets ************

    /**
     * Sets the expected end date.
     *
     * @param expected_end_Date The expected end date to set.
     */
    public void setExpected_end_Date(Data expected_end_Date) {
        this.expected_end_Date = expected_end_Date;
    }

    /**
     * Sets the starting date and updates the expected end date accordingly.
     *
     * @param starting_Date The starting date to set.
     */
    public void setStarting_Date(Data starting_Date) {
        this.starting_Date = starting_Date;
        this.expected_end_Date = calculateEndDate(starting_Date, agendaEntry);
    }

    /**
     * Sets the team assigned to this agenda entry.
     *
     * @param team The team to set.
     */
    public void setTeam(Team team) {
        this.team = team;
    }

    /**
     * Sets the real end date.
     *
     * @param real_end_Date The real end date to set.
     */
    public void setReal_end_Date(Data real_end_Date) {
        this.real_end_Date = real_end_Date;
    }

    /**
     * Cancels the task associated with this agenda entry.
     */
    public void cancelTask() {
        agendaEntry.setStatus(String.valueOf(Status.CANCELED));
    }

    // **********************

    // ********** Gets ************

    /**
     * Gets the starting date for this agenda entry.
     *
     * @return Starting Date.
     */
    public Data getStarting_Date() {
        return starting_Date;
    }

    /**
     * Gets the responsible user for this agenda entry.
     *
     * @return The responsible user's email.
     */
    public String getResponsible() {
        return responsible;
    }

    /**
     * Gets the expected end date.
     *
     * @return The expected end date.
     */
    public Data getExpected_end_Date() {
        return expected_end_Date;
    }

    /**
     * Gets the to-do entry associated with this agenda entry.
     *
     * @return The to-do entry.
     */
    public ToDoEntry getAgendaEntry() {
        return agendaEntry;
    }

    /**
     * Gets the starting date.
     *
     * @return The starting date.
     */
    public Data getStartingDate() {
        return starting_Date;
    }

    /**
     * Gets the team assigned to this agenda entry.
     *
     * @return The team.
     */
    public Team getTeam() {
        return team;

    }

    /**
     * Gets the list of vehicles assigned to this agenda entry.
     *
     * @return The list of vehicles.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Adds a vehicle to the list of vehicles assigned to this agenda entry.
     *
     * @param vehicle The vehicle to add.
     * @return true if the vehicle was added successfully, false otherwise.
     */
    public boolean addVehicle(Vehicle vehicle) {
        if (!vehicles.contains(vehicle) && !agendaEntry.getStatus().equals(String.valueOf(Status.CANCELED)))  {
            vehicles.add(vehicle);
            setVehiclesString();
            return true;
        }
        return false;
    }

    /**
     * Gets the status of the agenda entry.
     *
     * @return The status of the agenda entry.
     */
    public String getStatus(){
        return agendaEntry.getStatus();
    }

    /**
     * Sets the string representation of vehicles assigned to this agenda entry.
     */
    public void setVehiclesString(){
        StringBuilder builder = new StringBuilder();
        for (Vehicle vehicle : vehicles) {
            builder.append("[").append(vehicle.getPlateId()).append(" | ").append(vehicle.getModel()).append(" | ").append(vehicle.getBrand()).append("]\n");
        }
        stringVehicle = builder.toString();
    }

    /**
     * Gets the string representation of vehicles assigned to this agenda entry.
     *
     * @return The string representation of vehicles.
     */
    public String getStringVehicle(){
        return stringVehicle;
    }

    /**
     * Adds a list of vehicles to the vehicles assigned to this agenda entry.
     *
     * @param vehicles The list of vehicles to add.
     */
    public void addVehicles(List<Vehicle> vehicles) {
        this.vehicles.addAll(vehicles);
    }

    /**
     * Sets the team assigned to this agenda entry.
     *
     * @param team The team to set.
     */
    public void addTeam(Team team) {
        this.team = team;
    }

    // **********************

    // ------------------------------------- JAVA -------------------------------------

    /**
     * Checks if this agenda entry is equal to another object.
     *
     * @param otherObject The object to compare with.
     * @return true if they are equal, false otherwise.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;

        if (otherObject == null || getClass() != otherObject.getClass())
            return false;

        AgendaEntry otherAgenda = (AgendaEntry) otherObject;
        return agendaEntry.equals(otherAgenda.agendaEntry) && starting_Date.equals(otherAgenda.starting_Date)
                && expected_end_Date.equals(otherAgenda.expected_end_Date);
    }

    /**
     * Creates a clone of this agenda entry.
     *
     * @return The cloned agenda entry.
     */
    @Override
    public AgendaEntry clone() {
        return new AgendaEntry(agendaEntry, starting_Date);
    }


    /**
     * Returns a string representation of the agenda entry.
     *
     * @return The string representation of the agenda entry.
     */
    @Override
    public String toString() {
        return String.format("  Information: %s \n  Status: %s \n  Team: %s \n  Vehicles: %s \n  Starting Date: %s \n  Expected End Date: %s \n  Real End Date: %s",
                agendaEntry, agendaEntry.getStatus() , team,
                vehicles.stream().map(Vehicle::toStringTaskPreview).collect(Collectors.joining("")), starting_Date,
                expected_end_Date, real_end_Date);
    }
}
