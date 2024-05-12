package pt.ipp.isep.dei.esoft.project.domain;

import java.util.ArrayList;
import java.util.List;

public class SkillList {
    private List<Skill> skills;

    public SkillList() {
        this.skills = new ArrayList<>();
    }

    public void addSkill(Skill skill){
        skills.add(skill);
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void removeSkill(Skill skill){
        skills.remove(skill);
    }

    public List<Skill> getSkillList() {
        // A clone of the skill list return, to avoid side effects and outside manipulation.
        return clone();
    }

    public List<Skill> clone(){
        // Create a new reference skill list with the same content of the instance one.
        return new ArrayList<>(this.skills);
    }
}
