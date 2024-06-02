package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckUpTest {
Vehicle v1;
Data data;
Float kms;


    @BeforeEach
    void setUp() {
        v1 = new Vehicle("12-AB-34", "AUDI", "A1", 0, 120, 200, 2000, 500, 1655, new Data(2010, 4,20), new Data(2010, 4,20));
        data = new Data(2024, 4, 26);
        kms = 2100f;
    }

    @AfterEach
    void tearDown() {
    }

    //-----------------------Validations for Check-Up----------------------------------------

    @Test
    void ensureCheckUpNameIsValid_1() {
        CheckUp checkUp = new CheckUp(v1, kms, data);
    }

    @Test
    void ensureCheckUpIsNotNull() {
        //Act and Assert
        assertThrows(NullPointerException.class, () -> new CheckUp(null, null, null));
    }


    @Test
    void ensureCheckUpIsInvalid_1() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new CheckUp(v1, -1000f, data));
    }

    @Test
    void ensureCheckUpIsInvalid_2() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new CheckUp(v1, kms, new Data(2030, 4, 29)));
    }

    @Test
    void ensureCheckUpIsInvalid_3() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new CheckUp(v1, 1500f , data));
    }

    //---------------------------------------------------------------



    //---------------------------Validations for Method Equals------------------------
    @Test
    void comparingCheckUpSameRef() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        CheckUp ck2 = ck1;
        assertTrue(ck1.equals(ck2));
    }

    @Test
    void comparingCheckUpWithDifferentObjectInstance_1() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        Object object = new Object();
        assertFalse(ck1.equals(object));
    }

    @Test
    void comparingCheckUpWithDifferentObjectInstance_2() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        Skill skill = new Skill("Java Developer");
        assertFalse(ck1.equals(skill));
    }


    @Test
    void comparingCheckUp_sameCheckUp() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        CheckUp ck2 = new CheckUp(v1, kms, data);
        assertTrue(ck1.equals(ck2));
    }

    @Test
    void comparingCheckUps_differentCheckUp() {
        CheckUp ck1 = new CheckUp(v1, kms, data);
        CheckUp ck2 = new CheckUp(v1, 2000f, data);
        assertFalse(ck1.equals(ck2));
    }

    //------------------------Validations Method Clone---------------------------
    @Test
    void differentRefForJob() {
        CheckUp checkUp1 = new CheckUp(v1, kms, data);
        CheckUp checkUp2 = checkUp1.clone();
        boolean sameRef = checkUp2 == checkUp1;
        assertFalse(sameRef);
    }
}