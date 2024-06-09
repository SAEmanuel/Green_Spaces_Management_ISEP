package pt.ipp.isep.dei.esoft.project.javaFX.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class InformationAlerts {

    public void informationMessages(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }


    public boolean informationMessagesReturn(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert.showAndWait().get() == ButtonType.OK;
    }
}
