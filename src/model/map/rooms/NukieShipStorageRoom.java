package model.map.rooms;

import graphics.sprites.Sprite;
import model.Actor;
import model.map.doors.Door;
import model.objects.OperativeStorage;
import model.objects.decorations.FixedShuttleThruster;
import model.objects.decorations.ShuttleThruster;
import model.objects.general.GameObject;

import java.util.ArrayList;
import java.util.List;

public class NukieShipStorageRoom extends NukieShipRoom {
    private final ArrayList<GameObject> thrusters;

    public NukieShipStorageRoom(int i, int i1, int i2, int i3, int i4, int[] ints, Door[] doubles) {
        super(i, i1, i2, i3, i4, ints, doubles);
        setName("Storage Room");
        OperativeStorage storage = new OperativeStorage(this);
        this.addObject(storage);

        this.thrusters = new ArrayList<>();
        for (int j = 3; j > 0; --j) {
            FixedShuttleThruster thruster = new FixedShuttleThruster(this);
            thruster.setAbsolutePosition(getX() + getWidth() + 0.1, getY() + getHeight() / 2.0 + 0.5*j - 1.0);
            thrusters.add(thruster);
        }

    }

    @Override
    public List<Sprite> getAlwaysSprites(Actor whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        for (GameObject thr : thrusters) {
            sprs.add(thr.getSprite(whosAsking));
        }
        return sprs;
    }
}
