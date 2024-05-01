package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.ArrayList;
import java.util.List;

public class JobRepository {

    private final List<Job> jobList;

    public JobRepository() {
        this.jobList = new ArrayList<>();
    }

    public boolean addJobName(String jobName) {
        if (!validateJobName(jobName)) {
            return false;
        }
        jobList.add(new Job(jobName));
        return true;
    }


    private boolean validateJobName(String jobName) {
        for (Job job : jobList) {
            if (job.getJobName().equals(jobName)) {
                System.out.println("Job already exists in the repository.");
                return false;
            }
        }
        System.out.println("Job added to the repository.");
        return true;
    }
}

