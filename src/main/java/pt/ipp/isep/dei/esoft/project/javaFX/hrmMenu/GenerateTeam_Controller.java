package pt.ipp.isep.dei.esoft.project.javaFX.hrmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.ipp.isep.dei.esoft.project.application.controller.GenerateTeamController;
import pt.ipp.isep.dei.esoft.project.application.controller.RegisterSkillController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.SkillList;
import pt.ipp.isep.dei.esoft.project.domain.Team;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;
import pt.ipp.isep.dei.esoft.project.javaFX.extras.SwitchWindows;
import pt.ipp.isep.dei.esoft.project.repository.CollaboratorRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.TeamRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GenerateTeam_Controller implements Initializable {

    private final SendErrors sendErrors = new SendErrors();
    private final ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private final InformationAlerts sendInformation = new InformationAlerts();
    private final SwitchWindows switchWindows = new SwitchWindows();
    private final GenerateTeamController controller = new GenerateTeamController();
    private final RegisterSkillController skillController = new RegisterSkillController();
    private final TeamRepository teamRepository = Repositories.getInstance().getTeamRepository();
    private final CollaboratorRepository collaboratorRepository = Repositories.getInstance().getCollaboratorRepository();

    @FXML
    private Label email_label;

    @FXML
    private ChoiceBox<Skill> choiceBox_Skill;

    @FXML
    private TextField field_max;

    @FXML
    private TextField field_min;

    @FXML
    private TableView<Skill> table_view_Skills;

    @FXML
    private TableColumn<Skill, String> selected_skills;

    @FXML
    private TableView<Team> table;

    @FXML
    private TableColumn<Team, List<Collaborator>> table_Collaborators;

    @FXML
    private TableColumn<Team, Integer> table_TeamID;

    private int minimun,maximun;

    private final ObservableList<Skill> skillObservableList = FXCollections.observableArrayList();
    private final ObservableList<Team> teamObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email_label.setText(Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail());
        choiceBox_Skill.getItems().addAll(skillController.getSkillList());
        choiceBox_Skill.setOnAction(event -> refreshList());

        selected_skills.setCellValueFactory(new PropertyValueFactory<>("skillName"));
        table_view_Skills.setItems(skillObservableList);

        table_Collaborators.setCellValueFactory(new PropertyValueFactory<>("collaborators"));
        table_TeamID.setCellValueFactory(new PropertyValueFactory<>("teamId"));
        table.setItems(teamObservableList);



    }

    public void submitRegistration(ActionEvent event) {
        try {
            getValues();
            SkillList skillList =getSkills();
            Optional<Team> generateTeam = controller.generateTeamJavaFx(skillList,collaboratorRepository.getCollaboratorList(),minimun,maximun);

            if (generateTeam.isPresent()) {
                Team team = generateTeam.get();
                if (showTeam(team)) {
                    controller.addTeam(generateTeam.get().getTeamId());
                    sendConfirmation.confirmationMessages("Team Accepted","Your team was successfully asccepted","Check the list of teams");
                    table.getItems().clear();
                    table.getItems().addAll(teamRepository.getListTeam());
                } else {
                    //TODO Acabar isto -> De: Jorge | Para: Paulo
                }
                sendConfirmation.confirmationMessages("Success", "Team successfully created!", "");
            } else {
                sendInformation.informationMessages("ATTENTION", "Team not created!", "");
            }
        } catch (IllegalArgumentException e) {

            sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
        }


    }

    private boolean showTeam(Team generateTeam) {
        int position = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Collaborator collaborator : generateTeam.getCollaborators()) {
            if (position < generateTeam.getCollaborators().size() - 1) {
                stringBuilder.append(collaborator.getName()).append(", ");
            } else {
                stringBuilder.append(collaborator.getName());
            }
        }
        return sendInformation.informationMessagesReturn("Generated Team", stringBuilder.toString(), "Press \"ok\" to accept this team! ");
    }

    private SkillList getSkills() {
        SkillList skillList = new SkillList();
        for (Skill skill : skillObservableList) {
            skillList.addSkill(skill);
        }
        return skillList;
    }

    private void getValues() {
        try {
            minimun = Integer.parseInt(field_min.getText());
            maximun = Integer.parseInt(field_max.getText());
        }catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid minimum or maximum number... Make sure to only input numbers.");
        }
    }


    private void refreshList() {
        Skill selectedSkill = choiceBox_Skill.getSelectionModel().getSelectedItem();
        if (selectedSkill != null && !skillObservableList.contains(selectedSkill)) {
            skillObservableList.add(selectedSkill);
        } else if (skillObservableList.contains(selectedSkill)) {
            sendInformation.informationMessages("Information", "The selected option already is in the list...", "Make another choice.");
        }
        choiceBox_Skill.getSelectionModel().clearSelection();
    }

    public void removeFromSkillTable(ActionEvent event) {
        Skill selectedSkill = table_view_Skills.getSelectionModel().getSelectedItem();
        if (selectedSkill != null) {
            skillObservableList.remove(selectedSkill);
        }
    }

    public void clear(ActionEvent event) {
        skillObservableList.clear();
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

    public void changeToAssignSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/assignSkillCollaborator.fxml");
    }






}
