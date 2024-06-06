package pt.ipp.isep.dei.Domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // ----US17-----//

            // Ler a matriz da US17 e escrever em um ficheiro UML
            int[][] matrix17 = FileReader.readMatrix("MDISC/sprintC/main/java/pt/ipp/isep/dei/Inputs/us17_matrix.csv");

            // Ler os nomes dos pontos da US17
            String[] pointsUs17 = FileReader.readPoints("MDISC/sprintC/main/java/pt/ipp/isep/dei/Inputs/us17_points_names.csv");
            // Cria o grafo em ficheiro UML
            FileWriter.writeToUmlFile(matrix17, "MDISC/sprintC/main/java/pt/ipp/isep/dei/Outputs/inputGraph_US17.puml", pointsUs17);

            // Encontrar o índice do ponto de encontro na US17
            int apIndex17 = -1;
            for (int i = 0; i < pointsUs17.length; i++) {
                if (pointsUs17[i].startsWith("AP")) {
                    apIndex17 = i;
                    break;
                }
            }

            // Verifica se nenhum ponto de encontro foi encontrado
            if (apIndex17 == -1) {
                System.out.println("Nenhum Ponto de Encontro encontrado na US17.");
                return;
            }

            // Executar o algoritmo de Dijkstra usando a matriz da US17
            int[] distances17 = DijkstraAlgorithm.dijkstra(matrix17, apIndex17);
            List<List<Integer>> paths17 = DijkstraAlgorithm.dijkstraWithPaths(matrix17, apIndex17);

            // Escrever a saída da US17 num ficheiro CSV
            FileWriter.writeOutputUS17(pointsUs17, distances17, apIndex17, paths17, "MDISC/sprintC/main/java/pt/ipp/isep/dei/Outputs/output_US17.csv");

            // ----US18-----//

            // Ler a matriz da US18 e escrever em um ficheiro UML
            int[][] matrix18 = FileReader.readMatrix("MDISC/sprintC/main/java/pt/ipp/isep/dei/Inputs/us18_matrix.csv");
            // Ler os nomes dos pontos da US18
            String[] pointsUs18 = FileReader.readPoints("MDISC/sprintC/main/java/pt/ipp/isep/dei/Inputs/us18_points_names.csv");
            // Cria o grafo em ficheiro UML
            FileWriter.writeToUmlFile(matrix18, "MDISC/sprintC/main/java/pt/ipp/isep/dei/Outputs/inputGraph_US18.puml", pointsUs18);

            // Encontrar os índices dos pontos de encontro na US18
            List<Integer> apIndices = new ArrayList<>();
            for (int i = 0; i < pointsUs18.length; i++) {
                if (pointsUs18[i].startsWith("AP")) {
                    apIndices.add(i);
                }
            }

            // Verifica se nenhum ponto de encontro foi encontrado
            if (apIndices.isEmpty()) {
                System.out.println("Nenhum Ponto de Encontro encontrado na US18.");
                return;
            }

            // Para cada ponto, encontrar o ponto de encontro mais próximo
            int[] closestApIndices = new int[pointsUs18.length];
            int[] closestDistances = new int[pointsUs18.length];
            List<List<Integer>> allPaths = new ArrayList<>();

            for (int i = 0; i < pointsUs18.length; i++) {
                int minDistance = Integer.MAX_VALUE;
                int closestApIndex = -1;
                List<Integer> bestPath = null;

                for (int j : apIndices) {
                    int[] distances = DijkstraAlgorithm.dijkstra(matrix18, j);
                    List<List<Integer>> paths = DijkstraAlgorithm.dijkstraWithPaths(matrix18, j);

                    if (distances[i] < minDistance) {
                        minDistance = distances[i];
                        closestApIndex = j;
                        bestPath = paths.get(i);
                    }
                }
                closestApIndices[i] = closestApIndex;
                closestDistances[i] = minDistance;
                allPaths.add(bestPath);
            }

            // Escrever a saída da US18 num ficheiro CSV
            FileWriter.writeOutputUS18(pointsUs18, closestApIndices, closestDistances, allPaths, "MDISC/sprintC/main/java/pt/ipp/isep/dei/Outputs/output_US18.csv");

            List<ArrayList<String>> graphUS17 = FileReader.readOutput("MDISC/sprintC/main/java/pt/ipp/isep/dei/Outputs/output_US17.csv");
            List<ArrayList<String>> graphUS18 = FileReader.readOutput("MDISC/sprintC/main/java/pt/ipp/isep/dei/Outputs/output_US18.csv");
            System.out.println(graphUS17);
            System.out.println(graphUS18);

            FileWriter.writeToUmlFile(graphUS17,"MDISC/sprintC/main/java/pt/ipp/isep/dei/Outputs/outputGraph_US17.puml");
            FileWriter.writeToUmlFile(graphUS18,"MDISC/sprintC/main/java/pt/ipp/isep/dei/Outputs/outputGraph_US18.puml");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
