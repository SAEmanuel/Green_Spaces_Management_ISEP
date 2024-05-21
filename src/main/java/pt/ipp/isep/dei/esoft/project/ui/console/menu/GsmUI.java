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
        options.add(new MenuItem("Register a Green Space", new RegisterGreenSpaceUI()));
        options.add(new MenuItem("Add a New Entry to the To-Do List", new ToDoListUI()));
        options.add(new MenuItem("Add a New Entry to the Agenda", new AgendaUI()));
        options.add(new MenuItem("Assign a team to a task in the Agenda", new AssignTeamToTaskAgendaUI()));
        options.add(new MenuItem("Postpone task in the Agenda", new PostponeTaskAgendaUI()));




        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- GSM MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }

}
