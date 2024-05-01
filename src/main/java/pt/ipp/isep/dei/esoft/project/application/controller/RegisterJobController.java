package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.repository.*;


import java.util.Optional;

public class RegisterJobController {

    private JobRepository jobRepository;

    public RegisterJobController() {
        this.jobRepository = getJobRepository();
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


    public Optional<String> registerJob(String jobName) {
        if (!isValidJobName(jobName)) {
            System.out.println("Invalid job name!");
            return Optional.empty();
        }

        boolean success = jobRepository.addJobName(jobName);

        return success ? Optional.of(jobName) : Optional.empty();
    }

    private boolean isValidJobName(String jobName) {
        if (jobName == null || jobName.isEmpty()) {
            return false;
        } else {
            jobName = jobName.trim();
            for (int i = 0; i < jobName.length(); i++) {
                if (!Character.isLetter(jobName.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }


}