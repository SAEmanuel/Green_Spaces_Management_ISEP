package pt.ipp.isep.dei.esoft.project.javaFX;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DevTeam_Controller implements Initializable {
    @FXML
    private AnchorPane devTeamMenu;

    @FXML
    private ListView<String> devTeamList;
    private static final String member1 = "Emanuel Almeida   - 1230839@isep.ipp.pt";
    private static final String member2 = "Francisco Santos  - 1230564@isep.ipp.pt";
    private static final String member3 = "Jorge Ubaldo      - 1231274@isep.ipp.pt";
    private static final String member4 = "Paulo Mendes      - 1231498@isep.ipp.pt";
    private static final String member5 = "Romeu Xu          - 1230444@isep.ipp.pt";
    String[] teamNames = {member1, member2, member3, member4, member5};

    @FXML
    private Label nameMember;
    int nameIndex;

    private Stage stage;

    public void switchToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("You're about to cancel!");
        alert.setContentText("Are you sure you want to cancel?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) devTeamMenu.getScene().getWindow();
            stage.close();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        devTeamList.getItems().addAll(teamNames);
        devTeamList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String string, String t1) {
                nameIndex = devTeamList.getSelectionModel().getSelectedIndex();
                if (nameIndex == 0) {
                    nameMember.setText("Emanuel Almeida");
                } else if (nameIndex == 1) {
                    nameMember.setText("Francisco Santos");
                } else if (nameIndex == 2) {
                    nameMember.setText("Jorge Ubaldo");
                } else if (nameIndex == 3) {
                    nameMember.setText("Romeu Xu");
                } else if (nameIndex == 4) {
                    nameMember.setText("Paulo Mendes");
                } else {
                    nameMember.setText("Romeu Xu");
                }
            }
        });
    }
}