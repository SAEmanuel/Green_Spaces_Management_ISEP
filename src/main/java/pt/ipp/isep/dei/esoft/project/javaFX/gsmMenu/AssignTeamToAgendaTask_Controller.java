package pt.ipp.isep.dei.esoft.project.javaFX.gsmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.AgendaRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AssignTeamToAgendaTask_Controller implements Initializable {

    private SendErrors sendErrors = new SendErrors();
    private ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private InformationAlerts sendInformation = new InformationAlerts();
    private final SwitchWindows switchWindows = new SwitchWindows();

    private AgendaController controller = new AgendaController();
    private Repositories repositories = Repositories.getInstance();
    private AgendaRepository agendaRepository = repositories.getAgenda();
    private Stage stage;

    @FXML
    private Label email_label;

    @FXML
    private ChoiceBox<String> choiceBox_email;

    @FXML
    private ChoiceBox<String> choiceBox_task;

    @FXML
    private ChoiceBox<String> choiceBox_team;

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

    private int teamID, taskID;
    private String emailService;

    private final String RESPONSIBLE = repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    private List<AgendaEntry> agendaEntryList = agendaRepository.getAgendaEntriesForResponsibleVehicle(RESPONSIBLE);


    ObservableList<AgendaEntry> list = FXCollections.observableArrayList(agendaEntryList);

    /**
     * Initializes the controller class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] eS = getEmailService();
        if(eS != null)
            choiceBox_email.getItems().addAll(eS);

        choiceBox_team.getItems().addAll(getTeams());
        choiceBox_task.getItems().addAll(getTask());

        table_task.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("agendaEntry"));
        table_satrtingDate.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("starting_Date"));
        table_size.setCellValueFactory(new PropertyValueFactory<AgendaEntry, String>("expected_end_Date"));
        table_status.setCellValueFactory(new PropertyValueFactory<AgendaEntry, AgendaEntry.Status>("status"));
        table_endDate.setItems(list);
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }

    /**
     * Handles the submission of team assignment.
     * @param event The event that triggered this action.
     */
    public void submitRegistration(ActionEvent event) {
        try {
            getValues();
            boolean toDoEntry = controller.assignTeam(teamID, taskID, emailService, controller.getResponsible());

            list = FXCollections.observableArrayList(controller.getAgendaEntriesForResponsibleTeam());
            table_endDate.setItems(list);
            getInfos();
            if (toDoEntry) {
                sendConfirmation.confirmationMessagesAndW8("Success", "Team successfully assigned!", "");
                sendInformation.informationMessages("Information","Emails sent to collaborators","Check the Email file!");
            } else {
                sendInformation.informationMessages("ATTENTION", "Team not assigned - This task already have a team assigned!", "");
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
        choiceBox_email.getSelectionModel().clearSelection();
        choiceBox_task.getSelectionModel().clearSelection();
        choiceBox_team.getSelectionModel().clearSelection();

    }

    /**
     * Clears the selected options and updates the task list.
     */
    private void getInfos() {
        choiceBox_email.getSelectionModel().clearSelection();
        choiceBox_task.getSelectionModel().clearSelection();
        choiceBox_team.getSelectionModel().clearSelection();
        choiceBox_task.getItems().remove(taskID);

    }

    /**
     * Retrieves the selected values from the UI.
     */
    private void getValues() {
        emailService = choiceBox_email.getSelectionModel().getSelectedItem();
        taskID = choiceBox_task.getSelectionModel().getSelectedIndex();
        teamID = choiceBox_team.getSelectionModel().getSelectedIndex();
        if (taskID == -1 || teamID == -1 || emailService == null) {
            throw new IllegalArgumentException("Task, Team or Email Service must been fill!");
        }
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

    /**
     * Retrieves team names.
     */
    private String[] getTeams() {
        List<Team> list = controller.getTeams();
        String[] team = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            team[i] = list.get(i).toString();
        }
        return team;
    }

    /**
     * Retrieves email services.
     */
    private String[] getEmailService() {
        List<String> emailServices = controller.getEmailServices();
        if(emailServices == null){
            sendInformation.informationMessages("Email Services", "File not found", "Configuration File not found!..Add one in \"/src/main/resource\" location");
            return null;
        }
        String[] emails = new String[emailServices.size()];
        for (int i = 0; i < emailServices.size(); i++) {
            emails[i] = emailServices.get(i);
        }
        return emails;
    }


    //------------------------------------ Options Side Bar --------------------------

    /**
     * Changes the window to add a green space.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addGreenSpace.fxml");
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
     * Changes the window to the entry to-do list.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addToDoEntry.fxml");
    }

    /**
     * Changes the window to assign a vehicle to an agenda task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignVehicleToAgendaTask.fxml");
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

