package main.java.pt.ipp.isep.dei.graph_classes;


public class Edge implements Comparable<Edge> {

    private Vertice pointX;
    private Vertice pointY;
    private Double cost;

    public Edge(Vertice pointX, Vertice pointY, Double cost) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s] -> %s%n", pointX, pointY, cost);
    }

    public Vertice getPointX() {
        return pointX;
    }

    public void setPointX(Vertice pointX) {
        this.pointX = pointX;
    }

    public Vertice getPointY() {
        return pointY;
    }

    public void setPointY(Vertice pointY) {
        this.pointY = pointY;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return pointX.equals(edge.pointX) && pointY.equals(edge.pointY);
    }

    @Override
    public int compareTo(Edge o) {
        if (this.cost < o.getCost()) {
            return -1;
        } else if (this.cost > o.getCost()) {
            return 1;
        }
        return 0;
    }


}