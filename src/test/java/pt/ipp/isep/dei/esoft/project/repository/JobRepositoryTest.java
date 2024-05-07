package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JobRepositoryTest {

    @Test
    void registerJobShouldAddJobToListWhenValid() {
        // Arrange
        JobRepository jobRepository = new JobRepository();

        // Act
        Optional<Job> result = jobRepository.registerJob("Software Developer");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, jobRepository.clone().size());
    }

    @Test
    void registerJobShouldNotAddJobToListWhenInvalid() {
        // Arrange
        JobRepository jobRepository = new JobRepository();
        jobRepository.registerJob("Software Developer");

        // Act
        Optional<Job> result = jobRepository.registerJob("Software Developer");

        // Assert
        assertFalse(result.isPresent());
        assertEquals(1, jobRepository.clone().size()); // The list should not increase in size since it was not added
    }

    @Test
    void getJobShouldReturnCorrectJob() {
        // Arrange
        JobRepository jobRepository = new JobRepository();
        jobRepository.registerJob("Software Developer");

        // Act
        Job result = jobRepository.getJob(0);

        // Assert
        assertNotNull(result);
        assertEquals("Software Developer", result.getJobName());
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
