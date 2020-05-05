package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.map.floors.SingleSpriteFloorSet;

public class MerchantShip extends ShuttleRoom {
    public MerchantShip(GameData gameData) {
        super(gameData.getMap().getMaxID()+1, "Merchant Skiff",
                0, 0, 2, 1, new int[]{},
                new Door[]{});
    }

    @Override
    public FloorSet getFloorSet() {
        return new SingleSpriteFloorSet("floormerchantship", 1, 3, "shuttle.png");
    }
}
