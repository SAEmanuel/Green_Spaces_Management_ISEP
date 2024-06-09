package pt.ipp.isep.dei.esoft.project.javaFX.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

// Class for managing confirmation alerts
public class ConfirmationAlerts {

    // Displays a confirmation alert message
    public void confirmationMessages(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    // Displays a confirmation alert message and waits for user response
    public void confirmationMessagesAndW8(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Displays a confirmation alert message and returns the alert object
    public Alert confirmationMessagesGiveAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    // Displays a confirmation alert message and returns true if user selects OK, false otherwise
    public boolean confirmationMessagesGiveReturn(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert.showAndWait().get() == ButtonType.OK;
    }

}
