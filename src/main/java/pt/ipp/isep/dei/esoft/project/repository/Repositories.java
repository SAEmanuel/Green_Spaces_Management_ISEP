package pt.ipp.isep.dei.esoft.project.repository;

public class Repositories {

    private static Repositories instance;
    private final AuthenticationRepository authenticationRepository;
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final VehicleRepository vehicleRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final TeamRepository teamRepository;
    private final VehicleCheckUpRepository vehicleCheckUpRepository;
    private final GreenSpaceRepository greenSpaceRepository;
    private final ToDoListRepository toDoListRepository;

    private Repositories() {
        authenticationRepository = new AuthenticationRepository();
        jobRepository = new JobRepository();
        skillRepository = new SkillRepository();
        vehicleRepository = new VehicleRepository();
        collaboratorRepository = new CollaboratorRepository();
        teamRepository = new TeamRepository();
        vehicleCheckUpRepository = new VehicleCheckUpRepository();
        greenSpaceRepository = new GreenSpaceRepository();
        toDoListRepository = new ToDoListRepository();
    }

    public static Repositories getInstance() {
        if (instance == null) {
            synchronized (Repositories.class) {
                instance = new Repositories();
            }
        }
        return instance;
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

    public VehicleCheckUpRepository getVehicleCheckUpRepository() {return vehicleCheckUpRepository; }

    public GreenSpaceRepository getGreenSpaceRepository() {return greenSpaceRepository; }

    public ToDoListRepository getToDoRepository() { return toDoListRepository;
    }
}