package pt.ipp.isep.dei.esoft.project.javaFX.gsmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.ToDoListController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddToDoEntry_Controller implements Initializable {

    private Stage stage;
    private ToDoListController controller = new ToDoListController();
    private SendErrors sendErrors = new SendErrors();
    private ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private InformationAlerts sendInformation = new InformationAlerts();

    @FXML
    private ChoiceBox<String> choiceBox_greenspace;

    @FXML
    private ChoiceBox<String> choiceBox_urgency;


    @FXML
    private TextField field_days, field_description, field_title;

    @FXML
    private Label email_label;

    @FXML
    private TableView<ToDoEntry> table;

    @FXML
    private TableColumn<ToDoEntry, Integer> table_expectedDuration;

    @FXML
    private TableColumn<ToDoEntry, String> table_description;

    @FXML
    private TableColumn<ToDoEntry, String> table_greenSpace;

    @FXML
    private TableColumn<ToDoEntry, String> table_title;

    @FXML
    private TableColumn<ToDoEntry, ToDoEntry.Urgency> table_urgency;

    private String title, description;
    private int days, urgency, greenSpaceId;
    private final ArrayList<GreenSpace> greenSpaceList = (ArrayList<GreenSpace>) controller.getGreenSpacesByResponsible();
    private final ToDoEntry.Urgency[] urgencyList = ToDoEntry.Urgency.values();


    ObservableList<ToDoEntry> list = FXCollections.observableArrayList(controller.getToDoEntries());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox_greenspace.getItems().addAll(getGreenSpaces());
        choiceBox_urgency.getItems().addAll(getUrgency());

        table_title.setCellValueFactory(new PropertyValueFactory<ToDoEntry, String>("title"));
        table_description.setCellValueFactory(new PropertyValueFactory<ToDoEntry, String>("description"));
        table_greenSpace.setCellValueFactory(new PropertyValueFactory<ToDoEntry, String>("greenSpace"));
        table_expectedDuration.setCellValueFactory(new PropertyValueFactory<ToDoEntry, Integer>("expectedDuration"));
        table_urgency.setCellValueFactory(new PropertyValueFactory<ToDoEntry, ToDoEntry.Urgency>("urgency"));
        table.setItems(list);
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }

    private String[] getGreenSpaces() {
        String[] greens = new String[controller.getGreenSpacesByResponsible().size()];
        for (GreenSpace s : greenSpaceList) {
            greens[greenSpaceList.indexOf(s)] = s.getName();
        }
        return greens;
    }

    private String[] getUrgency() {
        String[] urgency = new String[urgencyList.length];
        for (ToDoEntry.Urgency s : urgencyList) {
            urgency[s.ordinal()] = s.toString();
        }
        return urgency;
    }


    public void submitRegistration(ActionEvent event) {
        boolean success = getValues();
        if (success) {
            try {
                Optional<ToDoEntry> toDoEntry = controller.registerToDoEntry(greenSpaceId, title, description, urgency, days);
                list = FXCollections.observableArrayList(controller.getToDoEntries());
                table.setItems(list);
                if (toDoEntry.isPresent()) {
                    sendConfirmation.confirmationMessages("Success", "ToDo entry successfully registered!", "");
                } else {
                    sendInformation.informationMessages("ATTENTION", "ToDo entry not registered - Already registered!", "");
                }
            } catch (IllegalArgumentException e) {
                sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
            }
        }

    }

    private boolean getValues() {
        try {
            days = Integer.parseInt(field_days.getText());
            title = field_days.getText();
            description = field_description.getText();
            urgency = choiceBox_urgency.getSelectionModel().getSelectedIndex();
            greenSpaceId = choiceBox_greenspace.getSelectionModel().getSelectedIndex();
            if (urgency == -1 || greenSpaceId == -1) {
                throw new IllegalArgumentException();
            }
            return true;
        } catch (NumberFormatException e) {
            String title = "Invalid Input days";
            String header = "Invalid Days! Make sure only put numbers...";
            String content = "Enter a new one:";
            sendErrors.errorMessages(title, header, content);
            return false;

        } catch (IllegalArgumentException e) {
            String title = "Invalid Urgency/Green Space";
            String header = "Invalid Urgency/Green Space! Make sure to make a selection... ";
            String content = "Enter a new one:";
            sendErrors.errorMessages(title, header, content);
            return false;
        }
    }

    public void clear(ActionEvent event) {
        field_days.clear();
        field_description.clear();
        field_title.clear();
        choiceBox_greenspace.getSelectionModel().clearSelection();
        choiceBox_urgency.getSelectionModel().clearSelection();
    }


    //------------------------------------ Options Side Bar --------------------------
    public void switchGSMMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gsmUI.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/addGreenSpace.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToAddEntryAgenda(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/addEntryAgenda.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToAssignTeam(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/assignTeamToAgendaTask.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/assignVehicleToAgendaTask.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToPsotponeTask(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/postponeTask.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToCancelTask(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cancelTask.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToMyGreenSpaces(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/myGreenSpaces.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

