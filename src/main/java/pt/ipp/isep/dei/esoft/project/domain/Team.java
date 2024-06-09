package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a team of collaborators.
 */
public class Team implements Serializable {
    private int teamId;
    private List<Collaborator> collaborators;

    /**
     * Constructs a team with the given team ID.
     *
     * @param teamId the ID of the team
     */
    public Team(int teamId) {
        this.teamId = teamId;
        this.collaborators = new ArrayList<>();
    }

    /**
     * Constructs a team as a copy of another team.
     *
     * @param originalTeam the original team to copy
     */
    public Team(Team originalTeam) {
        this.teamId = originalTeam.getTeamId();
        this.collaborators = new ArrayList<>(originalTeam.getCollaborators());
    }

    /**
     * Retrieves the ID of the team.
     *
     * @return the ID of the team
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Sets the ID of the team.
     *
     * @param teamId the ID of the team to set
     */
    public void setTeamId(int teamId) { this.teamId = teamId; }

    /**
     * Sets the list of collaborators for the team.
     *
     * @param collaborators the list of collaborators to set
     */
    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Adds a collaborator to the team.
     *
     * @param c the collaborator to add
     */
    public void addCollaborator(Collaborator c) {
        collaborators.add(c);
    }

    /**
     * Retrieves the list of collaborators in the team.
     *
     * @return the list of collaborators
     */
    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    /**
     * Retrieves the email addresses of all collaborators in the team.
     *
     * @return the list of email addresses
     */
    public List<String> getCollaboratorsEmail() {
        List<String> collaboratorsEmail = new ArrayList<>();

        for(Collaborator c : collaborators){
            collaboratorsEmail.add(c.getEmailAddress());
        }

        return collaboratorsEmail;
    }

    /**
     * Checks if the team has a specific collaborator.
     *
     * @param collaborator the collaborator to check
     * @return true if the team has the collaborator, false otherwise
     */
    public boolean hasCollaborator(Collaborator collaborator) {
        for (Collaborator c : collaborators) {
            if (c.equals(collaborator)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a string representation of the team.
     *
     * @return a string representation of the team
     */
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
