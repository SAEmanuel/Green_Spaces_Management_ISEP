package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;

import static org.junit.jupiter.api.Assertions.*;



public class CollaboratorTest {

    private Collaborator collaborator;
    private Data birthDate;
    private Data admissionDate;
    private Job job;
    private Password password;

    @BeforeEach
    public void setUp() {
        birthDate = new Data(2000, 1, 1); // assuming Data has this constructor
        admissionDate = new Data(2020, 1, 1); // assuming Data has this constructor
        job = new Job("Software Engineer"); // assuming Job has this constructor
        password = new Password("AAA12ab"); // assuming Password has this constructor
        collaborator = new Collaborator("Francisco", birthDate, admissionDate, "123 Street",
                912345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "xico@gmail.com");
    }

    @Test
    public void testValidTaxPayerNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Francisco", birthDate, admissionDate, "123 Street",
                    912345678, "xico@gmail.com", 12345678, 0, "123456789", job, password, "xico@gmail.com");
        });
    }

    @Test
    public void testValidEmailAddress() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Francisco", birthDate, admissionDate, "123 Street",
                    912345678, "invalid-email", 123456789, 0, "123456789", job, password, "xico@gmail.com");
        });
    }

    @Test
    public void testValidAddress() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Francisco", birthDate, admissionDate, "",
                    912345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "xico@gmail.com");
        });
    }

    @Test
    public void testValidDocNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Francisco", birthDate, admissionDate, "123 Street",
                    912345678, "xico@gmail.com", 123456789, 0, "12345678", job, password, "xico@gmail.com");
        });
    }

    @Test
    public void testValidPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Francisco", birthDate, admissionDate, "123 Street",
                    812345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "xico@gmail.com");
        });
    }

    @Test
    public void testValidBirthDate() {
        Data invalidBirthDate = new Data(2010, 1, 1); // assuming Data has this constructor
        assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("Francisco", invalidBirthDate, admissionDate, "123 Street",
                    912345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "xico@gmail.com");
        });
    }

    @Test
    public void testValidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Collaborator("", birthDate, admissionDate, "123 Street",
                    912345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "xico@gmail.com");
        });
    }

    @Test
    public void testAddSkill() {
        Skill skill = new Skill("Java"); // assuming Skill has this constructor
        assertTrue(collaborator.addSkill(skill));
        assertFalse(collaborator.addSkill(skill));
    }

    @Test
    public void testClone() {
        Collaborator cloned = collaborator.clone();
        assertEquals(collaborator, cloned);
        assertNotSame(collaborator, cloned);
    }

    @Test
    public void testEquals() {
        Collaborator sameCollaborator = new Collaborator("Francisco", birthDate, admissionDate, "123 Street",
                912345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "Jane Smith");
        assertTrue(collaborator.equals(sameCollaborator));

        Collaborator differentCollaborator = new Collaborator("Gorete", birthDate, admissionDate, "123 Street",
                912345678, "gori@gmail.com", 987654321, 0, "987654321", job, password, "gori@gmail.com");
        assertFalse(collaborator.equals(differentCollaborator));
    }

    @Test
    public void testGetters() {
        assertEquals("Francisco", collaborator.getName());
        assertEquals(birthDate, collaborator.getBirthDate());
        assertEquals(admissionDate, collaborator.getAdmissionDate());
        assertEquals("123 Street", collaborator.getAddress());
        assertEquals(912345678, collaborator.getPhoneNumber());
        assertEquals("xico@gmail.com", collaborator.getEmailAddress());
        assertEquals(123456789, collaborator.getTaxPayerNumber());
        assertEquals(0, collaborator.getDocType());
        assertEquals(job, collaborator.getJob());
        assertEquals(password.getPass(), collaborator.getPassword());
    }
}
