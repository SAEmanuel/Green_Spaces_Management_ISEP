package pt.ipp.isep.dei.esoft.project.javaFX.gsmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.AgendaController;
import pt.ipp.isep.dei.esoft.project.domain.AgendaEntry;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.ToDoEntry;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.ToDoListRepository;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddEntryAgenda_Controller implements Initializable {

    private AgendaController controller = new AgendaController();
    private ToDoListRepository repository = Repositories.getInstance().getToDoRepository();
    private final SwitchWindows switchWindows = new SwitchWindows();

    private SendErrors sendErrors = new SendErrors();
    private ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private InformationAlerts sendInformation = new InformationAlerts();

    @FXML
    private TableView<ToDoEntry> table;

    @FXML
    private Label email_label;

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

    @FXML
    private DatePicker startingDate;
    @FXML
    private ChoiceBox<String> toDo_Option;

    private Data dataInput;
    private int toDoEntry;

    private final String responsible = Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();
    private final List<ToDoEntry> toDoListForResponsible = repository.getToDoListForResponsible(responsible);
    private Stage stage;

    ObservableList<ToDoEntry> list = FXCollections.observableArrayList(toDoListForResponsible);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] toDoList = new String[toDoListForResponsible.size()];
        for (int i = 0; i < toDoListForResponsible.size(); i++) {
            toDoList[i] = toDoListForResponsible.get(i).getTitle() + " | " + toDoListForResponsible.get(i).getGreenSpace().getName();
        }
        toDo_Option.getItems().addAll(toDoList);

        table_title.setCellValueFactory(new PropertyValueFactory<ToDoEntry, String>("title"));
        table_description.setCellValueFactory(new PropertyValueFactory<ToDoEntry, String>("description"));
        table_greenSpace.setCellValueFactory(new PropertyValueFactory<ToDoEntry, String>("parkName"));
        table_expectedDuration.setCellValueFactory(new PropertyValueFactory<ToDoEntry, Integer>("expectedDuration"));
        table_urgency.setCellValueFactory(new PropertyValueFactory<ToDoEntry, ToDoEntry.Urgency>("urgency"));
        table.setItems(list);
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());

    }

    /**
     * Submits the registration of a new entry to the agenda.
     *
     * @param event the action event
     */
    public void submitRegistration(ActionEvent event) {
        try {
            getDate();
            Optional<AgendaEntry> agendaEntry = controller.registerAgendaEntry(toDoEntry, dataInput);
            if (agendaEntry.isPresent()) {
                sendConfirmation.confirmationMessages("Success", "Green Space successfully registered!", "");
            } else {
                sendInformation.informationMessages("ATTENTION", "Green Space not registered - Already registered!", "");
            }
            list = FXCollections.observableArrayList(repository.getToDoListForResponsible(responsible));
            table.setItems(list);
            getInfos();
        } catch (IllegalArgumentException e) {
            sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
        }

    }

    /**
     * Retrieves the date input by the user.
     */
    private void getDate() {

        try {
            LocalDate myDate = startingDate.getValue();
            String[] date = myDate.toString().split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            dataInput = new Data(year, month, day);
            toDoEntry =toDo_Option.getSelectionModel().getSelectedIndex();
            if (toDoEntry == -1) {
                throw new IllegalArgumentException("Select a toDo entry!");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Enter a valid date... Make sure is not null or the format is correct (dd-mm-yyyy)");
        }
    }


    /**
     * Retrieves information about the selected to-do entry and updates the UI accordingly.
     */
    private void getInfos() {
        int selection = toDo_Option.getSelectionModel().getSelectedIndex();
        startingDate.setValue(null);
        toDo_Option.getSelectionModel().clearSelection();
        toDo_Option.getItems().remove(selection);
    }

    /**
     * Clears the selected date and to-do entry.
     *
     * @param event the action event
     */
    public void clear(ActionEvent event) {
        startingDate.setValue(null);
        toDo_Option.getSelectionModel().clearSelection();
    }


    //------------------------------------ Options Side Bar --------------------------

    /**
     * Switches to the GSM Menu UI when triggered by an action event.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void switchGSMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/gsmUI.fxml");
    }

    /**
     * Switches to the Add Green Space UI when triggered by an action event.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addGreenSpace.fxml");
    }

    /**
     * Switches to the Entry To-Do List UI when triggered by an action event.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addToDoEntry.fxml");
    }

    /**
     * Switches to the Assign Team UI when triggered by an action event.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToAssignTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignTeamToAgendaTask.fxml");
    }

    /**
     * Switches to the Assign Vehicle UI when triggered by an action event.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignVehicleToAgendaTask.fxml");
    }

    /**
     * Switches to the Postpone Task UI when triggered by an action event.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToPsotponeTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/postponeTask.fxml");
    }

    /**
     * Switches to the Cancel Task UI when triggered by an action event.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToCancelTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/cancelTask.fxml");
    }

    /**
     * Switches to the My Green Spaces UI when triggered by an action event.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToMyGreenSpaces(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/myGreenSpaces.fxml");
    }

}

