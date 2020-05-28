package model.map.rooms;

import model.GameData;
import model.events.ambient.DarkEvent;
import model.items.NoSuchThingException;
import model.items.general.TornClothes;
import model.map.GameMap;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import model.npcs.MouseNPC;
import model.npcs.ParasiteNPC;
import model.objects.VentObject;
import model.objects.general.AirlockPanel;
import model.objects.power.Lighting;
import util.Logger;
import util.MyRandom;

public class AirDuctRoom extends StationRoom {

    private static final double VENT_CHANCE = 0.4;
    private double MICE_CHANCE = 0.33;
    private double PARASITE_CHANCE = 0.15;
    private double TRASH_CHANCE = 0.4;

    public AirDuctRoom(int ID, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, "Air Duct #" + ID, x, y, width, height, neighbors, doors);
        setZ(-1);
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    protected String getPaintingStyle() {
        return "NoWallsNoDoors";
    }

    @Override
    protected String getBackgroundStyle() {
        return "Black";
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("ventshaftfloor", 13, 9);
    }

    @Override
    public void doSetup(GameData gameData) {
        super.doSetup(gameData);
        setupVents(gameData);
        addSomeVermin(gameData);
        addSomeTrash(gameData);
        addEvent(new DarkEvent());
    }

    @Override
    protected boolean getsTrashBin() {
        return false;
    }

    private void addSomeTrash(GameData gameData) {
        while (MyRandom.nextDouble() < TRASH_CHANCE) {
            this.addItem(MyRandom.getRandomTrash(gameData));
        }
    }

    private void addSomeVermin(GameData gameData) {
        while (MyRandom.nextDouble() < MICE_CHANCE) {
            gameData.addNPC(new MouseNPC(this));
        }

        while (MyRandom.nextDouble() < PARASITE_CHANCE) {
            gameData.addNPC(new ParasiteNPC(this));
        }
    }


    private void setupVents(GameData gameData) {
        Logger.log("Setting up vents for " + this.getName());
        GameMap map = gameData.getMap();
        String ss13 = GameMap.STATION_LEVEL_NAME;
        try {
            for (Room r : map.getArea(ss13, map.getAreaForRoom(ss13, this))) {
                if (r != this && !(r instanceof AirLockRoom) && !(r instanceof DecorativeRoom)) {
                    if (MyRandom.nextDouble() < VENT_CHANCE) {
                        Logger.log("... Adding a vent to " + r.getName());
                        VentObject vent = new VentObject(this, r, null);
                        this.addObject(vent);
                        VentObject vent2 = new VentObject(r, this, vent);
                        r.addObject(vent2);
                        vent.setOtherSide(vent2);
                    }
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Lighting getLighting() {
        return null;
    }
}
