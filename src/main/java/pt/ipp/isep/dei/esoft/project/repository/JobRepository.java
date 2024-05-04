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
            // A clone of the job is added to the list of jobs, to avoid side effects and outside manipulation.
            success = jobList.add(job.clone());
        }
        return success;

    }

//    public Optional<Job> add(Job job) {
//
//        Optional<Job> newJob = Optional.empty();
//        boolean operationSuccess = false;
//
//        if (validateJob(job)) {
//            newJob = Optional.of(job.clone());
//            operationSuccess = jobList.add(newJob.get());
//        }
//
//        if (!operationSuccess) {
//            newJob = Optional.empty();
//        }
//
//        return newJob;
//    }

    /**
     * This method validates the job, checking for duplicates.
     *
     * @param job The job to be validated.
     * @return True if the job is valid.
     */
    private boolean validateJob(Job job) {
        return jobDoNotContain(job);
    }

    /**
     * This method checks if the job is already in the list of jobs.
     *
     * @param job The job to be checked.
     * @return True if the job is not in the list of jobs.
     */
    private boolean jobDoNotContain(Job job) {
        return !jobList.contains(job);
    }



}

