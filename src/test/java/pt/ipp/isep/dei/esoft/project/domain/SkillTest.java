package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillTest  {

//-----------------------Validations for Skill Name----------------------------------------

    @Test
    void ensureSkillNameIsValid_1() {
        Skill skill = new Skill("Mecanico");
    }

    @Test
    void ensureSkillNameIsValid_2() {
        Skill skill = new Skill("Mecanico de carros");
    }

    @Test
    void ensureSkillNameIsNotNull() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill(null));
    }

    @Test
    void ensureSkillNameIsNotEmpty_1() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill(""));
    }

    @Test
    void ensureSkillNameIsNotEmpty_2() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill("  "));
    }

    @Test
    void ensureSkillNameIsInvalid_1() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill("Meca1nico1"));
    }

    @Test
    void ensureSkillNameIsInvalid_2() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Skill("Mecanico?"));
    }
//---------------------------------------------------------------


//--------------------------Validations when getting Skill Name--------------------------------
    @Test
    void shouldReturnSkillNameWithDiferentRef() {
        String name = "Mecanico";
        Skill skill = new Skill("name");
        boolean sameRef = skill.getSkillName() == name;
        assertFalse(sameRef);
    }


    @Test
    void shouldReturnSkillName() {
        Skill skill = new Skill("Java");
        assertEquals("Java", skill.getSkillName());
    }
//---------------------------------------------------------------


//---------------------------Validations for Method Equals------------------------
    @Test
    void comparingSkillsSameRef() {
        Skill skill1 = new Skill("Java");
        Skill skill2 = skill1;
        assertTrue(skill1.equals(skill2));
    }

    @Test
    void comparingSkillWithDiferentObjectInstance_1() {
        Skill skill1 = new Skill("Java");
        Object object = new Object();
        assertFalse(skill1.equals(object));
    }

    @Test
    void comparingSkillWithDiferentObjectInstance_2() {
        Skill skill1 = new Skill("Java");
        Job job = new Job("Java");
        assertFalse(skill1.equals(job));
    }


    @Test
    void comparingSkills_sameSkilName() {
        Skill skill1 = new Skill("Java");
        Skill skill2 = new Skill("Java");
        assertTrue(skill1.equals(skill2));
    }

    @Test
    void comparingSkills_diferentSillName() {
        Skill skill1 = new Skill("Java");
        Skill skill2 = new Skill("Python");
        assertFalse(skill1.equals(skill2));
    }

//------------------------Validations Method Clone---------------------------
    @Test
    void diferentRefForSkill() {
        Skill skill1 = new Skill("Java");
        Skill skill2 = skill1.clone();
        boolean sameRef = skill1 == skill2;
        assertFalse(sameRef);
    }

}