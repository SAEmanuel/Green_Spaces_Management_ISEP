package pt.ipp.isep.dei.esoft.project.domain;

import java.util.List;

public class Team {
    private int teamId;

    private List<Collaborator> collaborators;
    public Team(int teamId) {
        this.teamId = teamId;
    }
}
