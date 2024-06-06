package pt.ipp.isep.dei.Domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public static int[][] readMatrix(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.FileReader(filePath));
        String line;
        int[][] matrix = null;
        int row = 0;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(";");
            if (matrix == null) {
                matrix = new int[values.length][values.length];
            }
            for (int col = 0; col < values.length; col++) {
                matrix[row][col] = convertToInt(values[col]);
            }
            row++;
        }
        br.close();
        return matrix;
    }

    private static int convertToInt(String str) {
        // Remove qualquer caracter não numérico
        str = str.replaceAll("[^\\d]", "");
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0; // Retorna 0 se não for possível converter para int
        }
    }

    public static String[] readPoints(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.FileReader(filePath));
        String line = br.readLine();
        br.close();
        return line.split(";");
    }

    public static List<ArrayList<String>> readOutput(String filePath) throws IOException {
        List<ArrayList<String>> output = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filePath))) {
            while (sc.hasNextLine()) {
                String[] values = sc.nextLine().split(";");
                ArrayList<String> row = new ArrayList<>(Arrays.asList(values));
                output.add(row);
            }
        }
        return output;
    }

}
