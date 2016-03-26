package model.items;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.events.ElectricalFire;
import model.map.Room;
import model.objects.GameObject;
import model.objects.ElectricalMachinery;
import model.actions.SensoryLevel;
import model.actions.itemactions.RigBoobyTrapAction;

public class BoobyTrapBomb extends BombItem {

	public BoobyTrapBomb() {
		super("Booby Trap Bomb");
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
			Player cl) {
		super.addYourActions(gameData, at, cl);
		if (hasRiggableObjects(cl.getPosition())) {
			at.add(new RigBoobyTrapAction(this, cl));
			
			
		}
	}

	private boolean hasRiggableObjects(Room position) {
		for (GameObject ob : position.getObjects()) {
			if (ob instanceof ElectricalMachinery) {
				return true;
			}
		}
		return false;
	}

	@Override
	public BoobyTrapBomb clone() {
		return new BoobyTrapBomb();
	}

}
