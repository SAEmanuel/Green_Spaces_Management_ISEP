package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeamTest {

    private Team team;
    Data birthDate = new Data(2000, 7, 26);
    Data admissionDate = new Data(2019, 6, 15);
    Job job = new Job("Student");

    Collaborator collaborator1 = new Collaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, 1, job);
    Collaborator collaborator2 = new Collaborator("Paulo", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456788, 1, job);
    @BeforeEach
    public void setUp() {
        team = new Team(1);

    }

    @Test
    public void testGetTeamId() {
        // Verify
        assertEquals(1, team.getTeamId());
    }

    @Test
    public void testAddCollaborator() {
        // Setup

        // Execute
        team.addCollaborator(collaborator1);

        // Verify
        List<Collaborator> collaborators = team.getCollaborators();
        assertEquals(1, collaborators.size());
        assertTrue(collaborators.contains(collaborator1));
    }

    @Test
    public void testGetCollaborators() {
        // Setup

        team.addCollaborator(collaborator1);
        team.addCollaborator(collaborator2);

        // Execute
        List<Collaborator> collaborators = team.getCollaborators();

        // Verify
        assertEquals(2, collaborators.size());
        assertTrue(collaborators.contains(collaborator1));
        assertTrue(collaborators.contains(collaborator2));
    }
}