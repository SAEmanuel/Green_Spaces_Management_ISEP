package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pt.ipp.isep.dei.esoft.project.domain.Extras.validations.Validations;

import static org.junit.jupiter.api.Assertions.*;

class ToDoEntryTest {

    private GreenSpace greenSpace;

    @BeforeEach
    void setUp() {
        greenSpace = new GreenSpace("Parque", 0, 10, "Rua do Parque", "gsm@this.app");
    }

    @Test
    void testConstructorValidParameters() {
        String title = "Test Title";
        String description = "Test Description";
        int urgency = 1;
        int expectedDuration = 100;

        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, title, description, urgency, expectedDuration);

        assertEquals(greenSpace, toDoEntry.getGreenSpace());
        assertEquals(title, toDoEntry.getTitle());
        assertEquals(description, toDoEntry.getDescription());
        assertEquals(ToDoEntry.Urgency.MEDIUM, toDoEntry.getUrgency());
        assertEquals(expectedDuration, toDoEntry.getExpectedDuration());
        assertEquals("Pending", toDoEntry.getStatus());
        assertEquals("gsm@this.app", toDoEntry.getResponsible());
    }

    @Test
    void testConstructorInvalidTitle() {
        String invalidTitle = "";

        Executable executable = () -> new ToDoEntry(greenSpace, invalidTitle, "Description", 1, 100);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Title and Description cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testConstructorInvalidUrgency() {
        Executable executable = () -> new ToDoEntry(greenSpace, "Title", "Description", 4, 100);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Invalid urgency level!", exception.getMessage());
    }

    @Test
    void testConstructorNegativeExpectedDuration() {
        Executable executable = () -> new ToDoEntry(greenSpace, "Title", "Description", 1, -1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Expected duration cannot be negative!", exception.getMessage());
    }

    @Test
    void testConstructorExpectedDurationExceedsLimit() {
        Executable executable = () -> new ToDoEntry(greenSpace, "Title", "Description", 1, 1000);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Expected duration cannot be greater than 730 days", exception.getMessage());
    }

    @Test
    void testSetStatus() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        toDoEntry.setStatus("Completed");

        assertEquals("Completed", toDoEntry.getStatus());
    }

    @Test
    void testSetExpectedDuration() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        toDoEntry.setExpectedDuration(200);

        assertEquals(200, toDoEntry.getExpectedDuration());
    }

    @Test
    void testClone() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        ToDoEntry clonedToDoEntry = toDoEntry.clone();

        assertNotSame(toDoEntry, clonedToDoEntry);
        assertEquals(toDoEntry, clonedToDoEntry);
    }

    @Test
    void testEqualsSameObject() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);

        assertTrue(toDoEntry.equals(toDoEntry));
    }

    @Test
    void testEqualsDifferentObjectSameValues() {
        ToDoEntry toDoEntry1 = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);

        assertTrue(toDoEntry1.equals(toDoEntry2));
    }

    @Test
    void testEqualsDifferentObjectDifferentValues() {
        ToDoEntry toDoEntry1 = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 100);
        ToDoEntry toDoEntry2 = new ToDoEntry(greenSpace, "Title2", "Description2", 2, 200);

        assertFalse(toDoEntry1.equals(toDoEntry2));
    }

    @Test
    void testToString() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title", "Description", 1, 100);
        String expectedString = "[Title | Description | Medium | 100 | Parque]";

        assertEquals(expectedString, toDoEntry.toString());
    }

    @Test
    void testIsValidUrgency() {
        assertDoesNotThrow(() -> new ToDoEntry(greenSpace, "Title", "Description", 0, 100));
        assertDoesNotThrow(() -> new ToDoEntry(greenSpace, "Title", "Description", 1, 100));
        assertDoesNotThrow(() -> new ToDoEntry(greenSpace, "Title", "Description", 2, 100));
        assertThrows(IllegalArgumentException.class, () -> new ToDoEntry(greenSpace, "Title", "Description", 3, 100));
    }

    @Test
    void testValidateString() {
        assertTrue(Validations.validateString("Valid String"));
        assertFalse(Validations.validateString(""));
    }
}
