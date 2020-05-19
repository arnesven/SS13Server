package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.map.floors.ChapelFloorSet;
import model.map.floors.FloorSet;

public class WizardDinghyRoom extends ShuttleRoom {
    public WizardDinghyRoom(GameData gameData) {
        super(gameData.getMap().getMaxID()+1, "Wizard Dinghy",
                -10, -10, 2, 2, new int[]{},
                new Door[]{}, 0);
        //rotate();
    }

    @Override
    public FloorSet getFloorSet() {
        return new ChapelFloorSet();
    }
}
