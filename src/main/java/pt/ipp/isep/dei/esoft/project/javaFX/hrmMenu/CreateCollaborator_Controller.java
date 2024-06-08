package pt.ipp.isep.dei.esoft.project.javaFX.hrmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterJobController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;
import pt.ipp.isep.dei.esoft.project.domain.Job;
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

public class CreateCollaborator_Controller implements Initializable {
    private final SendErrors sendErrors = new SendErrors();
    private final ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private final InformationAlerts sendInformation = new InformationAlerts();

    private final SwitchWindows switchWindows = new SwitchWindows();
    private final Repositories repositories = Repositories.getInstance();
    private final CreateCollaboratorController controller = new CreateCollaboratorController();
    private final RegisterJobController jobController = new RegisterJobController();


    @FXML
    private Label email_label;


    @FXML
    private ChoiceBox<Collaborator.DocType> choiceBox_DocType;

    @FXML
    private ChoiceBox<Job> choiceBox_Job;

    @FXML
    private TextField field_DocNumber, field_address, field_email, field_name, field_phone, field_taxPayer, field_Password;

    @FXML
    private DatePicker picker_Admissiondate, picker_birthDate;

    @FXML
    private TableView<Collaborator> table;

    @FXML
    private TableColumn<Collaborator, Collaborator.DocType> table_docType;

    @FXML
    private TableColumn<Collaborator, String> table_email;

    @FXML
    private TableColumn<Collaborator, Job> table_job;

    @FXML
    private TableColumn<Collaborator, String> table_name;

    @FXML
    private TableColumn<Collaborator, Password> table_password;

    @FXML
    private TableColumn<Collaborator, Integer> table_taxPayer;

    private Data birthDate, admissionDate;
    private String name, address, emailAddress, docNumber;
    private int phoneNumber, taxPayerNumber, docType;
    private Job job;
    private Password password;


    //-------------------------------------------------------------------------------
    ObservableList<Collaborator> list = FXCollections.observableArrayList(controller.getCollaboratorRepository().getCollaboratorList());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
        field_DocNumber.setDisable(true);
        field_DocNumber.setPromptText("Select Doc Type first!");
        choiceBox_DocType.setOnAction(event ->  setText());
        choiceBox_DocType.getItems().addAll(Collaborator.DocType.values());
        choiceBox_Job.getItems().addAll(jobController.getJobRepository().getJobList());

        table_name.setCellValueFactory(new PropertyValueFactory<Collaborator, String>("name"));
        table_email.setCellValueFactory(new PropertyValueFactory<Collaborator, String>("emailAddress"));
        table_docType.setCellValueFactory(new PropertyValueFactory<Collaborator, Collaborator.DocType>("type"));
        table_password.setCellValueFactory(new PropertyValueFactory<Collaborator, Password>("password"));
        table_taxPayer.setCellValueFactory(new PropertyValueFactory<Collaborator, Integer>("taxPayerNumber"));
        table_job.setCellValueFactory(new PropertyValueFactory<Collaborator, Job>("job"));
        table.setItems(list);
    }

    private void setText() {
        field_DocNumber.setDisable(false);
        Collaborator.DocType selectedDocType = choiceBox_DocType.getSelectionModel().getSelectedItem();
        if (selectedDocType == Collaborator.DocType.PASSPORT) {
            field_DocNumber.setPromptText("Ex: YY123456");
        } else if (selectedDocType == Collaborator.DocType.CITIZEN_CARD) {
            field_DocNumber.setPromptText("Ex: 123456789");
        } else {
            field_DocNumber.setPromptText("Ex: V22F36625");
        }
    }

    public void submitRegistration(ActionEvent event) {
        try {
            getValues();
            Optional<Collaborator> collaborator = controller.registerCollaborator(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, docNumber, job, password);

            table.getItems().clear();
            table.getItems().addAll(controller.getCollaboratorRepository().getCollaboratorList());

            if (collaborator.isPresent()) {
                sendConfirmation.confirmationMessages("Success", "Collaborator successfully registered!", "");
            } else {
                sendInformation.informationMessages("ATTENTION", "Collaborator not registered - Already registered!", "");
            }
        } catch (IllegalArgumentException e) {
            sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
        }
    }

    private void getValues() {
        getBoxes();
        getStrigs();
        getInteiros();
        getDates();
    }

    private void getDates() {
        birthDate = convertDate(picker_birthDate);
        admissionDate = convertDate(picker_Admissiondate);
    }

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

    private void getBoxes() {
        docType = choiceBox_DocType.getSelectionModel().getSelectedIndex();
        job = choiceBox_Job.getSelectionModel().getSelectedItem();
        if (docType == -1 || job == null) {
            throw new IllegalArgumentException("Invalid Doc Type or Job! Make sure to make a selection... ");
        }
    }

    private void getInteiros() {
        try {
            phoneNumber = Integer.parseInt(field_phone.getText());
            taxPayerNumber = Integer.parseInt(field_taxPayer.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid \"Phone Number\" or \"Tax Payer Number\", make sure only put numbers...");
        }
    }

    private void getStrigs() {
        name = field_name.getText();
        address = field_address.getText();
        emailAddress = field_email.getText();
        docNumber = field_DocNumber.getText();
        password = new Password(field_Password.getText());
    }

    public void clear(ActionEvent event) {
        choiceBox_DocType.getSelectionModel().clearSelection();
        choiceBox_Job.getSelectionModel().clearSelection();
        field_address.clear();
        field_email.clear();
        field_name.clear();
        field_phone.clear();
        field_taxPayer.clear();
        field_DocNumber.clear();
        field_Password.clear();
        picker_Admissiondate.setValue(null);
        picker_birthDate.setValue(null);
        field_DocNumber.setDisable(true);
        field_DocNumber.setPromptText("Select Doc Type first!");
    }


    public void information_password(ActionEvent event) {
        sendInformation.informationMessages("Password Format", "Password must have 7 alphanumeric characters, including three capital letters and two digits", "null");
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


    public void changeToAssignSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/assignSkillCollaborator.fxml");
    }

    public void changeGenerateTeam(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event,"/generateTeam.fxml");
    }


}
