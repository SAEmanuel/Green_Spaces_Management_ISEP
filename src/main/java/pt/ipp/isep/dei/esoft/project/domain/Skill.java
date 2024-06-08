package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Extras.validations.Validations;

import java.io.Serializable;

public class Skill implements Serializable {

    /**
     * The name of the skill.
     */
    private final String skillName;


    /**
     * Constructs an instance of Skill with the given skill name.
     *
     * @param skillName The name of the skill.
     * @throws IllegalArgumentException If the skillName is null, empty, or contains special characters/numbers.
     */
    public Skill(String skillName) {

        validateSkillName(skillName);
        this.skillName = skillName.trim();

    }

    /**
     * Retrieves the name of the skill.
     *
     * @return A clone of the skill name.
     */
    public String getSkillName() {
        return clone().skillName;
    }


    /**
     * Validates a skill name to ensure it meets established criteria.
     *
     * @param skillName The skill name to validate.
     * @throws IllegalArgumentException If the skillName is null, empty, or contains special characters/numbers.
     */
    private void validateSkillName(String skillName) {
        // Check if skillName is null or empty
        if (!Validations.validateString(skillName)) {
            throw new IllegalArgumentException("Skill name cannot be null or empty.");
        }
        if (!Validations.hasOnlyLettersAndSpaces(skillName.trim())) {
            throw new IllegalArgumentException("Skill name have special characters/numbers.");
        }

    }



    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj The object to compare with.
     * @return True if the object is equal to this one (ignoring case); otherwise, false.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if the other object is the same as this one by reference.
        if (this == obj) {
            return true;
        }
        // Check if the other object is an instance of Skill
        if (!(obj instanceof Skill)) {
            return false;
        }

        // Cast the other object to Skill
        Skill skill = (Skill) obj;
        // Compare the skill names ignoring case sensitivity
        return (this.skillName.equalsIgnoreCase(skill.skillName));
    }


    /**
     * Creates and returns a clone of the current instance.
     *
     * @return A clone of the current instance.
     */
    public Skill clone() {
        return new Skill(this.skillName);
    }


    /**
     * Returns a textual description of the Skill name.
     *
     * @return skill name.
     */
    @Override
    public String toString() {
        return String.format("%s%n", skillName);
    }
}
