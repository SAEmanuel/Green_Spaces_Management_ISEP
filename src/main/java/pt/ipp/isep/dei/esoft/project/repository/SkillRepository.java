package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepository {

    private final List<Skill> skillList;

    public SkillRepository() {
        this.skillList = new ArrayList<>();
    }


    public Optional<Skill> registerSkill(String skillName){
        Optional<Skill> optionalValue = Optional.empty();

        Skill skill = new Skill(skillName);

        if ( addSkill(skill) ) {
            optionalValue = Optional.of(skill);
        }
        return optionalValue;
    }


    /**
     * This method adds a task to the list of tasks.
     *
     * @param skill The task to be added.
     * @return True if the task was added successfully.
     */
    private boolean addSkill(Skill skill) {
        boolean success = false;
        if ( validate(skill) ) {
            // A clone of the task is added to the list of tasks, to avoid side effects and outside manipulation.
            success = skillList.add(skill.clone());
        }
        return success;

    }

    /**
     * This method validates the task, checking for duplicates.
     *
     * @param skill The task to be validated.
     * @return True if the task is valid.
     */
    private boolean validate(Skill skill) {
        return skillListDoNotContain(skill);
    }

    /**
     * This method checks if the task is already in the list of tasks.
     *
     * @param skill The task to be checked.
     * @return True if the skill is not in the list of skills.
     */
    private boolean skillListDoNotContain(Skill skill) {
        return !skillList.contains(skill);
    }

    public List<Skill> getSkillList() {
        return clone();
    }

    public List<Skill> clone(){
        return new ArrayList<>(this.skillList);
    }
}
