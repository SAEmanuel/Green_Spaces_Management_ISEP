package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToDoListRepository implements Serializable {
    private final List<ToDoEntry> toDoList;

    public ToDoListRepository() {
       toDoList = new ArrayList<>();
    }

    public List<ToDoEntry> getToDoList() {
        return toDoList;
    }

    public List<ToDoEntry> getToDoListCLone() {
        return clone();
    }

    public List<ToDoEntry> clone() {
        return new ArrayList<>(this.toDoList);
    }


    public Optional<ToDoEntry> registerToDoEntry(GreenSpace greenSpace, String title, String description, int urgency, int expectedDuration) {
        Optional<ToDoEntry> optionalToDoEntry = Optional.empty();

        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, title, description, urgency, expectedDuration);

        if (addToDoEntry(toDoEntry)) {
            optionalToDoEntry = Optional.of(toDoEntry.clone());
        }

        return optionalToDoEntry;
    }

    public void add(ToDoEntry entry){
        toDoList.add(entry);
    }

    private boolean addToDoEntry(ToDoEntry toDoEntry) {
        boolean success = false;

        // Validate the Check-Up before adding
        if (validate(toDoEntry)) {
            success = toDoList.add(toDoEntry);
        }

        return success;
    }

    private boolean validate(ToDoEntry toDoEntry) {
        return toDoListDoesNotContainToDoEntry(toDoEntry);
    }

    private boolean toDoListDoesNotContainToDoEntry(ToDoEntry toDoEntry) {
        return !toDoList.contains(toDoEntry);
    }

    public ToDoEntry.Urgency[] getUrgency() {
        return ToDoEntry.Urgency.values();
    }

    public List<ToDoEntry> getToDoListForResponsible(String responsible) {
        List<ToDoEntry> entry = new ArrayList<>();

        for (ToDoEntry toDoEntry : toDoList) {
            if (toDoEntry.getResponsible().equals(responsible)) {
                entry.add(toDoEntry);
            }
        }
        return entry;
    }


}
