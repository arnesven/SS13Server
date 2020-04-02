package model.map.rooms;

import model.GameData;
import model.items.NoSuchThingException;
import model.map.GameMap;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;
import model.objects.VentObject;
import util.Logger;
import util.MyRandom;

public class AirDuctRoom extends Room {

    private static final double VENT_CHANCE = 0.5;

    public AirDuctRoom(int ID, int x, int y, int width, int height, int[] neighbors, double[] doors) {
        super(ID, "Air Duct #" + ID, x, y, width, height, neighbors, doors);
        setZ(-1);
    }

  //  @Override
  //  public boolean isHidden() {
  //      return true;
  //  }

    @Override
    protected String getAppearanceScheme() {
        return "NoWallsNoDoors-Black";
    }

    @Override
    protected FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("ventshaftfloor", 13, 9);
    }

    @Override
    public void doSetup(GameData gameData) {
        Logger.log("Setting up vents for " + this.getName());
        GameMap map = gameData.getMap();
        String ss13 = GameMap.STATION_LEVEL_NAME;
        try {
            for (Room r : map.getArea(ss13, map.getAreaForRoom(ss13, this))) {
                if (r != this) {
                    if (MyRandom.nextDouble() < VENT_CHANCE) {
                        Logger.log("... Adding a vent to " + r.getName());
                        this.addObject(new VentObject(this, r));
                        r.addObject(new VentObject(r, this));
                    }
                }
            }
        } catch (NoSuchThingException e) {
            e.printStackTrace();
        }
    }
}
