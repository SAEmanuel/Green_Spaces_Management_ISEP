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
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterSkill_Controller implements Initializable {

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final RegisterSkillController controller = new RegisterSkillController();
    private SendErrors sendErrors = new SendErrors();
    private ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private InformationAlerts sendInformation = new InformationAlerts();

    @FXML
    private Label email_label;

    @FXML
    private TextField field_name;


    @FXML
    private TableView<Skill> table;

    @FXML
    private TableColumn<Skill, String> table_skills;

    ObservableList<Skill> list = FXCollections.observableArrayList(controller.getSkillList());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_skills.setCellValueFactory(new PropertyValueFactory<Skill, String>("skillName"));
        table.setItems(list);
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());

    }


    public void submitRegistration(ActionEvent event) {
            try {
                Optional<Skill> skill = controller.registerSkill(field_name.getText());

                table.getItems().clear();
                table.getItems().addAll(controller.getSkillList());
                if (skill.isPresent()) {
                    sendConfirmation.confirmationMessages("Success","Skill successfully registered!","");
                } else {
                    sendInformation.informationMessages("ATTENTION","Skill not registered - Already registered!","");
                }
            } catch (IllegalArgumentException e) {

                sendErrors.errorMessages("Invalid Inputs",e.getMessage(),"");
            }

    }


    public void clear(ActionEvent event) {
        field_name.clear();
    }

    //------------------------------------ Options Side Bar --------------------------

    public void switchHRMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/hrmUI.fxml");
    }

    public void changeToRegisterJob(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/registerJob.fxml");
    }

    public void changeToCreateCollaborator(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/createCollaborator.fxml");
    }

    public void changeToAssignSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignSkillCollaborator.fxml");
    }

    public void changeGenerateTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/generateTeam.fxml");
    }
}
