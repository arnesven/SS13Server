package model.events;

import model.GameData;
import model.Player;
import model.actions.Target;
import model.map.Room;

public class HullBreach extends OngoingEvent {

	@Override
	public double getProbability() {
		return 0.1;
	}

	@Override
	public String howYouAppear(Player whosAsking) {
		return "Low Pressure!";
	}

	@Override
	protected void maintain(GameData gameData) {
		for (Target t : getRoom().getTargets()) {
			t.beExposedTo(null, new Damager() {
				
				@Override
				public boolean isDamageSuccessful(boolean reduced) {
					return true;
				}
				
				@Override
				public String getText() {
					return "You're gasping for air!";
				}
				
				@Override
				public double getDamage() {
					return 0.5;
				}
			});
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
