package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CollaboratorRepositoryTest {

    private CollaboratorRepository collaboratorRepository;
    private final Data birthDate = new Data(2000, 7, 26);
    private final Data admissionDate = new Data(2019, 6, 15);
    private final Job job = new Job("Student");
    private final Password password = new Password("AAA12ab");

    @BeforeEach
    public void setUp() {
        collaboratorRepository = new CollaboratorRepository();

    }

    /**
     * Tests if collaborator is registered successfully
     */
    @Test
    public void testRegisterCollaborator() {
        Optional<Collaborator> result = collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "romeu@gmail.com", 123456789, 0, "123456789", job, password, "romeu@gmail.com");
        assertTrue(result.isPresent());
    }

    /**
     * Tests if collaborator registered is not duplicated
     */
    @Test
    public void testRegisterDuplicateCollaborator() {

        collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "romeu@gmail.com", 123456789, 0, "123456789", job, password, "romeu@gmail.com");
        Optional<Collaborator> result = collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "romeu@gmail.com", 123456789, 0, "123456789", job, password, "romeu@gmail.com");
        assertFalse(result.isPresent());
    }

    /**
     * Tests if collaborator is found
     */
    @Test
    public void testFindCollaborator() {

        collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "romeu@gmail.com", 123456789, 0, "123456789", job, password, "romeu@gmail.com");
        Collaborator result = collaboratorRepository.findCollaborator(123456789);
        assertNotNull(result);
    }

    /**
     * Tests if skill is assigned to a collaborator
     */
    @Test
    public void testAssignSkillCollaborator() {
        Skill javaSkill = new Skill("drive");

        collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "romeu@gmail.com", 123456789, 0, "123456789", job, password, "romeu@gmail.com");
        boolean result = collaboratorRepository.assignSkillCollaborator(123456789, javaSkill);
        assertTrue(result);
    }
}

