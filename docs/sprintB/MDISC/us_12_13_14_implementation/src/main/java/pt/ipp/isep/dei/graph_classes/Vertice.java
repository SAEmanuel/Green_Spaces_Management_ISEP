package main.java.pt.ipp.isep.dei.graph_classes;

public class Vertice {
    private String label;

    public Vertice(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("%s", this.label);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Vertice vertex = (Vertice) o;
        return label.equalsIgnoreCase(vertex.label);
    }
}
