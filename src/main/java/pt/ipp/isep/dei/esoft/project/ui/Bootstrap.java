package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.Serialization;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.*;
import pt.ipp.isep.dei.esoft.project.repository.*;

public class Bootstrap implements Runnable {

    private final Job job1 = new Job("Marketing Coordinator");
    private final Job job2 = new Job("President of Sales");
    private final Job job3 = new Job("Nursing Assistant");
    private final Job job4 = new Job("Medical Assistant");
    private final Job job5 = new Job("Account Executive");
    private final Job job6 = new Job("Project Manager");
    private final Job job7 = new Job("Pasteleira");

    private final Skill skill1 = new Skill("Irrigation Systems Management");
    private final Skill skill2 = new Skill("Pest Control and Management");
    private final Skill skill3 = new Skill("Tree Care and Maintenance");
    private final Skill skill4 = new Skill("Garden Tool Maintenance");
    private final Skill skill5 = new Skill("Greenhouse Management");
    private final Skill skill6 = new Skill("Plant Identification");
    private final Skill skill7 = new Skill("Floral Arrangements");
    private final Skill skill8 = new Skill("Pruning Techniques");
    private final Skill skill9 = new Skill("Seasonal Planting");
    private final Skill skill10 = new Skill("Soil Analysis");
    private final Skill skill11 = new Skill("Weed Control");
    private final Skill skill12 = new Skill("Lawn Care");

//    private final GreenSpace infanteDomHenrique = new GreenSpace("Infante Dom Henrique",0,100,"Porto");
//    private final GreenSpace parqueDaCidade = new GreenSpace("Parque da Cidade",2,100,"Matosinhos");
//    private final GreenSpace palacioDeCristal = new GreenSpace("Palacio de Cristal",1,40,"Porto");
//    private final GreenSpace quintaDoCovelo = new GreenSpace("Quinta do Covelo",0,100,"Porto");
//    private final GreenSpace passeioAlegre = new GreenSpace("Passeio Alegre",0,11,"Porto");

    private final Data data1 = new Data(2023, 1, 1);
    private final Data data2 = new Data(2021, 1, 1);


    private final Vehicle vehicle1 = new Vehicle("AA-00-AA", "Toyota", "Camnry", 0, 1000, 2000, 10000, 2000, 9005, data2, data1);
    private final Vehicle vehicle2 = new Vehicle("BB-00-AA", "Toyota", "Tundra", 0, 5000, 7000, 10000, 2000, 8000, data2, data1);
    private final Vehicle vehicle3 = new Vehicle("CC-00-AA", "Toyota", "Yaris", 0, 500, 1000, 10000, 2000, 4000, data2, data1);

    //Add some task categories to the repository as bootstrap
    public void run() {
        inputAppInformation();
        addUsers();
        addJobs();
//        addVehicles();
        addCollaborator();
        addSkills();
        assingSkills();
    }

    protected static void inputAppInformation() {
            Serialization serialization = new Serialization();
            serialization.serializeSkillInput();
            serialization.serializeVehicleInput();




    }


    private void addVehicles() {
        VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();
        vehicleRepository.add(vehicle1);
        vehicleRepository.add(vehicle2);
        vehicleRepository.add(vehicle3);
    }


    private void addCollaborator() {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        collaboratorRepository.add(new Collaborator("Francisco", new Data(2004, 5, 20), new Data(2023, 6, 12), "Rua da pedra", 912809789, "fran@gmail.com", 123456744, 0, "123456789", job1));
        collaboratorRepository.add(new Collaborator("Emanuel", new Data(2004, 4, 20), new Data(2023, 6, 12), "Rua da Mariana", 912809777, "ema@gmail.com", 123456755, 0, "123456789", job2));
        collaboratorRepository.add(new Collaborator("Paulo", new Data(2002, 8, 20), new Data(2023, 6, 12), "Rua de Fanzeres", 912809888, "paul@gmail.com", 123456766, 0, "123456789", job3));
        collaboratorRepository.add(new Collaborator("Xu", new Data(2000, 4, 20), new Data(2023, 6, 12), "Rua do restaurante asiatico", 912809666, "xu@gmail.com", 123456777, 0, "123456789", job4));
        collaboratorRepository.add(new Collaborator("Jorge", new Data(2004, 5, 31), new Data(2023, 6, 12), "Rua enganhairo do isep", 912809555, "jorge@gmail.com", 123456788, 0, "123456789", job5));
        collaboratorRepository.add(new Collaborator("Mariana Silva", new Data(2001, 5, 31), new Data(2023, 6, 12), "Rua da igreja", 912809551, "mari@gmail.com", 123456799, 0, "123456789", job6));
        collaboratorRepository.add(new Collaborator("Gorete", new Data(1993, 5, 31), new Data(2023, 6, 12), "Rua de Fanzeres", 912809552, "gori@gmail.com", 123456999, 0, "123456789", job7));
    }

    private void assingSkills(){
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        collaboratorRepository.assignSkillCollaborator(123456744,skill1);
        collaboratorRepository.assignSkillCollaborator(123456755,skill1);
        collaboratorRepository.assignSkillCollaborator(123456766,skill1);
        collaboratorRepository.assignSkillCollaborator(123456777,skill1);
    }

    private void addJobs() {
        JobRepository jobRepository = Repositories.getInstance().getJobRepository();
        jobRepository.add(job1);
        jobRepository.add(job2);
        jobRepository.add(job3);
        jobRepository.add(job4);
        jobRepository.add(job5);
        jobRepository.add(job6);
        jobRepository.add(job7);
    }

    private void addSkills() {
        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
        skillRepository.add(skill1);
        skillRepository.add(skill2);
        skillRepository.add(skill3);
        skillRepository.add(skill4);
        skillRepository.add(skill5);
        skillRepository.add(skill6);
        skillRepository.add(skill7);
        skillRepository.add(skill8);
        skillRepository.add(skill9);
        skillRepository.add(skill10);
        skillRepository.add(skill11);
        skillRepository.add(skill12);
    }


    private void addUsers() {
        //TODO: add Authentication users here: should be created for each user in the organization
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE, AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_HRM, AuthenticationController.ROLE_HRM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_VFM, AuthenticationController.ROLE_VFM);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_GSM, AuthenticationController.ROLE_GSM);

        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin",
                AuthenticationController.ROLE_ADMIN);

        authenticationRepository.addUserWithRole("Employee", "employee@this.app", "pwd",
                AuthenticationController.ROLE_EMPLOYEE);

        authenticationRepository.addUserWithRole("Human Resource Manager", "hrm@this.app", "hrm",
                AuthenticationController.ROLE_HRM);

        authenticationRepository.addUserWithRole("Vehicle Fleet Manager", "vfm@this.app", "vfm",
                AuthenticationController.ROLE_VFM);

        authenticationRepository.addUserWithRole("Green Spaces Manager", "gsm@this.app", "gsm",
                AuthenticationController.ROLE_GSM);
    }
}