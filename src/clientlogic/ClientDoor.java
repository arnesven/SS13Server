package clientlogic;

import clientview.MapPanel;
import clientview.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ClientDoor extends MouseInteractable {

    private double x;
    private double y;
    private String name;


    public ClientDoor(double x, double y, String name) {
       this.x = x;
       this.y = y;
       this.name = name;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    protected void doOnClick(MouseEvent e) {
        System.out.println("clicked a door " + getName());
    }

    @Override
    protected void doOnHover(MouseEvent e, MapPanel mapPanel) {

    }

    public void drawYourself(Graphics g, Room room, int xOffset, int yOffset, int xOffPx, int yOffPx) {
        ImageIcon ic = SpriteManager.getSprite(getName() + "door0");
        int xpos = (int)((getX()-xOffset) * room.getXScale()) + xOffPx;
        int ypos = (int)((getY()-yOffset) * room.getYScale()) + yOffPx;

        g.drawImage(SpriteManager.getSprite(room.getFloorSpriteBaseName()).getImage(), xpos, ypos, null);
        g.drawImage(ic.getImage(), xpos, ypos, null);

        setHitBox(xpos, ypos, MapPanel.getZoom(), MapPanel.getZoom());
        g.setColor(Color.BLACK);
    }
}
