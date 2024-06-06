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

public class HrmUI_Controller implements Initializable  {

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final Repositories repositories = Repositories.getInstance();

    @FXML
    private Label email_label;

    private Stage stage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }







    //------------------------------------ Options Side Bar --------------------------

    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/login.fxml");
    }

    public void changeToRegisterSkill(ActionEvent event) {
    }

    public void changeToRegisterJob(ActionEvent event) {
    }

    public void changeToCreateCollaborator(ActionEvent event) {
    }

    public void changeToAssignSkill(ActionEvent event) {
    }

    public void changeGenerateTeam(ActionEvent event) {
    }



}
