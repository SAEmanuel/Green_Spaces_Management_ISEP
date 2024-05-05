package pt.ipp.isep.dei.esoft.project.domain;

import java.util.Objects;

public class Job {

    private String jobName;

    public Job(String jobName) {
        if (isValidJobName(jobName))
            this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    @Override
    public boolean equals(Object otherJob) {
        if (this == otherJob)
            return true;
        if (otherJob == null || getClass() != otherJob.getClass())
            return false;
        Job job = (Job) otherJob;
        return jobName.trim().equals(job.jobName.trim());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jobName);
    }

    public Job clone() {
        return new Job(this.jobName);
    }

    private boolean isValidJobName(String jobName) {
        if (jobName == null) {
            throw new IllegalArgumentException("Job name cannot be null.");
        }

        if (jobName.trim().isEmpty()) {
            throw new IllegalArgumentException("Job name cannot be empty.");
        }

        for (int i = 0; i < jobName.length(); i++) {
            if (!Character.isLetter(jobName.charAt(i)) && jobName.charAt(i) != ' ') {
                throw new IllegalArgumentException("Job name contains invalid characters.");
            }
        }

        return true;
    }



    @Override
    public String toString() {
        return String.format("Job: %s", jobName);
    }
}
