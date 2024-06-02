package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for managing a list of ToDoEntry objects.
 */
public class ToDoListRepository implements Serializable {
    private final List<ToDoEntry> toDoList;

    /**
     * Constructor for ToDoListRepository.
     */
    public ToDoListRepository() {
        toDoList = new ArrayList<>();
    }

    /**
     * Retrieves the list of ToDoEntry objects.
     *
     * @return the list of ToDoEntry objects.
     */
    public List<ToDoEntry> getToDoList() {
        return toDoList;
    }

    /**
     * Retrieves a cloned list of ToDoEntry objects.
     *
     * @return a clone of the list of ToDoEntry objects.
     */
    public List<ToDoEntry> getToDoListClone() {
        return clone();
    }

    /**
     * Creates a clone of the current list of ToDoEntry objects.
     *
     * @return a new list containing the cloned ToDoEntry objects.
     */
    public List<ToDoEntry> clone() {
        return new ArrayList<>(this.toDoList);
    }

    /**
     * Registers a new ToDoEntry object and adds it to the repository.
     *
     * @param greenSpace       the green space associated with the entry.
     * @param title            the title of the entry.
     * @param description      the description of the entry.
     * @param urgency          the urgency level of the entry.
     * @param expectedDuration the expected duration of the entry.
     * @return an Optional containing the registered ToDoEntry object, or empty if registration fails.
     */
    public Optional<ToDoEntry> registerToDoEntry(GreenSpace greenSpace, String title, String description, int urgency, int expectedDuration) {
        Optional<ToDoEntry> optionalToDoEntry = Optional.empty();

        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, title, description, urgency, expectedDuration);

        if (addToDoEntry(toDoEntry)) {
            optionalToDoEntry = Optional.of(toDoEntry.clone());
        }

        return optionalToDoEntry;
    }

    /**
     * Adds an entry to the to-do list.
     *
     * @param entry the ToDoEntry object to be added.
     */
    public void add(ToDoEntry entry) {
        toDoList.add(entry);
    }

    /**
     * Adds a ToDoEntry object to the repository if it passes validation.
     *
     * @param toDoEntry the ToDoEntry object to be added.
     * @return true if the entry is successfully added, false otherwise.
     */
    private boolean addToDoEntry(ToDoEntry toDoEntry) {
        boolean success = false;

        if (validate(toDoEntry)) {
            success = toDoList.add(toDoEntry);
        }

        return success;
    }

    /**
     * Validates a ToDoEntry object before adding it to the repository.
     *
     * @param toDoEntry the ToDoEntry object to be validated.
     * @return true if the entry is valid, false otherwise.
     */
    private boolean validate(ToDoEntry toDoEntry) {
        return toDoListDoesNotContainToDoEntry(toDoEntry);
    }

    /**
     * Checks if the repository does not already contain the specified ToDoEntry object.
     *
     * @param toDoEntry the ToDoEntry object to be checked.
     * @return true if the repository does not contain the entry, false otherwise.
     */
    private boolean toDoListDoesNotContainToDoEntry(ToDoEntry toDoEntry) {
        return !toDoList.contains(toDoEntry);
    }

    /**
     * Retrieves the urgency levels available for ToDoEntry objects.
     *
     * @return an array of Urgency enums.
     */
    public ToDoEntry.Urgency[] getUrgency() {
        return ToDoEntry.Urgency.values();
    }

    /**
     * Retrieves a list of ToDoEntry objects for the specified responsible person.
     *
     * @param responsible the responsible person.
     * @return a list of ToDoEntry objects assigned to the specified person.
     */
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
