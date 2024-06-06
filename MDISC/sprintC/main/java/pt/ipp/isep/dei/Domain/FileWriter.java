package pt.ipp.isep.dei.Domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileWriter {
    public static void writeOutputUS17(String[] points, int[] distances, int apIndex, List<List<Integer>> paths, String outputPath) throws IOException {
        try (PrintWriter writer = new PrintWriter(outputPath)) {
            for (int i = 0; i < points.length; i++) {
                if (i != apIndex && distances[i] != Integer.MAX_VALUE) {
                    List<Integer> path = paths.get(i);
                    for (int j = 0; j < path.size(); j++) {
                        writer.print(points[path.get(j)]);
                        if (j < path.size() - 1) {
                            writer.print(";");
                        }
                    }
                    writer.print(";" + distances[i]);
                    writer.println();
                }
            }
        }
    }

    public static void writeOutputUS18(String[] points, int[] closestApIndices, int[] distances, List<List<Integer>> allPaths, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (int i = 0; i < points.length; i++) {
                if (distances[i] != Integer.MAX_VALUE && distances[i] != 0) {
                    List<Integer> path = allPaths.get(i);
                    for (int j = path.size() - 1; j >= 0; j--) {
                        writer.print(points[path.get(j)]);
                        if (j > 0) {
                            writer.print(";");
                        }
                    }
                    writer.print(";" + distances[i]);
                    writer.println();
                }
            }
        }
    }


    public static void writeToUmlFile(int[][] matrix, String file, String[] points) {
        try (PrintWriter graphPrintWriter = new PrintWriter(file)) {
            graphPrintWriter.println("@startuml");
            graphPrintWriter.printf("graph GraphRep {%n");

            int numVertices = matrix.length;

            for (int i = 0; i < numVertices; i++) {
                for (int j = i + 1; j < numVertices; j++) {
                    int weight = matrix[i][j];
                    if (weight > 0) {
                        graphPrintWriter.printf("%s --%s [label=\"%d\"]%n", points[i], points[j], weight);
                    }
                }
            }

            graphPrintWriter.printf("}%n");
            graphPrintWriter.println("@enduml");
        } catch (FileNotFoundException exception) {
            System.out.println("File write error: " + exception.getMessage());
        }
    }

}
