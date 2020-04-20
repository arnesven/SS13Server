package model.map.rooms;

import model.Actor;
import model.GameData;
import model.map.doors.AirLockDoor;
import model.map.doors.Door;
import model.map.floors.AirLockFloorSet;
import model.map.floors.FloorSet;
import model.objects.general.OxyMaskDispenser;
import model.objects.general.AirlockPanel;

public class AirLockRoom extends StationRoom {

	public AirLockRoom(int ID, int number, int x, int y,
			int width, int height, int[] neighbors, Door[] doors) {
		super(ID, "Air Lock #"+number         ,
                x, y, width, height, neighbors, doors);
		
		this.addObject(new AirlockPanel(this));
        this.addObject(new OxyMaskDispenser(this));
	}

	@Override
    public FloorSet getFloorSet() {
		return new AirLockFloorSet();
	}

	@Override
	protected String getAppearanceScheme() {
		return "WallsNoWindows-Space";
	}

	public void cycle(GameData gameData, Actor performingClient) {
		for (Door d : getDoors()) {
			if (d instanceof AirLockDoor) {
				((AirLockDoor) d).cycle(gameData, performingClient);
			}
		}
	}
}
