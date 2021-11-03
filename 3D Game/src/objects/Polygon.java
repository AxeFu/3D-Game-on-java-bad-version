package objects;

import java.awt.*;

public class Polygon {
    private Color c;
    private Vector[] p;
    public Polygon(Color c, Vector... p) {
        this.p = p;
        this.c = c;
    }

    public Color getColor() {
        return c;
    }

    public Vector[] getPoints() {
        return p;
    }

    public void setColor(Color color) {
        c = color;
    }

    public void setPoints(Vector[] points) {
        p = points;
    }
}
