package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

    public class TeamRepositoryTest {

        private TeamRepository teamRepository = new TeamRepository();
        private CollaboratorRepository collaboratorRepository = new CollaboratorRepository();
        SkillList skills = new SkillList();
        List<Collaborator> collaborators = new ArrayList<>();
        Skill skill1 = new Skill("jardineiro");
        Skill skill2 = new Skill("construtor");

        @BeforeEach
        public void setUp() {
            teamRepository = new TeamRepository();
            SkillList skills = new SkillList();

            Data birthDate = new Data(2000, 7, 26);
            Data admissionDate = new Data(2019, 6, 15);
            Job job = new Job("Student");
            collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
            collaboratorRepository.registerCollaborator("Paulo", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456788, "Passport", job);
            collaboratorRepository.registerCollaborator("Jorge", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456787, "Passport", job);
            collaboratorRepository.registerCollaborator("Emanuel", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456786, "Passport", job);

            collaboratorRepository.findCollaborator(123456789).addSkill(skill1);
            collaboratorRepository.findCollaborator(123456788).addSkill(skill2);
            collaboratorRepository.findCollaborator(123456787).addSkill(skill2);
            collaboratorRepository.findCollaborator(123456787).addSkill(skill1);
        }

        @Test
        public void testGenerateTeam1Collaborator() {
            // Setup
            int minCollaborators = 1;
            int maxCollaborators = 2;

            skills.addSkill(skill1);
            // Execute
            Optional<Team> result = teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);

            // Verify
            assertTrue(result.isPresent());
        }
        @Test
        public void testGenerateTeamMultipleCollabortor() {
            // Setup
            int minCollaborators = 2;
            int maxCollaborators = 3;

            skills.addSkill(skill2);
            // Execute
            Optional<Team> result = teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);

            // Verify
            assertTrue(result.isPresent());
        }
        @Test
        public void testGenerateTeamWithoutCollabortors() {
            // Setup
            int minCollaborators = 2;
            int maxCollaborators = 3;

            skills.addSkill(skill1);
            skills.addSkill(skill2);
            // Execute
            Optional<Team> result = teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);

            // Verify
            assertTrue(result.isPresent());
        }

        @Test
        public void testRemoveTeam() {
            // Setup

            int minCollaborators = 1;
            int maxCollaborators = 2;
            skills.cleanSkillList();
            skills.addSkill(skill1);

            Optional<Team> teamOpt = teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);

            assertTrue(teamOpt.isPresent());
            Team team = teamOpt.get();

            // Execute
            teamRepository.removeTeam(team.getTeamId());

            // Verify
            assertTrue(teamRepository.getTeamList().isEmpty());
        }
}
