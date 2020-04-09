package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.itemactions.PutOutActorAction;
import model.actions.itemactions.PutOutFireAction;
import model.events.NoSuchEventException;
import model.events.ambient.ElectricalFire;
import model.events.Event;
import model.items.weapons.BluntWeapon;
import model.map.rooms.Room;

public class FireExtinguisher extends BluntWeapon {

    private static final int MAX_LEVEL = 6;
    private int level = MAX_LEVEL;
	
	public FireExtinguisher() {
		super("Fire ext.", 1.0, 45, 0.85);
	}
	
	@Override
	public FireExtinguisher clone() {
		return new FireExtinguisher();
	}
	
	@Override
	public String getFullName(Actor whosAsking) {
		if (level > 0) {
            return super.getBaseName() + "(" + level + "/" + MAX_LEVEL + ")";
        }

		return super.getBaseName() + "(empty)";
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Actor cl) {
		try {
            if (getFire(cl.getPosition()) != null && level > 0) {
                at.add(new PutOutFireAction(this));
            }


        } catch (NoSuchEventException nse) {
            // doesnt matter
        }
        Action putOutActorAction = new PutOutActorAction(cl);
        if (putOutActorAction.getOptions(gameData, cl).numberOfSuboptions() > 0) {
            at.add(putOutActorAction);
        }

	}

    public static ElectricalFire getFire(Room position) throws NoSuchEventException {
        for (Event e : position.getEvents()) {
            if (e instanceof ElectricalFire) {
                return (ElectricalFire)e;
            }
        }
        throw new NoSuchEventException("No fire where one should be!");
    }

    @Override
    public Sprite getSprite(Actor whosAsking) {
        return new Sprite("fireext", "items.png", 40, this);
    }

    public void decrementLevel() {
        level--;
    }

    public int getUsesRemaining() {
        return level;
    }
}
