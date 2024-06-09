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
import javafx.scene.control.Label;
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
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.AgendaRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AssignVehicleToAgendaTask_Controller implements Initializable {

    private Stage stage;
    private int vehicleID, taskID;
    private Vehicle vehicle;

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final SendErrors sendErrors = new SendErrors();
    private final ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private final InformationAlerts sendInformation = new InformationAlerts();
    private final AgendaController controller = new AgendaController();
    private final Repositories repositories = Repositories.getInstance();
    private final AgendaRepository agendaRepository = repositories.getAgenda();
    private final String RESPONSIBLE = repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();

    private List<AgendaEntry> agendaEntryList = agendaRepository.getAgendaEntriesForResponsibleVehicle(RESPONSIBLE);
    private List<Vehicle> vehicleList = controller.getAvailableVehicles();

    @FXML
    private ChoiceBox<String> choiceBox_task;

    @FXML
    private ChoiceBox<Vehicle> choiceBox_vehicle;

    @FXML
    private TableView<AgendaEntry> table;

    @FXML
    private TableColumn<AgendaEntry, String> table_task;

    @FXML
    private TableColumn<AgendaEntry, String> table_satrtingDate;

    @FXML
    private TableColumn<AgendaEntry, String> table_vehicles;

    @FXML
    private Label email_label;

    ObservableList<AgendaEntry> list = FXCollections.observableArrayList(agendaEntryList);

    /**
     * Initializes the controller class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox_vehicle.getItems().addAll(vehicleList);
        choiceBox_task.getItems().addAll(getTask());

        table_task.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("agendaEntry"));
        table_satrtingDate.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("starting_Date"));
        table_vehicles.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("stringVehicle"));
        table.setItems(list);
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }

    /**
     * Handles the submission of vehicle assignment to a task.
     * @param event The event that triggered this action.
     */
    public void submitRegistration(ActionEvent event) {
        try {
            getValues();

            boolean toDoEntry = controller.assignVehicle(agendaEntryList.get(taskID),vehicle);

            getInfos();

            table.getItems().clear();
            table.getItems().addAll(agendaEntryList);
            if (toDoEntry) {
                sendConfirmation.confirmationMessages("Success", "Vehicle successfully assigned to the task.!", "");
            } else {
                sendInformation.informationMessages("ATTENTION", "Vehicle not assigned - Already assigned to a task.!", "");
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
        choiceBox_vehicle.getSelectionModel().clearSelection();

    }

    /**
     * Clears the selected options and updates the task list.
     */
    private void getInfos() {
        choiceBox_task.getSelectionModel().clearSelection();
        choiceBox_vehicle.getSelectionModel().clearSelection();
        choiceBox_vehicle.getItems().remove(vehicleID);

    }

    /**
     * Retrieves the selected values from the UI.
     */
    private void getValues() {
        taskID = choiceBox_task.getSelectionModel().getSelectedIndex();
        vehicleID = choiceBox_vehicle.getSelectionModel().getSelectedIndex();
        if (taskID == -1 || vehicleID == -1) {
            throw new IllegalArgumentException("Task or vehicle field must been fill!");
        }
        vehicle = choiceBox_vehicle.getSelectionModel().getSelectedItem();
    }


    /**
     * Retrieves task entries.
     */
    private String[] getTask() {
        String[] taskEntries = new String[agendaEntryList.size()];
        for (int i = 0; i < agendaEntryList.size(); i++) {
            taskEntries[i] = "Task " + (i + 1) + ":  [" + agendaEntryList.get(i).getAgendaEntry().getTitle() + " | " + agendaEntryList.get(i).getAgendaEntry().getUrgency() + " | " + agendaEntryList.get(i).getAgendaEntry().getGreenSpace().getName() + " | " + agendaEntryList.get(i).getAgendaEntry().getStatus() + "]\n                - Starting Date:    " + agendaEntryList.get(i).getStarting_Date() + " \n                - End Date:           " + agendaEntryList.get(i).getExpected_end_Date();
        }
        return taskEntries;
    }




    //------------------------------------ Options Side Bar --------------------------

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

