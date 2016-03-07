package model.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.MyRandom;
import model.GameData;
import model.Player;
import model.actions.Target;
import model.map.Room;

public class ElectricalFire extends OngoingEvent {

	

	@Override
	public double getProbability() {
		// TODO: Change this into something more reasonable
		// like 0.2
		return 1.0;
	}

	


	@Override
	public String howYouAppear(Player whosAsking) {
		return "Fire!";
	}
	
	@Override
	public ElectricalFire clone() {
		return new ElectricalFire();
	}

	protected void maintain(GameData gameData) {
		for (Target t : getRoom().getTargets()) {
			t.beExposedTo(null, new Damager() {
				
				@Override
				public boolean isDamageSuccessful(boolean reduced) {
					return true;
				}
				
				@Override
				public String getText() {
					return "The fire burned you!";
				}
				
				@Override
				public double getDamage() {
					return 0.5;
				}
			});
		}
	}

}
