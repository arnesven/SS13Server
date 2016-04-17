package model.events.ambient;

import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.events.damage.AsphyxiationDamage;
import model.map.Room;

public class HullBreach extends OngoingEvent {

	public double getProbability() {
		return 0.1;
	}

	@Override
	public SensoryLevel getSense() {
		return new SensoryLevel(VisualLevel.INVISIBLE, 
				AudioLevel.SAME_ROOM, OlfactoryLevel.UNSMELLABLE);
	}

	@Override
	public String howYouAppear(Actor performingClient) {
		return "Low Pressure!";
	}

	@Override
	protected void maintain(GameData gameData) {
		for (Target t : getRoom().getTargets()) {
			t.beExposedTo(null, new AsphyxiationDamage(t));
		}
	}

	@Override
	protected OngoingEvent clone() {
		return new HullBreach();
	}

	@Override
	protected boolean hasThisEvent(Room randomRoom) {
		return randomRoom.hasHullBreach();
	}


	
}
