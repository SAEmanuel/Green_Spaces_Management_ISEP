package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Extras.validations.Validations;

import java.io.Serializable;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

/**
 * The ToDoEntry class represents a to-do entry associated with a green space.
 * It contains details about the to-do task such as title, description, urgency,
 * expected duration, status, and the responsible person.
 */
public class ToDoEntry implements Serializable {

    private final GreenSpace greenSpace;
    private final String title;
    private final String description;
    private final int urgency;
    private int expectedDuration;
    private String status;
    private final String responsible;
    private static final int DAYS_LIMIT = 730; // DOIS ANOS
    private static final String DEFAULT_STATUS = "Pending";

    /**
     * Constructs a new ToDoEntry instance with the specified parameters.
     *
     * @param greenSpace the associated green space
     * @param title the title of the to-do entry
     * @param description the description of the to-do entry
     * @param urgency the urgency level of the to-do entry
     * @param expectedDuration the expected duration of the to-do entry in days
     * @throws IllegalArgumentException if any validation fails
     */
    public ToDoEntry(GreenSpace greenSpace, String title, String description, int urgency, int expectedDuration) {
        validateToDo(title, description, urgency, expectedDuration);

        this.greenSpace = greenSpace;
        this.title = title;
        this.description = description;
        this.urgency = urgency;
        this.expectedDuration = expectedDuration;
        this.status = DEFAULT_STATUS;
        this.responsible = greenSpace.getResponsible();
    }

    /**
     * Sets the status of the to-do entry.
     *
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the expected duration of the to-do entry in days.
     *
     * @return the expected duration
     */
    public int getExpectedDuration() {
        return expectedDuration;
    }

    /**
     * Gets the title of the to-do entry.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the associated green space.
     *
     * @return the green space
     */
    public GreenSpace getGreenSpace() {
        return greenSpace;
    }

    /**
     * Gets the responsible person's email.
     *
     * @return the responsible person
     */
    public String getResponsible() {
        return responsible;
    }

    /**
     * Gets the status of the to-do entry.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the expected duration of the to-do entry in days.
     *
     * @param expectedDuration the new expected duration
     */
    public void setExpectedDuration(int expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    /**
     * The Urgency enum represents the urgency levels of a to-do entry.
     */
    public enum Urgency {
        LOW {
            @Override
            public String toString() {
                return "Low";
            }
        },
        MEDIUM {
            @Override
            public String toString() {
                return "Medium";
            }
        },
        HIGH {
            @Override
            public String toString() {
                return "High";
            }
        }
    }

    /**
     * Validates the parameters for creating a to-do entry.
     *
     * @param title the title
     * @param description the description
     * @param urgency the urgency level
     * @param expectedDuration the expected duration in days
     * @throws IllegalArgumentException if any validation fails
     */
    private void validateToDo(String title, String description, int urgency, int expectedDuration) {
        isValidTitleAndDescription(title);
        isValidTitleAndDescription(description);
        isValidUrgency(urgency);
        isValidExpectedDuration(expectedDuration);
    }

    /**
     * Creates and returns a copy of this to-do entry.
     *
     * @return a clone of this to-do entry
     */
    @Override
    public ToDoEntry clone() {
        return new ToDoEntry(this.greenSpace, this.title, this.description, this.urgency, this.expectedDuration);
    }

    /**
     * Checks if this to-do entry is equal to another object.
     *
     * @param otherObject the other object
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;

        if (otherObject == null || getClass() != otherObject.getClass())
            return false;

        ToDoEntry otherToDo = (ToDoEntry) otherObject;
        return greenSpace.equals(otherToDo.greenSpace)
                && title.equals(otherToDo.title)
                && description.equals(otherToDo.description)
                && urgency == otherToDo.urgency
                && expectedDuration == otherToDo.expectedDuration
                && status.equals(otherToDo.status);
    }

    /**
     * Validates the urgency level.
     *
     * @param urgency the urgency level
     * @throws IllegalArgumentException if the urgency level is invalid
     */
    private void isValidUrgency(int urgency) {
        boolean isValid = false;

        for (Urgency type : Urgency.values()) {
            if (type.ordinal() == urgency) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            throw new IllegalArgumentException("Invalid urgency level!");
        }
    }

    /**
     * Validates the title and description.
     *
     * @param phrase the title or description
     * @throws IllegalArgumentException if the title or description is null or empty
     */
    private void isValidTitleAndDescription(String phrase) {
        if (!Validations.validateString(phrase)) {
            throw new IllegalArgumentException("Title and Description cannot be null or empty.");
        }
    }

    /**
     * Validates the expected duration.
     *
     * @param expectedDuration the expected duration in days
     * @throws IllegalArgumentException if the expected duration is invalid
     */
    private void isValidExpectedDuration(int expectedDuration) {
        validatesNegatives(expectedDuration);
        validateExpectedDuration(expectedDuration);
    }

    /**
     * Validates if the expected duration exceeds the limit.
     *
     * @param expectedDuration the expected duration in days
     * @throws IllegalArgumentException if the expected duration is greater than the limit
     */
    public static void validateExpectedDuration(int expectedDuration) {
        if (expectedDuration > DAYS_LIMIT) {
            throw new IllegalArgumentException("Expected duration cannot be greater than " + DAYS_LIMIT + " days");
        }
    }

    /**
     * Validates if the expected duration is negative.
     *
     * @param expectedDuration the expected duration in days
     * @throws IllegalArgumentException if the expected duration is negative
     */
    private void validatesNegatives(int expectedDuration) {
        if (isNegative(expectedDuration)) {
            throw new IllegalArgumentException("Expected duration cannot be negative!");
        }
    }

    /**
     * Checks if the expected duration is negative.
     *
     * @param expectedDuration the expected duration in days
     * @return true if the expected duration is negative, false otherwise
     */
    private boolean isNegative(int expectedDuration) {
        return expectedDuration < 0;
    }

    /**
     * Gets the description of the to-do entry.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the urgency level of the to-do entry.
     *
     * @return the urgency level
     */
    public Urgency getUrgency() {
        return Urgency.values()[urgency];
    }

    /**
     * Gets the name of the associated green space.
     *
     * @return the name of the green space
     */
    public String getParkName() {
        return greenSpace.getName();
    }

    /**
     * Returns a string representation of the to-do entry.
     *
     * @return a string representation of the to-do entry
     */
    @Override
    public String toString() {
        Urgency[] urgencies = ToDoEntry.Urgency.values();
        return String.format("[%s | %s | %s | %d | %s]", title, description, urgencies[urgency], expectedDuration, greenSpace.getName());
    }
}
