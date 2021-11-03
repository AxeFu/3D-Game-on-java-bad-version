package window;

import control.KeyBoard;
import control.Mouse;
import objects.Camera;
import objects.Place;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel implements Runnable {

    private Mouse mouse;
    private Thread thread;
    private KeyBoard keyBoard;
    public static Camera camera;

    private final boolean syn = true;
    private static boolean playing = true;

    private double maxFPS;
    public static int WIDTH, HEIGHT;

    public Screen(JFrame frame) {
        WIDTH = frame.getWidth();
        HEIGHT = frame.getHeight();
        init();
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
        addKeyListener(keyBoard);
        setFocusable(true);
        thread.start();
    }

    private void init() {
        maxFPS = 500;
        camera = new Camera(0,1001,0);
        mouse = new Mouse();
        thread = new Thread(this);
        keyBoard = new KeyBoard();

        new Place();
    }

    @Override
    public void run() {
        double time = System.nanoTime();
        double now, dif = 0;
        while (playing) {
            now = System.nanoTime();
            dif += (now - time) * 60d/1e9d;
            time = now;
            if (dif >= 1) {
                dif--;
                tick();
                if (syn) repaint();
            }
        }
        System.exit(0);
    }

    private double lastRefresh = System.currentTimeMillis();
    private void refresh() {
        long time = (long) (System.currentTimeMillis() - lastRefresh);
        if (time < 1000d/maxFPS) try {Thread.sleep((long) (1000d/maxFPS - time));} catch (Exception ignore) {}
        lastRefresh = System.currentTimeMillis();
        repaint();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0, WIDTH, HEIGHT);
        camera.draw(g);

        if (!syn) refresh();
    }

    private void tick() {
        camera.tick();
    }

    public static void setPlaying(boolean param) {
        playing = param;
    }

}
