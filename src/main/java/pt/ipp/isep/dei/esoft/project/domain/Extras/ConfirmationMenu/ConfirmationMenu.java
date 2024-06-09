package pt.ipp.isep.dei.esoft.project.domain.Extras.ConfirmationMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

/**
 * The ConfirmationMenu class provides functionality to display a confirmation menu and handle user input.
 */
public class ConfirmationMenu {

    /**
     * Displays a confirmation menu and handles user input.
     *
     * @return The user's choice: 0 for confirmation, 1 for cancellation, 2 for exiting the process.
     */
    public static int confirmsData() {
        Scanner input = new Scanner(System.in);
        int option;

        while(true){
            try {
                option = input.nextInt();

                if (option == 0) {
                    return option;
                }else if (option == 1) {
                    return option;
                } else if (option == 2) {
                    System.out.println(ANSI_BRIGHT_RED+"LEAVING..."+ANSI_RESET);
                    return option; // Exit the registration process
                } else {
                    System.out.println(ANSI_BRIGHT_RED+ "Invalid choice. Please enter 0 or 1 or 2."+ ANSI_RESET);
                }
            }catch (InputMismatchException e){
                System.out.println(ANSI_BRIGHT_RED+ "Invalid choice. Please enter 0 or 1 or 2."+ ANSI_RESET);
                input.nextLine();
            }
        }
    }
}
