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

/**
 * Controller class for the main menu functionality.
 */
public class MainMenu_Controller {

    private final ConfirmationAlerts confirmationAlerts = new ConfirmationAlerts();
    private final InformationAlerts informationAlerts = new InformationAlerts();
    private final SwitchWindows switchWindows = new SwitchWindows();


    @FXML
    private AnchorPane mainMenuScene;


    private Stage stage;

    /**
     * Handles the logout action.
     *
     * @param event The ActionEvent triggered by clicking the logout button.
     */
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

    /**
     * Saves application information.
     */
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

    /**
     * Switches to the development team menu.
     *
     * @param event The ActionEvent triggered by clicking the development team button.
     * @throws IOException If an I/O error occurs.
     */
    public void switchToDevTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/devTeamMenu.fxml");

    }

    /**
     * Switches to the login menu.
     *
     * @param event The ActionEvent triggered by clicking the login button.
     * @throws IOException If an I/O error occurs.
     */
    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/login.fxml");
    }
}
