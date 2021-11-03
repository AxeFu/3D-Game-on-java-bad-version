package control;

import objects.Camera;
import window.Screen;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBoard extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) Screen.setPlaying(false);

        if (e.getKeyCode() == KeyEvent.VK_W) Camera.frontal = true;
        if (e.getKeyCode() == KeyEvent.VK_D) Camera.right = true;
        if (e.getKeyCode() == KeyEvent.VK_A) Camera.left = true;
        if (e.getKeyCode() == KeyEvent.VK_S) Camera.back = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) Camera.up = true;
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) Camera.down = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) Camera.frontal = false;
        if (e.getKeyCode() == KeyEvent.VK_D) Camera.right = false;
        if (e.getKeyCode() == KeyEvent.VK_A) Camera.left = false;
        if (e.getKeyCode() == KeyEvent.VK_S) Camera.back = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) Camera.up = false;
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) Camera.down = false;
    }
}
