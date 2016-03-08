package model.events;

import util.MyRandom;
import model.GameData;
import model.Player;
import model.actions.Target;
import model.map.Room;

public class ElectricalFire extends OngoingEvent {

	

	private static final double SPREAD_CHANCE = 0.167;




	@Override
	public double getProbability() {
		// TODO: Change this into something more reasonable
		// like 0.2
		return 0.2;
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
		
		for (Room neighbor : getRoom().getNeighborList()) {
			if (MyRandom.nextDouble() < SPREAD_CHANCE) {
				System.out.println("Fire spread to " + neighbor.getName() + "!");
				startNewEvent(neighbor);
			}
		}
	}




	@Override
	protected boolean hasThisEvent(Room randomRoom) {
		return randomRoom.hasFire();
	}

	@Override
	public String addYourselfToRoomInfo(Player whosAsking) {
		return "f" + howYouAppear(whosAsking);
	}
	
}
