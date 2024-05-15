package graph_classes;

public class VerticeC {
    private String label;

    public VerticeC(String label) {
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

        VerticeC vertex = (VerticeC) o;
        return label.equalsIgnoreCase(vertex.label);
    }
}
