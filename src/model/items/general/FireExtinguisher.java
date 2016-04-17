package model.items.general;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.actions.itemactions.PutOutFireAction;
import model.events.ambient.ElectricalFire;
import model.events.Event;
import model.items.weapons.BluntWeapon;
import model.map.Room;

public class FireExtinguisher extends BluntWeapon {

	private int level = 2;
	
	public FireExtinguisher() {
		super("Fire ext.", 1.0);
	}
	
	@Override
	public FireExtinguisher clone() {
		return new FireExtinguisher();
	}
	
	@Override
	public String getFullName(Actor whosAsking) {
		if (level == 2) {
			return super.getBaseName() + "(full)";
		} else if (level == 1) {
			return super.getBaseName() + "(half)";
		} 
		
		return super.getBaseName() + "(empty)";
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
		if (getFire(cl.getPosition()) != null && level > 0) {
			at.add(new PutOutFireAction(this));
		}

	}

    public static ElectricalFire getFire(Room position) {
        for (Event e : position.getEvents()) {
            if (e instanceof ElectricalFire) {
                return (ElectricalFire)e;
            }
        }
        return null;
    }
	
	@Override
	protected char getIcon() {
		return 'x';
	}

    public void decrementLevel() {
        level--;
    }
}
