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



    public void clear(ActionEvent event) {
        choiceBox_email.getSelectionModel().clearSelection();
        choiceBox_task.getSelectionModel().clearSelection();
        choiceBox_team.getSelectionModel().clearSelection();

    }

    private void getInfos() {
        choiceBox_email.getSelectionModel().clearSelection();
        choiceBox_task.getSelectionModel().clearSelection();
        choiceBox_team.getSelectionModel().clearSelection();
        choiceBox_task.getItems().remove(taskID);

    }

    private void getValues() {
        emailService = choiceBox_email.getSelectionModel().getSelectedItem();
        taskID = choiceBox_task.getSelectionModel().getSelectedIndex();
        teamID = choiceBox_team.getSelectionModel().getSelectedIndex();
        if (taskID == -1 || teamID == -1 || emailService == null) {
            throw new IllegalArgumentException("Task, Team or Email Service must been fill!");
        }
    }

    private String[] getTask() {
        String[] taskEntries = new String[agendaEntryList.size()];
        for (int i = 0; i < agendaEntryList.size(); i++) {
            taskEntries[i] = "Task " + (i + 1) + ":  [" + agendaEntryList.get(i).getAgendaEntry().getTitle() + " | " + agendaEntryList.get(i).getAgendaEntry().getUrgency() + " | " + agendaEntryList.get(i).getAgendaEntry().getGreenSpace().getName() + " | " + agendaEntryList.get(i).getAgendaEntry().getStatus() + "]\n                - Starting Date:    " + agendaEntryList.get(i).getStarting_Date() + " \n                - End Date:           " + agendaEntryList.get(i).getExpected_end_Date();
        }
        return taskEntries;
    }

    private String[] getTeams() {
        List<Team> list = controller.getTeams();
        String[] team = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            team[i] = list.get(i).toString();
        }
        return team;
    }

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

    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addGreenSpace.fxml");
    }

    public void changeToAddEntryAgenda(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addEntryAgenda.fxml");
    }

    public void switchGSMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/gsmUI.fxml");
    }

    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addToDoEntry.fxml");
    }

    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignVehicleToAgendaTask.fxml");
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

