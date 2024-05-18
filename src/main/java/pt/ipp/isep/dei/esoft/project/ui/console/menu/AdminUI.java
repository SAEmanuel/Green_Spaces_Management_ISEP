package pt.ipp.isep.dei.esoft.project.ui.console.menu;


import pt.ipp.isep.dei.esoft.project.ui.console.*;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */

public class AdminUI implements Runnable {
    public AdminUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register Skill", new RegisterSkillUI()));
        options.add(new MenuItem("Register Job", new RegisterJobUI()));
        options.add(new MenuItem("Create Collaborator", new CreateCollaboratorUI()));
        options.add(new MenuItem("Assign Skill to Collaborator", new AssignSkillCollaboratorUI()));
        options.add(new MenuItem("Create Team", new GenerateTeamUI()));
        options.add(new MenuItem("Register Vehicle", new RegisterVehicleUI()));
        options.add(new MenuItem("Register Vehicle Check-Up", new CheckUpRegisterUI()));
        options.add(new MenuItem("Create Vehicle Check-Up List", new CheckUpListUI()));
        options.add(new MenuItem("Register a Green Space", new RegisterGreenSpaceUI()));
        options.add(new MenuItem("Add a New Entry to the To-Do List", new ToDoListUI()));
        options.add(new MenuItem("Add a New Entry to the Agenda", new AgendaUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- ADMIN MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}