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
    private List<AgendaEntry> agendaEntryList = agendaRepository.getAgendaEntriesForResponsibleVehicle(RESPONSIBLE);

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


    /**
     * Initializes the controller class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
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

    /**
     * Handles the submission of task cancellation.
     * @param event The event that triggered this action.
     */
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

    /**
     * Clears the selected options.
     * @param event The event that triggered this action.
     */
    public void clear(ActionEvent event) {
        choiceBox_task.getSelectionModel().clearSelection();

    }

    /**
     * Clears the selected options and updates the task list.
     */
    private void getInfos() {
        choiceBox_task.getSelectionModel().clearSelection();
        choiceBox_task.getItems().remove(taskID);

    }

    /**
     * Retrieves the selected values from the UI.
     */
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

    /**
     * Changes the window to assign a vehicle to an agenda task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignVehicleToAgendaTask.fxml");
    }

    /**
     * Changes the window to add an entry to the agenda.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAddEntryAgenda(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addEntryAgenda.fxml");
    }

    /**
     * Switches to the GSM menu window.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void switchGSMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/gsmUI.fxml");
    }

    /**
     * Changes the window to add a green space.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addGreenSpace.fxml");
    }

    /**
     * Changes the window to the entry to-do list.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addToDoEntry.fxml");
    }

    /**
     * Changes the window to assign a team to an agenda task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignTeamToAgendaTask.fxml");
    }

    /**
     * Changes the window to postpone a task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToPsotponeTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/postponeTask.fxml");
    }

    /**
     * Changes the window to cancel a task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToCancelTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/cancelTask.fxml");
    }

    /**
     * Changes the window to the user's green spaces.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToMyGreenSpaces(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/myGreenSpaces.fxml");
    }


}

