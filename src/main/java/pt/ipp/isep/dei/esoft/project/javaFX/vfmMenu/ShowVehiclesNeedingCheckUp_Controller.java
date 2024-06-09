package pt.ipp.isep.dei.esoft.project.javaFX.vfmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.VehicleController;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowVehiclesNeedingCheckUp_Controller implements Initializable {

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final VehicleController controller = new VehicleController();
    private final VehicleRepository vehicleRepository = Repositories.getInstance().getVehicleRepository();

    @FXML
    private Label email_label;

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

    private final ObservableList<Vehicle> list = FXCollections.observableArrayList(vehicleRepository.getVehiclesNeedingCheckUp());

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
        table_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        table_plateID.setCellValueFactory(new PropertyValueFactory<>("plateId"));
        table_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        table_lastCheckUp.setCellValueFactory(new PropertyValueFactory<>("lastCheckUp"));
        table_currentKm.setCellValueFactory(new PropertyValueFactory<>("currentKm"));
        table_checkUpFreq.setCellValueFactory(new PropertyValueFactory<>("checkUpFrequency"));
        table.setItems(list);
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
     * Changes to the vehicle check-up registration window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToRegisterVehicleCheckUp(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/registerVehicleCheckUp.fxml");
    }


}
