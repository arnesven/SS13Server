package model.events.ambient;

import graphics.sprites.Sprite;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.PutOutFireAction;
import model.characters.decorators.OnFireCharacterDecorator;
import model.characters.general.GameCharacter;
import model.events.animation.AnimatedSprite;
import model.events.damage.FireDamage;
import model.items.NoSuchThingException;
import model.items.general.FireExtinguisher;
import model.items.general.GameItem;
import sounds.Sound;
import util.Logger;
import util.MyRandom;
import model.Actor;
import model.GameData;
import model.Target;
import model.actions.general.SensoryLevel;
import model.map.rooms.Room;

import java.util.ArrayList;
import java.util.List;

public class ElectricalFire extends OngoingEvent {

	private static final double SPREAD_CHANCE = 0.05;
    private static final double BURNOUT_CHANCE = 0.025;
    private static final double occurenceChance = 0.075;
    private static final double BURN_CHANCE = 0.05;
    //private static final double SPREAD_CHANCE = 0.1;

    @Override
    protected double getStaticProbability() {
        return occurenceChance;
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

            if (t instanceof Actor) {
                Actor targetAsActor = (Actor)t;
                if (! targetAsActor.getCharacter().checkInstance((GameCharacter gc) -> gc instanceof OnFireCharacterDecorator)) {
                    if (MyRandom.nextDouble() < BURN_CHANCE) {
                        targetAsActor.setCharacter(new OnFireCharacterDecorator(targetAsActor.getCharacter()));
                    } else {
                        t.beExposedTo(null, new FireDamage());
                    }
                } else {
                    // Do nothing, burn decorator will damage actor this round.
                }
            } else {
                t.beExposedTo(null, new FireDamage());
            }
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
        return new AnimatedSprite("electricalfirefillwholeroom", "fire.png", 5, 8, 32, 32, this, 10);
    }

    @Override
	protected boolean hasThisEvent(Room randomRoom) {
		return randomRoom.hasFire();
	}


	@Override
	public String getDistantDescription() {
		return "Something is burning...";
	}

    @Override
    public boolean hasRealSound() {
        return true;
    }

    @Override
    public Sound getRealSound() {
        return new Sound("http://www.ida.liu.se/~erini02/ss13/fire-burning.mp3");
    }

    @Override
    public List<Action> getOverlaySpriteActionList(GameData gameData, Room r, Player forWhom) {
        List<Action> acts = new ArrayList<>();

        if (this.getRoom() == forWhom.getPosition()) {
            try {
                FireExtinguisher fe = GameItem.getItemFromActor(forWhom, new FireExtinguisher());
                PutOutFireAction putOutFireAction = new PutOutFireAction(fe);
                acts.add(putOutFireAction);
            } catch (NoSuchThingException e) {
                Logger.log("No fire ext found for " + forWhom);
            }
        }

        return acts;
    }
}
