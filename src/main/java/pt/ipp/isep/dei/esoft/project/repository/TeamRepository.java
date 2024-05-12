package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.SkillList;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamRepository {
    private final List<Team> teamList;
    /**
     * Constructs a new TeamRepository with an empty list of teams.
     */
    public TeamRepository() {
        this.teamList = new ArrayList<>();
    }
    /**
     * Attempts to generate a team based on the provided skills, collaborator list, and constraints.
     * @param skills The required skills for the team.
     * @param collaboratorList The list of available collaborators.
     * @param minCollaborators The minimum number of collaborators required for the team.
     * @param maxCollaborators The maximum number of collaborators allowed for the team.
     * @return An Optional containing the generated team if successful, otherwise an empty Optional.
     */

    public Optional<Team> generateTeam(SkillList skills, List<Collaborator> collaboratorList, int minCollaborators, int maxCollaborators){
        Optional<Team> optionalValue = Optional.empty();

        if(verifyIfExistingCollab(skills,collaboratorList, minCollaborators))
            return optionalValue;

        Team team = new Team(teamList.size()+1);

        int encontrados = 0;
        SkillList skillsClone = new SkillList();
        skillsClone.setSkills(skills.getSkillList());
        boolean lastCollab = false;

        while(encontrados < maxCollaborators) {
            for (Collaborator c : collaboratorList){
                if(skills.getSkillList().isEmpty())
                    break;
                if(checkIfHasSkills(c, skills)){
                    if(!collaboratorHasTeam(c) && !team.getCollaborators().contains(c)) {
                        removeSkills(c,skills);
                        team.addCollaborator(c);
                        encontrados++;
                        if (lastCollab) {
                            break;
                        }
                    }
                }
            }

            if(encontrados >= minCollaborators && skills.getSkillList().isEmpty() || lastCollab){
                encontrados = maxCollaborators;
            }
            else
                if(encontrados < minCollaborators && skills.getSkillList().isEmpty()){
                    skills.setSkills(skillsClone.getSkillList());
                    if(encontrados == minCollaborators-1)
                        lastCollab = true;
                }
        }

        optionalValue = Optional.of(team);

        if(!team.getCollaborators().isEmpty()) {
            teamList.add(team);
        }

        return optionalValue;
    }
    /**
     * Checks if there are enough collaborators with the required skills available.
     * @param skills The required skills for the team.
     * @param collaboratorList The list of available collaborators.
     * @param minCollaborators The minimum number of collaborators required for the team.
     * @return True if there are not enough collaborators with the required skills, otherwise false.
     */
    private boolean verifyIfExistingCollab(SkillList skills, List<Collaborator> collaboratorList, int minCollaborators) {
        int count=0;

        SkillList skillsCloneVerify = new SkillList();
        skillsCloneVerify.setSkills(skills.getSkillList());

        for (Collaborator c : collaboratorList){
            if(checkIfHasSkills(c, skillsCloneVerify)){
                if(!collaboratorHasTeam(c)){
                    removeSkills(c, skillsCloneVerify);
                    count++;
                }
            }
            if(skillsCloneVerify.getSkillList().isEmpty())
                skillsCloneVerify.setSkills(skills.getSkillList());
        }

        if(count < minCollaborators)
            return true;
        return false;
    }

    /**
     * Checks if a given collaborator is already assigned to a team.
     * @param c The collaborator to check.
     * @return True if the collaborator is already in a team, otherwise false.
     */
    private boolean collaboratorHasTeam(Collaborator c) {
        for (Team team: teamList){
            if(team.getCollaborators().contains(c)){
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if a collaborator possesses the required skills.
     * @param c The collaborator to check.
     * @param skills The required skills.
     * @return True if the collaborator possesses at least one of the required skills, otherwise false.
     */
    private boolean checkIfHasSkills(Collaborator c, SkillList skills) {
        boolean hasSkills = false;
        for(Skill s : c.cloneList()){
            if(skills.getSkillList().contains(s)){
                hasSkills= true;
                break;
            }
        }
        return hasSkills;
    }
    /**
     * Removes the skills possessed by a collaborator from a given skill list.
     * @param c The collaborator whose skills are to be removed.
     * @param skills The skill list to remove skills from.
     */
    private void removeSkills(Collaborator c, SkillList skills) {
        for(Skill s : c.cloneList()){
            if(skills.getSkillList().contains(s)){
                skills.removeSkill(s);
            }
        }
    }
    /**
     * Returns a clone of the list of teams.
     * @return A cloned list of teams to avoid side effects and outside manipulation.
     */
    public List<Team> getTeamList() {
        // A clone of the skill list return, to avoid side effects and outside manipulation.
        return clone();
    }
    /**
     * Clones the list of teams.
     * @return A new list of teams with the same content as the original one.
     */
    public List<Team> clone(){
        // Create a new reference skill list with the same content of the instance one.
        return new ArrayList<>(this.teamList);
    }
    /**
     * Removes a team from the repository based on its ID.
     * @param teamId The ID of the team to be removed.
     */
    public void removeTeam(int teamId) {
        for (Team t : teamList)
            if(t.getTeamId() == teamId)
                teamList.remove(t);
    }

}
