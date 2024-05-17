package pt.ipp.isep.dei.esoft.project.domain;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int teamId;
    private List<Collaborator> collaborators;

    public Team(int teamId) {
        this.teamId = teamId;
        this.collaborators = new ArrayList<>();
    }

    public int getTeamId() {
        return teamId;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public void addCollaborator(Collaborator c) {
        collaborators.add(c);
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }
}
