package pt.ipp.isep.dei.esoft.project.javaFX.hrmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.AssignSkillCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;
import pt.ipp.isep.dei.esoft.project.domain.Job;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AssignSkillToCollaborator_Controller implements Initializable {

    private final SendErrors sendErrors = new SendErrors();
    private final ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private final InformationAlerts sendInformation = new InformationAlerts();
    private final SwitchWindows switchWindows = new SwitchWindows();
    private final Repositories repositories = Repositories.getInstance();
    private final RegisterSkillController skillController = new RegisterSkillController();
    private final AssignSkillCollaboratorController controller = new AssignSkillCollaboratorController();

    @FXML
    private TextField field_name;

    @FXML
    private ChoiceBox<Skill> choiceBox_Skill;

    @FXML
    private Label email_label;

    @FXML
    private TableView<Collaborator> table;

    @FXML
    private TableColumn<Collaborator, Integer> table_TaxPayer;

    @FXML
    private TableColumn<Collaborator, String> table_name;

    @FXML
    private TableColumn<Collaborator, List<Skill>> table_skill;
    private int taxPayerNumber;
    private Skill skillSelected;

    ObservableList<Collaborator> list = FXCollections.observableArrayList(controller.getCollaboratorList());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
        choiceBox_Skill.getItems().addAll(skillController.getSkillList());

        table_name.setCellValueFactory(new PropertyValueFactory<Collaborator, String>("name"));
        table_TaxPayer.setCellValueFactory(new PropertyValueFactory<Collaborator, Integer>("taxPayerNumber"));
        table_skill.setCellValueFactory(new PropertyValueFactory<Collaborator, List<Skill>>("skills"));
        table.setItems(list);
    }

    public void submitRegistration(ActionEvent event) {
        try {
            getValues();
            boolean result = controller.assignSkillCollaboratorByTaxNumber(taxPayerNumber, skillSelected);

            table.getItems().clear();
            table.getItems().addAll(controller.getCollaboratorList());
            if (result) {
                sendConfirmation.confirmationMessages("Success", "Skill successfully registered!", "");
            } else {
                sendInformation.informationMessages("ATTENTION", "Skill not registered - Already registered!", "");
            }
        } catch (IllegalArgumentException e) {

            sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
        }
    }

    private void getValues() {
        try {
            taxPayerNumber = Integer.parseInt(field_name.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Tax Number, please enter a valid Tax Number");
        }

        skillSelected = choiceBox_Skill.getSelectionModel().getSelectedItem();
        if (skillSelected == null) {
            throw new IllegalArgumentException("Invalid Skill, please select a Skill");
        }
    }

    public void clear(ActionEvent event) {
        field_name.clear();
        choiceBox_Skill.getSelectionModel().clearSelection();
    }


    //------------------------------------ Options Side Bar --------------------------

    public void switchHRMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/hrmUI.fxml");
    }

    public void changeToRegisterSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/registerSkill.fxml");
    }

    public void changeToRegisterJob(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/registerJob.fxml");
    }

    public void changeToCreateCollaborator(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/createCollaborator.fxml");
    }

    public void changeGenerateTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/generateTeam.fxml");
    }

}
