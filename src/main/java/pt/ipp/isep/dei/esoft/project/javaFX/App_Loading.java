package pt.ipp.isep.dei.esoft.project.javaFX;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.ui.Bootstrap;
import pt.ipp.isep.dei.esoft.project.ui.Main;

import java.io.IOException;
import java.util.Objects;

public class App_Loading extends Application {

    private final ConfirmationAlerts confirmationAlerts = new ConfirmationAlerts();
    private final InformationAlerts informationAlerts = new InformationAlerts();

    /**
     * Starts the loading stage.
     *
     * @param stage the primary stage
     * @throws IOException if an error occurs during loading
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Initialize loading stage
        Stage loadingStage = new Stage();
        loadingStage.initModality(Modality.APPLICATION_MODAL);
        loadingStage.initStyle(StageStyle.UNDECORATED);

        // Create and configure progress bar
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);
        progressBar.setPrefHeight(20);
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        // Create VBox to hold loading message and progress bar
        VBox vBox = new VBox();
        vBox.getChildren().addAll(new Label("Loading data from system..."), progressBar);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        // Create loading scene
        Scene loadingScene = new Scene(vBox, 300, 100);
        loadingStage.setScene(loadingScene);
        loadingStage.show();

        // Background task for loading
        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(3000); // Simulate loading delay of 3 seconds
                return null;
            }
        };

        // Event handler for task completion
        loadingTask.setOnSucceeded(event -> {
            loadingStage.close();
            try {
                // Initialize Bootstrap and load main menu
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.run();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mainMenu.fxml")));
                Scene scene = new Scene(root, 1000, 600);
                stage.setTitle("MusgoSublime | GS Management");
                Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/Logo_black.png")));
                stage.getIcons().add(icon);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

                // Event handler for window close request
                stage.setOnCloseRequest(event1 -> {
                    event1.consume();
                    logout(stage);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Start background loading task
        new Thread(loadingTask).start();
    }

    /**
     * Logs out the user.
     *
     * @param stage the primary stage
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
     * Saves information.
     */
    private void saveInformation() {
        if (confirmationAlerts.confirmationMessagesGiveAlert("Save Information", "Do you wish to save?", "").showAndWait().get() == ButtonType.OK) {
            try {
                Main.saveAppInformation();
            } catch (Exception e) {
                informationAlerts.informationMessages("Something go wrong", "App information cannot be saved, check: " + e.getMessage(), "");
            }
        } else {
            informationAlerts.informationMessages("Information", "No information saved...", "");
        }
    }


    /**
     * Main method.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }


}
