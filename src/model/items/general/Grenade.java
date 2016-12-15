package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.Target;
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
		return "A grenade exploaded!";
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

	public double getDamage() {
		return 1.0;
	}

	@Override
	public Grenade clone() {
		return new Grenade();
	}

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("grenade", "grenade.png", 0);
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
    }

    @Override
    public void setConceledWithin(ExplodingFood explodingFood) {

    }
}
