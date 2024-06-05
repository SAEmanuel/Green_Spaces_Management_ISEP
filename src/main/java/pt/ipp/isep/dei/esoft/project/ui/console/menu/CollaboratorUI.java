package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import javazoom.jl.player.Player;
import pt.ipp.isep.dei.esoft.project.application.controller.CreateCollaboratorController;
import pt.ipp.isep.dei.esoft.project.domain.Collaborator;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.ui.console.RetrieveTasksUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
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
        Collaborator loggedInCollaborator = getLoggedInCollaborator(collaborators);

        for (Collaborator colab : collaborators) {
            if (colab.getEmailAddress().equals(controller.getResponsible()) &&
                    colab.getBirthDate().isBirthDate(colab.getBirthDate().getDay(), colab.getBirthDate().getMonth())) {
                System.out.printf(ANSI_BRIGHT_YELLOW + "Happy Birthday %s!!!%nWe are happy to have you at MusgoSublime%n", colab.getName());
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
                        "|___________________________________|" + ANSI_RESET);


                String path = "src/main/java/pt/ipp/isep/dei/esoft/project/domain/Extras/Sounds/happyBirthday.mp3";
                try {
                    FileInputStream fis = new FileInputStream(path);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    Player player = new Player(bis);

                    player.play();
                } catch (Exception e) {
                    System.out.println("Music didn't play: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        }
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Consult assigned tasks", new RetrieveTasksUI(loggedInCollaborator)));


        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- COLLABORATOR MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }

    private Collaborator getLoggedInCollaborator(List<Collaborator> collaborators) {
        String loggedInEmail = controller.getResponsible();
        for (Collaborator collaborator : collaborators) {
            if (collaborator.getEmailAddress().equals(loggedInEmail)) {
                return collaborator;
            }
        }
        return null;
    }
}
