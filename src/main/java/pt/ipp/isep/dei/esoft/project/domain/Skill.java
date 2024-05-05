package pt.ipp.isep.dei.esoft.project.domain;

public class Skill {

    /**
     * The skill name
     */
    private String skillName;




    /**
     * Constructs an instance of Skill, by giving the skill name only.
     *
     * @param skillName the skill name (string)
     */
    public Skill(String skillName) {
        skillName = skillName.trim();
        //This method verify the skill name.
        try {
            validateSkillName(skillName);

            this.skillName = skillName;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    public String getSkillName() {
        return skillName;
    }


    /**
     * Validates a skill name to ensure it meets to establish criteria.
     *
     * @param skillName The skill name to be validated.
     * @throws IllegalArgumentException If the skillName is null or empty, or if it contains special characters/numbers.
     */
    private void validateSkillName(String skillName){
        // Check if skillName is null or empty
        if ( !validateString(skillName) ) {
            throw new IllegalArgumentException("Reference cannot be null or empty.");
        }

        // Check if skillName contains special characters/numbers
        if ( !validateName(skillName) ) {
            throw new IllegalArgumentException("Skill name have special characters/numbers.");
        }
    }


    /**
     * This method validates is a String is not empty or null.
     * Return true if is not null and empty; otherwise false
     *
     * @param string the string that need to be validated
     * @return the result of the operation (null and empty)
     */
    private boolean validateString(String string) {
        return ( string != null && !string.isEmpty() );
    }


    /**
     * Validates if the skill name don´t have numbers or special characters.
     *
     * @param skillName
     * @return false if the skill name have numbers or special characters.
     */
    private boolean validateName(String skillName) {
        //This loop iterate through each character in the string
        for (int i = 0; i < skillName.length() ; i++ ) {
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
     * @param obj The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
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
     * Clone method.
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
        return String.format("Skill -> %s%n",skillName);
    }
}
