package model.events.ambient;

import graphics.Sprite;
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
        if (lowPressureInAllAdjacent()) {
            return new NoPressureEvent(null, getRoom(), null).howYouAppear(performingClient);
        }
		return "Low Pressure!";
	}



    @Override
	public void maintain(GameData gameData) {
        if (lowPressureInAllAdjacent()) {
            new NoPressureEvent(null, getRoom(), null).apply(gameData);
        } else {
            for (Target t : getRoom().getTargets()) {
                t.beExposedTo(null, new AsphyxiationDamage(t));
            }
        }
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (lowPressureInAllAdjacent()) {
            return new NoPressureEvent(null, getRoom(), null).getSprite(whosAsking);

        }
        return super.getSprite(whosAsking);
    }

    @Override
	protected OngoingEvent clone() {
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
        return true;
    }
	
}
