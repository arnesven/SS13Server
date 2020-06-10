package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.objects.ai.SecurityCamera;
import model.objects.recycling.TrashBin;
import sounds.Sound;
import util.MyRandom;

public abstract class StationRoom extends Room {

    private final int ambiIndex;

    public StationRoom(int ID, String name, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
        ambiIndex = MyRandom.nextInt(13)+1;
    }

    @Override
    public void doSetup(GameData gameData) {
        super.doSetup(gameData);
        if (!isHidden()) {
            this.addObject(new SecurityCamera(this));
        }
        if (getsTrashBin() && MyRandom.nextDouble() < 0.9) {
            this.addObject(new TrashBin(this));
        }
    }

    protected boolean getsTrashBin() {
        return true;
    }

    @Override
    public Sound getSpecificAmbientSound() {
        return new Sound("ambigen" + ambiIndex);
    }
}
