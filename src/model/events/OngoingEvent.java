package model.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.MyRandom;
import model.GameData;
import model.map.Room;

public abstract class OngoingEvent extends Event {
	
	private List<OngoingEvent> eventsToMaintain = new ArrayList<>();

	private boolean isFixed = false;
	private Room room;
	
	protected abstract void maintain(GameData gameData);
	protected abstract OngoingEvent clone();
	protected abstract boolean hasThisEvent(Room randomRoom);
	
	public Room getRoom() {
		return room;
	}

	protected void setRoom(Room randomRoom) {
		this.room = randomRoom;
	}

	public void fix() {
		this.isFixed = true;
		room.removeEvent(this);
	}


	@Override
	public void apply(GameData gameData) {
		handleAllMaintainables(gameData);
		if (MyRandom.nextDouble() < getProbability()) {
			Room randomRoom;
			do {
				randomRoom = gameData.getRooms().get(MyRandom.nextInt(gameData.getRooms().size()));
			} while (hasThisEvent(randomRoom));
			startNewEvent(randomRoom);
		}
	}
	
	protected void startNewEvent(Room randomRoom) {
		if (!hasThisEvent(randomRoom)) {
			OngoingEvent e = this.clone();
			e.setRoom(randomRoom);
			randomRoom.addEvent(e);
			System.out.println("New " + howYouAppear(null) + " started in " + randomRoom.getName());
			eventsToMaintain.add(e);
		}
	}
	
	
	private void handleAllMaintainables(GameData gameData) {
		List<OngoingEvent> thisTurnsEvents = new ArrayList<>();
		thisTurnsEvents.addAll(eventsToMaintain);
		Iterator<OngoingEvent> it = thisTurnsEvents.iterator();
		while (it.hasNext()) {
			OngoingEvent ev = it.next();
			if (ev.isFixed) {
				//it.remove();
			} else {
				ev.maintain(gameData);
			}
		}
	}
	public int noOfFixed() {
		int res = 0;
		for (OngoingEvent oe : eventsToMaintain) {
			if (oe.isFixed) {
				res++;
			}
		}
		return res;
	}
	public int noOfOngoing() {
		return eventsToMaintain.size();
	}
	

	
}
