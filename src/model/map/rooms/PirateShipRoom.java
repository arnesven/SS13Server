package model.map.rooms;

import model.GameData;
import model.items.foods.SpaceRum;
import model.items.suits.OxygenMask;
import model.items.suits.PirateOutfit;
import model.items.weapons.Flamer;
import model.items.weapons.LaserSword;
import model.items.weapons.Revolver;
import util.MyRandom;

/**
 * Created by erini02 on 06/09/17.
 */
public class PirateShipRoom extends Room {
    private final int airlock;

    public PirateShipRoom(GameData gameData, int randAirLock) {
        super(gameData.getMap().getMaxID()+1, "Pirate Ship", "",
                getXForAirLock(randAirLock, gameData),
                getYForAirLock(randAirLock, gameData),
                2, 1, new int[]{},
                new double[]{}, RoomType.derelict);
        this.airlock = randAirLock;
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



    private static int getXForAirLock(int randAirLock, GameData gameData) {
        if (randAirLock == 1) {
            return 0;
        } else if (randAirLock == 2) {
            return 12;
        } else {
            return 3;
        }
    }

    private static int getYForAirLock(int randAirLock, GameData gameData) {
        if (randAirLock == 1) {
            return 11;
        } else if (randAirLock == 2) {
            return 1;
        } else {
            return 0;
        }
    }


}
