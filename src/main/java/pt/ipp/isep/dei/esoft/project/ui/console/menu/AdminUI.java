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
        options.add(new MenuItem("Create Task", new CreateTaskUI()));
        options.add(new MenuItem("Register Skill", new RegisterSkillUI()));
        options.add(new MenuItem("Register Job", new RegisterJobUI()));
        options.add(new MenuItem("Create Collaborator", new ShowTextUI("Soon...")));
        options.add(new MenuItem("Assign Skill to Collaborator", new AssignSkillCollaboratorUI()));
        options.add(new MenuItem("Create Team", new ShowTextUI("Soon...")));
            options.add(new MenuItem("Register Vehicle", new RegisterVehicleUI()));
        options.add(new MenuItem("Register Vehicle Check-Up",new CheckUpRegisterUI()));
        options.add(new MenuItem("Create Vehicle Check-Up List", new CheckUpListUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- ADMIN MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}