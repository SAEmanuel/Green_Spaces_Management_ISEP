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


    /**
     * Changes the current window to the specified window.
     *
     * @param event the event that triggered the window change
     * @param window the path to the FXML file of the window to switch to
     * @throws IOException if an I/O error occurs
     */
    public void changeWindow(Event event, String window) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(window)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
