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

public class ShuttleThruster extends GameObject {
    private final ShuttleRoom shuttle;
    private Map<String, Point2D> offsetsForDirection = new HashMap<>();

    public ShuttleThruster(ShuttleRoom position) {
        super("Thruster", position);
        this.shuttle = position;

        offsetsForDirection.put("right", new Point2D.Double(-0.1, 0.5));
        offsetsForDirection.put("left", new Point2D.Double(position.getWidth() + 0.1, 0.5));
        offsetsForDirection.put("up", new Point2D.Double(0.5, position.getWidth() + 0.1));
        offsetsForDirection.put("down", new Point2D.Double(0.5, -0.1));

    }



    @Override
    public Sprite getSprite(Player whosAsking) {
        Map<String, Sprite> sprs = new HashMap<>();
        sprs.put("right", new Sprite("thrusterright", "shuttle.png", 13, 13, this));
        sprs.put("left", new Sprite("thrusterleft", "shuttle.png", 12, 13, this));
        sprs.put("up", new Sprite("thrusterup", "shuttle.png", 10, 13, this));
        sprs.put("down", new Sprite("thrusterdown", "shuttle.png", 11, 13, this));


        return sprs.get(shuttle.getDirection());
    }

    public void moveTo(int x, int y, int z) {
        setAbsolutePosition(x + offsetsForDirection.get(shuttle.getDirection()).getX(),
                y + offsetsForDirection.get(shuttle.getDirection()).getY(), z);

    }
}
