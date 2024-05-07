package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Optional;

public class CreateCollaboratorController {

    private CollaboratorRepository collaboratorRepository;

    public CreateCollaboratorController() {
        this.collaboratorRepository = new CollaboratorRepository();
    }

    private CollaboratorRepository getCollaboratorRepository() {
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();
            collaboratorRepository = repositories.getCollaboratorRepository();
        }
        return collaboratorRepository;
    }

    public Optional<Collaborator> registerCollaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, String docType, int jobID) {
        Optional<Collaborator> newCollaborator = Optional.empty();

        newCollaborator = collaboratorRepository.registerCollaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, jobID);
        return newCollaborator;
    }
}
