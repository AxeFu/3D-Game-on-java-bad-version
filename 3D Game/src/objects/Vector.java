package objects;

public class Vector {
    public double x,y,z;
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector plus(double x, double y, double z) {
        return new Vector(this.x + x, this.y + y, this.z + z);
    }

    public Vector plus(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y, this.z + other.z);
    }
}
