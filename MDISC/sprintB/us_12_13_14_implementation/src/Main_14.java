import file_classes.FileScanner;
import file_classes.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static graph_classes.GraphicCreator.createScatterPlot;

public class Main_14 {

    static final String OUTPUT_FILE_GRAPHIC = "MDISC/sprintB/us_12_13_14_implementation/src/main/java/pt/ipp/isep/dei/output_14/runtime_graphic.png";
    static final String INPUT_FILE_GRAPHIC = "MDISC/sprintB/us_12_13_14_implementation/src/main/java/pt/ipp/isep/dei/output_14/runtime_log.csv";

    public static void main(String[] args) {
        ArrayList<String> runtimes;

        //============== US14 ==============
        //Scan dos arquivos no diretório
        runtimes = FileScanner.scanCSVFiles();
        FileWriter.writeElapsedTime(runtimes, INPUT_FILE_GRAPHIC);

        //criacao de grafico com os tempos de execucao
        try {
            createScatterPlot(INPUT_FILE_GRAPHIC, OUTPUT_FILE_GRAPHIC);
            System.out.println("Graphic created successfully.");
        } catch (
                IOException e) {
            System.err.println("Error during graphic creation: " + e.getMessage());
        }
    }
}
