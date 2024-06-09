package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ToDoListRepositoryTest {

    private ToDoListRepository repository;
    private GreenSpace greenSpace;

    @BeforeEach
    void setUp() {
        repository = new ToDoListRepository();
        greenSpace = new GreenSpace("Parque", 0, 10, "Rua do Parque", "gsm@this.app");
    }

    @Test
    void testGetToDoList() {
        List<ToDoEntry> toDoList = repository.getToDoList();
        assertNotNull(toDoList);
        assertTrue(toDoList.isEmpty());
    }

    @Test
    void testGetToDoListClone() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        repository.add(toDoEntry);
        List<ToDoEntry> clonedList = repository.getToDoListClone();
        assertNotSame(repository.getToDoList(), clonedList);
        assertEquals(1, clonedList.size());
        assertEquals(toDoEntry, clonedList.get(0));
    }

    @Test
    void testRegisterToDoEntry() {
        String title = "Title1";
        String description = "Description1";
        int urgency = 1;
        int expectedDuration = 60;

        Optional<ToDoEntry> optionalToDoEntry = repository.registerToDoEntry(greenSpace, title, description, urgency, expectedDuration);
        assertTrue(optionalToDoEntry.isPresent());
        ToDoEntry toDoEntry = optionalToDoEntry.get();
        assertEquals(title, toDoEntry.getTitle());
        assertEquals(description, toDoEntry.getDescription());
        assertEquals(urgency, toDoEntry.getUrgency().ordinal());
        assertEquals(expectedDuration, toDoEntry.getExpectedDuration());
    }

    @Test
    void testAdd() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        repository.add(toDoEntry);
        assertEquals(1, repository.getToDoList().size());
        assertTrue(repository.getToDoList().contains(toDoEntry));
    }

    @Test
    void testAddToDoEntry() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        assertTrue(repository.addToDoEntry(toDoEntry));
        assertEquals(1, repository.getToDoList().size());
        assertTrue(repository.getToDoList().contains(toDoEntry));
    }

    @Test
    void testAddToDoEntryFailsIfDuplicate() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        repository.addToDoEntry(toDoEntry);
        assertFalse(repository.addToDoEntry(toDoEntry)); // Adding the same entry should fail
        assertEquals(1, repository.getToDoList().size());
    }

    @Test
    void testValidate() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        assertTrue(repository.validate(toDoEntry));
        repository.add(toDoEntry);
        assertFalse(repository.validate(toDoEntry)); // Should return false for duplicate entry
    }

    @Test
    void testToDoListDoesNotContainToDoEntry() {
        ToDoEntry toDoEntry = new ToDoEntry(greenSpace, "Title1", "Description1", 1, 60);
        assertTrue(repository.toDoListDoesNotContainToDoEntry(toDoEntry));
        repository.add(toDoEntry);
        assertFalse(repository.toDoListDoesNotContainToDoEntry(toDoEntry));
    }

    @Test
    void testGetUrgency() {
        ToDoEntry.Urgency[] urgencies = repository.getUrgency();
        assertNotNull(urgencies);
        assertEquals(ToDoEntry.Urgency.values().length, urgencies.length);
    }

}