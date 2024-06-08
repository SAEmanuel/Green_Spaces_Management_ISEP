package pt.ipp.isep.dei.esoft.project.repository;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.SkillList;
import pt.ipp.isep.dei.esoft.project.domain.Team;

import java.io.Serializable;
import java.util.*;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class TeamRepository implements Serializable {
    private final List<Team> teamList;
    private List<Team> teamListAux;

    /**
     * Constructs a new TeamRepository with an empty list of teams.
     */
    public TeamRepository() {
        this.teamList = new ArrayList<>();
        this.teamListAux = new ArrayList<>();
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


        if(minCollaborators > maxCollaborators) {
//            System.out.println(ANSI_BRIGHT_RED+"Minimum collaborators is greater than maximum collaborators"+ANSI_RESET);
            throw new IllegalArgumentException("Minimum collaborators is greater than maximum collaborators");
//            return optionalValue;
        }
        if( verifyIfExistingCollab(skills,collaboratorList, minCollaborators) ) {
//            System.out.println(ANSI_BRIGHT_RED+"Not enought collaborators to generate team"+ANSI_RESET);
            throw new IllegalArgumentException("Not enought collaborators to generate team");
//            return optionalValue;
        }

        int newTeamId = 1 + teamListAux.size();
        for(Team t : teamList) {
            if (t.getTeamId() != newTeamId) {
                break;
            }
            newTeamId++;
        }

        Team team = new Team(newTeamId);
        int encontrados = 0;
        boolean allSkillsDone = false;
        boolean diferentTeam = true;
        SkillList skillsClone = new SkillList();
        skillsClone.setSkills(skills.getSkillList());
        List<Collaborator> shuffledCollaboratorList = new ArrayList<>(collaboratorList);

        int breakDown = 0;

        while(true && breakDown != 5){
            Collections.shuffle(shuffledCollaboratorList);

            while(encontrados < maxCollaborators) {
                for (Collaborator c : shuffledCollaboratorList){
                    if(skills.getSkillList().isEmpty())
                        break;

                    if(checkIfHasSkills(c, skills)){
                        if(!collaboratorHasTeam(c, teamList) && !team.getCollaborators().contains(c)) {
                            if(!allSkillsDone)
                                removeSkills(c,skills);
                            else
                                skills.setSkills(skillsClone.getSkillList());

                            team.addCollaborator(c);
                            encontrados++;
                        }
                    }

                    if(allSkillsDone && encontrados == minCollaborators) {
                        skills.setSkills(new ArrayList<>());
                    }
                }

                if(encontrados >= minCollaborators && skills.getSkillList().isEmpty()){
                    encontrados = maxCollaborators;
                }
                else {

                    skills.setSkills(skillsClone.getSkillList());
                    allSkillsDone = true;
                }
                if(encontrados < minCollaborators && skills.getSkillList().isEmpty()){
                }

            }

            if(!checkTeamCollaborators(team)) {
                diferentTeam = false;
                breakDown++;
            }

            if(diferentTeam == true){
                break;
            }

            team.setCollaborators(new ArrayList<>());
            skills.setSkills(skillsClone.getSkillList());
            diferentTeam = true;
            encontrados = 0;
        }

        if(breakDown == 5){
            throw new IllegalArgumentException("Not enought collaborators to generate team");
//            System.out.println(ANSI_BRIGHT_RED+"Not enought collaborators to generate team"+ANSI_RESET);
//            return optionalValue;
        }

        optionalValue = Optional.of(team);

        if(!team.getCollaborators().isEmpty()) {
            teamListAux.add(team);
        }

        return optionalValue;
    }

    public boolean checkTeamCollaborators(Team team) {
        for (Team t : teamListAux) {
            if (areCollaboratorsEqual(t.getCollaborators(), team.getCollaborators())) {
                return false;
            }
        }
        return true;
    }

    private boolean areCollaboratorsEqual(List<Collaborator> collaborators1, List<Collaborator> collaborators2) {
        if (collaborators1.size() != collaborators2.size()) {
            return false;
        }
        int count = 0;

        for (Collaborator c1: collaborators1) {
            for (Collaborator c2 : collaborators2)
                if (c1.getTaxPayerNumber() == c2.getTaxPayerNumber()) {
                    count++;
                    break;
                }
        }

        if(count == collaborators2.size())
            return true;
        return false;
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
        boolean allSkillsDone = false;

        SkillList skillsCloneVerify = new SkillList();
        skillsCloneVerify.setSkills(skills.getSkillList());

        for (Collaborator c : collaboratorList){
            if(checkIfHasSkills(c, skillsCloneVerify)){
                if(!collaboratorHasTeam(c, teamList)){
                    if(!allSkillsDone)
                        removeSkills(c, skillsCloneVerify);
                    else
                        skillsCloneVerify.setSkills(skills.getSkillList());
                    count++;
                }
            }
            if(skillsCloneVerify.getSkillList().isEmpty()) {
                skillsCloneVerify.setSkills(skills.getSkillList());
                allSkillsDone = true;
            }
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
    private boolean collaboratorHasTeam(Collaborator c, List<Team> teamList) {
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

    public void addTeam(int teamId){
        for (Team t : teamListAux) {
            if (t.getTeamId() == teamId) {
                teamList.add(t);
                break;
            }
        }
    }

    public void cleanTeamListAux(){
        teamListAux = new ArrayList<>();
    }

    public List<Team> generatedList(){
        return new ArrayList<>(teamListAux);
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
        Iterator<Team> iterator = teamList.iterator();
        while (iterator.hasNext()) {
            Team t = iterator.next();
            if (t.getTeamId() == teamId) {
                iterator.remove();
            }
        }
    }

    public List<Team> getListTeam() {
        return teamList;
    }

    public void add(Team team) {
        teamList.add(team);
    }
}
