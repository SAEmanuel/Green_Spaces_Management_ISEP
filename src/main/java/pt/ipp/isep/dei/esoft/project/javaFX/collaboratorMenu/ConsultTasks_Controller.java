package pt.ipp.isep.dei.esoft.project.javaFX.collaboratorMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.AgendaRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ConsultTasks_Controller implements Initializable {

    private final Repositories repositories = Repositories.getInstance();
    private final AgendaRepository agendaRepository = repositories.getAgenda();
    private final SwitchWindows switchWindows = new SwitchWindows();
    private CreateCollaboratorController controller;


    private Collaborator getLoggedInCollaborator(List<Collaborator> collaborators) {
        String loggedInEmail = controller.getResponsible();
        for (Collaborator collaborator : collaborators) {
            if (collaborator.getEmailAddress().equals(loggedInEmail)) {
                return collaborator;
            }
        }
        return null;
    }


    @FXML
    Label email_label;

    @FXML
    private TableView<AgendaEntry> tasksTableView;

    @FXML
    private TableColumn<AgendaEntry, String> taskNameColumn;

    @FXML
    private TableColumn<AgendaEntry, String> teamNameColumn;

    @FXML
    private TableColumn<AgendaEntry, String> vehicleNameColumn;

    @FXML
    private TableColumn<AgendaEntry, String> startDateColumn;

    @FXML
    private TableColumn<AgendaEntry, String> expectedEndDateColumn;

    @FXML
    private TableColumn<AgendaEntry, String> realEndDateColumn;

    @FXML
    private TableColumn<AgendaEntry, String> statusColumn;

    @FXML
    private DatePicker startingDateLimit;

    @FXML
    private DatePicker endingDateLimit;

    @FXML
    private ChoiceBox<String> filterSelection;

    @FXML
    private ChoiceBox<String> taskSelection;

    private int filterSelected;
    private Data startDateInput;
    private Data endingDateInput;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new CreateCollaboratorController();
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());


        taskNameColumn.setCellValueFactory(cellData -> cellData.getValue().taskNameProperty());
        teamNameColumn.setCellValueFactory(cellData -> cellData.getValue().teamNameProperty());
        vehicleNameColumn.setCellValueFactory(cellData -> cellData.getValue().vehicleNameProperty());
        startDateColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        expectedEndDateColumn.setCellValueFactory(cellData -> cellData.getValue().expectedEndDateProperty());
        realEndDateColumn.setCellValueFactory(cellData -> cellData.getValue().realEndDateProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        List<String> statusList = Arrays.stream(AgendaEntry.Status.values()).map(Enum::toString).toList();
        filterSelection.setItems(FXCollections.observableArrayList(statusList));

        loadAllTasks();
    }

    private void loadAllTasks() {
        List<Collaborator> collaborators = repositories.getCollaboratorRepository().getCollaboratorList();
        Collaborator currentCollaborator = getLoggedInCollaborator(collaborators);
        Data minDate = new Data(1900, 1, 1);
        Data maxDate = new Data(2100, 12, 31);
        List<AgendaEntry> allTasks = agendaRepository.getTaskList(currentCollaborator, minDate, maxDate, 1);

        ObservableList<AgendaEntry> tasksObservableList = FXCollections.observableArrayList(allTasks);
        tasksTableView.setItems(tasksObservableList);
    }


    private void getStartDate() {

        try {
            LocalDate myDate = startingDateLimit.getValue();
            String[] date = myDate.toString().split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            startDateInput = new Data(year, month, day);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Enter a valid date... Make sure is not null or the format is correct (dd-mm-yyyy)");
        }
    }

    private void getValues() {
        filterSelected = filterSelection.getSelectionModel().getSelectedIndex() + 1;
        getStartDate();
        getEndDate();


    }


    private void getEndDate() {

        try {
            LocalDate myDate = endingDateLimit.getValue();
            String[] date = myDate.toString().split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            endingDateInput = new Data(year, month, day);

        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Enter a valid date... Make sure is not null or the format is correct (dd-mm-yyyy)");
        }
    }


    public void switchToCollaboratorMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/collaboratorUI.fxml");
    }
}
