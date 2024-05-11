package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CollaboratorRepositoryTest {

    private CollaboratorRepository collaboratorRepository;

    @BeforeEach
    public void setUp() {
        collaboratorRepository = new CollaboratorRepository();
    }

    /**
     * Tests if collaborator is registered successfully
     */
    @Test
    public void testRegisterCollaborator() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");

        Optional<Collaborator> result = collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        assertTrue(result.isPresent());
    }

    /**
     * Tests if collaborator registered is not duplicated
     */
    @Test
    public void testRegisterDuplicateCollaborator() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");

        collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        Optional<Collaborator> result = collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        assertFalse(result.isPresent());
    }

    /**
     * Tests if collaborator is found
     */
    @Test
    public void testFindCollaborator() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");

        collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        Collaborator result = collaboratorRepository.findCollaborator(123456789);
        assertNotNull(result);
    }

    /**
     * Tests if skill is assigned to a collaborator
     */
    @Test
    public void testAssignSkillCollaborator() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");
        Skill javaSkill = new Skill("drive");

        collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        boolean result = collaboratorRepository.assignSkillCollaborator(123456789, javaSkill);
        assertTrue(result);
    }
}

