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

    public CreateCollaboratorController() {
        this.collaboratorRepository = new CollaboratorRepository();
        this.jobRepository = new JobRepository();
    }

    private JobRepository getJobRepository() {
        if (this.jobRepository == null){
            Repositories repositories = Repositories.getInstance();
            jobRepository = repositories.getJobRepository();
        }
        return jobRepository;
    }

    private CollaboratorRepository getCollaboratorRepository() {
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();
            collaboratorRepository = repositories.getCollaboratorRepository();
        }
        return collaboratorRepository;
    }

    public CollaboratorRepository getCollaboratorRepository2() {
        return collaboratorRepository;
    }

    public Optional<Collaborator> registerCollaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, String docType, Job job) {
        Optional<Collaborator> newCollaborator = Optional.empty();

        newCollaborator = collaboratorRepository.registerCollaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, job);
        return newCollaborator;
    }
}
