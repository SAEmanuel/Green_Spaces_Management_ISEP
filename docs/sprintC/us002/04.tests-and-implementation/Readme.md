# US002 - Job Registration 

## 4. Tests 
### Validations for Job Name
**Test 1:** Ensure that the job name is valid 

	@Test
    void ensureJobNameIsValid_1() {
        Job job = new Job("Gardener");
    }
	

**Test 2:** Ensure that the job name is valid 

	@Test
    void ensureJobNameIsValid_2() {
        Job job = new Job("Painter of walls");
    }

**Test 3:** Ensure that the job name is not null

	@Test
    void ensureJobNameIsNotNull() {
    assertThrows(IllegalArgumentException.class, () -> new Job(null));
    }

**Test 4:** Ensure that the job name is not empty

	 @Test
    void ensureJobNameIsNotEmpty_1() {
        assertThrows(IllegalArgumentException.class, () -> new Job(""));
    }

**Test 5:** Ensure that the job name is not empty

	 @Test
    void ensureJobNameIsNotEmpty_2() {
        assertThrows(IllegalArgumentException.class, () -> new Job("  "));
    }

**Test 6:** Ensure that the job name is invalid

	  @Test
    void ensureJobNameIsInvalid_1() {
        assertThrows(IllegalArgumentException.class, () -> new Job("Engineer1"));
    }

**Test 7:** Ensure that the job name is invalid

	 @Test
    void ensureJobNameIsInvalid_2() {
        assertThrows(IllegalArgumentException.class, () -> new Job("Mechanic?"));
    }

### Validations when getting Job Name

**Test 8:** Ensure Job Name Returns with Different Reference

	 @Test
    void shouldReturnJobNameWithDifferentRef() {
        String name = "Mechanic";
        Job job = new Job("name");
        boolean sameRef = job.getJobName() == name;
        assertFalse(sameRef);
    }

**Test 9:** Ensure Correct Job Name is Returned

	@Test
    void shouldReturnJobName() {
        Job job = new Job("Java Developer");
        assertEquals("Java Developer", job.getJobName());
    }


### Validations for Method Equals
**Test 10:** Comparing Jobs with Same Reference

	 @Test
    void comparingJobsSameRef() {
        Job job1 = new Job("Java Developer");
        Job job2 = job1;
        assertTrue(job1.equals(job2));
    }


**Test 11:** Comparing Job with Different Object Instance

	 @Test
    void comparingJobWithDifferentObjectInstance_2() {
        Job job1 = new Job("Java Developer");
        Skill skill = new Skill("Java Developer");
        assertFalse(job1.equals(skill));
    }

**Test 12:** Comparing Jobs with Same Job Name

	 @Test
    void comparingJobs_sameJobName() {
        Job job1 = new Job("Java Developer");
        Job job2 = new Job("Java Developer");
        assertTrue(job1.equals(job2));
    }

**Test 13:** Comparing Jobs with Different Job Name

	 @Test
    void comparingJobs_differentJobName() {
        Job job1 = new Job("Java Developer");
        Job job2 = new Job("Python Developer");
        assertFalse(job1.equals(job2));
    }

### Validations Method Clone
**Test 14:** Ensure Cloned Job has Different Reference

	   @Test
    void differentRefForJob() {
        Job job1 = new Job("Java Developer");
        Job job2 = job1.clone();
        boolean sameRef = job1 == job2;
        assertFalse(sameRef);
    }


## 5. Construction (Implementation)

### Class RegisterJobController

```java

public Optional<Job> registerJob(String jobName) {
    Optional<Job> newJob;
    
    newJob = jobRepository.registerJob(jobName);
    return newJob;
}
```
