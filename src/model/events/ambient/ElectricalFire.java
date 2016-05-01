package model.events.ambient;

import graphics.sprites.Sprite;
import model.events.damage.FireDamage;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.map.Room;

public class ElectricalFire extends OngoingEvent {

	private static final double SPREAD_CHANCE = 0.1;
    private static final double BURNOUT_CHANCE = 0.25;
    //private static final double SPREAD_CHANCE = 0.1;

	public double getProbability() {
		// TODO: Change this into something more reasonable
		// like 0.2
		return 0.15;
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
        Logger.log("Maintaining fire in " + getRoom().getName());
		for (Target t : getRoom().getTargets()) {
			t.beExposedTo(null, new FireDamage());
		}

        boolean anyAroundHasFire = false;
		for (Room neighbor : getRoom().getNeighborList()) {
			if (MyRandom.nextDouble() < SPREAD_CHANCE) {
				Logger.log(Logger.INTERESTING,
                        "  Fire spread to " + neighbor.getName() + "!");
				startNewEvent(neighbor);
			}
            if (neighbor.hasFire()) {
                anyAroundHasFire = true;
            }
		}

        if (!anyAroundHasFire & MyRandom.nextDouble() < BURNOUT_CHANCE) {
            this.setShouldBeRemoved(true);
        }
		
		getRoom().addToEventsHappened(this);
	}


    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("electricalfire", "decals.png", 0, 2, 32, 32);
    }

    @Override
	protected boolean hasThisEvent(Room randomRoom) {
		return randomRoom.hasFire();
	}


	@Override
	public String getDistantDescription() {
		return "Something is burning...";
	}


}
