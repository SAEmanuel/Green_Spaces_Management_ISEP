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

// Controller class for Collaborator Menu UI
public class CollaboratorUI_Controller implements Initializable {

    // Instance variables for Repositories and SwitchWindows
    private final Repositories repositories = Repositories.getInstance();
    private final SwitchWindows switchWindows = new SwitchWindows();

    // Label to display the collaborator's email
    @FXML
    private Label email_label;

    /**
     * Initializes the CollaboratorUI_Controller.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Retrieves the email of the currently logged-in collaborator and displays it
        String email = repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
        email_label.setText(email);
    }

    /**
     * Handles the action to switch to the login menu.
     *
     * @param event the event that triggered the action
     * @throws IOException if an I/O error occurs
     */
    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/login.fxml");
    }

    /**
     * Handles the action to switch to the menu for consulting tasks.
     *
     * @param event the event that triggered the action
     * @throws IOException if an I/O error occurs
     */
    public void switchToConsultTasks(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/consultTasks.fxml");
    }
}
