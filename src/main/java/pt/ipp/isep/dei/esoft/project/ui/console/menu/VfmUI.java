package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.*;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class VfmUI implements Runnable{
    public VfmUI() {
    }

    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Create Vehicle", new RegisterVehicleUI()));
        options.add(new MenuItem("Register Vehicle Check-Up", new ShowTextUI("Soon...")));
        options.add(new MenuItem("Create Vehicle Check-Up List", new CheckUpListUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\n--- VFM MENU -------------------------");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
