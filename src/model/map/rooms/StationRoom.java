package model.map.rooms;

import model.GameData;
import model.map.doors.Door;
import model.map.floors.FloorSet;
import model.objects.ai.SecurityCamera;

public abstract class StationRoom extends Room {

    public StationRoom(int ID, String name, int x, int y, int width, int height, int[] neighbors, Door[] doors) {
        super(ID, name, x, y, width, height, neighbors, doors);
    }

    @Override
    public void doSetup(GameData gameData) {
        super.doSetup(gameData);
        if (!isHidden()) {
            this.addObject(new SecurityCamera(this));
        }
    }
}
