package pt.ipp.isep.dei.esoft.project.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoriesTest {

    @Test
    void testGetInstance() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance);
    }


    @Test
    void getAuthenticationRepository() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance.getAuthenticationRepository());
    }

    @Test
    void getJobRepository() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance.getJobRepository());
    }

    @Test
    void getSkillRepository() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance.getSkillRepository());
    }

    @Test
    void getVehicleRepository() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance.getVehicleRepository());
    }

    @Test
    void getCollaboratorRepository() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance.getCollaboratorRepository());
    }

    @Test
    void getTeamRepository() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance.getTeamRepository());
    }

    @Test
    void getVehicleCheckUpRepository() {
        Repositories instance = Repositories.getInstance();
        assertNotNull(instance.getVehicleCheckUpRepository());
    }


}