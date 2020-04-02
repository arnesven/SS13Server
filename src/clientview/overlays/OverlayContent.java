package clientview.overlays;

import clientview.components.MapPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class OverlayContent {


    public abstract int drawYourself(Graphics2D g2d, int i, int y, MapPanel comp);

    public void handleMousePressed(MouseEvent e) { }

    public void handleMouseReleased(MouseEvent e) { }

    public boolean handleMouseMoved(MouseEvent e) { return false;}

    public void handleMouseDragged(MouseEvent e) { };
}
