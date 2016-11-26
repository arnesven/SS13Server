package model.events.ambient;

import java.util.Iterator;

import model.Actor;
import model.GameData;
import model.actions.general.SensoryLevel;
import model.characters.general.GameCharacter;
import model.characters.decorators.DarknessShroudDecorator;
import model.characters.decorators.InstanceChecker;
import model.events.Event;
import model.items.NoSuchThingException;
import model.map.Room;
import model.objects.consoles.GeneratorConsole;
import model.objects.consoles.PowerSource;

public abstract class DarknessEvent extends Event {

    private final SimulatePower simPower;
    private PowerSource gc;

    protected abstract PowerSource findPowerSource(GameData gameData) throws NoSuchThingException;

	public DarknessEvent(GameData gameData, SimulatePower simulatePower) {
        this.simPower = simulatePower;
        try {
            this.gc = findPowerSource(gameData);
        } catch (NoSuchThingException e) {
            throw new IllegalStateException("Should not get any darkness if there is no generator!");
        }
    }


    @Override
	public void apply(GameData gameData) {
		for (Room r : simPower.getAffactedRooms(gameData)) {
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
