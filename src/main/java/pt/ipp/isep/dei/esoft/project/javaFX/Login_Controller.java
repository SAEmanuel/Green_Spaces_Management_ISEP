package pt.ipp.isep.dei.esoft.project.javaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Login_Controller {

    @FXML
    private AnchorPane loginScene;

    @FXML
    private TextField userEmail;
    @FXML
    PasswordField userPassword;
    @FXML
    private Button cancelbtn, submitBtn;

    private Stage stage;
    private String email, password;


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

    public void submit(ActionEvent event) throws IOException {
        email = userEmail.getText();
        password = userPassword.getText();

        System.out.println(email +" " + password);
        switchToGsmMenu(event);

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

}
