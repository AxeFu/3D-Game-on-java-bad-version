package objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import static window.Screen.*;
import static java.lang.Math.*;

public class Camera {

    //Draw screen
    private final sortInstruction sort = new sortInstruction();
    private final ArrayList<Polygon> polygons = new ArrayList<>();
    private final Font font = new Font("custom", Font.BOLD, 20);

    //Player move
    private double x,y,z;
    private final Vector move;
    private boolean jump = true;
    public static boolean up, left, right, down, frontal, back;

    //Player rotate
    private double[] v;
    private double radYZ = 0, radXZ = 0;

    //Player options
    private final double gravity = 4;
    private final double jumpPower = 100;
    private final double playerHeight = 1000;
    private final double movementSpeed = 10;
    private final double resistance = 0.9d;

    public Camera(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        move = new Vector(0,0, 0);
        rotate(0,0);
    }

    private Vector transformToScreen(Vector p) {
        return new Vector(
                v[0]*p.x + v[1]*p.y + v[2]*p.z,
                v[3]*p.x + v[4]*p.y + v[5]*p.z,
                v[6]*p.x + v[7]*p.y + v[8]*p.z
                );
    }

    public synchronized void draw(Graphics g) {
        polygons.sort(sort);
        for (Polygon polygon : polygons) {
            Vector[] p = new Vector[3];
            for (int i = 0; i < 3; i++) {
                p[i] = transformToScreen(polygon.getPoints()[i].plus(-x,-y,-z));
                p[i].x *= (WIDTH/2d)/abs(p[i].z);
                p[i].y *= (WIDTH/2d)/abs(p[i].z);
                p[i].y = HEIGHT/2d - p[i].y;
                p[i].x += WIDTH/2d;
            }
            if (p[0].z + p[1].z + p[2].z >= 0) {
                g.setColor(polygon.getColor());
                g.fillPolygon(new int[]{(int) p[0].x, (int) p[1].x, (int) p[2].x}, new int[]{(int) p[0].y, (int) p[1].y, (int) p[2].y}, 3);
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(0,0,200,200);

        g.setColor(Color.BLUE);
        g.drawLine(100, 100,(int) (100 + v[6]*100), (int) (100 - v[7]*100));
        g.setColor(Color.GREEN);
        g.drawLine(100, 100,(int) (100 + v[3]*100), (int) (100 - v[4]*100));
        g.setColor(Color.RED);
        g.drawLine(100, 100,(int) (100 + v[0]*100), (int) (100 - v[1]*100));

        g.setColor(Color.DARK_GRAY);
        g.drawRect(0,0,200,200);

        g.setColor(Color.red);
        g.setFont(font);
        g.drawString("x: " + ((int) x)/1e3d, 20, 220);
        g.drawString("y: " + ((int) y)/1e3d, 20, 240);
        g.drawString("z: " + ((int) z)/1e3d, 20, 260);
        g.drawString("XZ: " +(int) radXZ, 20, 280);
        g.drawString("YZ: " +(int) radYZ, 20, 300);
    }

    public void tick() {
        double length;
        if (frontal) {
            length = Math.sqrt(v[6]*v[6] + v[8]*v[8]);
            move.x += v[6]*movementSpeed/length;
            move.z += v[8]*movementSpeed/length;
        }
        if (back) {
            length = Math.sqrt(v[6]*v[6] + v[8]*v[8]);
            move.x -= v[6]*movementSpeed/length;
            move.z -= v[8]*movementSpeed/length;
        }
        if (right) {
            length = Math.sqrt(v[0]*v[0] + v[2]*v[2]);
            move.x += v[0]*movementSpeed/length;
            move.z += v[2]*movementSpeed/length;
        }
        if (left) {
            length = Math.sqrt(v[0]*v[0] + v[2]*v[2]);
            move.x -= v[0]*movementSpeed/length;
            move.z -= v[2]*movementSpeed/length;
        }

        //Physics and collision
        if (up && jump) {
            move.y += jumpPower;
            jump = false;
        }
        if (down) {
            y += 1;
        }

        move.x *= resistance;
        move.z *= resistance;

        if (y + move.y >= playerHeight) {
            move.y -= gravity;
        } else {
            move.y = 0;
            jump = true;
        }

        x += move.x;
        y += move.y;
        z += move.z;

    }

    public void rotate(double radXZ, double radYZ) {
        this.radYZ += radYZ;
        this.radXZ += radXZ;
        if (this.radYZ > 89) this.radYZ = 89;
        if (this.radYZ < -90) this.radYZ = -90;
        if ((this.radXZ > 180) || (this.radXZ < -180)) this.radXZ += 360*-(signum(radXZ));

        radYZ = toRadians(this.radYZ);
        radXZ = toRadians(this.radXZ);
        double cosA = cos(radXZ), cosB = cos(radYZ), sinA = sin(radXZ), sinB = sin(radYZ);
        v = new double[] {
                      cosA,    0,     sinA,
                -sinA*sinB, cosB,sinB*cosA,
                -sinA*cosB,-sinB,cosA*cosB
        };
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public class sortInstruction implements Comparator<Polygon> {
        public synchronized int compare(Polygon polygon1, Polygon polygon2) {
            Vector[] p = new Vector[6];
            for (int i = 0; i < 3; i++) {
                p[i] = transformToScreen(polygon1.getPoints()[i].plus(-x,-y,-z));
                p[i + 3] = transformToScreen(polygon2.getPoints()[i].plus(-x,-y,-z));
            }
            return (int) (
                    pow(p[3].x + p[4].x + p[5].x, 2) +
                    pow(p[3].y + p[4].y + p[5].y, 2) +
                    pow(p[3].z + p[4].z + p[5].z, 2) -
                    pow(p[0].x + p[1].x + p[2].x, 2) -
                    pow(p[0].y + p[1].y + p[2].y, 2) -
                    pow(p[0].z + p[1].z + p[2].z, 2)
            );
        }
    }
}
