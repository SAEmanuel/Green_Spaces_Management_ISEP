package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GreenSpaceTest {

    @Test
    void testConstructorAndGetters() {
        GreenSpace greenSpace = new GreenSpace("Park", 2, 1000, "123 Main St", "John Doe");

        assertEquals("Park", greenSpace.getName());
        assertEquals(GreenSpace.Size.LARGE, greenSpace.getSize());
        assertEquals(1000, greenSpace.getArea());
        assertEquals("123 Main St", greenSpace.getAddress());
        assertEquals("John Doe", greenSpace.getResponsible());
    }

    @Test
    void testConstructorWithInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace("", 1, 1000, "123 Main St", "John Doe"));

        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace("Park", 1, 0, "123 Main St", "John Doe"));

        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace("Park", 1, -100, "123 Main St", "John Doe"));

        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace("Park", 1, 1000, "", "John Doe"));


    }



    @Test
    void testClone() {
        GreenSpace original = new GreenSpace("Park", 2, 1000, "123 Main St", "John Doe");
        GreenSpace clone = original.clone();

        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getSize(), clone.getSize());
        assertEquals(original.getArea(), clone.getArea());
        assertEquals(original.getAddress(), clone.getAddress());
        assertEquals(original.getResponsible(), clone.getResponsible());
        assertNotSame(original, clone);
    }


    @Test
    void testValidation() {
        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace(null, 1, 1000, "123 Main St", "John Doe"));

        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace("Park", 1, -100, "123 Main St", "John Doe"));

        assertThrows(IllegalArgumentException.class, () ->
                new GreenSpace("Park", 1, 1000, null, "John Doe"));


    }
}
