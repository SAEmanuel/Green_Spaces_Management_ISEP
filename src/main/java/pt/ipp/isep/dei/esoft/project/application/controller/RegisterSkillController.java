package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.util.Optional;


public class RegisterSkillController {

    private SkillRepository skillRepository;

    /**
     * Constructs a new RegisterSkillController and obtains a SkillRepository instance from Repositories class.
     */
    public RegisterSkillController() {
        this.skillRepository = getSkillRepository();
    }

    /**
     * Gets the SkillRepository instance from Repositories class.
     *
     * @return The SkillRepository instance.
     */
    private SkillRepository getSkillRepository() {
        if (skillRepository == null) {
            Repositories repositories = Repositories.getInstance();

            skillRepository = repositories.getSkillRepository();
        }
        return skillRepository;
    }

    /**
     * Registers a new skill with the given name.
     *
     * @param skillName The name of the skill to register.
     * @return An Optional containing the registered Skill if successful, or empty otherwise.
     */
    public Optional<Skill> registerSkill(String skillName) {

        Optional<Skill> newSkill;

        newSkill = skillRepository.registerSkill(skillName);

        return newSkill;

    }

}
