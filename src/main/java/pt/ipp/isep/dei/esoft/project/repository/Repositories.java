package pt.ipp.isep.dei.esoft.project.repository;

public class Repositories {

    private static Repositories instance;
    private final OrganizationRepository organizationRepository;
    private final TaskCategoryRepository taskCategoryRepository;
    private final AuthenticationRepository authenticationRepository;
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final VehicleRepository vehicleRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final TeamRepository teamRepository;

    private Repositories() {
        organizationRepository = new OrganizationRepository();
        taskCategoryRepository = new TaskCategoryRepository();
        authenticationRepository = new AuthenticationRepository();
        jobRepository = new JobRepository();
        skillRepository = new SkillRepository();
        vehicleRepository = new VehicleRepository();
        collaboratorRepository = new CollaboratorRepository();
        teamRepository = new TeamRepository();
    }

    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
    }

    public OrganizationRepository getOrganizationRepository() {
        return organizationRepository;
    }

    public TaskCategoryRepository getTaskCategoryRepository() {
        return taskCategoryRepository;
    }

    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }

    public JobRepository getJobRepository() {
        return jobRepository;
    }

    public SkillRepository getSkillRepository(){
        return skillRepository;
    }

    public VehicleRepository getVehicleRepository() {
        return vehicleRepository;
    }

    public CollaboratorRepository getCollaboratorRepository() { return collaboratorRepository; }

    public TeamRepository getTeamRepository() { return teamRepository; }
}