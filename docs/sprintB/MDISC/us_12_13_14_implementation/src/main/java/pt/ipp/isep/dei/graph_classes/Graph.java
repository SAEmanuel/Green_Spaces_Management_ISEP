package main.java.pt.ipp.isep.dei.graph_classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Graph implements Iterable<Edge> {
    private ArrayList<Edge> grafo;

    public Graph() {
        grafo = new ArrayList<>();
    }

    public Iterator<Edge> iterator() {
        return grafo.iterator();
    }

    public void addEdge(Edge edge) {
        this.grafo.add(edge);
    }



    public ArrayList<Vertice> getVertices() {

        ArrayList<Vertice> vertices = new ArrayList<>();

        for (Edge edge : this.grafo) {
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
        for (Edge edge : this.grafo) {
            total += edge.getCost();
        }
        return total;
    }

    public int getNumEdges() {
        return this.grafo.size();
    }

    // ------------------ Implementing the Algorithm ------------------ //


    public Graph treeBuilding() {
        List<ArrayList<Vertice>> containers = new ArrayList<>();
        Graph tree = new Graph();
        removeCycles();
        removeParallelEdges();
        createAndFillContainers(containers);

        for (Edge edge : grafo) {
            ArrayList<Vertice> containerX = getContainerWithVertex(edge.getPointX(), containers);
            ArrayList<Vertice> containerY = getContainerWithVertex(edge.getPointY(), containers);

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
                    Edge temp = this.grafo.get(i);
                    this.grafo.set(i, this.grafo.get(j));
                    this.grafo.set(j, temp);
                }
            }
        }
    }

    private void removeCycles() {
        for (Edge edge : this.grafo) {
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

    private void createAndFillContainers(List<ArrayList<Vertice>> containers) {
        ArrayList<Vertice> vertices = getVertices();

        for (Vertice vertex : vertices) {
            ArrayList<Vertice> container = new ArrayList<>();
            container.add(vertex);
            containers.add(container);
        }
    }

    private static ArrayList<Vertice> getContainerWithVertex(Vertice vertex, List<ArrayList<Vertice>> containers) {
        for (ArrayList<Vertice> container : containers) {
            if (container.contains(vertex)) {
                return container;
            }
        }
        return null;
    }

    // ------------------------------------------------- //




}
