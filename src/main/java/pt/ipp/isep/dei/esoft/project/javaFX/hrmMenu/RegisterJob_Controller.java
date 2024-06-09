package pt.ipp.isep.dei.esoft.project.javaFX.hrmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobController;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controller class for the Register Job screen.
 */
public class RegisterJob_Controller implements Initializable {

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final RegisterJobController controller = new RegisterJobController();
    private final SendErrors sendErrors = new SendErrors();
    private final ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private final InformationAlerts sendInformation = new InformationAlerts();

    @FXML
    private Label email_label;

    @FXML
    private TextField field_name;


    @FXML
    private TableView<Job> table;

    @FXML
    private TableColumn<Job, String> table_skills;

    ObservableList<Job> list = FXCollections.observableArrayList(controller.getJobRepository().getJobList());

    /**
     * Initializes the Register Job screen.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_skills.setCellValueFactory(new PropertyValueFactory<Job, String>("jobName"));
        table.setItems(list);
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());

    }

    /**
     * Handles the event when the user submits job registration.
     * @param event The action event triggered by the button click.
     */
    public void submitRegistration(ActionEvent event) {
        try {
            Optional<Job> job = controller.registerJob(field_name.getText());

            table.getItems().clear();
            table.getItems().addAll(controller.getJobRepository().getJobList());
            if (job.isPresent()) {
                sendConfirmation.confirmationMessages("Success","Job successfully registered!","");
            } else {
                sendInformation.informationMessages("ATTENTION","Job not registered - Already registered!","");
            }
        } catch (IllegalArgumentException e) {

            sendErrors.errorMessages("Invalid Inputs",e.getMessage(),"");
        }

    }

    /**
     * Clears the input field for job name.
     * @param event The action event triggered by the button click.
     */
    public void clear(ActionEvent event) {
        field_name.clear();
    }

    //------------------------------------ Options Side Bar --------------------------

    /**
     * Switches the view to the HRM menu.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void switchHRMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/hrmUI.fxml");
    }

    /**
     * Switches the view to the register skill screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToRegisterSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/registerSkill.fxml");
    }

    /**
     * Switches the view to the create collaborator screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToCreateCollaborator(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/createCollaborator.fxml");
    }

    /**
     * Switches the view to the assign skill to collaborator screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignSkillCollaborator.fxml");
    }

    /**
     * Switches the view to the generate team screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeGenerateTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/generateTeam.fxml");
    }

}
