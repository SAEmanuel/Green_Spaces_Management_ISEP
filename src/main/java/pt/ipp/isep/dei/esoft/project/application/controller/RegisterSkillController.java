package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.Optional;


public class RegisterSkillController {

    private SkillRepository skillRepository;

    //Repository instance is obtained from the Repositories class
    public RegisterSkillController() {
        this.skillRepository = getSkillRepository();
    }

    private SkillRepository getSkillRepository() {
        if (skillRepository == null) {
            Repositories repositories = Repositories.getInstance();

            skillRepository = repositories.getSkillRepository();
        }
        return skillRepository;
    }

    public Optional<Skill> registerSkill(String skillName) {

        Optional<Skill> newSkill;

        newSkill = skillRepository.registerSkill(skillName);

        return newSkill;

    }
}
