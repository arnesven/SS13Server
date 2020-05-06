package model.events.ambient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.events.Event;
import model.events.NoPressureEvent;
import util.Logger;
import util.MyRandom;
import model.GameData;
import model.map.rooms.Room;

public abstract class OngoingEvent extends AmbientEvent {
	
	private List<OngoingEvent> eventsToMaintain = new ArrayList<>();

	private boolean isFixed = false;
	private Room room;

	private boolean shouldBeRemoved = false;
	
	protected abstract void maintain(GameData gameData);
	protected abstract OngoingEvent clone();
	protected abstract boolean hasThisEvent(Room randomRoom);


	public Room getRoom() {
		return room;
	}

	public void setRoom(Room randomRoom) {
		this.room = randomRoom;
	}

	public void fix() {
		this.isFixed = true;
		room.removeEvent(this);
	}

	
	@Override
	public void apply(GameData gameData) {
		handleAllMaintainables(gameData);
		if (MyRandom.nextDouble() < getProbability() && !allRoomsBurning(gameData)) {
			Room randomRoom;
			do {
				Logger.log("Finding a room for a ongoing event..");
				randomRoom = gameData.getNonHiddenStationRooms().get(MyRandom.nextInt(gameData.getNonHiddenStationRooms().size()));
			} while (hasThisEvent(randomRoom));
			startNewEvent(randomRoom);
		}
	}
	
	
	public void startNewEvent(Room randomRoom) {
		if (!hasThisEvent(randomRoom) && isApplicable(randomRoom)) {
			OngoingEvent e = this.clone();
			e.setRoom(randomRoom);
			randomRoom.addEvent(e);
			Logger.log(Logger.INTERESTING,
                    "New " + howYouAppear(null) + " started in " + randomRoom.getName());
			eventsToMaintain.add(e);
		}
	}
	
	private boolean isApplicable(Room randomRoom) {
		for (Event e : randomRoom.getEvents()) {
			if (e instanceof NoPressureEvent) {
				return false;
			}
		}
		return true;
	}
	
	private void handleAllMaintainables(GameData gameData) {
		List<OngoingEvent> thisTurnsEvents = new ArrayList<>();
		thisTurnsEvents.addAll(eventsToMaintain);
		Iterator<OngoingEvent> it = thisTurnsEvents.iterator();
		while (it.hasNext()) {
			OngoingEvent ev = it.next();
			if (ev.shouldBeRemoved(gameData)) {
				it.remove();
				ev.getRoom().removeEvent(ev);
			} else if (ev.isFixed) {
				ev.handleAllMaintainables(gameData);
			} else {
				ev.handleAllMaintainables(gameData);
				ev.maintain(gameData);
			}
		}
	}
	public int noOfFixed() {
		int res = 0;
		if (this.isFixed) {
			res = 1;
		} 
		
		for (OngoingEvent oe : eventsToMaintain) {
			res += oe.noOfFixed();
		}

		return res;
	}
	public int noOfOngoing() {
		if (eventsToMaintain.size() == 0) {
			return 0;
		}
		int sum = 0;
		for (OngoingEvent e : eventsToMaintain) {
			sum += (1 + e.noOfOngoing());
		}
		return sum;
	}
	
	private boolean allRoomsBurning(GameData gameData) {
		for (Room r : gameData.getNonHiddenStationRooms()) {
			if (!hasThisEvent(r)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		return shouldBeRemoved;
	}
	
	@Override
	public void setShouldBeRemoved(boolean b) {
		shouldBeRemoved = b;
	}
	
}
