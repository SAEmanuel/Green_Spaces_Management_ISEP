import file_classes.FileReaderC;
import file_classes.FileWriterC;
import graph_classes.GraphC;

public class Main_17 {


    static final String OUTPUT_MM_TREE_FILE = "MDISC/sprintC/src/output_17/minimum_spanning_tree.csv";
    static final String OUTPUT_INPUT_GRAPH = "MDISC/sprintC/src/output_17/input_graph.csv";
    static final String OUTPUT_GRAPH_FILE = "MDISC/sprintC/src/output_17/graph.puml";
    static final String OUTPUT_INITIAL_GRAPH_FILE = "MDISC/sprintC/src/output_17/initial_graph.puml";


    public static void main(String[] args) {
        GraphC g1;

        //cria o grafo a partir de um ficheiro
        g1 = FileReaderC.readGraph(true, null);

        //só corre o programa se o grafo criado nao estiver vazio
        if (!g1.isEmpty()) {
            FileWriterC.writeToUmlFile(g1, OUTPUT_INITIAL_GRAPH_FILE);
            FileWriterC.writeInputGraph(g1,OUTPUT_INPUT_GRAPH);
            //g1 = g1.treeBuilding(); // VAMOS UTILIZAR O MESMO ALGORITMO????????
            FileWriterC.writeMMTreeToCsvFile(g1, OUTPUT_MM_TREE_FILE);
            FileWriterC.writeToUmlFile(g1, OUTPUT_GRAPH_FILE);
        }

    }
}