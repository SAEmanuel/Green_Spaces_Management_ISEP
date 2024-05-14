package main.java.pt.ipp.isep.dei.file_classes;


import main.java.pt.ipp.isep.dei.graph_classes.Graph;

import java.io.File;
import java.util.ArrayList;


public class FileScanner {
    static final double CONVERTOR_NS_MLS = Math.pow(10, 6);
    static final double THOUSAND = Math.pow(10, 3);
    static final String DIRECTORY_CSV = "src/main/java/pt/ipp/isep/dei/test_input";
    static final int NUM_REPETITIONS = 5;

    public static ArrayList<String> scanCSVFiles() {
        ArrayList<String> runtimes = new ArrayList<>();
        String tempString;
        int numEdgesInput = 0;
        double startTime, elapsedTime, endTime, totalTime;

        File pasta = new File(DIRECTORY_CSV);
        File[] files = pasta.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".csv"));

        if (files != null) {
            for (File file : files) {
                totalTime = 0;
                System.out.printf("Processing file: %s -> Average Time: ", file.getName());
                for (int i = 0; i < NUM_REPETITIONS; i++) {
                    Graph generalGraph = FileReader.readGraph(false, file.getName());
                    numEdgesInput = generalGraph.getNumEdges();

                    startTime = System.nanoTime();
                    generalGraph.treeBuilding();
                    endTime = System.nanoTime();

                    elapsedTime = (endTime - startTime) / CONVERTOR_NS_MLS;
                    totalTime += elapsedTime;
                }
                double averageTime = totalTime / NUM_REPETITIONS;
                System.out.printf("%.2fms%n", averageTime);
                tempString = (numEdgesInput + "," + averageTime);
                runtimes.add(tempString);
            }
        } else {
            System.err.println("No .csv file found.");
        }

        return runtimes;
    }
}