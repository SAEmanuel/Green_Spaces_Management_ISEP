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
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
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
    private final SwitchWindows switchWindows = new SwitchWindows();

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


    /**
     * Initializes the controller with the UI components and sets up the table view.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize choice boxes with available green spaces and urgencies
        choiceBox_greenspace.getItems().addAll(getGreenSpaces());
        choiceBox_urgency.getItems().addAll(getUrgency());

        // Set up the table view with columns and data
        table_title.setCellValueFactory(new PropertyValueFactory<ToDoEntry, String>("title"));
        table_description.setCellValueFactory(new PropertyValueFactory<ToDoEntry, String>("description"));
        table_greenSpace.setCellValueFactory(new PropertyValueFactory<ToDoEntry, String>("greenSpace"));
        table_expectedDuration.setCellValueFactory(new PropertyValueFactory<ToDoEntry, Integer>("expectedDuration"));
        table_urgency.setCellValueFactory(new PropertyValueFactory<ToDoEntry, ToDoEntry.Urgency>("urgency"));
        table.setItems(list);

        // Set the email label to display the current user's email
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }

    /**
     * Retrieves the names of available green spaces.
     *
     * @return An array containing the names of the available green spaces.
     */
    private String[] getGreenSpaces() {
        String[] greens = new String[greenSpaceList.size()];
        for (GreenSpace s : greenSpaceList) {
            greens[greenSpaceList.indexOf(s)] = s.getName();
        }
        return greens;
    }

    /**
     * Retrieves the names of urgencies.
     *
     * @return An array containing the names of the urgencies.
     */
    private String[] getUrgency() {
        String[] urgency = new String[urgencyList.length];
        for (ToDoEntry.Urgency s : urgencyList) {
            urgency[s.ordinal()] = s.toString();
        }
        return urgency;
    }

    /**
     * Handles the action to submit the registration of a new to-do entry.
     *
     * @param event The action event triggering the method.
     */
    public void submitRegistration(ActionEvent event) {
        // Attempt to get the input values and register the to-do entry
        boolean success = getValues();
        if (success) {
            try {
                Optional<ToDoEntry> toDoEntry = controller.registerToDoEntry(greenSpaceId, title, description, urgency, days);
                // Update the table view with the new data
                list = FXCollections.observableArrayList(controller.getToDoEntries());
                table.setItems(list);
                // Show confirmation or information message based on the result
                if (toDoEntry.isPresent()) {
                    sendConfirmation.confirmationMessages("Success", "ToDo entry successfully registered!", "");
                } else {
                    sendInformation.informationMessages("ATTENTION", "ToDo entry not registered - Already registered!", "");
                }
            } catch (IllegalArgumentException e) {
                // Show error message if an exception occurs
                sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
            }
        }

    }

    /**
     * Validates and retrieves the input values for registering a to-do entry.
     *
     * @return True if the input values are valid; false otherwise.
     */
    private boolean getValues() {
        try {
            // Parse the input values
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
            // Show error message for invalid days input
            String title = "Invalid Input days";
            String header = "Invalid Days! Make sure only put numbers...";
            String content = "Enter a new one:";
            sendErrors.errorMessages(title, header, content);
            return false;

        } catch (IllegalArgumentException e) {
            // Show error message for missing urgency or green space selection
            String title = "Invalid Urgency/Green Space";
            String header = "Invalid Urgency/Green Space! Make sure to make a selection... ";
            String content = "Enter a new one:";
            sendErrors.errorMessages(title, header, content);
            return false;
        }
    }

    /**
     * Clears the input fields and selections.
     *
     * @param event The action event triggering the method.
     */
    public void clear(ActionEvent event) {
        field_days.clear();
        field_description.clear();
        field_title.clear();
        choiceBox_greenspace.getSelectionModel().clearSelection();
        choiceBox_urgency.getSelectionModel().clearSelection();
    }



    //------------------------------------ Options Side Bar --------------------------
    /**
     * Changes the current window to the "Add Green Space" screen.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addGreenSpace.fxml");
    }

    /**
     * Changes the current window to the "Add Entry Agenda" screen.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToAddEntryAgenda(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addEntryAgenda.fxml");
    }

    /**
     * Changes the current window to the "GSM Menu" screen.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void switchGSMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/gsmUI.fxml");
    }

    /**
     * Changes the current window to the "Entry ToDo List" screen.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addToDoEntry.fxml");
    }

    /**
     * Changes the current window to the "Assign Team" screen.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToAssignTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignTeamToAgendaTask.fxml");
    }

    /**
     * Changes the current window to the "Assign Vehicle" screen.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignVehicleToAgendaTask.fxml");
    }

    /**
     * Changes the current window to the "Postpone Task" screen.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToPsotponeTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/postponeTask.fxml");
    }

    /**
     * Changes the current window to the "Cancel Task" screen.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToCancelTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/cancelTask.fxml");
    }

    /**
     * Changes the current window to the "My Green Spaces" screen.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToMyGreenSpaces(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/myGreenSpaces.fxml");
    }




}

