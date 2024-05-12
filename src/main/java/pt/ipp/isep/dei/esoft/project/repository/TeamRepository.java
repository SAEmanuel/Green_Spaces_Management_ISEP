package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamRepository {
    private final List<Team> teamList;

    public TeamRepository() {
        this.teamList = new ArrayList<>();
    }

    public Optional<Team> generateTeam(List<Skill> skills, List<Collaborator> collaboratorList, int minCollaborators, int maxCollaborators){
        Optional<Team> optionalValue = Optional.empty();

        Team team = new Team(teamList.size()+1);

        int encontrados = 0;
        List<Skill> skillsClone = skills;
        boolean lastCollab = false;

        while(encontrados < maxCollaborators) {
            for (Collaborator c : collaboratorList){
                if(skills.isEmpty())
                    break;
                System.out.println(checkIfHasSkills(c, skills));
                if(checkIfHasSkills(c, skills)){
                    System.out.println(!collaboratorHasTeam(c));
                    if(!collaboratorHasTeam(c) && !team.getCollaboratorList().contains(c)) {
                        team.addCollaborator(c);
                        System.out.println("added");
                        encontrados++;
                    }
                }

                if (lastCollab)
                    break;
            }

            if(encontrados >= minCollaborators && skills.isEmpty() || lastCollab)
                encontrados = maxCollaborators;
            else
                if(encontrados < minCollaborators && skills.isEmpty())
                    skills = skillsClone;
        }

        if (team == null) {
            optionalValue = Optional.empty();
        }
        System.out.println(team.getCollaboratorList());
        return optionalValue;
    }

    private boolean collaboratorHasTeam(Collaborator c) {
        for (Team team: teamList){
            if(team.getCollaboratorList().contains(c)){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfHasSkills(Collaborator c, List<Skill> skills) {
        boolean hasSkills = false;
        for(Skill s : c.cloneList()){
            if(skills.contains(s)){
                skills.remove(s);
                hasSkills= true;
            }
        }
        return hasSkills;
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
}
