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
import pt.ipp.isep.dei.esoft.project.application.Serialization;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.ui.Main;

import java.io.IOException;


public class MainMenu_Controller {

    private final ConfirmationAlerts confirmationAlerts = new ConfirmationAlerts();
    private final InformationAlerts informationAlerts = new InformationAlerts();
    private final SwitchWindows switchWindows = new SwitchWindows();


    @FXML
    private AnchorPane mainMenuScene;


    private Stage stage;


    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("Are you sure you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            saveInformation();
            stage = (Stage) mainMenuScene.getScene().getWindow();
            stage.close();
        }
    }

    private void saveInformation() {
        if (confirmationAlerts.confirmationMessagesGiveAlert("Save Information", "Do you wish to save?", "").showAndWait().get() == ButtonType.OK) {
            try {
                Main.saveAppInformation();
            }catch (Exception e){
                informationAlerts.informationMessages("Something go wrong","App information cannot be saved, check: "+ e.getMessage(),"");
            }
        } else {
            informationAlerts.informationMessages("Information","No information saved...","");
        }
    }

    public void switchToDevTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/devTeamMenu.fxml");

    }

    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/login.fxml");
    }


}
