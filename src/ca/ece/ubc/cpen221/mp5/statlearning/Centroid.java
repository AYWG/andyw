package ca.ece.ubc.cpen221.mp5.statlearning;

/**
 * A Centroid represents the center point of a cluster
 * of restaurants, defined by x and y coordinates.
 */

public class Centroid {
    private double x, y;
    private final int id;

    public Centroid(double x, double y, int id) {
        setCoords(x, y);
        this.id = id;
    }

    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getID() {
        return this.id;
    }
    
    
}
