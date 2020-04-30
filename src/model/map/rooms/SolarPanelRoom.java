package model.map.rooms;

import graphics.sprites.Sprite;
import model.Actor;
import model.Player;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import model.objects.decorations.SolarPanel;
import model.objects.general.GameObject;
import model.objects.power.LifeSupport;
import model.objects.power.Lighting;

import java.util.ArrayList;
import java.util.List;

public class SolarPanelRoom extends DecorativeRoom {
    public SolarPanelRoom(int id, int x, int y, int z, int w, int h, int panels) {
        super(id, "Solar Panels", x, y, w, h, new int[]{}, new Door[]{});
        setZ(z);
        for (int i = 0; i < panels; ++i) {
            addObject(new SolarPanel(this));
        }
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("solarpanelfloor", 1, 2, "lattice.png");
    }

    @Override
    protected String getAppearanceScheme() {
        return "NoWallsNoDoors-Space";
    }

    @Override
    public List<Sprite> getAlwaysSprites(Actor whosAsking) {
        List<Sprite> sprs = new ArrayList<>();
        for (GameObject obj : getObjects()) {
            sprs.add(obj.getSprite(whosAsking));
        }
        return sprs;
    }

    @Override
    public Lighting getLighting() {
        return null;
    }

    @Override
    public LifeSupport getLifeSupport() {
        return null;
    }
}
