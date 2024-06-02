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
                    switch (sortingMethod){
                        case "Ascending By Name":
                            greenSpaces = ascendingName(greenSpaces);
                            sorted= true;
                            break;
                        case "Descending By Name":
                            greenSpaces = descendingName(greenSpaces);
                            sorted= true;
                            break;
                        case "Bubble Sort":
                            greenSpaces = bubbleSort(greenSpaces);
                            sorted= true;
                            break;
                        case "Insertion Sort":
                            greenSpaces = insertionSort(greenSpaces);
                            sorted= true;
                            break;
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

    private static List<GreenSpace> ascendingName(List<GreenSpace> greenSpaces) {
        Collections.sort(greenSpaces, Comparator.comparing(GreenSpace::getName));
        return greenSpaces;
    }

    private static List<GreenSpace> descendingName(List<GreenSpace> greenSpaces) {
        Collections.sort(greenSpaces, Comparator.comparing(GreenSpace::getName).reversed());
        return greenSpaces;
    }

    private static List<GreenSpace> bubbleSort(List<GreenSpace> greenSpaces) {
        List<GreenSpace> newGreenSpacesList = new ArrayList<>(greenSpaces);
        int n = newGreenSpacesList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (newGreenSpacesList.get(j).getArea() < newGreenSpacesList.get(j + 1).getArea()) {
                    // Swap greenSpaces[j] and greenSpaces[j+1] for descending order
                    GreenSpace temp = newGreenSpacesList.get(j);
                    newGreenSpacesList.set(j, newGreenSpacesList.get(j + 1));
                    newGreenSpacesList.set(j + 1, temp);
                }
            }
        }
        return newGreenSpacesList;
    }

    private static List<GreenSpace> insertionSort(List<GreenSpace> greenSpaces) {
        List<GreenSpace> newGreenSpacesList = new ArrayList<>(greenSpaces);
        int n = newGreenSpacesList.size();
        for (int i = 1; i < n; ++i) {
            GreenSpace key = newGreenSpacesList.get(i);
            int j = i - 1;

            // Move elements of arr[0..i-1], that are greater than key, to one position ahead
            // of their current position
            while (j >= 0 && newGreenSpacesList.get(j).getArea() < key.getArea()) {
                newGreenSpacesList.set(j + 1, newGreenSpacesList.get(j));
                j = j - 1;
            }
            newGreenSpacesList.set(j + 1, key);
        }

        return newGreenSpacesList;
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
