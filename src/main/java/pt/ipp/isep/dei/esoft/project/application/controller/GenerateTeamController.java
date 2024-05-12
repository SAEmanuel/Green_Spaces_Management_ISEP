package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.SkillList;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

public class GenerateTeamController {
    private TeamRepository teamRepository;
    private SkillRepository skillRepository;
    private CollaboratorRepository collaboratorRepository;
    private SkillList skillList;

    public GenerateTeamController(){
        teamRepository = getTeamRepository();
        collaboratorRepository = getCollaboratorRepository();
    }

    public List<Skill> getSkillList() {
        if (skillRepository == null) {
            Repositories repositories = Repositories.getInstance();
            skillRepository = repositories.getSkillRepository();
        }
        return skillRepository.getSkillList();
    }

    public TeamRepository getTeamRepository() {
        if(teamRepository == null){
            Repositories repositories = Repositories.getInstance();
            teamRepository = repositories.getTeamRepository();
        }
        return teamRepository;
    }

    private CollaboratorRepository getCollaboratorRepository() {
        if (collaboratorRepository == null) {
            Repositories repositories = Repositories.getInstance();
            collaboratorRepository = repositories.getCollaboratorRepository();
        }
        return collaboratorRepository;
    }

    public void addSkill(Skill skill){
        if(skillList == null)
            skillList = new SkillList();

        if(!skillList.getSkillList().contains(skill))
            skillList.addSkill(skill);
        else
            System.out.println("Skill Already Added!");
    }

    public Optional<Team> generateTeam(int minCollaborators, int maxCollaborators) {
        Optional<Team> optionalValue = Optional.empty();

        SkillList skills = new SkillList();
        skills.setSkills(skillList.getSkillList());

        if(collaboratorRepository == null || skills == null)
            return optionalValue;

        if(!skills.getSkillList().isEmpty() && !collaboratorRepository.getCollaboratorList().isEmpty() && minCollaborators != 0 && maxCollaborators != 0){
            return teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);
        }
        else{
            return optionalValue;
        }
    }

    public void removeTeam(int teamId){
        teamRepository.removeTeam(teamId);
    }

    public void cleanSkillList(){
        if(skillList == null)
            skillList = new SkillList();

        skillList.cleanSkillList();
    }
}
