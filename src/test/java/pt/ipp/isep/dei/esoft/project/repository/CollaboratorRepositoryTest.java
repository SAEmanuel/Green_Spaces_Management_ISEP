package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CollaboratorRepositoryTest {

    private CollaboratorRepository repository;
    private Collaborator collaborator;
    private Data birthDate;
    private Data admissionDate;
    private Job job;
    private Password password;

    @BeforeEach
    public void setUp() {
        repository = new CollaboratorRepository();
        birthDate = new Data(2000, 1, 1);
        admissionDate = new Data(2020, 1, 1);
        job = new Job("Software Engineer");
        password = new Password("AAA12ab");
        collaborator = new Collaborator("Francisco", birthDate, admissionDate, "123 Street",
                912345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "xico@gmail.com");
    }

    @Test
    public void testRegisterCollaborator() {
        Optional<Collaborator> registered = repository.registerCollaborator("Francisco", birthDate, admissionDate,
                "123 Street", 912345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "xico@gmail.com");
        assertTrue(registered.isPresent());
        assertEquals("Francisco", registered.get().getName());
    }

    @Test
    public void testRegisterCollaboratorDuplicate() {
        repository.add(collaborator);
        Optional<Collaborator> registered = repository.registerCollaborator("Francisco", birthDate, admissionDate,
                "123 Street", 912345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "xico@gmail.com");
        assertFalse(registered.isPresent());
    }

    @Test
    public void testAddCollaborator() {
        assertTrue(repository.addCollaborator(collaborator));
        assertFalse(repository.addCollaborator(collaborator)); // Duplicated
    }

    @Test
    public void testValidateCollaborator() {
        assertTrue(repository.validateCollaborator(collaborator));
        repository.add(collaborator);
        assertFalse(repository.validateCollaborator(collaborator)); // Duplicated
    }

    @Test
    public void testCollaboratorListDoNotContains() {
        assertTrue(repository.collaboratorListDoNotContains(collaborator));
        repository.add(collaborator);
        assertFalse(repository.collaboratorListDoNotContains(collaborator)); // Now it contains
    }

    @Test
    public void testGetCollaboratorList() {
        assertEquals(0, repository.getCollaboratorList().size());
        repository.add(collaborator);
        assertEquals(1, repository.getCollaboratorList().size());
    }

    @Test
    public void testFindCollaborator() {
        repository.add(collaborator);
        assertNotNull(repository.findCollaborator(123456789));
        assertNull(repository.findCollaborator(987654321)); // Not existing
    }

    @Test
    public void testAssignSkillCollaborator() {
        Skill skill = new Skill("Java");
        repository.add(new Collaborator("Francisco", birthDate, admissionDate, "123 Street",
                912345678, "xico@gmail.com", 123456789, 0, "123456789", job, password, "xico@gmail.com"));
        assertTrue(repository.assignSkillCollaborator(123456789, skill));

    }

    @Test
    public void testGetDocType() {
        Collaborator.DocType[] docTypes = repository.getDocType();
        assertNotNull(docTypes);
        assertTrue(docTypes.length > 0);
    }
}
