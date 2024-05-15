package pt.ipp.isep.dei.esoft.project.ui;

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
    private final Job job7 = new Job("Web Designer");

    private final Skill skill1 = new Skill("Condutor de ligeiros");
    private final Skill skill2 = new Skill("Condutor de pesados");
    private final Skill skill3 = new Skill("Fritador de batatas");
    private final Skill skill4 = new Skill("Programador em Python");
    private final Skill skill5 = new Skill("Programador em Java");
    private final Skill skill6 = new Skill("Data Analysis");
    private final Skill skill7 = new Skill("Cozinheiro");



    //Add some task categories to the repository as bootstrap
    public void run() {
        addTaskCategories();
        addOrganization();
        addUsers();
        addSkills();
        addJobs();
        addCollaborator();
    }

    private void addCollaborator() {
        CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();
        collaboratorRepository.add(new Collaborator("Francisco",new Data(2004,5,20),new Data(2023,6,12),"Rua da pedra",912809789,"fran@gmail.com",123456744,1,job1));
        collaboratorRepository.add(new Collaborator("Emanuel",new Data(2004,4,20),new Data(2023,6,12),"Rua da Mariana",912809777,"ema@gmail.com",123456755,1,job2));
        collaboratorRepository.add(new Collaborator("Paulo",new Data(2002,8,20),new Data(2023,6,12),"Rua de Fanzeres",912809888,"paul@gmail.com",123456766,1,job3));
        collaboratorRepository.add(new Collaborator("Xu",new Data(2000,4,20),new Data(2023,6,12),"Rua do restaurante asiatico",912809666,"xu@gmail.com",123456777,1,job4));
        collaboratorRepository.add(new Collaborator("Jorge",new Data(2004,5,31),new Data(2023,6,12),"Rua enganhairo do isep",912809555,"jorge@gmail.com",123456788,1,job5));
        collaboratorRepository.add(new Collaborator("Mariana Silva",new Data(2001,5,31),new Data(2023,6,12),"Rua da igreja",912809551,"mari@gmail.com",123456799,1,job6));
        collaboratorRepository.add(new Collaborator("Gorete",new Data(1993,5,31),new Data(2023,6,12),"Rua de Fanzeres",912809552,"gori@gmail.com",123456999,1,job7));

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

    }


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

        authenticationRepository.addUserWithRole("Main Administrator", "admin@this.app", "admin",
                AuthenticationController.ROLE_ADMIN);

        authenticationRepository.addUserWithRole("Employee", "employee@this.app", "pwd",
                AuthenticationController.ROLE_EMPLOYEE);

        authenticationRepository.addUserWithRole("Human Resource Manager", "hrm@this.app", "hrm",
                AuthenticationController.ROLE_HRM);

        authenticationRepository.addUserWithRole("VEHICLE FLEET MANAGER", "vfm@this.app", "vfm",
                AuthenticationController.ROLE_VFM);
    }
}