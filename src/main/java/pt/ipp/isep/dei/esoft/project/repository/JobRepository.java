package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Job;

import java.util.ArrayList;
import java.util.List;

public class JobRepository {

    private final List<Job> jobList;

    public JobRepository() {
        this.jobList = new ArrayList<>();
    }

    public boolean add(Job job) {
        if (jobList.contains(job)) {
            System.out.println("Job already exists in the repository.");
            return false;
        }
        jobList.add(job);
        System.out.println("Job added to the repository.");
        return true;
    }
}
