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
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.AgendaRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CancelTask_Controller implements Initializable {

    private Stage stage;

    private SendErrors sendErrors = new SendErrors();
    private ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private InformationAlerts sendInformation = new InformationAlerts();
    private final SwitchWindows switchWindows = new SwitchWindows();

    private AgendaController controller = new AgendaController();
    private Repositories repositories = Repositories.getInstance();
    private AgendaRepository agendaRepository = repositories.getAgenda();
    private final String RESPONSIBLE = repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    private List<AgendaEntry> agendaEntryList = agendaRepository.getAgendaEntriesForResponsibleTeam(RESPONSIBLE);

    @FXML
    private Label email_label;

    @FXML
    private ChoiceBox<AgendaEntry> choiceBox_task;

    @FXML
    private DatePicker choice_data;

    @FXML
    private TableView<AgendaEntry> table_endDate;

    @FXML
    private TableColumn<AgendaEntry, String> table_task;

    @FXML
    private TableColumn<AgendaEntry, String> table_satrtingDate;

    @FXML
    private TableColumn<AgendaEntry, String> table_size;

    @FXML
    private TableColumn<AgendaEntry, AgendaEntry.Status> table_status;

    private int taskID;


    ObservableList<AgendaEntry> list = FXCollections.observableArrayList(agendaEntryList);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox_task.getItems().addAll(agendaEntryList);

        table_task.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("agendaEntry"));
        table_satrtingDate.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("starting_Date"));
        table_size.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("expected_end_Date"));
        table_status.setCellValueFactory(new PropertyValueFactory<AgendaEntry, AgendaEntry.Status>("status"));
        table_endDate.setItems(list);
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }


    public void submitRegistration(ActionEvent event) {
        try {
            getValues();
            boolean toDoEntry = controller.cancelTask(taskID);

            table_endDate.getItems().clear();
            table_endDate.getItems().addAll(agendaEntryList);
            getInfos();
            if (toDoEntry) {
                sendConfirmation.confirmationMessages("Success", "Task successfully canceled!", "");
            } else {
                sendInformation.informationMessages("ATTENTION", "Cancel task not registered - Already registered!", "");
            }
        } catch (IllegalArgumentException e) {
            sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
        }
    }


    public void clear(ActionEvent event) {
        choiceBox_task.getSelectionModel().clearSelection();

    }


    private void getInfos() {
        choiceBox_task.getSelectionModel().clearSelection();
        choiceBox_task.getItems().remove(taskID);

    }

    private void getValues() {
        try {
            taskID = choiceBox_task.getSelectionModel().getSelectedIndex();
            if (taskID == -1) {
                throw new IllegalArgumentException("Task field must been fill!");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Enter a valid date... Make sure is not null or the format is correct (dd-mm-yyyy)");
        }
    }


    //------------------------------------ Options Side Bar --------------------------

    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignVehicleToAgendaTask.fxml");
    }

    public void changeToAddEntryAgenda(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addEntryAgenda.fxml");
    }

    public void switchGSMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/gsmUI.fxml");
    }

    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addGreenSpace.fxml");
    }

    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addToDoEntry.fxml");
    }

    public void changeToAssignTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignTeamToAgendaTask.fxml");
    }

    public void changeToPsotponeTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/postponeTask.fxml");
    }

    public void changeToCancelTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/cancelTask.fxml");
    }

    public void changeToMyGreenSpaces(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/myGreenSpaces.fxml");
    }

}

