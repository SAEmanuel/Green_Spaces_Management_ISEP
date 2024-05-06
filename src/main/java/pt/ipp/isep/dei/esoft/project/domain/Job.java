package pt.ipp.isep.dei.esoft.project.domain;

import java.util.Objects;

/**
 * Represents a job.
 */
public class Job {

    private String jobName;

    /**
     * Constructs a job with the given name.
     *
     * @param jobName the name of the job
     */
    public Job(String jobName) {
        // Checks if the job name is valid before assigning it.
        if (isValidJobName(jobName))
            this.jobName = jobName;
    }

    /**
     * Gets the name of the job.
     *
     * @return the name of the job
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Compares this job to another object for equality.
     *
     * @param otherJob the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object otherJob) {
        if (this == otherJob)
            return true;
        if (otherJob == null || getClass() != otherJob.getClass())
            return false;
        Job job = (Job) otherJob;
        // Compares job names ignoring case and leading/trailing whitespace.
        return jobName.trim().equalsIgnoreCase(job.jobName.trim());
    }

    /**
     * Generates a hash code value for the job.
     *
     * @return the hash code value for the job
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(jobName);
    }

    /**
     * Creates a clone of the job.
     *
     * @return a clone of the job
     */
    public Job clone() {
        // Creates a clone of the job.
        return new Job(this.jobName);
    }

    /**
     * Validates the job name.
     *
     * @param jobName the name to validate
     * @return true if the job name is valid, false otherwise
     */
    private boolean isValidJobName(String jobName) {
        if (jobName != null) {
            // Validates the job name.
            if (jobName.trim().isEmpty()) {
                throw new IllegalArgumentException("Job name cannot be empty.");
            }

            for (int i = 0; i < jobName.length(); i++) {
                // Checks if the job name contains only letters or spaces.
                if (!Character.isLetter(jobName.charAt(i)) && jobName.charAt(i) != ' ') {
                    throw new IllegalArgumentException("Job name contains invalid characters.");
                }
            }
        }

        return true;
    }


    /**
     * Returns a string representation of the job.
     *
     * @return a string representation of the job
     */
    @Override
    public String toString() {
        return String.format("Job: %s", jobName);
    }
}
