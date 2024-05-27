package pt.ipp.isep.dei.esoft.project.domain;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class SortGreenSpaces {
    private static Properties config;

    private static void loadFileConfig() throws IOException {
        config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/config.properties")) {
            config.load(fis);
        }
    }

    public static List<GreenSpace> sortGreenSpaces( String sortingMethod, List<GreenSpace> greenSpaces) throws IOException {
        loadFileConfig();
        boolean sorted = false;

        for (String key : config.stringPropertyNames()) {
            if (key.startsWith("sorting.")) {
                String value = config.getProperty(key);

                if(value.equalsIgnoreCase(sortingMethod)){
                    if (value.equalsIgnoreCase("ascending")) {
                        greenSpaces = ascending(greenSpaces);
                        sorted= true;
                    }else if (value.equalsIgnoreCase("descending")) {
                        greenSpaces = descending(greenSpaces);
                        sorted= true;
                    }
                }
            }
        }

        if(sorted)
            return greenSpaces;
        else{
            System.out.printf(ANSI_BRIGHT_RED + "\nNo sort method found" + ANSI_RESET);
            return null;
        }
    }

    private static List<GreenSpace> ascending(List<GreenSpace> greenSpaces) {
        Collections.sort(greenSpaces, Comparator.comparing(GreenSpace::getName));
        return greenSpaces;
    }

    // Example sorting method
    private static List<GreenSpace> descending(List<GreenSpace> greenSpaces) {
        Collections.sort(greenSpaces, Comparator.comparing(GreenSpace::getName).reversed());
        return greenSpaces;
    }

    public static List<String> getSortTypes() throws IOException {
        loadFileConfig();
        List<String> sortTypes = new ArrayList<>();

        for (String key : config.stringPropertyNames()) {
            if (key.startsWith("sorting.")) {
                String value = config.getProperty(key);
                if(!value.equalsIgnoreCase("false"))
                    sortTypes.add(value);
            }
        }

        return sortTypes;
    }
}
