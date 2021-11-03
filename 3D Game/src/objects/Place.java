package objects;

import window.Screen;

import java.awt.*;

public class Place {
    public Place() {
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 40; x++) {
                int scale = 1000;
                Screen.camera.addPolygon(new Polygon(new Color((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255)),
                        new Vector(x*scale,0,y*scale),
                        new Vector(scale + x*scale,0,y*scale),
                        new Vector(scale + x*scale,0,scale + y*scale)
                ));
                Screen.camera.addPolygon(new Polygon(new Color((int) (Math.random()*255),(int) (Math.random()*255),(int) (Math.random()*255)),
                        new Vector(x*scale,0,y*scale),
                        new Vector(x*scale,0,scale + y*scale),
                        new Vector(scale + x*scale,0,scale + y*scale)
                ));
            }
        }
    }
}
