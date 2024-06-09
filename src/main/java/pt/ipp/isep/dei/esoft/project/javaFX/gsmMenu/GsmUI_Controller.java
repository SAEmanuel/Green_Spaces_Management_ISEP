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

    /**
     * Initializes the controller class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }



    //------------------------------------ Options Side Bar --------------------------
    /**
     * Switches to the login menu window.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void switchToLoginMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/login.fxml");
    }

    /**
     * Changes the window to add a green space.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAddGreenSpace(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addGreenSpace.fxml");
    }

    /**
     * Changes the window to the entry to-do list.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addToDoEntry.fxml");
    }

    /**
     * Changes the window to add an entry to the agenda.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAddEntryAgenda(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/addEntryAgenda.fxml");
    }

    /**
     * Changes the window to assign a team to an agenda task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignTeamToAgendaTask.fxml");
    }

    /**
     * Changes the window to assign a vehicle to an agenda task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignVehicleToAgendaTask.fxml");
    }

    /**
     * Changes the window to postpone a task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToPostponeTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/postponeTask.fxml");
    }

    /**
     * Changes the window to cancel a task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToCancelTask(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/cancelTask.fxml");
    }

    /**
     * Changes the window to the user's green spaces.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToMyGreenSpaces(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/myGreenSpaces.fxml");
    }

}
