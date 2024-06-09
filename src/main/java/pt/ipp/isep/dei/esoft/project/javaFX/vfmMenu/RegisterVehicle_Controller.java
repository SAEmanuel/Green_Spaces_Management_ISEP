package pt.ipp.isep.dei.esoft.project.javaFX.vfmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterVehicle_Controller implements Initializable {

    private final SendErrors sendErrors = new SendErrors();
    private final ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private final InformationAlerts sendInformation = new InformationAlerts();

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final Repositories repositories = Repositories.getInstance();
    private final VehicleController controller = new VehicleController();


    @FXML
    private ChoiceBox<Vehicle.Type> choiceBox_type;


    @FXML
    private Label email_label;


    @FXML
    private TextField field_PlateID;

    @FXML
    private TextField field_brand;

    @FXML
    private TextField field_checkUpFq;

    @FXML
    private TextField field_currentKm;

    @FXML
    private TextField field_grossWeigth;

    @FXML
    private TextField field_lastCheckUp;

    @FXML
    private TextField field_model;

    @FXML
    private TextField field_weigth;


    @FXML
    private DatePicker picker_Acquisitiondate;

    @FXML
    private DatePicker picker_RegisterDate;


    @FXML
    private TableView<Vehicle> table;

    @FXML
    private TableColumn<Vehicle, Float> table_checkUpFreq;

    @FXML
    private TableColumn<Vehicle, Float> table_currentKm;

    @FXML
    private TableColumn<Vehicle, Float> table_lastCheckUp;

    @FXML
    private TableColumn<Vehicle, String> table_model;

    @FXML
    private TableColumn<Vehicle, String> table_plateID;

    @FXML
    private TableColumn<Vehicle, Vehicle.Type> table_type;

    private Data acquisitionDate, registerDate;
    private int type;
    private float tareWeigth, grossWeigth, currentKm, checkUpFrequency, lastCheckUp;
    private String plateID,brand,model;

    private final ObservableList<Vehicle> list = FXCollections.observableArrayList(controller.getVehicles());

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
        choiceBox_type.getItems().addAll(Vehicle.Type.values());


        table_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        table_plateID.setCellValueFactory(new PropertyValueFactory<>("plateId"));
        table_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        table_lastCheckUp.setCellValueFactory(new PropertyValueFactory<>("lastCheckUp"));
        table_currentKm.setCellValueFactory(new PropertyValueFactory<>("currentKm"));
        table_checkUpFreq.setCellValueFactory(new PropertyValueFactory<>("checkUpFrequency"));
        table.setItems(list);
    }

    /**
     * Handles the action event when the user submits a vehicle registration.
     *
     * @param event The action event.
     */
    public void submitRegistration(ActionEvent event) {
        try {
            getValues();
            Optional<Vehicle> vehicle = controller.registerVehicle(plateID,brand,model,type,tareWeigth,grossWeigth,currentKm,checkUpFrequency,lastCheckUp,registerDate,acquisitionDate);

            table.getItems().clear();
            table.getItems().addAll(controller.getVehicles());

            if (vehicle.isPresent()) {
                sendConfirmation.confirmationMessages("Success", "Vehicle successfully registered!", "");
            } else {
                sendInformation.informationMessages("ATTENTION", "Vehicle not registered - Already registered!", "");
            }
        } catch (IllegalArgumentException e) {
            sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
        }
    }

    /**
     * Retrieves values from input fields.
     */
    private void getValues() {
        getStrigs();
        getBoxes();
        getFloats();
        getDates();
    }

    /**
     * Retrieves string values from input fields.
     */
    private void getStrigs() {
        plateID = field_PlateID.getText();
        brand = field_brand.getText();
        model = field_model.getText();
    }

    /**
     * Retrieves values from choice boxes.
     */
    private void getBoxes() {
        type = choiceBox_type.getSelectionModel().getSelectedIndex();
        if (type == -1 ) {
            throw new IllegalArgumentException("Invalid \"Vehicle Type\"! Make sure to make a selection... ");
        }
    }

    /**
     * Retrieves date values from date pickers.
     */
    private void getDates() {
        acquisitionDate = convertDate(picker_Acquisitiondate);
        registerDate = convertDate(picker_RegisterDate);
    }

    /**
     * Converts a DatePicker value to a Data object.
     *
     * @param data The DatePicker value to be converted.
     * @return The corresponding Data object.
     */
    private Data convertDate(DatePicker data) {
        try {
            LocalDate myDate = data.getValue();
            String[] date = myDate.toString().split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            return new Data(year, month, day);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Enter valid date(s)... Make sure is not null or the format is correct (dd-mm-yyyy)");
        }
    }

    /**
     * Retrieves float values from input fields.
     */
    private void getFloats() {
        try {
            tareWeigth = Float.parseFloat(field_weigth.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid \"Tare Weight\", make sure only put numbers...");
        }
        try{
            grossWeigth = Float.parseFloat(field_grossWeigth.getText());

        }catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid \"Gross Weight\", make sure only put numbers...");
        }

        try{
            currentKm = Float.parseFloat(field_currentKm.getText());

        }catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid \"Current Kilometers\", make sure only put numbers...");
        }
        try{
            checkUpFrequency = Float.parseFloat(field_checkUpFq.getText());

        }catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid \"Check-Up Frequency\", make sure only put numbers...");
        }
        try{
            lastCheckUp = Float.parseFloat(field_lastCheckUp.getText());

        }catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid \"Last Check-Up\", make sure only put numbers...");
        }

    }

    /**
     * Clears all input fields and selections.
     *
     * @param event The action event.
     */
    public void clear(ActionEvent event) {
        field_PlateID.clear();
        field_brand.clear();
        field_checkUpFq.clear();
        field_currentKm.clear();
        field_lastCheckUp.clear();
        field_model.clear();
        field_weigth.clear();
        field_grossWeigth.clear();
        picker_Acquisitiondate.setValue(null);
        picker_RegisterDate.setValue(null);
        choiceBox_type.getSelectionModel().clearSelection();
    }


    //------------------------------------ Options Side Bar --------------------------

    /**
     * Switches to the main menu window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void switchVFMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/vfmUI.fxml");
    }


    /**
     * Changes to the vehicle check-up registration window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToRegisterVehicleCheckUp(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/registerVehicleCheckUp.fxml");
    }

    /**
     * Changes to the check-up list creation window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToCreateCheckUpList(ActionEvent event) throws IOException{
        switchWindows.changeWindow(event, "/showVehiclesNeedingCheckUp.fxml");
    }


}
