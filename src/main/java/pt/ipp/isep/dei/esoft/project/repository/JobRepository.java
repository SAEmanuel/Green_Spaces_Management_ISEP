package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Job;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobRepository {

    private final List<Job> jobList;

    public JobRepository() {
        // Initializes the list of jobs.
        this.jobList = new ArrayList<>();
    }

    /**
     * Registers a new job in the repository.
     *
     * @param jobName The name of the job to register.
     * @return An Optional containing the registered job if successful, or empty otherwise.
     */
    public Optional<Job> registerJob(String jobName) {

        // Initialize the Optional with an empty value.
        Optional<Job> optionalValue = Optional.empty();

        // Create a new job object.
        Job job = new Job(jobName);

        // If the job is successfully added to the repository, update the Optional value.
        if (addJob(job)) {
            optionalValue = Optional.of(job);
        }
        return optionalValue;
    }

    /**
     * Adds a job to the repository if it's valid.
     *
     * @param job The job to add.
     * @return True if the job is added successfully, false otherwise.
     */
    private boolean addJob(Job job) {
        boolean success = false;
        // Validates the job before adding it to the repository.
        if (validateJob(job)) {
            // A clone of the job is added to the list of jobs to avoid side effects and outside manipulation.
            success = jobList.add(job.clone());
        }
        return success;
    }

    /**
     * Validates a job before adding it to the repository.
     *
     * @param job The job to validate.
     * @return True if the job is valid (not a duplicate), false otherwise.
     */
    private boolean validateJob(Job job) {
        return jobDoNotContain(job);
    }

    /**
     * Checks if the job is not already in the repository.
     *
     * @param job The job to check.
     * @return True if the job is not in the repository, false otherwise.
     */
    private boolean jobDoNotContain(Job job) {
        return !jobList.contains(job);
    }

    /**
     * Retrieves a job from the repository based on its position.
     *
     * @param position The position of the job in the repository.
     * @return The job object at the specified position.
     */
    public Job getJob(int position) {
        return jobList.get(position);
    }

    /**
     * Creates a clone of the current list of jobs.
     *
     * @return A clone of the list of jobs.
     */
    public List<Job> clone() {
        // Create a new reference job list with the same content of the instance one.
        return new ArrayList<>(jobList);
    }

    public void showJobs() {
        if (jobList.isEmpty()) {
            System.out.println(ANSI_BRIGHT_RED + "No jobs found in the repository." + ANSI_RESET);
        } else {
            System.out.println("\n--List of Jobs--");
            for (int i = 0; i < jobList.size(); i++) {
                Job job = jobList.get(i);
                System.out.println("• Job: " + job.getJobName() + "\n   Option -> [" + i + "]");
            }
            System.out.println("----------------");
        }
    }
}
