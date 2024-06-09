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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.ShowGreenSpacesByManagerController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MyGreenSpace_Controller implements Initializable {

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final ShowGreenSpacesByManagerController controller = new ShowGreenSpacesByManagerController();
    private final SendErrors sendErrors = new SendErrors();

    @FXML
    private Label email_label;


    @FXML
    private TableView<GreenSpace> table_greenSpaces;

    @FXML
    private TableColumn<GreenSpace, String> table_address;

    @FXML
    private TableColumn<GreenSpace, Integer> table_area;

    @FXML
    private TableColumn<GreenSpace, String> table_name;

    @FXML
    private TableColumn<GreenSpace, GreenSpace.Size> table_size;


    private Stage stage;

    ObservableList<GreenSpace> list;

    /**
     * Initializes the controller class.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_area.setCellValueFactory(new PropertyValueFactory<>("area"));
        table_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        loadGreenSpaces();
        table_greenSpaces.setItems(list);
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
    }

    /**
     * Loads green spaces into the table.
     */
    public void loadGreenSpaces() {
        try {
            List<GreenSpace> greenSpaces = controller.showGreenSpacesJavaFx();
            if (greenSpaces == null) {
                greenSpaces = new ArrayList<>();
            }
            list = FXCollections.observableArrayList(greenSpaces);
        }catch(IllegalArgumentException e){
            sendErrors.errorMessages("Error",e.getMessage(),"");
        }

    }




    //------------------------------------ Options Side Bar --------------------------

    /**
     * Changes the window to assign a vehicle to an agenda task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignVehicleToAgendaTask.fxml");
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
     * Switches to the GSM (Green Space Management) user interface window.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void switchGSMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/gsmUI.fxml");
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
     * Changes the window to assign a team to an agenda task.
     * @param event The event that triggered this action.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignTeamToAgendaTask.fxml");
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
}

