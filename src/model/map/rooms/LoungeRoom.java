

package model.map.rooms;

import model.map.doors.Door;
import model.map.doors.DowngoingStairsDoor;
import model.map.doors.UpgoingStairsDoor;
import model.map.floors.ChapelFloorSet;
import model.map.floors.FloorSet;
import model.objects.LongSofa;
import model.objects.decorations.ComfyChair;
import model.objects.general.JunkVendingMachine;
import sounds.Sound;

public class LoungeRoom extends SupportRoom {

    public LoungeRoom(int ID, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, "Lounge", "", x, y, width, height, neighbors, doors);
        setZ(1);
        addObject(new DowngoingStairsDoor(this));
        addObject(new JunkVendingMachine(this));
        this.addObject(new ComfyChair(this));
        //this.addObject(new ComfyChair(this));
        this.addObject(new LongSofa(this));
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
