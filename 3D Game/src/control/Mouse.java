package control;

import window.Screen;

import java.awt.*;
import java.awt.event.*;

public class Mouse extends MouseAdapter {

    private Point p;
    private Robot r;

    public Mouse() {
        p = new Point(0,0);
        try {r = new Robot();} catch (AWTException ignore) { }
        r.mouseMove(Screen.WIDTH/2, Screen.HEIGHT/2);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        p = e.getPoint();
        Screen.camera.rotate(Screen.WIDTH/2d - p.x, p.y - Screen.HEIGHT/2d);
        r.mouseMove(Screen.WIDTH/2, Screen.HEIGHT/2);
    }

    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }
}
