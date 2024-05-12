# US001 - Registering Skills


## 4. Tests 
###
#### ------------------------------- Validations for Skill Name --------------------------------------

**Test 1:** Check that Skill name is a valid one.

    @Test
    void ensureSkillNameIsValid_1() {
        Skill skill = new Skill("Mecanico");
    }

**Test 2:** Check that Skill name is valid (with blank spaces).

    @Test
    void ensureSkillNameIsValid_2() {
        Skill skill = new Skill("Mecanico de carros");
    }

**Test 3:** Check that it is not possible to create an instance of the Skill class with null name.

    @Test
    void ensureSkillNameIsNotNull() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill(null));
    }

**Test 4:** Check that the name of the skill is nothing.

    @Test
    void ensureSkillNameIsNotEmpty_1() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill(""));
    }

**Test 5:** Check that the name of the skill is not only blank spaces.

    @Test
    void ensureSkillNameIsNotEmpty_2() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill("  "));
    }
**Test 6:** Check that the name of the skill have numbers.

    @Test
    void ensureSkillNameIsInvalid_1() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill("Meca1nico1"));
    }
**Test 7:** Check that the name of the skill have special characters.

    @Test
    void ensureSkillNameIsInvalid_2() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill("Mecanico?"));
    }

###	
#### --------------------------- Validations when getting Skill Name ----------------------------
###
**Test 8:** Checks if the skill name don't have same reference as the name passed when the Skill instance was created.

	    @Test
    void shouldReturnSkillNameWithDiferentRef() {
        String name = "Mecanico";
        Skill skill = new Skill("name");
        boolean sameRef = skill.getSkillName() == name;
        assertFalse(sameRef);
    }

**Test 9:** Checks if the skill name is equals to other one.

    @Test
    void shouldReturnSkillName() {
        Skill skill = new Skill("Java");
        assertEquals("Java", skill.getSkillName());
    }

###	
#### --------------------------- Validations for Method Equals ----------------------------
###
**Test 10:** Compare Skills names references.
    @Test
    void comparingSkillsSameRef() {
        Skill skill1 = new Skill("Java");
        Skill skill2 = skill1;
        assertTrue(skill1.equals(skill2));
    }
**Test 11:** Compare an instance of class Skill with a different class object.

    @Test
    void comparingSkillWithDiferentObjectInstance_1() {
        Skill skill1 = new Skill("Java");
        Object object = new Object();
        assertFalse(skill1.equals(object));
    }
**Test 12:** Compare an instance of class Skill with a different class object.

    @Test
    void comparingSkillWithDiferentObjectInstance_2() {
        Skill skill1 = new Skill("Java");
        Job job = new Job("Java");
        assertFalse(skill1.equals(job));
    }

**Test 13:** Compare Skill objects with same name.
    
    @Test
    void comparingSkills_sameSkilName() {
        Skill skill1 = new Skill("Java");
        Skill skill2 = new Skill("Java");
        assertTrue(skill1.equals(skill2));
    }

**Test 14:** Compare Skill objects with different name.

    @Test
    void comparingSkills_diferentSillName() {
        Skill skill1 = new Skill("Java");
        Skill skill2 = new Skill("Python");
        assertFalse(skill1.equals(skill2));
    }

###	
#### --------------------------- Validations Method Clone ----------------------------
###

**Test 15:** Compare references of Skill objects, when using method clone.

    @Test
    void diferentRefForSkill() {
        Skill skill1 = new Skill("Java");
        Skill skill2 = skill1.clone();
        boolean sameRef = skill1 == skill2;
        assertFalse(sameRef);
    }





## 5. Construction (Implementation)

### Class RegisterSkillController 



```java
    public Optional<Skill> registerSkill(String skillName) {

        Optional<Skill> newSkill;

        newSkill = skillRepository.registerSkill(skillName);

        return newSkill;
    }
```




## 6. Integration and Demo 

* None

## 7. Observations

n/a