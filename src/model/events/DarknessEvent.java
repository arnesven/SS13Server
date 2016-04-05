package model.events;

import java.util.Iterator;
import java.util.NoSuchElementException;

import model.Actor;
import model.GameData;
import model.actions.SensoryLevel;
import model.characters.decorators.CharacterDecorator;
import model.characters.GameCharacter;
import model.characters.decorators.DarknessShroudDecorator;
import model.characters.decorators.InstanceChecker;
import model.map.Room;
import model.objects.consoles.GeneratorConsole;
import model.characters.decorators.InstanceRemover;

public class DarknessEvent extends Event {

	private GeneratorConsole gc;

	public DarknessEvent(GameData gameData) {
		this.gc = GeneratorConsole.find(gameData);
	}

	@Override
	public void apply(GameData gameData) {
		for (Room r : gameData.getRooms()) {
			for (Actor a : r.getActors()) {
				if (gc.getNoLightRooms().contains(r)) {
					addDarkness(a);
				} else {
					removeDarkness(a);
				}
			}
			if (gc.getNoLightRooms().contains(r)) {
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
		Iterator<Event> it = r.getEvents().iterator();
		while (it.hasNext()) {
			if (it.next() instanceof DarkEvent) {
				it.remove();
			}
		}
	}

	private void addDarkness(Actor a) {
		InstanceChecker check = new InstanceChecker(){
			@Override
			public boolean checkInstanceOf(GameCharacter ch) {
				return ch instanceof DarknessShroudDecorator;
			}	
		};
		if (!a.getCharacter().checkInstance(check)) { 
			a.setCharacter(new DarknessShroudDecorator(a.getCharacter()));
		}
	}
	
	private void removeDarkness(Actor a) {
		InstanceRemover instRem = new InstanceRemover() {
			
			@Override
			public GameCharacter removeInstance(GameCharacter ch) {
				if (ch instanceof DarknessShroudDecorator) {
					return ((DarknessShroudDecorator) ch).getInner();
				} else if (ch instanceof CharacterDecorator) {
					return removeInstance(((CharacterDecorator) ch).getInner());
				}
				return ch; // if not found, do nothing
			}
		};
		a.removeInstance(instRem);
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
