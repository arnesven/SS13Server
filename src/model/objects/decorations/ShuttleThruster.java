package model.objects.decorations;

import graphics.sprites.Sprite;
import model.Player;
import model.map.rooms.Room;
import model.map.rooms.ShuttleRoom;
import model.objects.general.GameObject;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class ShuttleThruster extends ShuttleDecoration {
    private Map<String, Point2D> offsetsForDirection = new HashMap<>();

    public ShuttleThruster(ShuttleRoom position, double shift) {
        super("Thruster", position);

        offsetsForDirection.put("right", new Point2D.Double(-0.1, shift));
        offsetsForDirection.put("left", new Point2D.Double(position.getWidth() + 0.1, shift));
        offsetsForDirection.put("up", new Point2D.Double(shift, position.getWidth() + 0.1));
        offsetsForDirection.put("down", new Point2D.Double(shift, -0.1));
    }

    public ShuttleThruster(ShuttleRoom position) {
        this(position, position.getHeight() / 2.0);
    }


        @Override
    public boolean hasAbsolutePosition() {
        return true;
    }


    public void moveTo(int x, int y, int z) {
        setAbsolutePosition(x + offsetsForDirection.get(getShuttle().getDirection()).getX(),
                y + offsetsForDirection.get(getShuttle().getDirection()).getY(), z);

    }

    @Override
    protected Sprite getDownSprite() {
        return new Sprite("thrusterdown", "shuttle.png", 11, 13, this);
    }

    @Override
    protected Sprite getUpSprite() {
        return new Sprite("thrusterup", "shuttle.png", 10, 13, this);
    }

    @Override
    protected Sprite getLeftSprite() {
        return new Sprite("thrusterleft", "shuttle.png", 12, 13, this);
    }

    @Override
    protected Sprite getRightSprite() {
        return new Sprite("thrusterright", "shuttle.png", 13, 13, this);
    }

    @Override
    public boolean alwaysShowSprite() {
        return true;
    }
}
