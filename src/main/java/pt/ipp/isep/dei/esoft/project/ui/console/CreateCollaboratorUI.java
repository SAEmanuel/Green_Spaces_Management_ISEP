package pt.ipp.isep.dei.esoft.project.ui.console;

import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.Data;

public class CreateCollaboratorUI {

    private final CreateCollaboratorController controller;
    private String name;
    private Data birthDate;
    private Data admissionDate;
    private String address;
    private int phoneNumber;
    private String emailAddress;
    private int taxPayerNumber;
    private String docType;
    private int job;

    public CreateCollaboratorUI() {
        this.controller = new CreateCollaboratorController();
    }

    public CreateCollaboratorController getController() {
        return controller;
    }
    //implementar runnable
}
