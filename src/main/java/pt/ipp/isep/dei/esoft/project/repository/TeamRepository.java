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

    public TeamRepository() {
        this.teamList = new ArrayList<>();
    }
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
                    if(!collaboratorHasTeam(c) && !team.getCollaboratorList().contains(c)) {
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

        if(!team.getCollaboratorList().isEmpty()) {
            teamList.add(team);
        }

        return optionalValue;
    }

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


    private boolean collaboratorHasTeam(Collaborator c) {
        for (Team team: teamList){
            if(team.getCollaboratorList().contains(c)){
                return true;
            }
        }
        return false;
    }

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

    private void removeSkills(Collaborator c, SkillList skills) {
        for(Skill s : c.cloneList()){
            if(skills.getSkillList().contains(s)){
                skills.removeSkill(s);
            }
        }
    }

    private boolean skillListDoNotContain(Team team) {
        return !teamList.contains(team);
    }

    public List<Team> getTeamList() {
        // A clone of the skill list return, to avoid side effects and outside manipulation.
        return clone();
    }

    public List<Team> clone(){
        // Create a new reference skill list with the same content of the instance one.
        return new ArrayList<>(this.teamList);
    }

    public void removeTeam(int teamId) {
        for (Team t : teamList)
            if(t.getTeamId() == teamId)
                teamList.remove(t);
    }

}
