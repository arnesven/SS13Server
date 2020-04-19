package model.events.ambient;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import model.actions.general.SensoryLevel.AudioLevel;
import model.actions.general.SensoryLevel.OlfactoryLevel;
import model.actions.general.SensoryLevel.VisualLevel;
import model.actions.itemactions.SealHullBreachAction;
import model.events.NoPressureEvent;
import model.events.animation.AnimatedSprite;
import model.events.damage.AsphyxiationDamage;
import model.items.NoSuchThingException;
import model.items.general.GameItem;
import model.items.general.Tools;
import model.map.rooms.Room;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

public class HullBreach extends OngoingEvent {

    private static final double occurenceChance = 0.075;

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
		return "Low Pressure";
	}

    @Override
    public String getPublicName(Actor whosAsking) {
        return "Hull Breach";
    }

    @Override
	public void maintain(GameData gameData) {
        if (lowPressureInAllAdjacent()) {
            new NoPressureEvent(null, getRoom(), null, false).apply(gameData);
        } else {
            for (Target t : getRoom().getTargets(gameData)) {
                t.beExposedTo(null, new AsphyxiationDamage(t), gameData);
            }
        }
	}

    @Override
    public boolean showSpriteInTopPanel() {
        return true;
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        if (lowPressureInAllAdjacent()) {
            return new NoPressureEvent(null, getRoom(), null, false).getSprite(whosAsking);

        }
        return new LowPressureEvent(getRoom()).getSprite(whosAsking);
    }

    @Override
    public Sprite getRoomSprite(Actor whosAsking) {
        return new AnimatedSprite("animatedhullbreach", "objects2.png", 2, 0, 32, 32, this, 8);
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

    @Override
    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        List<Action> acts = new ArrayList<>();

        if (this.getRoom() == forWhom.getPosition()) {
            try {
                Tools fe = GameItem.getItemFromActor(forWhom, new Tools());
                SealHullBreachAction seal = new SealHullBreachAction();
                acts.add(seal);
            } catch (NoSuchThingException e) {
                Logger.log("No fire ext found for " + forWhom);
            }
        }

        return acts;
    }
}
