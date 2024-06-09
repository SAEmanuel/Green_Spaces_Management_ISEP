package pt.ipp.isep.dei.esoft.project.javaFX.hrmMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the HRM UI screen.
 */
public class HrmUI_Controller implements Initializable  {

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final Repositories repositories = Repositories.getInstance();

    @FXML
    private Label email_label;

    private Stage stage;

    @FXML
    private Label num_collaborators;

    @FXML
    private Label num_jobs;

    @FXML
    private Label num_skills;


    /**
     * Initializes the HRM UI screen.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
        num_collaborators.setText(String.valueOf(repositories.getCollaboratorRepository().getCollaboratorList().size()));
        num_jobs.setText(String.valueOf(repositories.getJobRepository().getJobList().size()));
        num_skills.setText(String.valueOf(repositories.getSkillRepository().getSkillList().size()));
    }


    //------------------------------------ Options Side Bar --------------------------
    /**
     * Switches the view to the login menu.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/login.fxml");
    }

    /**
     * Switches the view to the register skill screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToRegisterSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/registerSkill.fxml");
    }

    /**
     * Switches the view to the register job screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToRegisterJob(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/registerJob.fxml");
    }

    /**
     * Switches the view to the create collaborator screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToCreateCollaborator(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/createCollaborator.fxml");
    }

    /**
     * Switches the view to the assign skill to collaborator screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignSkillCollaborator.fxml");
    }

    /**
     * Switches the view to the generate team screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeGenerateTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/generateTeam.fxml");
    }

}
