package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamRepository {
    private final List<Team> teamList;

    /**
     * Constructs a new SkillRepository with an empty list of skills.
     */
    public TeamRepository() {
        this.teamList = new ArrayList<>();
    }

    /**
     * Registers a new skill with the given name.
     *
     * @param teamId The name of the skill to register.
     * @return An Optional containing the registered Skill if successful, or empty otherwise.
     */
    public Optional<Skill> registerTeam(int teamId){
        Optional<Skill> optionalValue = Optional.empty();

        Team team = new Team(teamId);

        if (addCollaborator(team)) {
            // A clone of the skill is added to the optional value, to avoid side effects and outside manipulation.
            optionalValue = Optional.of(team.clone());
        }
        return optionalValue;
    }



    /**
     * Adds a skill to the list of skills if it is valid.
     *
     * @param team The skill to add.
     * @return True if the skill was added successfully, false otherwise.
     */
    private boolean addCollaborator(Team team) {
        boolean success = false;

        if ( (validate(team)) && (team.getSkillName() != null) ) {
            success = teamList.add(team);
        }

        return success;

    }

    /**
     * Validates a skill by checking for duplicates.
     *
     * @param team The skill to validate.
     * @return True if the skill is valid (not a duplicate), false otherwise.
     */
    private boolean validate(Team team) {
        return skillListDoNotContain(team);
    }

    /**
     * Checks if the list of skills does not contain the given skill.
     *
     * @param team The skill to check.
     * @return True if the list of skills does not contain the given skill, false otherwise.
     */
    private boolean skillListDoNotContain(Team team) {
        return !teamList.contains(team);
    }

    /**
     * Returns a clone of the list of skills to avoid side effects and outside manipulation.
     *
     * @return A clone of the list of skills.
     */
    public List<Team> getTeamList() {
        // A clone of the skill list return, to avoid side effects and outside manipulation.
        return clone();
    }

    /**
     * Creates a clone of the current list of skills.
     *
     * @return A clone of the list of skills.
     */
    public List<Team> clone(){
        // Create a new reference skill list with the same content of the instance one.
        return new ArrayList<>(this.teamList);
    }
}
