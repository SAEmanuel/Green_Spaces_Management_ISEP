package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.SerializationOutput;
import pt.ipp.isep.dei.esoft.project.application.SerializationRead;
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

//    private final Skill skill1 = new Skill("Condutor de ligeiros");
//    private final Skill skill2 = new Skill("Condutor de pesados");
//    private final Skill skill3 = new Skill("Fritador de batatas");
//    private final Skill skill4 = new Skill("Programador em Python");
//    private final Skill skill5 = new Skill("Programador em Java");
//    private final Skill skill6 = new Skill("Data Analysis");
//    private final Skill skill7 = new Skill("Cozinheiro");


    private final GreenSpace parqueDaCidade = new GreenSpace("Parque da Cidade",2,100,"Matosinhos");
    private final GreenSpace palacioDeCristal = new GreenSpace("Palacio de Cristal",1,40,"Porto");
    private final GreenSpace passeioAlegre = new GreenSpace("Passeio Alegre",0,11,"Porto");
    private final GreenSpace quintaDoCovelo = new GreenSpace("Quinta do Covelo",0,100,"Porto");
    private final GreenSpace infanteDomHenrique = new GreenSpace("Infante Dom Henrique",0,100,"Porto");


    //Add some task categories to the repository as bootstrap
    public void run() {
        readAppInformation();
        addTaskCategories();
        addOrganization();
        addUsers();
        addJobs();
        addGreenSpaces();
//        addSkills();
//        addCollaborator();
    }

    protected static void readAppInformation(){
        SerializationRead serializationRead = new SerializationRead();
        serializationRead.serializeSkill();

    }








    private void addGreenSpaces(){
        GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();
        greenSpaceRepository.add(parqueDaCidade);
        greenSpaceRepository.add(palacioDeCristal);
        greenSpaceRepository.add(passeioAlegre);
        greenSpaceRepository.add(quintaDoCovelo);
        greenSpaceRepository.add(infanteDomHenrique);
    }

//    private void addCollaborator() {
//        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
//        collaboratorRepository.add(new Collaborator("Francisco", new Data(2004, 5, 20), new Data(2023, 6, 12), "Rua da pedra", 912809789, "fran@gmail.com", 123456744, 1, "123456789", job1));
//        collaboratorRepository.add(new Collaborator("Emanuel", new Data(2004, 4, 20), new Data(2023, 6, 12), "Rua da Mariana", 912809777, "ema@gmail.com", 123456755, 1, "123456789", job2));
//        collaboratorRepository.add(new Collaborator("Paulo", new Data(2002, 8, 20), new Data(2023, 6, 12), "Rua de Fanzeres", 912809888, "paul@gmail.com", 123456766, 1, "123456789", job3));
//        collaboratorRepository.add(new Collaborator("Xu", new Data(2000, 4, 20), new Data(2023, 6, 12), "Rua do restaurante asiatico", 912809666, "xu@gmail.com", 123456777, 1, "123456789", job4));
//        collaboratorRepository.add(new Collaborator("Jorge", new Data(2004, 5, 31), new Data(2023, 6, 12), "Rua enganhairo do isep", 912809555, "jorge@gmail.com", 123456788, 1, "123456789", job5));
//        collaboratorRepository.add(new Collaborator("Mariana Silva", new Data(2001, 5, 31), new Data(2023, 6, 12), "Rua da igreja", 912809551, "mari@gmail.com", 123456799, 1, "123456789", job6));
//        collaboratorRepository.add(new Collaborator("Gorete", new Data(1993, 5, 31), new Data(2023, 6, 12), "Rua de Fanzeres", 912809552, "gori@gmail.com", 123456999, 1, "123456789", job7));

//    }

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

//    private void addSkills() {
//        SkillRepository skillRepository = Repositories.getInstance().getSkillRepository();
//        skillRepository.add(skill1);
//        skillRepository.add(skill2);
//        skillRepository.add(skill3);
//        skillRepository.add(skill4);
//        skillRepository.add(skill5);
//        skillRepository.add(skill6);
//        skillRepository.add(skill7);
//    }


    private void addOrganization() {
        //TODO: add organizations bootstrap here
        //get organization repository
        OrganizationRepository organizationRepository = Repositories.getInstance().getOrganizationRepository();
        Organization organization = new Organization("This Company");
        organization.addEmployee(new Employee("admin@this.app"));
        organization.addEmployee(new Employee("employee@this.app"));
        organizationRepository.add(organization);
    }

    private void addTaskCategories() {
        //TODO: add bootstrap Task Categories here

        //get task category repository
        TaskCategoryRepository taskCategoryRepository = Repositories.getInstance().getTaskCategoryRepository();
        taskCategoryRepository.add(new TaskCategory("Analysis"));
        taskCategoryRepository.add(new TaskCategory("Design"));
        taskCategoryRepository.add(new TaskCategory("Implementation"));
        taskCategoryRepository.add(new TaskCategory("Development"));
        taskCategoryRepository.add(new TaskCategory("Testing"));
        taskCategoryRepository.add(new TaskCategory("Deployment"));
        taskCategoryRepository.add(new TaskCategory("Maintenance"));
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