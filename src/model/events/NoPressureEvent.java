package model.events;

import java.util.Iterator;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.SensoryLevel;
import model.map.Room;
import model.objects.PressurePanel;

public class NoPressureEvent extends Event {

	private PressurePanel panelRef;
	private Room roomRef;
	private Actor performingClient;

	public NoPressureEvent(PressurePanel panelRef, Room roomRef, Actor performingClient) {
		this.panelRef = panelRef;
		this.roomRef = roomRef;
		this.performingClient = performingClient;
		removeFires(roomRef);
	}


	private void removeFires(Room roomRef2) {
		// TODO Auto-generated method stub
		for (Iterator<Event> it = roomRef2.getEvents().iterator(); it.hasNext(); ) {
			Event e = it.next();
			if (e instanceof ElectricalFire) {
				e.setShouldBeRemoved(true);
				it.remove();
			}
		}
	}


	@Override
	public double getProbability() {
		return 0;
	}

	@Override
	public void apply(GameData gameData) {
		if (!shouldBeRemoved(gameData)) {
			for (Target t : roomRef.getTargets()) {
				t.beExposedTo(performingClient, new NoPressureDamage(t));
			}
		}

	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "No Pressure!";
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.NO_SENSE;
	}

	@Override
	public boolean shouldBeRemoved(GameData gameData) {
		return panelRef.getPressure();
	}




}