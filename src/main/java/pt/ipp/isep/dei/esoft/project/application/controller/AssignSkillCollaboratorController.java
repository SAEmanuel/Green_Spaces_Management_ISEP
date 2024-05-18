package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.List;

/**
 * The AssignSkillCollaboratorController class manages the assignment of skills to collaborators
 * in the project management system.
 */
public class AssignSkillCollaboratorController {

    // Attributes
    private SkillRepository skillRepository;
    private CollaboratorRepository collaboratorRepository;

    /**
     * Default constructor that initializes the AssignSkillCollaboratorController.
     */
    public AssignSkillCollaboratorController() {
        this.skillRepository = getSkillRepository();
        this.collaboratorRepository = getCollaboratorRepository();
    }

    /**
     * Retrieves the SkillRepository instance.
     *
     * @return The SkillRepository instance.
     */
    private SkillRepository getSkillRepository() {
        if (skillRepository == null) {
            skillRepository = getRepositories().getSkillRepository();
        }
        return skillRepository;
    }

    /**
     * Retrieves the CollaboratorRepository instance.
     * @return The CollaboratorRepository instance.
     */
    private CollaboratorRepository getCollaboratorRepository() {
        if (collaboratorRepository == null) {
            collaboratorRepository = getRepositories().getCollaboratorRepository();
        }
        return collaboratorRepository;
    }

    /**
     * Retrieves the Repositories singleton instance.
     *
     * @return The Repositories instance.
     */
    private Repositories getRepositories() {
        return Repositories.getInstance();
    }

    /**
     * Checks if the collaborator list is empty.
     *
     * @return True if the collaborator list is empty, false otherwise.
     */
    public boolean isCollaboratorListEmpty(){
        return collaboratorRepository.getCollaboratorList().isEmpty();
    }

    /**
     * Retrieves the list of skills.
     *
     * @return The list of skills.
     */
    public List<Skill> getSkillList() {
        return skillRepository.getSkillList();
    }

    /**
     * Retrieves the list of collaborators.
     *
     * @return The list of collaborators.
     */
    public List<Collaborator> getCollaboratorList() {
        return collaboratorRepository.getCollaboratorList();
    }

    /**
     * Assigns a skill to a collaborator based on tax number and skill name.
     *
     * @param collaboratorTaxNumber The tax number of the collaborator.
     * @param skillName             The name of the skill to assign.
     * @return True if the skill was successfully assigned, false otherwise.
     */
    public boolean assignSkillCollaboratorByTaxNumber(int collaboratorTaxNumber, Skill skill) {
        return collaboratorRepository.assignSkillCollaborator(collaboratorTaxNumber, skill);
    }

    /**
     * Retrieves the name of the collaborator with the given tax number.
     *
     * @param collaboratorTaxNumber The tax number of the collaborator.
     * @return The name of the collaborator, or null if not found.
     */
    public String getCollaboratorName(int collaboratorTaxNumber) {
        return collaboratorRepository.findCollaborator(collaboratorTaxNumber).getName();
    }

    /**
     * Retrieves the name of the collaborator with the given tax number.
     *
     * @param collaboratorTaxNumber The tax number of the collaborator.
     * @return The email of the collaborator, or null if not found.
     */
    public String getCollaboratorEmail(int collaboratorTaxNumber) {
        return collaboratorRepository.findCollaborator(collaboratorTaxNumber).getEmailAddress();
    }

    /**
     * Retrieves the name of the collaborator with the given tax number.
     *
     * @param collaboratorTaxNumber The tax number of the collaborator.
     * @return The birth date of the collaborator, or null if not found.
     */
    public Data getCollaboratorBirthDate(int collaboratorTaxNumber) {
        return collaboratorRepository.findCollaborator(collaboratorTaxNumber).getBirthDate();
    }

    public void showSkills() {
        getSkillRepository().showSkills();
    }

    public Skill getSkill(int position) {
        return getSkillRepository().getSkill(position);
    }
}

