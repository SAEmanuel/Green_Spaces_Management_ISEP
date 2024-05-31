package pt.ipp.isep.dei.esoft.project.ui.console.menu;



import pt.ipp.isep.dei.esoft.project.ui.console.*;

import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class GsmUI implements Runnable {

    public GsmUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register Green Space", new RegisterGreenSpaceUI()));
        options.add(new MenuItem("Add Entry To-Do List", new ToDoListUI()));
        options.add(new MenuItem("Add Entry Agenda", new AgendaUI()));
        options.add(new MenuItem("Assign Team to Task in Agenda", new AssignTeamToTaskAgendaUI()));
        options.add(new MenuItem("Postpone Task in Agenda", new PostponeTaskAgendaUI()));
        options.add(new MenuItem("Cancel Task in Agenda", new CancelAgendaTaskUI()));
        options.add(new MenuItem("Assign Vehicle to Task in Agenda", new AssignVehicleToAgendaTaskUI()));
        options.add(new MenuItem("Show Green Spaces Managed by Me", new ShowGreenSpacesUI()));




        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- GSM MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }

}
