package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;

public class Skill implements Serializable {

    /**
     * The name of the skill.
     */
    private String skillName;


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
        if (skillName == null || skillName.trim().isEmpty()) {
            throw new IllegalArgumentException("Skill name cannot be null or empty.");
        }

        // Check if skillName contains special characters/numbers
        if (!validateName(skillName.trim())) {
            throw new IllegalArgumentException("Skill name have special characters/numbers.");
        }
    }


    /**
     * Validates if a string is not empty or not null.
     *
     * @param string The string to validate.
     * @return True if the string is not null and not empty; otherwise, false.
     */
    private boolean validateString(String string) {
        return (string != null && !string.isEmpty());
    }


    /**
     * Validates if the skill name contains only letters and spaces.
     *
     * @param skillName The skill name to validate.
     * @return True if the skill name contains only letters and spaces; otherwise, false.
     */
    private boolean validateName(String skillName) {
        //This loop iterate through each character in the string
        for (int i = 0; i < skillName.length(); i++) {
            char ch = skillName.charAt(i);
            // Check if the character is not a letter
            if (!Character.isLetter(ch) && skillName.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
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
        return String.format("Skill -> %s%n", skillName);
    }
}
