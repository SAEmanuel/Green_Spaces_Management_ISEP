package pt.ipp.isep.dei.esoft.project.javaFX;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.authorization.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.console.authorization.AuthenticationUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Login_Controller implements Initializable {
    AuthenticationController ctrl = new AuthenticationController();

    @FXML
    private AnchorPane loginScene;
    @FXML
    private CheckBox rememberMe;
    @FXML
    private TextField userEmail;
    @FXML
    PasswordField userPassword;
    @FXML
    private Button cancelbtn, submitBtn;

    private Stage stage;
    private String email, password;
    private final String GSMMAIL = "gsm";
    private String emailCheckBox = "gsm@this.app";
    private String passwordCheckBox = "gsm";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userEmail.setText(emailCheckBox);
        userPassword.setText(passwordCheckBox);
    }


    public void submit(ActionEvent event) throws IOException {
        email = userEmail.getText();
        password = userPassword.getText();

        if (ctrl.doLogin(email, password)) {
            verificaUSR(email, event);
        } else {
            alertBox();
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



    public void switchToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToGsmMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gsmUI.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    private void verificaUSR(String email, Event event) throws IOException {
        String[] emailSplit = email.split("@");
        if (emailSplit[0].equals(GSMMAIL)) {
            switchToGsmMenu((ActionEvent) event);
        }
    }

    private void alertBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Credentials");
        alert.setHeaderText("Invalid UserId and/or Password.");
        alert.showAndWait();
        userEmail.clear();
        userPassword.clear();
    }
}
