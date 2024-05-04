package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobRepository {

    private final List<Job> jobList;

    public JobRepository() {
        this.jobList = new ArrayList<>();
    }

    public Optional<Job> registerJob(String jobName) {

        Optional<Job> optionalValue = Optional.empty();

        Job job = new Job(jobName);

        if (addJob(job)) {
            optionalValue = Optional.of(job);
        }
        return optionalValue;
    }

    private boolean addJob(Job job) {
        boolean success = false;
        if (validateJob(job)) {
            // A clone of the task is added to the list of tasks, to avoid side effects and outside manipulation.
            success = jobList.add(job.clone());
        }
        return success;

    }

    public Optional<Job> add(Job job) {

        Optional<Job> newJob = Optional.empty();
        boolean operationSuccess = false;

        if (validateJob(job)) {
            newJob = Optional.of(job.clone());
            operationSuccess = jobList.add(newJob.get());
        }

        if (!operationSuccess) {
            newJob = Optional.empty();
        }

        return newJob;
    }

    /**
     * This method validates the task, checking for duplicates.
     *
     * @param job The task to be validated.
     * @return True if the task is valid.
     */
    private boolean validateJob(Job job) {
        return jobDoNotContain(job);
    }

    /**
     * This method checks if the task is already in the list of tasks.
     *
     * @param job The task to be checked.
     * @return True if the task is not in the list of tasks.
     */
    private boolean jobDoNotContain(Job job) {
        return !jobList.contains(job);
    }



}

