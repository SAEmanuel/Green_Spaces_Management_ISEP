package pt.ipp.isep.dei.esoft.project.javaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GsmUI_Controller {

    @FXML
    private AnchorPane side_anchorpane;
    @FXML
    private Pane search_bar,inf_one,inf_two,inf_3;
    @FXML
    private Button back_btn;


    private Stage stage;


    public void switchToLoginMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }










    //------------------------------------ Options Side Bar --------------------------
    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/addGreenSpace.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/addToDoEntry.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToAddEntryAgenda(ActionEvent event) {
    }

    public void changeToAssignTeam(ActionEvent event) {
    }

    public void changeToAssignVehicle(ActionEvent event) {
    }

    public void changeToPsotponeTask(ActionEvent event) {
    }

    public void changeToCancelTask(ActionEvent event) {
    }

    public void changeToMyGreenSpaces(ActionEvent event) {
    }
}
