package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private int teamId;
    private List<Collaborator> collaborators;

    public Team(int teamId) {
        this.teamId = teamId;
        this.collaborators = new ArrayList<>();
    }

    public Team(Team originalTeam) {
        this.teamId = originalTeam.getTeamId();
        this.collaborators = new ArrayList<>(originalTeam.getCollaborators());
    }



    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) { this.teamId = teamId; }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public void addCollaborator(Collaborator c) {
        collaborators.add(c);
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }
    public List<String> getCollaboratorsEmail() {
        List<String> collaboratorsEmail = new ArrayList<>();

        for(Collaborator c : collaborators){
            collaboratorsEmail.add(c.getEmailAddress());
        }

        return collaboratorsEmail;
    }

    public boolean hasCollaborator(Collaborator collaborator) {
        for (Collaborator c : collaborators) {
            if (c.equals(collaborator)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Collaborator c : collaborators) {
            if(i!=0)
                builder.append(", "+c.getName() + " ");
            else
                builder.append(c.getName() + " ");
            i++;
        }
        return String.format("[ Team ID: %d | Collaborators: %s ]", teamId, builder);
    }
}
