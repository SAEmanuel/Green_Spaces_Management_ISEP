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

public class Login_Controller implements Initializable {
    AuthenticationController ctrl = new AuthenticationController();

    @FXML
    private AnchorPane loginScene;

    @FXML
    private TextField userEmail;

    @FXML
    private PasswordField userPassword;

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final SendErrors sendErrors = new SendErrors();
    private final ConfirmationAlerts confirmationAlerts = new ConfirmationAlerts();

    private Stage stage;
    private final String GSMPREFIX = "gsm";
    private final String HRMPREFIX = "hrm";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userEmail.setText("hrm@this.app");
        userPassword.setText("hrm");
    }


    public void submit(ActionEvent event) throws IOException {
        String email = userEmail.getText();
        String password = userPassword.getText();

        if (ctrl.doLogin(email, password)) {
            verifyUSR(email, event);
        } else {
            sendErrors.errorMessages("Invalid Credentials", "Invalid UserId and/or Password.", "");
        }
    }


    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("You're about to cancel!");
        alert.setContentText("Are you sure you want to cancel?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) loginScene.getScene().getWindow();
            stage.close();
        }
    }


    private void verifyUSR(String email, Event event) throws IOException {
        String[] emailSplit = email.split("@");
        if (emailSplit[0].equals(GSMPREFIX)) {
            switchToGsmMenu((ActionEvent) event);
        }
        if (emailSplit[0].equals(HRMPREFIX)) {
            switchToHrmMenu((ActionEvent) event);
        }
        if (!emailSplit[0].equals(HRMPREFIX) && !emailSplit[0].equals(GSMPREFIX)) {
            switchToCollaboratorMenu((ActionEvent) event);
        }
    }


    public void switchToMainMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/mainMenu.fxml");
    }

    public void switchToGsmMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/gsmUI.fxml");
    }

    public void switchToHrmMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/hrmUI.fxml");
    }

    public void switchToCollaboratorMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/collaboratorUI.fxml");
    }

}
