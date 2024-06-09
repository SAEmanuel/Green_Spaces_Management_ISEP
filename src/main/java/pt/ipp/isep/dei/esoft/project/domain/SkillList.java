package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of skills.
 */
public class SkillList implements Serializable {
    private List<Skill> skills;

    /**
     * Constructs a SkillList object.
     */
    public SkillList() {
        this.skills = new ArrayList<>();
    }

    /**
     * Adds a skill to the list.
     *
     * @param skill The skill to add.
     */
    public void addSkill(Skill skill){
        skills.add(skill);
    }

    /**
     * Sets the skills of the list.
     *
     * @param skills The list of skills to set.
     */
    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    /**
     * Removes a skill from the list.
     *
     * @param skill The skill to remove.
     */
    public void removeSkill(Skill skill){
        skills.remove(skill);
    }

    /**
     * Retrieves a list of skills.
     *
     * @return A clone of the skill list to avoid side effects and outside manipulation.
     */
    public List<Skill> getSkillList() {
        // A clone of the skill list return, to avoid side effects and outside manipulation.
        return clone();
    }

    /**
     * Clones the skill list.
     *
     * @return A clone of the skill list.
     */
    public List<Skill> clone(){
        // Create a new reference skill list with the same content of the instance one.
        return new ArrayList<>(this.skills);
    }

    /**
     * Clears the skill list.
     */
    public void cleanSkillList(){
        this.skills.clear();
    }
}
