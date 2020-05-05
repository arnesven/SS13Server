package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.Player;
import model.Target;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
import model.events.animation.ExplodingAnimation;
import model.items.foods.ExplodingFood;
import model.map.rooms.Room;
import model.objects.general.BreakableObject;
import model.objects.general.GameObject;
import util.MyRandom;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.ThrowGrenadeAction;
import model.events.damage.Damager;

public class Grenade extends GameItem implements Damager, ExplodableItem {

	public Grenade() {
		super("Grenade", 0.5, 120);
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
		super.addYourActions(gameData, at, cl);
	    at.add(new ThrowGrenadeAction(cl));
	}

	@Override
	public String getText() {
		return "A grenade exploded!";
	}

	
	@Override
	public boolean isDamageSuccessful(boolean reduced) {
		return MyRandom.nextDouble() < 0.75;
	}

	@Override
	public String getName() {
		return getBaseName();
	}

    @Override
    public void doDamageOnMe(Target target) {
        if (target instanceof Actor) {
            ((Actor)target).subtractFromHealth(this.getDamage());
        } else {
            target.addToHealth(-getDamage());
        }
    }

    @Override
    public GameItem getOriginItem() {
        return this;
    }

    public double getDamage() {
		return 1.0;
	}

	@Override
	public Grenade clone() {
		return new Grenade();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("grenade", "grenade.png", 0, this);
    }

    @Override
    public GameItem getAsItem() {
        return this;
    }

    @Override
    public void explode(GameData gameData, Room room, Actor maker) {
        for (Actor a : room.getActors()) {
            a.getAsTarget().beExposedTo(maker, this, gameData);
        }
        for (GameObject o : room.getObjects()) {
            if (o instanceof BreakableObject) {
                ((BreakableObject) o).beExposedTo(maker, this, gameData);
            }
        }
        room.addEvent(new ExplodingAnimation(gameData, room));
    }

    @Override
    public void setConceledWithin(ExplodingFood explodingFood) {

    }

    @Override
    public String getDescription(GameData gameData, Player performingClient) {
        return "An exploding cannister, designed to be thrown at an adversary, through a window or into an adjacent room.";
    }
}
