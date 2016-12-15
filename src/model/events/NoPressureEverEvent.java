package model.events;

import model.GameData;
import model.map.rooms.Room;

public class NoPressureEverEvent extends NoPressureEvent {

	public NoPressureEverEvent(Room roomRef) {
		super(null, roomRef, null, false);
	}
	
	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		return false;
	}

}
