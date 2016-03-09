package model.events;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.SensoryLevel;
import model.actions.Target;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;
import model.map.Room;
import model.objects.GameObject;

public class HullBreach extends OngoingEvent {

	@Override
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
