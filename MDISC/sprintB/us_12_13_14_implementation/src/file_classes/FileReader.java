package file_classes;

import graph_classes.Edge;
import graph_classes.Graph;
import graph_classes.Vertice;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    private static int contadorLinha = 0;
    private static String fileName = null;
    private static String fileNamePerGraph = null;

    public static Graph readGraph(Boolean singleFile, String fileNamePerGraph) {
        Graph graph = new Graph();

        //so começa a construcao do grafo se o ficheiro for válido
        if (isValidFile(singleFile, fileNamePerGraph)){

            try {
                Scanner lerFicheiro = new Scanner(new File(fileName));
                while (lerFicheiro.hasNextLine()) {
                    String line = lerFicheiro.nextLine();
                    String[] lineSplit = line.split(";");

                    //como já foi feita a validacao dos dados anteriormente, não é necessario verificacao
                    Vertice waterPointX = new Vertice(lineSplit[0]);
                    Vertice waterPointY = new Vertice(lineSplit[1]);
                    double distance = Double.parseDouble(lineSplit[2]);

                    Edge edge = new Edge(waterPointX, waterPointY, distance);
                    graph.addEdge(edge);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Couldn't find file: " + fileName);
            }
        }
        return graph;
    }

    private static boolean isValidFile(Boolean singleFile, String fileNamePerGraph) {
        Scanner ler = new Scanner(System.in);
        Scanner lerFicheiro;

        //primeiro o programa ira verificar se o ficheiro existe

        if (singleFile) {
            System.out.print("Write the name of the file: ");
            fileName = "MDISC/sprintB/us_12_13_14_implementation/src/graph_input/" + ler.nextLine();
        } else {
            fileName = "MDISC/sprintB/us_12_13_14_implementation/src/test_input/" + fileNamePerGraph;
        }

        try {
            lerFicheiro = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            // caso ele nao encontre o ficheiro, retorna false
            System.out.println("Couldn't find file: " + fileName);
            return false;
        }

        //verifica se todos os dados do ficheiro são validos (3 colunas, vertices e o custo)
        try{
            while (lerFicheiro.hasNextLine()) {
                contadorLinha++;
                String line = lerFicheiro.nextLine();
                String[] lineSplit = line.split(";");

                if (lineSplit.length != 3 || !isValidString(lineSplit[0]) || !isValidString(lineSplit[1]) || !isDouble(lineSplit[2])) {
                    throw new InvalidFileFormatException("Check line: " + contadorLinha);

                }
            }
        } catch (InvalidFileFormatException e){
            System.out.println(e.getMessage());
            return false;
        } finally {
            ler.close();
        }
        return true;
    }

    private static boolean isDouble(String word) {
        try {
            //tenta converter a String em um inteiro positivo
            return Double.parseDouble(word) >= 0;
        } catch (NumberFormatException exception) {
            //Caso a String nao seja um numero inteiro, ira retorna false
            return false;
        }
    }

    private static boolean isValidString(String word) {
        //retorna true se for uma String valida
        return word != null && !word.isEmpty();
    }

}
