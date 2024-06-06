package pt.ipp.isep.dei.esoft.project.javaFX.extras;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SwitchWindows {



    public void changeWindow(Event event, String window) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(window)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
