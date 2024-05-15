package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.JobRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Optional;

public class CreateCollaboratorController {
    private CollaboratorRepository collaboratorRepository;
    private JobRepository jobRepository;

    /**
     * Default constructor that initializes the CreateCollaboratorController and RegisterJobController
     */
    public CreateCollaboratorController() {
        this.collaboratorRepository = getCollaboratorRepository();
        this.jobRepository = getJobRepository();
    }

    /**
     * Gets the JobRepository instance
     * @return JobRepository instance
     */
    private JobRepository getJobRepository() {
        if (this.jobRepository == null){
            Repositories repositories = Repositories.getInstance();
            jobRepository = repositories.getJobRepository();
        }
        return jobRepository;
    }

    /**
     * Gets the CollaboratorRepository instance
     * @return CollaboratorRepository instance
     */
    public CollaboratorRepository getCollaboratorRepository() {
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();
            collaboratorRepository = repositories.getCollaboratorRepository();
        }
        return collaboratorRepository;
    }

    /**
     * Gets the CollaboratorRepository instance
     * @return CollaboratorRepository instance
     */
    public CollaboratorRepository getCollaboratorRepository2() {
        return collaboratorRepository;
    }

    /**
     * Register a collaborator with the given parameters
     * @param name of the collaborator
     * @param birthDate of the collaborator
     * @param admissionDate of the collaborator
     * @param address of the collaborator
     * @param phoneNumber of the collaborator
     * @param emailAddress of the collaborator
     * @param taxPayerNumber of the collaborator
     * @param docType of the collaborator
     * @param job of the collaborator
     * @return an optional containing the registered collaborator, or empty if registration failed
     */
    public Optional<Collaborator> registerCollaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, int docType, Job job) {
        Optional<Collaborator> newCollaborator = Optional.empty();

        newCollaborator = collaboratorRepository.registerCollaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, job);
        return newCollaborator;
    }
    public Collaborator.DocType[] getDocType() {
        return collaboratorRepository.getDocType();
    }

}
