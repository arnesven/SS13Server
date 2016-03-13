package model.events;

import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.SensoryLevel;
import model.map.Room;

public class ElectricalFire extends OngoingEvent {

	private static final double SPREAD_CHANCE = 0.1;

	@Override
	public double getProbability() {
		// TODO: Change this into something more reasonable
		// like 0.2
		return 0.1;
	}

	@Override
	public SensoryLevel getSense() {
		return SensoryLevel.FIRE;
	}
	
	
	@Override
	public String howYouAppear(Actor whosAsking) {
		return "Fire!";
	}
	
	@Override
	public ElectricalFire clone() {
		return new ElectricalFire();
	}

	protected void maintain(GameData gameData) {
		System.out.println("Maintaining fire in " + getRoom());
		for (Target t : getRoom().getTargets()) {
			t.beExposedTo(null, new Damager() {
				
				@Override
				public boolean isDamageSuccessful(boolean reduced) {
					return true;
				}
				
				@Override
				public String getName() {
					return "fire";
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
		
		for (Room neighbor : getRoom().getNeighborList()) {
			if (MyRandom.nextDouble() < SPREAD_CHANCE) {
				System.out.println("Fire spread to " + neighbor.getName() + "!");
				startNewEvent(neighbor);
			}
		}
		
		getRoom().addToEventsHappened(this);
	}




	@Override
	protected boolean hasThisEvent(Room randomRoom) {
		return randomRoom.hasFire();
	}

	@Override
	public String addYourselfToRoomInfo(Player whosAsking) {
		return "f" + howYouAppear(whosAsking);
	}

	@Override
	public String getDistantDescription() {
		return "Something is burning...";
	}

	

}
