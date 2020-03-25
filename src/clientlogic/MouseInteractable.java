package clientlogic;

import clientview.MapPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class MouseInteractable {

    private Rectangle hitBox;

    public boolean mouseHitsThis(MouseEvent e) {
        if (hitBox != null) {
            if (hitBox.contains(e.getPoint())) {
                return true;
            }
        }
        return false;
    }


    public void isHoveredOver(MouseEvent e, MapPanel mapPanel) {
        if (mouseHitsThis(e)) {
            doOnHover(e, mapPanel);

        }
    }


    public boolean actOnClick(MouseEvent e) {
        if (mouseHitsThis(e)) {
           // System.out.println(name + " Got click");
            doOnClick(e);
            return true;
        }
        return false;
    }

    protected abstract void doOnClick(MouseEvent e);
    protected abstract void doOnHover(MouseEvent e, MapPanel mapPanel);


    protected void setHitBox(int x, int y, int finalW, int finalH) {
        this.hitBox = new Rectangle(x, y, finalW, finalH);
    }
}