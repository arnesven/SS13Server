package model.modes;

import model.GameData;
import model.events.NoPressureEvent;
import model.map.Room;

public class NoPressureEverEvent extends NoPressureEvent {

	public NoPressureEverEvent(Room roomRef) {
		super(null, roomRef, null, false);
	}
	
	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		return false;
	}

}
