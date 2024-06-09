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
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class AddGreenSpace_Controller implements Initializable {

    GreenSpaceController controller = new GreenSpaceController();
    private SendErrors sendErrors = new SendErrors();
    private ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private InformationAlerts sendInformation = new InformationAlerts();
    private final SwitchWindows switchWindows = new SwitchWindows();

    @FXML
    private TableView<GreenSpace> table_greenSpaces;
    @FXML
    private TableColumn<GreenSpace, String> table_address;

    @FXML
    private TableColumn<GreenSpace, Integer> table_area;

    @FXML
    private Label email_label;

    @FXML
    private TableColumn<GreenSpace, String> table_name;

    @FXML
    private TableColumn<GreenSpace, GreenSpace.Size> table_size;

    @FXML
    private TextField label_parkName, label_parkArea,label_parkAddress;
    @FXML
    private ChoiceBox<String> choiceBox_Size;

    private Stage stage;
    private final GreenSpace.Size[] sizes = GreenSpace.Size.values();
    private String name,address;
    private int area,size;

    ObservableList<GreenSpace> list = FXCollections.observableArrayList(controller.getAllGreenSpaces());


    /**
     * Initializes the controller with the UI components and sets up the table view.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the choice box with the sizes of green spaces
        choiceBox_Size.getItems().addAll(getTamanhos());
        // Set up the table view with columns and data
        table_address.setCellValueFactory(new PropertyValueFactory<GreenSpace, String>("address"));
        table_name.setCellValueFactory(new PropertyValueFactory<GreenSpace, String>("name"));
        table_area.setCellValueFactory(new PropertyValueFactory<GreenSpace, Integer>("area"));
        table_size.setCellValueFactory(new PropertyValueFactory<GreenSpace, GreenSpace.Size>("size"));
        table_greenSpaces.setItems(list);
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }

    /**
     * Gets the names of the sizes of green spaces.
     *
     * @return An array containing the names of the sizes of green spaces.
     */
    private String[] getTamanhos() {
        String[] size = new String[sizes.length];
        for (GreenSpace.Size s : sizes) {
            size[s.ordinal()] = s.toString();
        }
        return size;
    }

    /**
     * Handles the action to submit the registration of a new green space.
     *
     * @param event The action event triggering the method.
     */
    public void submitRegistration(ActionEvent event) {
        boolean success = getValues();
        if (success) {
            try {
                Optional<GreenSpace> greenSpace = controller.registerGreenSpace(name,size,area,address);
                list = FXCollections.observableArrayList(controller.getAllGreenSpaces());
                table_greenSpaces.setItems(list);
                if (greenSpace.isPresent()) {
                    sendConfirmation.confirmationMessages("Success","Green Space successfully registered!","");
                } else {
                    sendInformation.informationMessages("ATTENTION","Green Space not registered - Already registered!","");
                }
            } catch (IllegalArgumentException e) {

                sendErrors.errorMessages("Invalid Inputs",e.getMessage(),"");
            }
        }
    }

    /**
     * Validates and retrieves the input values for registering a green space.
     *
     * @return True if the input values are valid; false otherwise.
     */
    private boolean getValues() {
        try {
            area = Integer.parseInt(label_parkArea.getText());
            name = label_parkName.getText();
            address = label_parkAddress.getText();
            size = choiceBox_Size.getSelectionModel().getSelectedIndex();
            if (size == -1 ) {
                throw new IllegalArgumentException();
            }
            return true;
        }catch (NumberFormatException e) {
            String title = "Invalid Input area";
            String header = "Invalid Area! Make sure only put numbers...";
            String content = "Enter a new one:";
            sendErrors.errorMessages(title,header,content);
            return false;
        }catch (IllegalArgumentException e){
            String title = "Invalid Size";
            String header = "Invalid Size! Make sure to select a size... ";
            String content = "Enter a new one:";
            sendErrors.errorMessages(title,header,content);
            return false;
        }
    }



    /**
     * Clears the input fields and selections.
     *
     * @param event The action event triggering the method.
     */
    public void clear(ActionEvent event) {
        label_parkName.clear();
        label_parkArea.clear();
        label_parkAddress.clear();
        choiceBox_Size.getSelectionModel().clearSelection();
    }


    
    
    //------------------------------------ Options Side Bar --------------------------

    /**
     * Switches to the GSM Add entry in Agenda when triggered by an action event.
     *
     * @param event The action event triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToAddEntryAgenda(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addEntryAgenda.fxml");
    }

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
