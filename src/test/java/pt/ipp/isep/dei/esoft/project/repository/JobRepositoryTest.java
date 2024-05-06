package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JobRepositoryTest {

    @Test
    void ensureJobIsRegisteredSuccessfully() {
        // Arrange
        JobRepository jobRepository = new JobRepository();

        // Act
        Optional<Job> result = jobRepository.registerJob("Software Developer");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Software Developer", result.get().getJobName());
    }

    @Test
    void ensureJobIsNotRegisteredWhenNameIsNull() {
        // Arrange
        JobRepository jobRepository = new JobRepository();

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> jobRepository.registerJob(null));
    }

    @Test
    void ensureJobIsNotRegisteredWhenNameIsEmpty() {
        // Arrange
        JobRepository jobRepository = new JobRepository();

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> jobRepository.registerJob(""));
    }

    @Test
    void ensureJobIsNotRegisteredWhenNameIsWhitespace() {
        // Arrange
        JobRepository jobRepository = new JobRepository();

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> jobRepository.registerJob("   "));
    }

    @Test
    void ensureJobIsNotRegisteredWhenNameContainsInvalidCharacters() {
        // Arrange
        JobRepository jobRepository = new JobRepository();

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> jobRepository.registerJob("Software123"));
    }

    @Test
    void ensureJobIsNotRegisteredWhenNameIsAlreadyRegistered() {
        // Arrange
        JobRepository jobRepository = new JobRepository();
        jobRepository.registerJob("Software Developer");

        // Act
        Optional<Job> result = jobRepository.registerJob("Software Developer");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void ensureJobIsRetrievedFromRepository() {
        // Arrange
        JobRepository jobRepository = new JobRepository();
        jobRepository.registerJob("Software Developer");

        // Act
        Job retrievedJob = jobRepository.getJob(0);

        // Assert
        assertNotNull(retrievedJob);
        assertEquals("Software Developer", retrievedJob.getJobName());
    }

    @Test
    void ensureClonedJobListIsIndependentFromOriginal() {
        // Arrange
        JobRepository jobRepository = new JobRepository();
        jobRepository.registerJob("Software Developer");

        // Act
        jobRepository.clone().clear();

        // Assert
        assertEquals(1, jobRepository.clone().size());
    }
}
