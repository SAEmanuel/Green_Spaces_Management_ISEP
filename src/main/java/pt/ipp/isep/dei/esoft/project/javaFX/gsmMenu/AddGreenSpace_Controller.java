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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.application.controller.GreenSpaceController;
import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.ConfirmationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.InformationAlerts;
import pt.ipp.isep.dei.esoft.project.javaFX.alerts.SendErrors;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class AddGreenSpace_Controller implements Initializable {

    GreenSpaceController controller = new GreenSpaceController();
    private SendErrors sendErrors = new SendErrors();
    private ConfirmationAlerts sendConfirmation = new ConfirmationAlerts();
    private InformationAlerts sendInformation = new InformationAlerts();


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

    @FXML
    private TextField label_parkName, label_parkArea,label_parkAddress;
    @FXML
    private ChoiceBox<String> choiceBox_Size;

    private Stage stage;
    private final GreenSpace.Size[] sizes = GreenSpace.Size.values();
    private String name,address;
    private int area,size;

    ObservableList<GreenSpace> list = FXCollections.observableArrayList(controller.getAllGreenSpaces());



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox_Size.getItems().addAll(getTamanhos());
        table_address.setCellValueFactory(new PropertyValueFactory<GreenSpace, String>("address"));
        table_name.setCellValueFactory(new PropertyValueFactory<GreenSpace, String>("name"));
        table_area.setCellValueFactory(new PropertyValueFactory<GreenSpace, Integer>("area"));
        table_size.setCellValueFactory(new PropertyValueFactory<GreenSpace, GreenSpace.Size>("size"));
        table_greenSpaces.setItems(list);
    }

    private String[] getTamanhos() {
        String[] size = new String[sizes.length];
        for (GreenSpace.Size s : sizes) {
            size[s.ordinal()] = s.toString();
        }
        return size;
    }


    public void submitRegistration(ActionEvent event) {
        boolean success = getValues();
        if (success) {
            try {
                Optional<GreenSpace> greenSpace = controller.registerGreenSpace(name,size,area,address);
                list = FXCollections.observableArrayList(controller.getAllGreenSpaces());
                table_greenSpaces.setItems(list);
                if (greenSpace.isPresent()) {
                    sendConfirmation.confirmationMessages("Success","Green Space successfully registered!","");
                } else {
                    sendInformation.informationMessages("ATTENTION","Green Space not registered - Already registered!","");
                }
            } catch (IllegalArgumentException e) {

                sendErrors.errorMessages("Invalid Inputs",e.getMessage(),"");
            }
        }
    }

    private boolean getValues() {
        try {
            area = Integer.parseInt(label_parkArea.getText());
            name = label_parkName.getText();
            address = label_parkAddress.getText();
            size = choiceBox_Size.getSelectionModel().getSelectedIndex();
            if (size == -1 ) {
                throw new IllegalArgumentException();
            }
            return true;
        }catch (NumberFormatException e) {
            String title = "Invalid Input area";
            String header = "Invalid Area! Make sure only put numbers...";
            String content = "Enter a new one:";
            sendErrors.errorMessages(title,header,content);
            return false;
        }catch (IllegalArgumentException e){
            String title = "Invalid Size";
            String header = "Invalid Size! Make sure to select a size... ";
            String content = "Enter a new one:";
            sendErrors.errorMessages(title,header,content);
            return false;
        }
    }



    public void clear(ActionEvent event) {
        label_parkName.clear();
        label_parkArea.clear();
        label_parkAddress.clear();
        choiceBox_Size.getSelectionModel().clearSelection();
    }


    
    
    //------------------------------------ Options Side Bar --------------------------

    public void switchGSMMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gsmUI.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToEntryToDoList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/addToDoEntry.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToAddEntryAgenda(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/addEntryAgenda.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToAssignTeam(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/assignTeamToAgendaTask.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToAssignVehicle(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/assignVehicleToAgendaTask.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToPsotponeTask(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/postponeTask.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToCancelTask(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/cancelTask.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeToMyGreenSpaces(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/myGreenSpaces.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
