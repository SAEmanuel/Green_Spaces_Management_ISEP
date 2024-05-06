package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.repository.JobRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Optional;

/**
 * Controller class for registering jobs.
 */
public class RegisterJobController {

    private JobRepository jobRepository;

    /**
     * Constructs a RegisterJobController.
     */
    public RegisterJobController() {
        // Retrieves the job repository.
        this.jobRepository = getJobRepository();
    }

    /**
     * Retrieves the job repository from the repositories.
     *
     * @return the job repository
     */
    private JobRepository getJobRepository() {
        if (jobRepository == null) {
            // Retrieves the repositories instance.
            Repositories repositories = Repositories.getInstance();
            // Retrieves the job repository from the repositories instance.
            jobRepository = repositories.getJobRepository();
        }
        return jobRepository;
    }

    /**
     * Registers a job with the given name.
     *
     * @param jobName the name of the job to register
     * @return an optional containing the registered job, or empty if registration failed
     */
    public Optional<Job> registerJob(String jobName) {
        Optional<Job> newJob;

        // Attempts to register the job using the job repository.
        newJob = jobRepository.registerJob(jobName);
        return newJob;
    }
}
