package model.map.rooms;

import model.GameData;
import model.items.foods.SpaceRum;
import model.items.suits.OxygenMask;
import model.items.suits.PirateOutfit;
import model.items.weapons.Flamer;
import model.items.weapons.LaserSword;
import model.items.weapons.Revolver;
import model.map.DockingPoint;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.NukieFloorSet;
import util.MyRandom;

/**
 * Created by erini02 on 06/09/17.
 */
public class PirateShipRoom extends ShuttleRoom {

    public PirateShipRoom(GameData gameData) {
        super(gameData.getMap().getMaxID()+1, "Pirate Ship",
                0, 0, 3, 1, new int[]{},
                new Door[]{}, 6);
        this.addItem(new SpaceRum());


        if (MyRandom.nextDouble() < 0.6) {
            this.addItem(new OxygenMask());
        }
        if (MyRandom.nextDouble() < 0.5) {
            this.addItem(new PirateOutfit(56));
        }
        if (MyRandom.nextDouble() < 0.3) {
            this.addItem(new Revolver());
        }
        if (MyRandom.nextDouble() < 0.3) {
            this.addItem(new Flamer());
        }
        if (MyRandom.nextDouble() < 0.1) {
            this.addItem(new LaserSword());
        }

    }

    @Override
    public FloorSet getFloorSet() {
        return new NukieFloorSet();
    }

    @Override
    protected String getWallAppearence() {
        return "badshuttle" + getDirection();
    }
}
