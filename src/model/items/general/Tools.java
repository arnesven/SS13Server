package model.items.general;

import java.util.ArrayList;

import model.GameData;
import model.Player;
import model.Target;
import model.actions.general.Action;
import model.actions.itemactions.RepairAction;
import model.actions.itemactions.SealHullBreachAction;
import model.items.weapons.BluntWeapon;
import model.map.Room;
import model.objects.general.GameObject;
import model.objects.general.Repairable;


public class Tools extends BluntWeapon {

	public Tools() {
		super("Toolkit", 1.0);
	}
	
	@Override
	public Tools clone() {
		return new Tools();
	}
	
	
	@Override
	protected char getIcon() {
		return 'i';
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {
		if (hasBrokenObjects(cl.getPosition())) {
			at.add(new RepairAction(cl));
		}
		if (cl.getPosition().hasHullBreach()) {
			at.add(new SealHullBreachAction());
		}
	}


	private boolean hasBrokenObjects(Room position) {
		for (GameObject ob : position.getObjects()) {
			if (ob instanceof Target) {
				if (ob instanceof Repairable) {
					if (((Repairable)ob).isDamaged() || ((Repairable)ob).isBroken()) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
