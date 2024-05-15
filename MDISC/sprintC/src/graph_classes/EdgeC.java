package graph_classes;


public class EdgeC implements Comparable<EdgeC> {

    private VerticeC pointX;
    private VerticeC pointY;
    private Double cost;

    public EdgeC(VerticeC pointX, VerticeC pointY, Double cost) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s] -> %s%n", pointX, pointY, cost);
    }

    public VerticeC getPointX() {
        return pointX;
    }

    public void setPointX(VerticeC pointX) {
        this.pointX = pointX;
    }

    public VerticeC getPointY() {
        return pointY;
    }

    public void setPointY(VerticeC pointY) {
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
        EdgeC edge = (EdgeC) o;
        return pointX.equals(edge.pointX) && pointY.equals(edge.pointY);
    }

    @Override
    public int compareTo(EdgeC o) {
        if (this.cost < o.getCost()) {
            return -1;
        } else if (this.cost > o.getCost()) {
            return 1;
        }
        return 0;
    }


}