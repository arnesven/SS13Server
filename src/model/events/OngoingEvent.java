package model.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.MyRandom;
import model.GameData;
import model.map.Room;

public abstract class OngoingEvent implements Event {
	
	private List<OngoingEvent> fires = new ArrayList<>();

	private boolean isFixed = false;
	private Room room;
	
	protected abstract void maintain(GameData gameData);
	protected abstract OngoingEvent clone();
	
	public Room getRoom() {
		return room;
	}

	protected void setRoom(Room randomRoom) {
		this.room = randomRoom;
	}

	public void putOut() {
		this.isFixed = true;
		room.removeEvent(this);
	}
	

	@Override
	public void apply(GameData gameData) {
		handleAllMaintainables(gameData);
		if (MyRandom.nextDouble() < getProbability()) {
			fires.add(startNewEvent(gameData));
		}
	}
	
	protected OngoingEvent startNewEvent(GameData gameData) {
		Room randomRoom;
		do {
			randomRoom = gameData.getRooms().get(MyRandom.nextInt(gameData.getRooms().size()));
		} while (randomRoom.hasFire());

		OngoingEvent e = this.clone();
		e.setRoom(randomRoom);
		randomRoom.addEvent(e);
		System.out.println("New " + howYouAppear(null) + " started in " + randomRoom.getName());
		return e;
	}
	
	private void handleAllMaintainables(GameData gameData) {
		Iterator<OngoingEvent> it = fires.iterator();
		while (it.hasNext()) {
			OngoingEvent ev = it.next();
			if (ev.isFixed) {
				it.remove();
			} else {
				ev.maintain(gameData);
			}
		}
	}
	

	
}
