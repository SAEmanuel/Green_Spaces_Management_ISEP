package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.List;

public class AssignSkillCollaboratorController {

    private SkillRepository skillRepository;
    private CollaboratorRepository collaboratorRepository;

    public AssignSkillCollaboratorController() {
        this.skillRepository = getSkillRepository();
        this.collaboratorRepository = getCollaboratorRepository();
    }

    private SkillRepository getSkillRepository() {
        if (skillRepository == null) {

            skillRepository = getRepositories().getSkillRepository();
        }
        return skillRepository;
    }

    private CollaboratorRepository getCollaboratorRepository() {
        if (collaboratorRepository == null) {
            collaboratorRepository = getRepositories().getCollaboratorRepository();
        }
        return collaboratorRepository;
    }

    private Repositories getRepositories() {
        return Repositories.getInstance();
    }

//    public boolean isCollaboratorIDValid(int collaboratorTaxNumber) {
//        return collaboratorRepository.findCollaboratorByTaxNumber(collaboratorTaxNumber);
//    }

    public boolean isCollaboratorListEmpty(){
        return collaboratorRepository.getCollaboratorList().isEmpty();
    }

    public List<Skill> getSkillList() {
        return skillRepository.getSkillList();
    }

    public List<Collaborator> getCollaboratorList() {
        return collaboratorRepository.getCollaboratorList();
    }

    public boolean isSkillNameValid(String skillName) {
        for (Skill skill : getSkillList()) {
            if (skill.getSkillName().equalsIgnoreCase(skillName)) {
                return true;
            }
        }
        return false;
    }

    private Skill getSkillByName(String name) {
        for (Skill skill : getSkillList()) {
            if (skill.getSkillName().equalsIgnoreCase(name)) {
                return skill;
            }
        }
        return null;
    }

    public boolean assignSkillCollaboratorByTaxNumber(int collaboratorTaxNumber, String skillName) {
        Skill skill = getSkillByName(skillName);

       return collaboratorRepository.assignSkillCollaborator(collaboratorTaxNumber, skill);
    }


    public String getCollaboratorName(int collaboratorTaxNumber) {
        return collaboratorRepository.findCollaborator(collaboratorTaxNumber).getName();
    }
}
