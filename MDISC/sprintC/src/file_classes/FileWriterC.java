package file_classes;

import graph_classes.EdgeC;
import graph_classes.GraphC;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileWriterC {

    static PrintWriter minimumSpanningTreePrintWriter;
    static PrintWriter inputGraphPrintWriter;
    static PrintWriter graphPrintWriter;


    public static void writeElapsedTime(ArrayList<String> runtimes, String outputFile) {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            for (String runtime : runtimes) {
                writer.println(runtime);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find file: " + outputFile);
        }
    }

    // ------------------ Writing to CSV file ------------------ //
    public static void writeMMTreeToCsvFile(GraphC tree, String MMTreeFile) {
        try {
            minimumSpanningTreePrintWriter = new PrintWriter(MMTreeFile);
            minimumSpanningTreePrintWriter.println("Minimum Spanning Tree:");
            for (EdgeC edge : tree) {
                minimumSpanningTreePrintWriter.printf("%s;%s;%.0f%n", edge.getPointX(),
                        edge.getPointY(), edge.getCost());
            }
            minimumSpanningTreePrintWriter.println("--------------------");
            minimumSpanningTreePrintWriter.printf("Minimum cost: %.2f%n", tree.getTotalCost());
            minimumSpanningTreePrintWriter.close();


        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public static void writeInputGraph(GraphC graph, String inputGraph) {
        try {
            inputGraphPrintWriter = new PrintWriter(inputGraph);
            inputGraphPrintWriter.println("Input Graph:");
            for (EdgeC edge : graph) {
                inputGraphPrintWriter.printf("%s;%s;%.0f%n", edge.getPointX(),
                        edge.getPointY(), edge.getCost());
            }
            inputGraphPrintWriter.close();


        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    // ------------------ Writing to DOT file ------------------ //

    public static void writeToUmlFile(GraphC tree, String dotFile) {
        try {

            graphPrintWriter = new PrintWriter(dotFile);
            graphPrintWriter.println("@startuml");
            graphPrintWriter.printf("graph GraphRep {%n");

            for (EdgeC edge : tree) {
                graphPrintWriter.printf("%s --%s [label=\"%.0f\"]%n", edge.getPointX(),
                        edge.getPointY(), edge.getCost());
            }
            graphPrintWriter.printf("}%n");
            graphPrintWriter.println("@enduml");
            graphPrintWriter.close();

        } catch (FileNotFoundException exception) {
            System.out.println("File write error: " + exception.getMessage());
        }
    }
}
