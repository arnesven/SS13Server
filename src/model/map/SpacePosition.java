package model.map;

import graphics.sprites.SpriteObject;
import model.Player;
import model.map.rooms.Room;
import model.objects.general.GameObject;

import java.io.Serializable;

public class SpacePosition implements Serializable {
    private double x;
    private double y;
    private double z;

    public SpacePosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SpacePosition(Room position) {
        x = position.getX() + (double)position.getWidth() / 2.0;
        y = position.getY() + (double)position.getHeight() / 2.0;
        z = position.getZ();
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }



}
