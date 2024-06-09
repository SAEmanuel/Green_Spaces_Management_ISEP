package pt.ipp.isep.dei.esoft.project.javaFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.ui.Bootstrap;
import pt.ipp.isep.dei.esoft.project.ui.Main;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    private final ConfirmationAlerts confirmationAlerts = new ConfirmationAlerts();
    private final InformationAlerts informationAlerts = new InformationAlerts();

    /**
     * Starts the JavaFX application.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainMenu.fxml")));
        Scene scene = new Scene(root,1000,600);
        stage.setTitle("MusgoSublime | GS Management");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Logo_black.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();



        stage.setOnCloseRequest(event -> {
            event.consume();
            logout(stage);
        });
    }

    /**
     * Logs out the user.
     *
     * @param stage The primary stage for the application.
     */
    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("Are you sure you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            saveInformation();
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
     * The main method of the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }

}