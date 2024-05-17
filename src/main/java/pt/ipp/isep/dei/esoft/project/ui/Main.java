package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.application.SerializationOutput;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MainMenuUI;

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

        saveInfoProcess();
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
        SerializationOutput serializationOutput = new SerializationOutput();
        serializationOutput.serializeSkill();
    }

}