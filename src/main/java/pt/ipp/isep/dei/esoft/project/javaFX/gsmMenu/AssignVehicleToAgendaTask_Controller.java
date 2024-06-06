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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.repository.AgendaRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AssignVehicleToAgendaTask_Controller implements Initializable {

    private Stage stage;


    private SendErrors sendErrors = new SendErrors();
    private ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private InformationAlerts sendInformation = new InformationAlerts();

    private AgendaController controller = new AgendaController();
    private Repositories repositories = Repositories.getInstance();
    private AgendaRepository agendaRepository = repositories.getAgenda();


    @FXML
    private ChoiceBox<String> choiceBox_task;

    @FXML
    private ChoiceBox<String> choiceBox_vehicle;

    @FXML
    private TableView<AgendaEntry> table;

    @FXML
    private TableColumn<AgendaEntry, String> table_task;

    @FXML
    private TableColumn<AgendaEntry, String> table_satrtingDate;

    @FXML
    private TableColumn<AgendaEntry, String> table_vehicles;

    private int vehicleID, taskID;
    private Vehicle vehicle;
    private AgendaEntry agendaEntry;

    private final String RESPONSIBLE = repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    private List<AgendaEntry> agendaEntryList = agendaRepository.getAgendaEntriesForResponsibleTeam(RESPONSIBLE);
    private List<Vehicle> vehicleList = controller.getAvailableVehicles();

    ObservableList<AgendaEntry> list = FXCollections.observableArrayList(agendaEntryList);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox_vehicle.getItems().addAll(getVehicles());
        choiceBox_task.getItems().addAll(getTask());

        table_task.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("agendaEntry"));
        table_satrtingDate.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("starting_Date"));
        table_vehicles.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("stringVehicle"));
        table.setItems(list);
    }


    public void submitRegistration(ActionEvent event) {
        try {
            getValues();
            vehicle = vehicleList.get(vehicleID);
            agendaEntry = agendaEntryList.get(taskID);
            boolean toDoEntry = controller.assignVehicle(agendaEntry,vehicle);

            list = FXCollections.observableArrayList(agendaEntryList);
            table.setItems(list);
            getInfos();
            if (toDoEntry) {
                sendConfirmation.confirmationMessages("Success", "ToDo entry successfully registered!", "");
            } else {
                sendInformation.informationMessages("ATTENTION", "ToDo entry not registered - Already registered!", "");
            }
        } catch (IllegalArgumentException e) {
            sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
        }
    }



    public void clear(ActionEvent event) {
        choiceBox_task.getSelectionModel().clearSelection();
        choiceBox_vehicle.getSelectionModel().clearSelection();

    }

    private void getInfos() {
        choiceBox_task.getSelectionModel().clearSelection();
        choiceBox_vehicle.getSelectionModel().clearSelection();
        choiceBox_task.getItems().remove(taskID);
        choiceBox_vehicle.getItems().remove(vehicleID);

    }

    private void getValues() {
        taskID = choiceBox_task.getSelectionModel().getSelectedIndex();
        vehicleID = choiceBox_vehicle.getSelectionModel().getSelectedIndex();
        if (taskID == -1 || vehicleID == -1) {
            throw new IllegalArgumentException("Task or vehicle field must been fill!");
        }
    }

    private String[] getTask() {
        String[] taskEntries = new String[agendaEntryList.size()];
        for (int i = 0; i < agendaEntryList.size(); i++) {
            taskEntries[i] = "Task " + (i + 1) + ":  [" + agendaEntryList.get(i).getAgendaEntry().getTitle() + " | " + agendaEntryList.get(i).getAgendaEntry().getUrgency() + " | " + agendaEntryList.get(i).getAgendaEntry().getGreenSpace().getName() + " | " + agendaEntryList.get(i).getAgendaEntry().getStatus() + "]\n                - Starting Date:    " + agendaEntryList.get(i).getStarting_Date() + " \n                - End Date:           " + agendaEntryList.get(i).getExpected_end_Date();
        }
        return taskEntries;
    }

    private String[] getVehicles() {
        String[] team = new String[vehicleList.size()];
        for (int i = 0; i < vehicleList.size(); i++) {
            team[i] = vehicleList.get(i).toString();
        }
        return team;
    }







    //------------------------------------ Options Side Bar --------------------------
    public void switchGSMMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gsmUI.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/addGreenSpace.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/addToDoEntry.fxml")));
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

