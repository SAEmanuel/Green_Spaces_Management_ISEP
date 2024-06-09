package pt.ipp.isep.dei.esoft.project.javaFX.alerts;

import javafx.scene.control.Alert;

// Class for managing error alerts
public class SendErrors {

    // Displays an error alert message
    public void errorMessages(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
