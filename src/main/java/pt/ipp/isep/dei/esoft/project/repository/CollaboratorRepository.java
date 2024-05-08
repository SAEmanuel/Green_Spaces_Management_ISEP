package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollaboratorRepository {

    private final List<Collaborator> collaboratorList;

    public CollaboratorRepository() {
        this.collaboratorList = new ArrayList<>();
    }

    public Optional<Collaborator> registerCollaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, String docType, Job job) {

        Optional<Collaborator> optionalValue = Optional.empty();

        Collaborator collaborator = new Collaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, job);
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
        return collaboratorListDoNotContainsByTaxNum(collaborator);
    }

    private boolean collaboratorListDoNotContainsByTaxNum(Collaborator collaborator) {
        for (Collaborator v : collaboratorList) {
            if (v.getTaxPayerNumber() == collaborator.getTaxPayerNumber()) {
                return false;
            }
        }
        return true;
    }

    public List<Collaborator> clone() {
        return new ArrayList<>(this.collaboratorList);
    }

    public List<Collaborator> getCollaboratorList() {
        return clone();
    }

    public boolean findCollaboratorByTaxNumber(int collaboratorTaxNumber) {
        return containsCollaboratorByTaxNumber(collaboratorTaxNumber);
    }

    private boolean containsCollaboratorByTaxNumber(int collaboratorTaxNumber) {
        for (Collaborator collaborator : collaboratorList) {
            if (collaborator.getTaxPayerNumber() == collaboratorTaxNumber) {
                return true;
            }
        }
        return false;
    }

    public Collaborator findCollaborator(int collaboratorTaxNumber) {
        for (Collaborator collaborator : collaboratorList) {
            if (collaborator.getTaxPayerNumber() == collaboratorTaxNumber) {
                return collaborator;
            }
        }
        return null;
    }

    public boolean assignSkillCollaborator(int collaboratorTaxNumber, Skill skill) {

        Collaborator collaborator = findCollaborator(collaboratorTaxNumber);

        return collaborator.addSkill(skill);

    }
}
