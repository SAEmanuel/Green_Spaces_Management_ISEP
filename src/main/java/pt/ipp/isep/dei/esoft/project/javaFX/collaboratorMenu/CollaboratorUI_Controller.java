package pt.ipp.isep.dei.esoft.project.javaFX.collaboratorMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CollaboratorUI_Controller implements Initializable {


    private final Repositories repositories = Repositories.getInstance();
    private final SwitchWindows switchWindows = new SwitchWindows();


    @FXML
    private Label email_label;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String email = repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
        email_label.setText(email);
    }

    //------------------------------------ Options Side Bar --------------------------
    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/login.fxml");
    }

    public void switchToConsultTasks(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/consultTasks.fxml");
    }


}
