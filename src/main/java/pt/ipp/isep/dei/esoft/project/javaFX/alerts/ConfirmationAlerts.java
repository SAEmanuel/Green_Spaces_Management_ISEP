package pt.ipp.isep.dei.esoft.project.javaFX.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmationAlerts {

    public void confirmationMessages(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public Alert confirmationMessagesGiveAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
        return alert;
    }

    public boolean confirmationMessagesGiveReturn(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert.showAndWait().get() == ButtonType.OK;
    }

}
