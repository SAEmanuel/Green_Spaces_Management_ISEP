package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.*;
import pt.ipp.isep.dei.esoft.project.ui.console.RegisterJobUI;


import java.util.Optional;

public class RegisterJobController {

    private JobRepository jobRepository;

    public RegisterJobController() {
        getJobRepository();
    }

    public RegisterJobController(JobRepository jobRepository) {
       this.jobRepository = jobRepository;
    }

    private JobRepository getJobRepository() {
        if (jobRepository == null) {
            Repositories repositories = Repositories.getInstance();
            //Get the JobRepository
            jobRepository = repositories.getJobRepository();
        }
        return jobRepository;
    }


    public Optional<Job> registerJob(Job job) {
        boolean success = jobRepository.add(job);
        return success ? Optional.of(job) : Optional.empty();
    }
}