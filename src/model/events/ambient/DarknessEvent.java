package model.events.ambient;

import java.util.HashSet;
import java.util.Set;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.decorators.DarknessShroudDecorator;
import model.characters.decorators.InstanceChecker;
import model.events.Event;
import model.map.rooms.Room;

@Deprecated
public class DarknessEvent extends Event {

    private final SimulatePower simPower;

	private DarknessEvent(GameData gameData, SimulatePower simulatePower) {
        this.simPower = simulatePower;
    }


    @Override
	public void apply(GameData gameData) {
		for (Room r : simPower.getAffectedRooms(gameData)) {
			for (Actor a : r.getActors()) {
				if (simPower.getNoLightRooms().contains(r)) {
					addDarkness(a);
				} else {
					removeDarkness(a);
				}
			}
			if (simPower.getNoLightRooms().contains(r)) {
				addDarkness(r);
			} else {
				removeDarkness(r);
			}
		}
	}

	private void addDarkness(Room r) {
		for (Event e : r.getEvents()) {
			if (e instanceof DarkEvent) {
				return;
			}
		}
		r.addEvent(new DarkEvent());		
	}

	private void removeDarkness(Room r) {
		Set<Event> eventsToRemove = new HashSet<>();
		for (Event e : r.getEvents()) {
			if (e instanceof DarkEvent) {
				eventsToRemove.add(e);
			}
		}

		for (Event e : eventsToRemove) {
			r.removeEvent(e);
		}
	}

	private void addDarkness(Actor a) {
		if (!isDarkened(a)) {
			a.setCharacter(new DarknessShroudDecorator(a.getCharacter()));			
		}
	}
	
	private boolean isDarkened(Actor a) {
		InstanceChecker check = new InstanceChecker(){
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof DarknessShroudDecorator;
			}	
		};
		
		return a.getCharacter().checkInstance(check);
	}

	private void removeDarkness(Actor a) {
		if (isDarkened(a)) {
			a.removeInstance(new InstanceChecker() {
				
				@Override
				public boolean checkInstanceOf(GameCharacter ch) {
					return ch instanceof DarknessShroudDecorator;
				}
			});
		}
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

}
