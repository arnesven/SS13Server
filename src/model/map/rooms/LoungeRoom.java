

package model.map.rooms;

import model.map.doors.Door;
import model.map.doors.DowngoingStairsDoor;
import model.map.floors.ChapelFloorSet;
import model.map.floors.FloorSet;
import model.objects.clawcrane.ClawCraneGame;
import model.objects.LongSofa;
import model.objects.decorations.ComfyChair;
import model.objects.general.JunkVendingMachine;
import sounds.Sound;

public class LoungeRoom extends SupportRoom {

    public LoungeRoom(int ID, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, "Lounge", "", x, y, width, height, neighbors, doors);
        setZ(1);
        addObject(new DowngoingStairsDoor(this), RelativePositions.CENTER);
        addObject(new JunkVendingMachine(this), RelativePositions.LOWER_RIGHT_CORNER);
        this.addObject(new ComfyChair(this), RelativePositions.MID_LEFT);
        //this.addObject(new ComfyChair(this));
        this.addObject(new LongSofa(this), RelativePositions.MID_TOP);
        this.addObject(new ClawCraneGame(this), RelativePositions.UPPER_LEFT_CORNER);
    }

    @Override
    public FloorSet getFloorSet() {
        return new ChapelFloorSet();
    }

    @Override
    public Sound getSpecificAmbientSound() {
        return new Sound("ambidet1");
    }
}
