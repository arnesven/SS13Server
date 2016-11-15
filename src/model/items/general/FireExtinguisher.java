package model.items.general;

import java.util.ArrayList;

import graphics.sprites.Sprite;
import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.PutOutFireAction;
import model.events.NoSuchEventException;
import model.events.ambient.ElectricalFire;
import model.events.Event;
import model.items.weapons.BluntWeapon;
import model.map.Room;

public class FireExtinguisher extends BluntWeapon {

	private int level = 4;
	
	public FireExtinguisher() {
		super("Fire ext.", 1.0, 45);
	}
	
	@Override
	public FireExtinguisher clone() {
		return new FireExtinguisher();
	}
	
	@Override
	public String getFullName(Actor whosAsking) {
		if (level == 4) {
            return super.getBaseName() + "(full)";
        } else if (level == 3) {
            return super.getBaseName() + "(3/4)";
        } else if (level == 2) {
            return super.getBaseName() + "(half)";
		} else if (level == 1) {
			return super.getBaseName() + "(1/4)";
		} 
		
		return super.getBaseName() + "(empty)";
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
		try {
            if (getFire(cl.getPosition()) != null && level > 0) {
                at.add(new PutOutFireAction(this));
            }
        } catch (NoSuchEventException nse) {
            // doesnt matter
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
        return new Sprite("fireext", "items.png", 40);
    }

    public void decrementLevel() {
        level--;
    }
}
