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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ConsultTasks_Controller implements Initializable {

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final Repositories repositories = Repositories.getInstance();
    private final AgendaRepository agendaRepository = repositories.getAgenda();
    private final CreateCollaboratorController controller = new CreateCollaboratorController();


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
    List<Collaborator> collaborators = repositories.getCollaboratorRepository().getCollaboratorList();
    private final Collaborator currentCollaborator = getLoggedInCollaborator(collaborators);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        filterSelection.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1 && startingDateLimit.getValue() != null && endingDateLimit.getValue() != null) {
                getValues();
            }
        });

        startingDateLimit.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (filterSelection.getSelectionModel().getSelectedIndex() != -1 && newValue != null && endingDateLimit.getValue() != null) {
                getValues();
            }
        });

        endingDateLimit.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (filterSelection.getSelectionModel().getSelectedIndex() != -1 && startingDateLimit.getValue() != null && newValue != null) {
                getValues();
            }
        });

        taskSelection.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && newSelection.intValue() >= 0) {
                handleTaskSelection(newSelection.intValue());
            }
        });
    }


    private void loadAllTasks() {
        Data minDate = new Data(1900, 1, 1);
        Data maxDate = new Data(2100, 12, 31);
        List<AgendaEntry> allTasks = agendaRepository.getTaskList(currentCollaborator, minDate, maxDate, 1);

        ObservableList<AgendaEntry> tasksObservableList = FXCollections.observableArrayList(allTasks);
        tasksTableView.setItems(tasksObservableList);
    }


    private void getValues() {
        filterSelected = filterSelection.getSelectionModel().getSelectedIndex() + 1;
        getStartDate();
        getEndDate();

        if (startingDateLimit.getValue() == null || endingDateLimit.getValue() == null) {
            showAlert();
            return;
        }

        if (filterSelected == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Task Completion");
            alert.setHeaderText("Do you wish to conclude a task?");

            ButtonType buttonTypeOK = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeOK) {
                taskSelection.getSelectionModel().clearSelection();
                filterSelected += 1;
                List<AgendaEntry> plannedTasksList = agendaRepository.getPossibleDoneTaskPlannedList(currentCollaborator, startDateInput, endingDateInput, filterSelected);
                ObservableList<AgendaEntry> plannedTasks = FXCollections.observableArrayList(plannedTasksList);
                tasksTableView.setItems(plannedTasks);
                taskSelection.setDisable(false);

                List<String> taskNames = plannedTasksList.stream().map(AgendaEntry::getTaskName).distinct().collect(Collectors.toList());

                if (!taskNames.isEmpty()) {
                    taskSelection.setItems(FXCollections.observableArrayList(taskNames));
                }
            } else {
                taskSelection.getSelectionModel().clearSelection();
                filterSelected += 1;
                List<AgendaEntry> plannedTasksList = agendaRepository.getTaskPlannedList(currentCollaborator, startDateInput, endingDateInput, filterSelected);
                ObservableList<AgendaEntry> plannedTasks = FXCollections.observableArrayList(plannedTasksList);
                tasksTableView.setItems(plannedTasks);
            }
        } else if (filterSelected == 2) {
            filterSelected += 1;
            taskSelection.setDisable(true);
            List<AgendaEntry> tasksList = agendaRepository.getTaskList(currentCollaborator, startDateInput, endingDateInput, filterSelected);
            ObservableList<AgendaEntry> tasks = FXCollections.observableArrayList(tasksList);
            tasksTableView.setItems(tasks);
        } else if (filterSelected == 3) {
            filterSelected += 1;
            taskSelection.setDisable(true);
            List<AgendaEntry> tasksList = agendaRepository.getTaskList(currentCollaborator, startDateInput, endingDateInput, filterSelected);
            for (AgendaEntry entry : tasksList) {
                System.out.println("Task: " + entry.getTaskName() + ", Vehicle: " + entry.getVehicles());
            }

            ObservableList<AgendaEntry> tasks = FXCollections.observableArrayList(tasksList);
            tasksTableView.setItems(tasks);

        }
        filterSelection.getSelectionModel().clearSelection();
    }

    private void handleTaskSelection(int taskIndex) {
        if (taskIndex >= 0) {
            String confirmation = "y";
            Optional<List<AgendaEntry>> mutatedTasks = agendaRepository.changedTaskStatusList(currentCollaborator, startDateInput, endingDateInput, filterSelected, confirmation, taskIndex);
            if (mutatedTasks.isPresent()) {
                ObservableList<AgendaEntry> mutatedTasksList = FXCollections.observableArrayList(mutatedTasks.get());
                tasksTableView.getItems().setAll(mutatedTasksList);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Task status updated successfully!");
                successAlert.showAndWait();
                tasksTableView.setItems(mutatedTasksList);
                taskSelection.getItems().remove(taskIndex);
                taskSelection.getSelectionModel().clearSelection();
            }
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please select both start and end dates.");
        alert.showAndWait();
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
            throw new IllegalArgumentException("Enter a valid date... Make sure it is not null and the format is correct (dd-mm-yyyy)");
        }
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
            throw new IllegalArgumentException("Enter a valid date... Make sure it is not null and the format is correct (dd-mm-yyyy)");
        }
    }


    public void switchToCollaboratorMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/collaboratorUI.fxml");
    }
}
