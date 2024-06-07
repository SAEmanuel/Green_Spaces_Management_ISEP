package pt.ipp.isep.dei.esoft.project.javaFX.gsmMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GsmUI_Controller implements Initializable {

    private final Repositories repositories = Repositories.getInstance();
    private final SwitchWindows switchWindows = new SwitchWindows();


    @FXML
    private Label email_label;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }







    //------------------------------------ Options Side Bar --------------------------
    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/login.fxml");
    }

    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addGreenSpace.fxml");
    }

    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addToDoEntry.fxml");
    }

    public void changeToAddEntryAgenda(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addEntryAgenda.fxml");
    }

    public void changeToAssignTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignTeamToAgendaTask.fxml");
    }

    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignVehicleToAgendaTask.fxml");
    }

    public void changeToPostponeTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/postponeTask.fxml");
    }

    public void changeToCancelTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/cancelTask.fxml");
    }

    public void changeToMyGreenSpaces(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/myGreenSpaces.fxml");
    }

}
