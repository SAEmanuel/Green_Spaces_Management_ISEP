package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class SkillRepository implements Serializable {

    /**
     * The list of skills.
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
     * Adds a skill to the list of skills if it is valid.
     *
     * @param skill The skill to add.
     * @return True if the skill was added successfully, false otherwise.
     */
    private boolean addSkill(Skill skill) {
        boolean success = false;

        if ( (validate(skill)) && (skill.getSkillName() != null) ) {
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


    /**
     * Shows all the skills in the list
     */
    public void showSkills() {
        if (skillList.isEmpty()) {
            System.out.println(ANSI_BRIGHT_RED + "No skills found in the repository." + ANSI_RESET);
        } else {
            System.out.println("\n--List of Skills--");
            for (int i = 0; i < skillList.size(); i++) {
                Skill skill = skillList.get(i);
                System.out.println("• Skill: " + skill.getSkillName() + "\n"+ANSI_PURPLE+"   Option -> [" + (i+1) + "]"+ ANSI_RESET);
            }
            System.out.println("----------------");
        }
    }

    /**
     * Retrieves a skill from the repository based on its position.
     *
     * @param position The position of the skill in the repository.
     * @return The skill object at the specified position.
     */
    public Skill getSkill(int position) {
        return skillList.get(position);
    }



    //----------------------- Serialization methods -------------------------------

    public List<Skill> getSkillListSerialization() {
        return skillList;
    }

    public void serializationInput(List<Skill> skillList) {
        this.skillList.clear();
        this.skillList.addAll(skillList);
    }

    //-------------------------------------------------------------------------------



    //---------------------- Boostrap methods ---------------------------------------

    public void add(Skill skill) {
        skillList.add(skill);
    }

    //-------------------------------------------------------------------------------

}
