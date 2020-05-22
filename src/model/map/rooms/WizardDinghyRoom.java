package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.map.floors.ChapelFloorSet;
import model.map.floors.FloorSet;
import model.objects.Altar;
import model.objects.BookCase;
import model.objects.CrystalBall;
import model.objects.WizardStaffCloset;
import model.objects.decorations.NiceBed;
import model.objects.decorations.ShuttleThruster;

public class WizardDinghyRoom extends ShuttleRoom {
    public WizardDinghyRoom(GameData gameData) {
        super(gameData.getMap().getMaxID()+1, "Wizard Dinghy",
                -10, -10, 2, 2, new int[]{},
                new Door[]{}, 0);
        addDecoration(new ShuttleThruster(this, 0.1, 0.5));
        addDecoration(new ShuttleThruster(this, 0.1, 1.5));
        rotate();
        moveTo(getX(), getY(), getZ());

        addObject(new CrystalBall(this));
        addObject(new WizardStaffCloset(this));
        addObject(new BookCase(this));
        addObject(new NiceBed(this));
    }

    @Override
    public FloorSet getFloorSet() {
        return new ChapelFloorSet();
    }
}
