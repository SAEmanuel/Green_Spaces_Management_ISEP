package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorUI implements Runnable {
    private CreateCollaboratorController controller;

    public CollaboratorUI() {
        this.controller = new CreateCollaboratorController();
    }

    public void run() {
        Repositories repositories = Repositories.getInstance();
        List<Collaborator> collaborators = repositories.getCollaboratorRepository().getCollaboratorList();
        for (Collaborator c : collaborators) {
            if (c.getEmailAddress().equals(controller.getResponsible()) && c.getBirthDate().isBirthDate(c.getBirthDate().getDay(), c.getBirthDate().getMonth())) {
                System.out.printf("Happy Birthday %s%nWe are happy to have you at MusgoSublime%n", c.getName());
                System.out.println("                0   0\n" +
                        "                |   |\n" +
                        "            ____|___|____\n" +
                        "         0  |~ ~ ~ ~ ~ ~|   0\n" +
                        "         |  |           |   |\n" +
                        "      ___|__|___________|___|__\n" +
                        "  0   |/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/|   0\n" +
                        "  |   |                       |   |\n" +
                        " _|___|_______________________|___|__\n" +
                        "|/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/|\n" +
                        "|             H a p p y             |\n" +
                        "|         B i r t h d a y           |\n" +
                        "| ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |\n" +
                        "|___________________________________|");
            }

        }
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
