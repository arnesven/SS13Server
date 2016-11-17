package model.events.ambient;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.events.NoPressureEvent;
import model.events.damage.AsphyxiationDamage;
import model.map.Room;
import util.Logger;

public class HullBreach extends OngoingEvent {

    private static final double occurenceChance = 0.15;

    public double getStaticProbability() {
		return occurenceChance;
	}

    @Override
	public SensoryLevel getSense() {
		return new SensoryLevel(VisualLevel.INVISIBLE, 
				AudioLevel.SAME_ROOM, OlfactoryLevel.UNSMELLABLE);
	}

	@Override
	public String howYouAppear(Actor performingClient) {
        if (lowPressureInAllAdjacent()) {
            return new NoPressureEvent(null, getRoom(), null, false).howYouAppear(performingClient);
        }
		return "Low Pressure!";
	}

    @Override
	public void maintain(GameData gameData) {
        if (lowPressureInAllAdjacent()) {
            new NoPressureEvent(null, getRoom(), null, false).apply(gameData);
        } else {
            for (Target t : getRoom().getTargets()) {
                t.beExposedTo(null, new AsphyxiationDamage(t));
            }
        }
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (lowPressureInAllAdjacent()) {
            return new NoPressureEvent(null, getRoom(), null, false).getSprite(whosAsking);

        }
        return new LowPressureEvent(getRoom()).getSprite(whosAsking);
    }

    @Override
	protected HullBreach clone() {
		return new HullBreach();
	}

	@Override
	protected boolean hasThisEvent(Room randomRoom) {
		return randomRoom.hasHullBreach();
	}

    private boolean lowPressureInAllAdjacent() {
        if (getRoom() == null) {
            return false;
        }
        for (Room r : getRoom().getNeighborList()) {
            if (!r.hasHullBreach() && !NoPressureEvent.hasNoPressureEvent(r)) {
                return false;
            }
        }

        Logger.log(Logger.CRITICAL, "Low pressure all around " + getRoom().getName());
        return true;
    }
	
}
