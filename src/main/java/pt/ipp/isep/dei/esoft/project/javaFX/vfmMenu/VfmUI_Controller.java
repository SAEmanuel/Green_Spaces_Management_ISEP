package pt.ipp.isep.dei.esoft.project.javaFX.vfmMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VfmUI_Controller implements Initializable {

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final Repositories repositories = Repositories.getInstance();

    @FXML
    private Label email_label;

    @FXML
    private Label num_checkUp;

    @FXML
    private Label num_vehicles;

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
        num_checkUp.setText(String.valueOf(repositories.getVehicleRepository().getVehiclesNeedingCheckUp().size()));
        num_vehicles.setText(String.valueOf(repositories.getVehicleRepository().getVehicleList().size()));
    }



    //------------------------------------ Options Side Bar --------------------------
    /**
     * Switches to the login menu window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/login.fxml");
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

    /**
     * Changes to the window showing vehicles needing check-up.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void changeToCreateCheckUpList(ActionEvent event) throws IOException{
        switchWindows.changeWindow(event, "/showVehiclesNeedingCheckUp.fxml");
    }
}
