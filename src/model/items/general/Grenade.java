package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.Target;
import model.events.animation.AnimatedSprite;
import model.events.animation.AnimationEvent;
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
            a.getAsTarget().beExposedTo(maker, this);
        }
        for (GameObject o : room.getObjects()) {
            if (o instanceof BreakableObject) {
                ((BreakableObject) o).beExposedTo(maker, this);
            }
        }
        room.addEvent(new AnimationEvent(this, gameData, room,
                new AnimatedSprite("explosion", "effects.png",
                        11, 7, 32, 32, this, 13)));
    }

    @Override
    public void setConceledWithin(ExplodingFood explodingFood) {

    }
}
