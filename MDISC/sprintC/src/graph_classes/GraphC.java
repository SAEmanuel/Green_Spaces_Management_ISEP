package graph_classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphC implements Iterable<EdgeC> {
    private ArrayList<EdgeC> grafo;

    public GraphC() {
        grafo = new ArrayList<>();
    }

    public Iterator<EdgeC> iterator() {
        return grafo.iterator();
    }

    public void addEdge(EdgeC edge) {
        this.grafo.add(edge);
    }



    public ArrayList<VerticeC> getVertices() {

        ArrayList<VerticeC> vertices = new ArrayList<>();

        for (EdgeC edge : this.grafo) {
            if (!vertices.contains(edge.getPointX())) {
                vertices.add(edge.getPointX());
            }

            if (!vertices.contains(edge.getPointY())) {
                vertices.add(edge.getPointY());
            }
        }
        return vertices;
    }

    @Override
    public String toString() {
        return String.format("Grafo: %s", grafo);
    }

    public boolean isEmpty() {
        return grafo.isEmpty();
    }

    public double getTotalCost() {
        double total = 0;
        for (EdgeC edge : this.grafo) {
            total += edge.getCost();
        }
        return total;
    }

    public int getNumEdges() {
        return this.grafo.size();
    }

    // ------------------ Implementing the Algorithm ------------------ //


    public GraphC treeBuilding() {
        List<ArrayList<VerticeC>> containers = new ArrayList<>();
        GraphC tree = new GraphC();
        removeCycles();
        removeParallelEdges();
        createAndFillContainers(containers);

        for (EdgeC edge : grafo) {
            ArrayList<VerticeC> containerX = getContainerWithVertex(edge.getPointX(), containers);
            ArrayList<VerticeC> containerY = getContainerWithVertex(edge.getPointY(), containers);

            if (containerX != containerY) {
                tree.addEdge(edge);
                containerX.addAll(containerY);
                containers.remove(containerY);
            }
        }

        return tree;
    }

    private void orderEdgesByDistance() {
        for (int i = 0; i < this.grafo.size(); i++) {
            for (int j = i + 1; j < this.grafo.size(); j++) {
                if (this.grafo.get(i).compareTo(this.grafo.get(j)) > 0) {
                    EdgeC temp = this.grafo.get(i);
                    this.grafo.set(i, this.grafo.get(j));
                    this.grafo.set(j, temp);
                }
            }
        }
    }

    private void removeCycles() {
        for (EdgeC edge : this.grafo) {
            if (edge.getPointX().equals(edge.getPointY())) {
                this.grafo.remove(edge);
            }
        }
    }

    private void removeParallelEdges() {
        orderEdgesByDistance();
        for (int i = 0; i < this.grafo.size(); i++) {
            for (int j = this.grafo.size() - 1; j > i; j--) {
                if (this.grafo.get(i).equals(this.grafo.get(j))) {
                    this.grafo.remove(j);
                }

            }
        }
    }

    private void createAndFillContainers(List<ArrayList<VerticeC>> containers) {
        ArrayList<VerticeC> vertices = getVertices();

        for (VerticeC vertex : vertices) {
            ArrayList<VerticeC> container = new ArrayList<>();
            container.add(vertex);
            containers.add(container);
        }
    }

    private static ArrayList<VerticeC> getContainerWithVertex(VerticeC vertex, List<ArrayList<VerticeC>> containers) {
        for (ArrayList<VerticeC> container : containers) {
            if (container.contains(vertex)) {
                return container;
            }
        }
        return null;
    }

    // ------------------------------------------------- //




}
