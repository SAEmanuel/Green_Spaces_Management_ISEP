package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.repository.JobRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Optional;

public class RegisterJobController {

    private JobRepository jobRepository;

    public RegisterJobController() {
        this.jobRepository = getJobRepository();
    }

    private JobRepository getJobRepository() {
        if (jobRepository == null) {
            Repositories repositories = Repositories.getInstance();
            //Get the JobRepository
            jobRepository = repositories.getJobRepository();
        }
        return jobRepository;
    }


    public Optional<Job> registerJob(String jobName) {
        Optional<Job> newjob = Optional.empty();

        newjob = jobRepository.registerJob(jobName);
        return newjob;
    }

}
