package pt.ipp.isep.dei.esoft.project.javaFX.hrmMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
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
import javafx.scene.control.ChoiceBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;


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

    /**
     * Initializes the UI components with necessary data.
     * Sets the email label to the current user's email.
     * Populates the choice box with available skills.
     * Configures action event for the choice box to refresh the skill list.
     * Sets up table columns for selected skills and teams with appropriate cell value factories.
     * Populates the skill and team tables with data from their respective observable lists.
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object, or null.
     */
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
        table.getItems().clear();
        table.getItems().addAll(teamRepository.getListTeam());
    }

    /**
     * Registers a new team based on the selected skills, minimum and maximum number of collaborators.
     * Displays success or failure messages accordingly.
     * @param event The action event triggered by the registration button.
     */
    public void submitRegistration(ActionEvent event) {
        try {
            boolean auxShowTeam = false;
            getValues();
            controller.cleanSkillList();
            SkillList skillList = getSkills();
            Optional<Team> generateTeam = controller.generateTeamJavaFx(skillList,collaboratorRepository.getCollaboratorList(),minimun,maximun);

            if (generateTeam.isPresent()) {
                Team team = generateTeam.get();
                if (showTeam(team)) {
                    controller.addTeam(generateTeam.get().getTeamId());
                    sendInformation.informationMessages("Success", "Team successfully created!", "Check the list of teams");
                    table.getItems().clear();
                    table.getItems().addAll(teamRepository.getListTeam());
                } else {
                    while (true) {
                        if(!auxShowTeam){
                            skillList = getSkills();
                            generateTeam = controller.generateTeamJavaFx(skillList, collaboratorRepository.getCollaboratorList(), minimun, maximun);
                            if(generateTeam.isPresent())
                                team = generateTeam.get();
                            auxShowTeam = true;
                        }

                        if (generateTeam.isPresent() && !showTeam(team)) {
                            skillList = getSkills();
                            generateTeam = controller.generateTeamJavaFx(skillList, collaboratorRepository.getCollaboratorList(), minimun, maximun);
                            if(generateTeam.isPresent())
                                team = generateTeam.get();
                        } else {
                            if(!generateTeam.isPresent()){
                                sendInformation.informationMessagesReturn("Team not generated", "All team generation possibilities have been done!", "");
                            }
                            generateTeam = showTeamSelectionDialog(controller.generatedList());
                            if(generateTeam.isPresent()) {
                                controller.addTeam(generateTeam.get().getTeamId());
                                sendInformation.informationMessages("Success", "Team successfully created!", "Check the list of teams");
                                table.getItems().clear();
                                table.getItems().addAll(teamRepository.getListTeam());
                            }
                            else{
                                sendInformation.informationMessages("ATTENTION", "Team not created!", "");
                            }
                            break;
                        }
                    }
                    //TODO Acabar isto -> De: Jorge | Para: Paulo
                }
//                sendConfirmation.confirmationMessages("Success", "Team successfully created!", "");
            } else {
                if(skillList == null || skillList.getSkillList().isEmpty())
                    throw new NumberFormatException("No selected skills... Make sure to select one or more.");
                else
                    sendInformation.informationMessages("ATTENTION", "Team not created! Not enought collaborators to generate team.", "");
            }
        } catch (IllegalArgumentException e) {
            sendErrors.errorMessages("Invalid Inputs", e.getMessage(), "");
        }
        controller.cleanTeamListAux();
    }

    /**
     * Displays a dialog for selecting a team from the generated teams list.
     * @param teamList The list of generated teams.
     * @return The selected team, if any.
     */
    private Optional<Team> showTeamSelectionDialog(List<Team> teamList) {
        Dialog<Team> dialog = new Dialog<>();
        dialog.setTitle("Generated Teams");

        // Set the button types
        ButtonType selectButtonType = new ButtonType("Select", ButtonType.OK.getButtonData());
        ButtonType cancelButtonType = ButtonType.CANCEL;
        dialog.getDialogPane().getButtonTypes().addAll(selectButtonType, cancelButtonType);

        // Create a ComboBox for team selection
        ComboBox<Team> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(teamList);
        comboBox.setPromptText("Generated Teams");

        VBox content = new VBox();
        content.getChildren().addAll(new Label("Select one of the teams generated to add:"), comboBox);
        dialog.getDialogPane().setContent(content);

        // Convert the result to the selected team when the select button is clicked
        dialog.setResultConverter(new Callback<ButtonType, Team>() {
            @Override
            public Team call(ButtonType dialogButton) {
                if (dialogButton == selectButtonType) {
                    return comboBox.getSelectionModel().getSelectedItem();
                }
                return null;
            }
        });

        Optional<Team> result = dialog.showAndWait();
        return result;
    }

    /**
     * Displays a confirmation message for showing the generated team.
     * @param generateTeam The generated team to be displayed.
     * @return True if the user confirms to show the team, otherwise false.
     */
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
        return sendConfirmation.confirmationMessagesGiveReturn("Generated Team", stringBuilder.toString(), "Press \"ok\" to accept this team! ");
    }

    /**
     * Retrieves the selected skills from the observable list and returns them as a SkillList object.
     * @return The selected skills as a SkillList object.
     */
    private SkillList getSkills() {
        SkillList skillList = new SkillList();
        for (Skill skill : skillObservableList) {
            skillList.addSkill(skill);
        }
        return skillList;
    }

    /**
     * Retrieves the minimum and maximum values for team generation from input fields.
     * @throws NumberFormatException If the input fields contain invalid values.
     */
    private void getValues() {
        try {
            minimun = Integer.parseInt(field_min.getText());
            maximun = Integer.parseInt(field_max.getText());
        }catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid minimum or maximum number... Make sure to only input numbers.");
        }
    }

    /**
     * Refreshes the skill list displayed in the table view.
     */
    private void refreshList() {
        Skill selectedSkill = choiceBox_Skill.getSelectionModel().getSelectedItem();
        if (selectedSkill != null && !skillObservableList.contains(selectedSkill)) {
            skillObservableList.add(selectedSkill);
        } else if (skillObservableList.contains(selectedSkill)) {
            sendInformation.informationMessages("Information", "The selected option already is in the list...", "Make another choice.");
        }
        choiceBox_Skill.getSelectionModel().clearSelection();
    }

    /**
     * Removes the selected skill from the observable list.
     * @param event The action event triggered by the remove button.
     */
    public void removeFromSkillTable(ActionEvent event) {
        Skill selectedSkill = table_view_Skills.getSelectionModel().getSelectedItem();
        if (selectedSkill != null) {
            skillObservableList.remove(selectedSkill);
        }
    }

    /**
     * Clears the skill observable list.
     * @param event The action event triggered by the clear button.
     */
    public void clear(ActionEvent event) {
        skillObservableList.clear();
    }

    //------------------------------------ Options Side Bar --------------------------

    /**
     * Switches the view to the HRM menu.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void switchHRMMenu(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/hrmUI.fxml");
    }

    /**
     * Switches the view to the register skill screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToRegisterSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/registerSkill.fxml");
    }

    /**
     * Switches the view to the register job screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToRegisterJob(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/registerJob.fxml");
    }

    /**
     * Switches the view to the create collaborator screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToCreateCollaborator(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/createCollaborator.fxml");
    }

    /**
     * Switches the view to the assign skill to collaborator screen.
     * @param event The action event triggered by the button click.
     * @throws IOException If an I/O exception occurs.
     */
    public void changeToAssignSkill(ActionEvent event) throws IOException {
        switchWindows.changeWindow(event, "/assignSkillCollaborator.fxml");
    }
}
