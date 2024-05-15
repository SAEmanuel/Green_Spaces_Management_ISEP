package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CollaboratorTest {

    /**
     * Tests if collaborator is created successfully
     */
    @Test
    public void testValidCollaboratorCreation() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");
        Collaborator collaborator = new Collaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.pt", 123456789, 1, job);
        assertNotNull(collaborator);
    }

    /**
     * Tests if name is valid
     */
    @Test
    public void testIsValidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Data birthDate = new Data(2000, 7, 26);
            Data admissionDate = new Data(2019, 6, 15);
            Job job = new Job("Student");
            new Collaborator(null, birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.pt", 123456789, 1, job);
        });
    }

    /**
     * Tests if phone number is valid
     */
    @Test
    public void testIsValidPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            Data birthDate = new Data(2000, 7, 26);
            Data admissionDate = new Data(2019, 6, 15);
            Job job = new Job("Student");
            new Collaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 999999, "Romeu@.pt", 123456789, 1, job);
        });
    }

    /**
     * Tests if email address is valid
     */
    @Test
    public void testIsValidEmailAddress() {
        assertThrows(IllegalArgumentException.class, () -> {
            Data birthDate = new Data(2000, 7, 26);
            Data admissionDate = new Data(2019, 6, 15);
            Job job = new Job("Student");
            new Collaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "", 123456789, 1, job);
        });
    }

    /**
     * Tests if taxpayer number is valid
     */
    @Test
    public void testIsValidTaxPayerNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            Data birthDate = new Data(2000, 7, 26);
            Data admissionDate = new Data(2019, 6, 15);
            Job job = new Job("Student");
            new Collaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.pt", 123, 1, job);
        });
    }

    /**
     * Tests if skill is added to collaborator
     */
    @Test
    public void testAddSkill() {
        Skill javaSkill = new Skill("drive");
        Collaborator collaborator = new Collaborator("Romeu", new Data(2000, 7, 26), new Data(2019, 6, 15), "Rua da fonte", 912345678, "Romeu@.pt", 123456789, 1, new Job("Student"));
        assertTrue(collaborator.addSkill(javaSkill));
    }

    /**
     * Tests if collaborator is cloned successfully
     */
    @Test
    public void testClone() {
        Collaborator original = new Collaborator("Romeu", new Data(2000, 7, 26), new Data(2019, 6, 15), "Rua da fonte", 912345678, "Romeu@.pt", 123456789, 1, new Job("Student"));
        Collaborator clone = original.clone();
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getBirthDate(), clone.getBirthDate());
        assertEquals(original.getAdmissionDate(), clone.getAdmissionDate());
        assertEquals(original.getAddress(), clone.getAddress());
        assertEquals(original.getPhoneNumber(), clone.getPhoneNumber());
        assertEquals(original.getEmailAddress(), clone.getEmailAddress());
        assertEquals(original.getTaxPayerNumber(), clone.getTaxPayerNumber());
        assertEquals(original.getDocType(), clone.getDocType());
        assertEquals(original.getJob(), clone.getJob());
        assertEquals(original.cloneList(), clone.cloneList());
    }
}
