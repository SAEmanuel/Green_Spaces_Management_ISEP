import file_classes.FileReader;
import file_classes.FileWriter;
import graph_classes.Graph;

public class Main_12_13 {


    static final String OUTPUT_MM_TREE_FILE = "MDISC/sprintB/us_12_13_14_implementation/src/output_13/minimum_spanning_tree.csv";
    static final String OUTPUT_INPUT_GRAPH = "MDISC/sprintB/us_12_13_14_implementation/src/output_13/input_graph.csv";
    static final String OUTPUT_GRAPH_FILE = "MDISC/sprintB/us_12_13_14_implementation/src/output_13/graph.puml";


    public static void main(String[] args) {
        Graph g1;

        //cria o grafo a partir de um ficheiro
        g1 = FileReader.readGraph(true, null);

        //só corre o programa se o grafo criado nao estiver vazio
        if (!g1.isEmpty()) {
            FileWriter.writeInputGraph(g1,OUTPUT_INPUT_GRAPH);
            g1 = g1.treeBuilding();
            FileWriter.writeMMTreeToCsvFile(g1, OUTPUT_MM_TREE_FILE);
            FileWriter.writeToUmlFile(g1, OUTPUT_GRAPH_FILE);
        }

    }
}





