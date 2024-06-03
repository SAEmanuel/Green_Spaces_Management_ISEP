package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GreenSpaceRepositoryTest {

    @Test
    void testRegisterGreenSpace() {
        GreenSpaceRepository repository = new GreenSpaceRepository();

        Optional<GreenSpace> optionalGreenSpace = repository.registerGreenSpace("Park", 2, 1000, "123 Main St", "John Doe");

        assertTrue(optionalGreenSpace.isPresent());
        assertEquals("Park", optionalGreenSpace.get().getName());
    }

    @Test
    void testRegisterGreenSpaceWithDuplicateName() {
        GreenSpaceRepository repository = new GreenSpaceRepository();

        repository.registerGreenSpace("Park", 2, 1000, "123 Main St", "John Doe");

        Optional<GreenSpace> optionalGreenSpace = repository.registerGreenSpace("Park", 2, 1000, "456 Elm St", "Jane Smith");

        assertFalse(optionalGreenSpace.isPresent());
    }

    @Test
    void testGetGreenSpacesByResponsible() {
        GreenSpaceRepository repository = new GreenSpaceRepository();

        repository.registerGreenSpace("Park", 2, 1000, "123 Main St", "John Doe");
        repository.registerGreenSpace("Garden", 0, 500, "456 Elm St", "John Doe");
        repository.registerGreenSpace("Field", 1, 2000, "789 Oak St", "Jane Smith");

        List<GreenSpace> greenSpaces = repository.getGreenSpacesByResponsible("John Doe");

        assertEquals(2, greenSpaces.size());
        assertEquals("Park", greenSpaces.get(0).getName());
        assertEquals("Garden", greenSpaces.get(1).getName());
    }

    @Test
    void testGetGreenSpacesList() {
        GreenSpaceRepository repository = new GreenSpaceRepository();

        repository.registerGreenSpace("Park", 2, 1000, "123 Main St", "John Doe");
        repository.registerGreenSpace("Garden", 0, 500, "456 Elm St", "John Doe");

        List<GreenSpace> greenSpaces = repository.getGreenSpacesList();

        assertEquals(2, greenSpaces.size());
        assertEquals("Park", greenSpaces.get(0).getName());
        assertEquals("Garden", greenSpaces.get(1).getName());
    }
}
