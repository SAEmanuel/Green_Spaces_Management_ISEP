package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CollaboratorRepository {

    private final List<Collaborator> collaboratorList;

    /**
     * Default constructor that initializes the CollaboratorRepository
     */
    public CollaboratorRepository() {
        this.collaboratorList = new ArrayList<>();
    }

    /**
     * Registers a new collaborator in the repository.
     *
     * @param name           of collaborator
     * @param birthDate      of collaborator
     * @param admissionDate  of collaborator
     * @param address        of collaborator
     * @param phoneNumber    of collaborator
     * @param emailAddress   of collaborator
     * @param taxPayerNumber of collaborator
     * @param docType        of collaborator
     * @param job            of collaborator
     * @return An Optional containing the registered collaborator if successful, or empty otherwise.
     */
    public Optional<Collaborator> registerCollaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, int docType, String docNumber, Job job) {

        Optional<Collaborator> optionalValue = Optional.empty();
        Collaborator collaborator = new Collaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, docNumber, job);
        if (addCollaborator(collaborator)) {
            optionalValue = Optional.of(collaborator);
        }
        return optionalValue;
    }

    /**
     * Adds a collaborator to the repository if it's valid.
     *
     * @param collaborator the collaborator to add
     * @return True if the collaborator is added successfully, false otherwise.
     */
    private boolean addCollaborator(Collaborator collaborator) {
        boolean success = false;
        if (validateCollaborator(collaborator)) {
            success = collaboratorList.add(collaborator.clone());
        }
        return success;
    }

    /**
     * Validates the collaborator
     *
     * @param collaborator the collaborator to add
     * @return True if the collaborator is valid, false otherwise
     */
    private boolean validateCollaborator(Collaborator collaborator) {
        return collaboratorListDoNotContains(collaborator);
    }

    /**
     * Verifies if collaborator is in the collaboratorList by taxpayer number
     *
     * @param collaborator the collaborator to search
     * @return true if collaborator is not in the collaboratorList, false otherwise
     */
    private boolean collaboratorListDoNotContains(Collaborator collaborator) {
        for (Collaborator v : collaboratorList) {
            if (v.equals(collaborator)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the collaboratorList
     *
     * @return collaboratorList
     */
    public List<Collaborator> getCollaboratorList() {
        return collaboratorList;
    }

    /**
     * Finds the collaborator by taxpayer number
     *
     * @param collaboratorTaxNumber of the collaborator
     * @return the collaborator if found, null otherwise
     */
    public Collaborator findCollaborator(int collaboratorTaxNumber) {
        for (Collaborator collaborator : collaboratorList) {
            if (collaborator.getTaxPayerNumber() == collaboratorTaxNumber) {
                return collaborator;
            }
        }
        return null;
    }

    /**
     * Assigns a skill to a collaborator
     *
     * @param collaboratorTaxNumber of the collaborator
     * @param skill                 to be assigned
     * @return true if assigned successfully, false otherwise
     */
    public boolean assignSkillCollaborator(int collaboratorTaxNumber, Skill skill) {
        Collaborator collaborator = findCollaborator(collaboratorTaxNumber);
        return collaborator.addSkill(skill);
    }

    public void add(Collaborator collaborator) {
        collaboratorList.add(collaborator);
    }

    /**
     * Retrieves an array containing all available document types for collaborators.
     *
     * @return An array of Collaborator.DocType values representing the available document types.
     */
    public Collaborator.DocType[] getDocType() {
        return Collaborator.DocType.values();
    }

}
