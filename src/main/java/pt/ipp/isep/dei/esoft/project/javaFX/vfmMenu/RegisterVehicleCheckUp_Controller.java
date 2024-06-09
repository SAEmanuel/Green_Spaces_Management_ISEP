package pt.ipp.isep.dei.esoft.project.javaFX.vfmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.CreateCheckUpController;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.CheckUp;
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

public class RegisterVehicleCheckUp_Controller implements Initializable {

    private final SendErrors sendErrors = new SendErrors();
    private final ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private final InformationAlerts sendInformation = new InformationAlerts();
    private final SwitchWindows switchWindows = new SwitchWindows();
    private final VehicleController controller = new VehicleController();
    private final Repositories repositories = Repositories.getInstance();
    private final CreateCheckUpController checkUpController = new CreateCheckUpController();

    @FXML
    private ChoiceBox<String> choice_plate;

    @FXML
    private Label email_label;

    @FXML
    private TextField field_checkUpKm;

    @FXML
    private DatePicker picker_checkUp;

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

    private final ObservableList<Vehicle> list = FXCollections.observableArrayList(controller.getVehicles());
    private float checkUpKm;
    private Data checkUpDate;
    private String plate;

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
        for (Vehicle vehicle : controller.getVehicles()) {
            choice_plate.getItems().add(vehicle.getPlateId().trim());
        }


        table_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        table_plateID.setCellValueFactory(new PropertyValueFactory<>("plateId"));
        table_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        table_lastCheckUp.setCellValueFactory(new PropertyValueFactory<>("lastCheckUp"));
        table_currentKm.setCellValueFactory(new PropertyValueFactory<>("currentKm"));
        table_checkUpFreq.setCellValueFactory(new PropertyValueFactory<>("checkUpFrequency"));
        table.setItems(list);
    }

    /**
     * Handles the action event when the user submits a vehicle check-up registration.
     *
     * @param event The action event.
     */
    public void submitRegistration(ActionEvent event) {
        try {
            getValues();
            Optional<CheckUp> vehicle = repositories.getVehicleCheckUpRepository().registerCheckUp(checkUpController.getVehicleByPlateID(plate),checkUpKm,checkUpDate);

            table.getItems().clear();
            table.getItems().addAll(controller.getVehicles());

            if (vehicle.isPresent()) {
                sendConfirmation.confirmationMessages("Success", "Check-Up successfully registered!", "");
            } else {
                sendInformation.informationMessages("ATTENTION", "Check-Up not registered!", "");
            }
        } catch (IllegalArgumentException e) {
            sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
        }
    }


    /**
     * Retrieves values from input fields.
     */
    private void getValues() {
        getBoxes();
        getFloats();
        getDates();
    }

    /**
     * Retrieves values from choice boxes.
     */
    private void getBoxes() {
        plate = choice_plate.getSelectionModel().getSelectedItem();
        if (plate == null ) {
            throw new IllegalArgumentException("Invalid \"Plate ID\"! Make sure to make a selection... ");
        }
    }

    /**
     * Retrieves date values from date pickers.
     */
    private void getDates() {
        checkUpDate = convertDate(picker_checkUp);
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
            checkUpKm = Float.parseFloat(field_checkUpKm.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid \"Check-Up Km\", make sure only put numbers...");
        }
    }

    /**
     * Clears all input fields and selections.
     *
     * @param event The action event.
     */
    public void clear(ActionEvent event) {
        choice_plate.getSelectionModel().clearSelection();
        field_checkUpKm.clear();
        picker_checkUp.setValue(null);
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
     * Changes to the vehicle registration window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToRegisterVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/registerVehicle.fxml");
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


