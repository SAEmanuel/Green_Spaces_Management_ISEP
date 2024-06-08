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

public class ConsultTasks_Controller implements Initializable {

    private final Repositories repositories = Repositories.getInstance();
    private final SwitchWindows switchWindows = new SwitchWindows();

    @FXML
    Label email_label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }

    public void switchToCollaboratorMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/collaboratorUI.fxml");
    }
}
