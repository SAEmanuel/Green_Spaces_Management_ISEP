package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollaboratorRepository {

    private final List<Collaborator> collaboratorList;

    public CollaboratorRepository() {
        this.collaboratorList = new ArrayList<>();
    }

    public Optional<Collaborator> registerCollaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, String docType, int jobID) {

        Optional<Collaborator> optionalValue = Optional.empty();

        Collaborator collaborator = new Collaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, jobID);

        if (addCollaborator(collaborator)) {
            optionalValue = Optional.of(collaborator);
        }
        return optionalValue;
    }

    private boolean addCollaborator(Collaborator collaborator) {
        boolean success = false;
        if (validateCollaborator(collaborator)) {
            // A clone of the job is added to the list of jobs, to avoid side effects and outside manipulation.
            success = collaboratorList.add(collaborator.clone());
        }
        return success;
    }

    private boolean validateCollaborator(Collaborator collaborator) {
        return doNotContainCollaborator(collaborator);
    }

    private boolean doNotContainCollaborator(Collaborator collaborator) {
        return !collaboratorList.contains(collaborator);
    }
}
