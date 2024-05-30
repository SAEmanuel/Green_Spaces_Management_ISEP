package pt.ipp.isep.dei.esoft.project.ui;


import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password;


import static pt.ipp.isep.dei.esoft.project.domain.Extras.Inputs.Password.getPasswordInput;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class ChangePasswordUI implements Runnable {

    private final Collaborator collaborator;
    private Password newPassword;
    private final CreateCollaboratorController controller;

    public ChangePasswordUI(Collaborator collaborator) {
        this.collaborator = collaborator;
        controller = new CreateCollaboratorController();
    }

    public CreateCollaboratorController getController() {
        return controller;
    }

    @Override
    public void run() {
        System.out.println("\n\n--- Password Change ------------------------");
        newPassword = requestPassword();
        submitData();

    }

    private Password requestPassword() {
        System.out.print("\n-- Collaborator's new password --\n");
        System.out.println(ANSI_BRIGHT_YELLOW + "(Password must have 7 alphanumeric characters, including three capital letters and two digits)" + ANSI_RESET);
        return getPassword();
    }

    private Password getPassword() {
        Password newPassword;

        while (true) {
            String pass = requestPass();
            try {
                newPassword = new Password(pass);
                return newPassword;
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_BRIGHT_RED + e.getMessage() + ANSI_RESET);
            }
        }
    }

    private String requestPass() {
        return getPasswordInput();
    }

    private void submitData() {
        try {
            boolean result = getController().changePassword(collaborator, newPassword);

            if (result) {
                System.out.println(ANSI_BRIGHT_GREEN + "Password successfully changed!" + ANSI_RESET);
            } else {
                System.out.println(ANSI_BRIGHT_RED + "Password not changed!" + ANSI_RESET);
            }

        } catch (IllegalArgumentException e) {
            System.out.print("");
        }

    }


}
