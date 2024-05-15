package graph_classes;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GraphicCreatorC {

    public static void createScatterPlot(String inputFileName, String outputFileName) throws IOException {

        // Ler os dados do arquivo
        XYDataset dataset = null;

        try {
            dataset = getXyDataset(inputFileName);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IOException("Input file not valid");
        }


        // Criar o gráfico
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Runtime Kruskal Algorithm", // Título do gráfico
                "Number of Edges", // Rótulo do eixo X
                "Execution Time (in milliseconds)", // Rótulo do eixo Y
                dataset, // Dados
                PlotOrientation.VERTICAL,
                true, // Incluir legenda
                true, // Incluir tooltips
                false // Incluir URLs
        );

        // Salvar o gráfico como PNG
        int width = 800;   /* Largura do gráfico */
        int height = 600;  /* Altura do gráfico */
        ChartUtils ChartUtilities = null;
        ChartUtils.saveChartAsPNG(new File(outputFileName), chart, width, height);
    }

    private static XYDataset getXyDataset(String inputFileName) throws IOException {
        XYSeries series = new XYSeries("Time");
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            double x = Double.parseDouble(parts[0]);
            double y = Double.parseDouble(parts[1]);
            series.add(x, y);
        }
        reader.close();

        // Criar o conjunto de dados
        return new XYSeriesCollection(series);
    }

}
