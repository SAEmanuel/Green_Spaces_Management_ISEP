package pt.ipp.isep.dei.esoft.project.domain;

import org.jfree.data.time.Day;
import pt.ipp.isep.dei.esoft.project.domain.validations.Validations;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class ToDoEntry {

    private GreenSpace greenSpace;
    private String title;
    private String description;
    private int urgency;
    private int expectedDuration;
    private String status;

    private static final int DAYS_LIMIT = 730; //DOIS ANOS

    public ToDoEntry(GreenSpace greenSpace, String title, String description, int urgency, int expectedDuration) {
        validateToDo(title, description, urgency, expectedDuration);

        this.greenSpace = greenSpace;
        this.title = title;
        this.description = description;
        this.urgency = urgency;
        this.expectedDuration = expectedDuration;
        this.status = "Pending";

    }

    public enum Urgency{
        LOW{
            @Override
            public String toString() {
                return ANSI_SPRING_GREEN + "Low" + ANSI_RESET;
            }
        },
        MEDIUM{
            @Override
            public String toString() {
                return ANSI_YELLOW + "Medium" + ANSI_RESET;
            }
        },
        HIGH{
            @Override
            public String toString() {
                return ANSI_FIREBRICK + "High" + ANSI_RESET;
            }
        },
    }

    private void validateToDo(String title, String description, int urgency, int expectedDuration) {
        isValidTitleAndDescription(title);
        isValidTitleAndDescription(description);
        isValidUrgency(urgency);
        isValidExpectedDuration(expectedDuration);

        //TODO: ACEITAR A MESMA ENTRY SE A ATUAL ESTIVER COM UM STATUS DIFERENTE



    }

    public ToDoEntry clone() {
        return new ToDoEntry(this.greenSpace, this.title, this.description , this.urgency, this.expectedDuration);
    }

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

    private void isValidTitleAndDescription(String phrase) {
        if (!Validations.validateString(phrase)) {
            throw new IllegalArgumentException("Title and Description cannot be null or empty.");

        }

    }

    private void isValidExpectedDuration(int expectedDuration) {
        validatesNegatives(expectedDuration);
        validateExpectedDuration(expectedDuration);
    }

    public static void validateExpectedDuration(int expectedDuration) {

        if (expectedDuration > DAYS_LIMIT) {
            throw new IllegalArgumentException("Expected duration cannot be greater than " + DAYS_LIMIT + "days");
        }
    }

    private void validatesNegatives(int expectedDuration) {
        if (isNegative(expectedDuration)) {
            throw new IllegalArgumentException("Expected duration cannot be negative!");
        }
    }

    private boolean isNegative(int expectedDuration) {
        return expectedDuration < 0;
    }


}
