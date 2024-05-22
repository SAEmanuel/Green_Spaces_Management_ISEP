package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.domain.Data;
import pt.ipp.isep.dei.esoft.project.ui.console.AgendaUI;
import pt.ipp.isep.dei.esoft.project.ui.console.PostponeTaskAgendaUI;
import pt.ipp.isep.dei.esoft.project.ui.console.RegisterGreenSpaceUI;
import pt.ipp.isep.dei.esoft.project.ui.console.ToDoListUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorUI implements Runnable {

    public CollaboratorUI() {
    }

    public void run() {
//        if (Collaborator.getBirthDate().isBirthDate(Data.currentDate().getDay(),
//                Data.currentDate().getMonth())) {
//            System.out.printf("Happy Birthday %s%nWe are happy to have you at MusgoSublime%n", Collaborator.getName());
//            System.out.println("                0   0\n" +
//                    "                |   |\n" +
//                    "            ____|___|____\n" +
//                    "         0  |~ ~ ~ ~ ~ ~|   0\n" +
//                    "         |  |           |   |\n" +
//                    "      ___|__|___________|___|__\n" +
//                    "  0   |/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/|   0\n" +
//                    "  |   |                       |   |\n" +
//                    " _|___|_______________________|___|__\n" +
//                    "|/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/|\n" +
//                    "|             H a p p y             |\n" +
//                    "|         B i r t h d a y           |\n" +
//                    "| ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |\n" +
//                    "|___________________________________|");
//        }
        List<MenuItem> options = new ArrayList<MenuItem>();
//        options.add(new MenuItem("Register a Green Space", new RegisterGreenSpaceUI()));
//        options.add(new MenuItem("Add a New Entry to the To-Do List", new ToDoListUI()));
//        options.add(new MenuItem("Add a New Entry to the Agenda", new AgendaUI()));
//        options.add(new MenuItem("Postpone task in the Agenda", new PostponeTaskAgendaUI()));


        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- COLLABORATOR MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
