# US003 - Register a collaborator

## 4. Tests 

CollaboratorTest

**Test 1:** Check if the collaborator to create is valid

	@Test
    public void testValidCollaboratorCreation() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");
        Collaborator collaborator = new Collaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.pt", 123456789, "Passport", job);
        assertNotNull(collaborator);
    }
	

**Test 2:** Check if the name is valid

	@Test
    public void testIsValidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Data birthDate = new Data(2000, 7, 26);
            Data admissionDate = new Data(2019, 6, 15);
            Job job = new Job("Student");
            new Collaborator(null, birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.pt", 123456789, "Passport", job);
        });
    }

**Test 3:** Check if the phone number is valid

	@Test
    public void testIsValidPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            Data birthDate = new Data(2000, 7, 26);
            Data admissionDate = new Data(2019, 6, 15);
            Job job = new Job("Student");
            new Collaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 999999, "Romeu@.pt", 123456789, "Passport", job);
        });
    }

**Test 4:** Check if the email address is valid

    @Test
    public void testIsValidEmailAddress() {
        assertThrows(IllegalArgumentException.class, () -> {
            Data birthDate = new Data(2000, 7, 26);
            Data admissionDate = new Data(2019, 6, 15);
            Job job = new Job("Student");
            new Collaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "", 123456789, "Passport", job);
        });
    }

**Test 5:** Check if the taxpayer number is valid

    @Test
    public void testIsValidTaxPayerNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            Data birthDate = new Data(2000, 7, 26);
            Data admissionDate = new Data(2019, 6, 15);
            Job job = new Job("Student");
            new Collaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.pt", 123, "Passport", job);
        });
    }

**Test 6:** Check if the skill was added to collaborator

    @Test
    public void testAddSkill() {
        Skill javaSkill = new Skill("drive");
        Collaborator collaborator = new Collaborator("Romeu", new Data(2000, 7, 26), new Data(2019, 6, 15), "Rua da fonte", 912345678, "Romeu@.pt", 123456789, "Passport", new Job("Student"));
        assertTrue(collaborator.addSkill(javaSkill));
    }

**Test 7:** Check if the collaborator was cloned successfully

    @Test
    public void testClone() {
        Collaborator original = new Collaborator("Romeu", new Data(2000, 7, 26), new Data(2019, 6, 15), "Rua da fonte", 912345678, "Romeu@.pt", 123456789, "Passport", new Job("Student"));
        Collaborator clone = original.clone();
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getBirthDate(), clone.getBirthDate());
        assertEquals(original.getAdmissionDate(), clone.getAdmissionDate());
        assertEquals(original.getAddress(), clone.getAddress());
        assertEquals(original.getPhoneNumber(), clone.getPhoneNumber());
        assertEquals(original.getEmailAddress(), clone.getEmailAddress());
        assertEquals(original.getTaxPayerNumber(), clone.getTaxPayerNumber());
        assertEquals(original.getDocType(), clone.getDocType());
        assertEquals(original.getJob(), clone.getJob());
        assertEquals(original.cloneList(), clone.cloneList());
    }


CollaboratorRepositoryTest

**Test 1:** Check if the collaborator was registered

    @Test
    public void testRegisterCollaborator() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");

        Optional<Collaborator> result = collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        assertTrue(result.isPresent());
    }

**Test 2:** Check if a duplicate collaborator can be added

    @Test
    public void testRegisterDuplicateCollaborator() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");

        collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        Optional<Collaborator> result = collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        assertFalse(result.isPresent());
    }

**Test 3:** Check if can find a collaborator through taxpayer number

    @Test
    public void testFindCollaborator() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");

        collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        Collaborator result = collaboratorRepository.findCollaborator(123456789);
        assertNotNull(result);
    }

**Test 4:** Check if skill is assigned to a collaborator

    @Test
    public void testAssignSkillCollaborator() {
        Data birthDate = new Data(2000, 7, 26);
        Data admissionDate = new Data(2019, 6, 15);
        Job job = new Job("Student");
        Skill javaSkill = new Skill("drive");

        collaboratorRepository.registerCollaborator("Romeu", birthDate, admissionDate, "Rua da fonte", 912345678, "Romeu@.com", 123456789, "Passport", job);
        boolean result = collaboratorRepository.assignSkillCollaborator(123456789, javaSkill);
        assertTrue(result);
    }

_It is also recommended to organize this content by subsections._ 


## 5. Construction (Implementation)

### Class CreateTaskController 

```java
public Task createTask(String reference, String description, String informalDescription, String technicalDescription,
                       Integer duration, Double cost, String taskCategoryDescription) {

	TaskCategory taskCategory = getTaskCategoryByDescription(taskCategoryDescription);

	Employee employee = getEmployeeFromSession();
	Organization organization = getOrganizationRepository().getOrganizationByEmployee(employee);

	newTask = organization.createTask(reference, description, informalDescription, technicalDescription, duration,
                                      cost,taskCategory, employee);
    
	return newTask;
}
```

### Class Organization

```java
public Optional<Task> createTask(String reference, String description, String informalDescription,
                                 String technicalDescription, Integer duration, Double cost, TaskCategory taskCategory,
                                 Employee employee) {
    
    Task task = new Task(reference, description, informalDescription, technicalDescription, duration, cost,
                         taskCategory, employee);

    addTask(task);
        
    return task;
}
```


## 6. Integration and Demo 

* For demo purposes some tasks are bootstrapped while system starts.


## 7. Observations

n/a