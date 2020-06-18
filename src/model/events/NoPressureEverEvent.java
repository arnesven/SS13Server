package model.events;

import model.Actor;
import model.GameData;
import model.map.rooms.Room;

public class NoPressureEverEvent extends NoPressureEvent {


	private NoPressureEverEvent(Room roomRef, Actor performingClient, boolean adjacent) {
		super(roomRef, performingClient, adjacent);
	}

	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		return false;
	}

}
