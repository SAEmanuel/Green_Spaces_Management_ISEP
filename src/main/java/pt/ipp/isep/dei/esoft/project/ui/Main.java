package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.Serialization;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MainMenuUI;

import java.util.Scanner;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.*;

public class Main {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.run();

        try {
            MainMenuUI menu = new MainMenuUI();
            menu.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        save();
    }

    private static void save() {
        System.out.printf(ANSI_BRIGHT_YELLOW+"\nDo you wish to save the changes made? (y/n): "+ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) {
                saveInfoProcess();
            } else if (!input.equalsIgnoreCase("n")) {
                System.out.printf(ANSI_BRIGHT_RED + "Please enter a valid option! (y/n): " + ANSI_RESET);
            } else {
                System.out.printf(ANSI_BLUE +"\nNo information saved...\n"+ ANSI_RESET);
            }
        }while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

    }


    private static void saveInfoProcess() {
        System.out.println("\n"+ANSI_BRIGHT_YELLOW+"Saving info process..."+ANSI_RESET);
        System.out.println(ANSI_BRIGHT_YELLOW+"This can take a few minutes!"+ANSI_RESET);
        try {
            saveAppInformation();
            System.out.println(ANSI_BRIGHT_GREEN+"App information successfully saved!"+ANSI_RESET);
        }catch(Exception e) {
            System.out.printf("%sApp information cannot be saved, check: %s%s",ANSI_BRIGHT_RED,e.getMessage(),ANSI_RESET);
        }
    }



    protected static void saveAppInformation(){
        Serialization serialization = new Serialization();
        serialization.serializeSkillOutput();
        serialization.serializeVehicleOutput();
    }

}