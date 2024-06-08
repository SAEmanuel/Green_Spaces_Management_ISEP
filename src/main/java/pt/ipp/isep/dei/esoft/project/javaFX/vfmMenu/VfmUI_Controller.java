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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
        num_checkUp.setText(String.valueOf(repositories.getVehicleCheckUpRepository().getCheckUpsList().size()));
        num_vehicles.setText(String.valueOf(repositories.getVehicleRepository().getVehicleList().size()));
    }


    //------------------------------------ Options Side Bar --------------------------

    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/login.fxml");
    }

    public void changeToRegisterVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/registerVehicle.fxml");
    }

        public void changeToRegisterVehicleCheckUp(ActionEvent event) throws IOException {
            switchWindows.changeWindow(event,"/registerVehicleCheckUp.fxml");
        }

    public void changeToCreateCheckUpList(ActionEvent event) {
    }


}
