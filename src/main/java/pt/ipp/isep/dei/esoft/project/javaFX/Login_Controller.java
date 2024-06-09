package pt.ipp.isep.dei.esoft.project.javaFX;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the login functionality.
 */
public class Login_Controller implements Initializable {
    AuthenticationController ctrl = new AuthenticationController();


    @FXML
    private TextField userEmail;

    @FXML
    private PasswordField userPassword;

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final SendErrors sendErrors = new SendErrors();
    private final ConfirmationAlerts confirmationAlerts = new ConfirmationAlerts();

    private Stage stage;
    private final String GSMPREFIX = "gsm";
    private final String VFMPREFIX = "vfm";
    private final String HRMPREFIX = "hrm";

    /**
     * Initializes the login screen.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl.doLogout();
    }

    /**
     * Handles the login action.
     *
     * @param event The ActionEvent triggered by clicking the login button.
     * @throws IOException If an I/O error occurs.
     */
    public void submit(ActionEvent event) throws IOException {
        String email = userEmail.getText();
        String password = userPassword.getText();
        boolean validCredentials = ctrl.doLogin(email, password);

        if (validCredentials) {
            verifyUSR(email, event);
        } else {
            sendErrors.errorMessages("Invalid Credentials", "Invalid UserId and/or Password.", "");
        }
    }

    /**
     * Handles the logout action.
     *
     * @param event The ActionEvent triggered by clicking the logout button.
     */
    public void logout(ActionEvent event) {
        userEmail.clear();
        userPassword.clear();
    }

    /**
     * Verifies the user type based on the email prefix and switches to the corresponding menu.
     *
     * @param email The user's email.
     * @param event The ActionEvent triggered by the user's action.
     * @throws IOException If an I/O error occurs.
     */
    private void verifyUSR(String email, Event event) throws IOException {
        String[] emailSplit = email.split("@");
        String prefix = emailSplit[0];

        switch (prefix) {
            case GSMPREFIX:
                switchToGsmMenu((ActionEvent) event);
                break;
            case HRMPREFIX:
                switchToHrmMenu((ActionEvent) event);
                break;
            case VFMPREFIX:
                switchToVfmMenu((ActionEvent) event);
                break;
            default:
                switchToCollaboratorMenu((ActionEvent) event);
                break;
        }
    }

    /**
     * Switches to the main menu.
     *
     * @param event The ActionEvent triggered by the user's action.
     * @throws IOException If an I/O error occurs.
     */
    public void switchToMainMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/mainMenu.fxml");
    }

    /**
     * Switches to the GSM menu.
     *
     * @param event The ActionEvent triggered by the user's action.
     * @throws IOException If an I/O error occurs.
     */
    public void switchToGsmMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/gsmUI.fxml");
    }

    /**
     * Switches to the HRM menu.
     *
     * @param event The ActionEvent triggered by the user's action.
     * @throws IOException If an I/O error occurs.
     */
    public void switchToHrmMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/hrmUI.fxml");
    }

    /**
     * Switches to the collaborator menu.
     *
     * @param event The ActionEvent triggered by the user's action.
     * @throws IOException If an I/O error occurs.
     */
    public void switchToCollaboratorMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/collaboratorUI.fxml");
    }

    /**
     * Switches to the VFM menu.
     *
     * @param event The ActionEvent triggered by the user's action.
     * @throws IOException If an I/O error occurs.
     */
    public void switchToVfmMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/vfmUI.fxml");
    }
}
