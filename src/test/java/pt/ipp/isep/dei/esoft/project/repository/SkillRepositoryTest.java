package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SkillRepositoryTest {


    @Test
    void registerSkillShouldAddSkillToListWhenValid() {
        // Arrange
        SkillRepository skillRepository = new SkillRepository();

        // Act
        Optional<Skill> result = skillRepository.registerSkill("Driver");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, skillRepository.clone().size());
    }

    @Test
    void registerSkillShouldNotAddSkillToListWhenInvalid() {
        // Arrange
        SkillRepository skillRepository = new SkillRepository();
        skillRepository.registerSkill("Driver");

        // Act
        Optional<Skill> result = skillRepository.registerSkill("Driver");

        // Assert
        assertFalse(result.isPresent());
        assertEquals(1, skillRepository.clone().size()); // The list should not increase in size since it was not added
    }


    @Test
    void ensureClonedSkillListIsIndependentFromOriginal() {
        // Arrange
        SkillRepository skillRepository = new SkillRepository();
        skillRepository.registerSkill("Driver");

        // Act
        skillRepository.clone().clear();

        // Assert
        assertEquals(1, skillRepository.clone().size());
    }

}