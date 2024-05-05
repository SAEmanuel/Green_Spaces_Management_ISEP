package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepository {

    /**
     * The skill List
     */
    private final List<Skill> skillList;

    /**
     * Constructs a new SkillRepository with an empty list of skills.
     */
    public SkillRepository() {
        this.skillList = new ArrayList<>();
    }

    /**
     * Registers a new skill with the given name.
     *
     * @param skillName The name of the skill to register.
     * @return An Optional containing the registered Skill if successful, or empty otherwise.
     */
    public Optional<Skill> registerSkill(String skillName){
        Optional<Skill> optionalValue = Optional.empty();

        Skill skill = new Skill(skillName);

        if (addSkill(skill)) {
            // A clone of the skill is added to the optional value, to avoid side effects and outside manipulation.
            optionalValue = Optional.of(skill.clone());
        }
        return optionalValue;
    }

    /**
     * Registers a new skill with the given name and description.
     *
     * @param skillName    The name of the skill to register.
     * @param description  The description of the skill.
     * @return An Optional containing the registered Skill if successful, or empty otherwise.
     */
    public Optional<Skill> registerSkill(String skillName, String description){
        Optional<Skill> optionalValue = Optional.empty();

        Skill skill = new Skill(skillName,description);

        if (addSkill(skill)) {
            // A clone of the skill is added to the optional value, to avoid side effects and outside manipulation.
            optionalValue = Optional.of(skill.clone());
        }
        return optionalValue;
    }



    /**
     * Adds a skill to the list of skills if it is valid.
     *
     * @param skill The skill to add.
     * @return True if the skill was added successfully, false otherwise.
     */
    private boolean addSkill(Skill skill) {
        boolean success = false;

        if ( validate(skill) && skill.getSkillName() != null ) {
            success = skillList.add(skill);
        }

        return success;

    }

    /**
     * Validates a skill by checking for duplicates.
     *
     * @param skill The skill to validate.
     * @return True if the skill is valid (not a duplicate), false otherwise.
     */
    private boolean validate(Skill skill) {
        return skillListDoNotContain(skill);
    }

    /**
     * Checks if the list of skills does not contain the given skill.
     *
     * @param skill The skill to check.
     * @return True if the list of skills does not contain the given skill, false otherwise.
     */
    private boolean skillListDoNotContain(Skill skill) {
        return !skillList.contains(skill);
    }

    /**
     * Returns a clone of the list of skills to avoid side effects and outside manipulation.
     *
     * @return A clone of the list of skills.
     */
    public List<Skill> getSkillList() {
        // A clone of the skill list return, to avoid side effects and outside manipulation.
        return clone();
    }

    /**
     * Creates a clone of the current list of skills.
     *
     * @return A clone of the list of skills.
     */
    public List<Skill> clone(){
        // Create a new reference skill list with the same content of the instance one.
        return new ArrayList<>(this.skillList);
    }
}
