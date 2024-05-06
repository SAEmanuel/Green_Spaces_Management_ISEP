package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobTest {

    //-----------------------Validations for Job Name----------------------------------------

    @Test
    void ensureJobNameIsValid_1() {
        Job job = new Job("Gardener");
    }

    @Test
    void ensureJobNameIsValid_2() {
        Job job = new Job("Painter of walls");
    }

    @Test
    void ensureJobNameIsNotNull() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Job(null));
    }

    @Test
    void ensureJobNameIsNotEmpty_1() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Job(""));
    }

    @Test
    void ensureJobNameIsNotEmpty_2() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Job("  "));
    }

    @Test
    void ensureJobNameIsInvalid_1() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Job("Engineer1"));
    }

    @Test
    void ensureJobNameIsInvalid_2() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Job("Mechanic?"));
    }

    //---------------------------------------------------------------


    //--------------------------Validations when getting Job Name--------------------------------
    @Test
    void shouldReturnJobNameWithDifferentRef() {
        String name = "Mechanic";
        Job job = new Job("name");
        boolean sameRef = job.getJobName() == name;
        assertFalse(sameRef);
    }


    @Test
    void shouldReturnJobName() {
        Job job = new Job("Java Developer");
        assertEquals("Java Developer", job.getJobName());
    }
    //---------------------------------------------------------------


    //---------------------------Validations for Method Equals------------------------
    @Test
    void comparingJobsSameRef() {
        Job job1 = new Job("Java Developer");
        Job job2 = job1;
        assertTrue(job1.equals(job2));
    }

    @Test
    void comparingJobWithDifferentObjectInstance_1() {
        Job job1 = new Job("Java Developer");
        Object object = new Object();
        assertFalse(job1.equals(object));
    }

    @Test
    void comparingJobWithDifferentObjectInstance_2() {
        Job job1 = new Job("Java Developer");
        Skill skill = new Skill("Java Developer");
        assertFalse(job1.equals(skill));
    }


    @Test
    void comparingJobs_sameJobName() {
        Job job1 = new Job("Java Developer");
        Job job2 = new Job("Java Developer");
        assertTrue(job1.equals(job2));
    }

    @Test
    void comparingJobs_differentJobName() {
        Job job1 = new Job("Java Developer");
        Job job2 = new Job("Python Developer");
        assertFalse(job1.equals(job2));
    }

    //------------------------Validations Method Clone---------------------------
    @Test
    void differentRefForJob() {
        Job job1 = new Job("Java Developer");
        Job job2 = job1.clone();
        boolean sameRef = job1 == job2;
        assertFalse(sameRef);
    }
}
