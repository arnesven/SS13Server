package model.items.general;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.general.Action;
import model.map.rooms.Room;
import model.objects.general.GameObject;
import model.objects.general.ElectricalMachinery;
import model.actions.itemactions.RigBoobyTrapAction;

public class BoobyTrapBomb extends BombItem {

	public BoobyTrapBomb() {
		super("Booby Trap Bomb", 650);
	}
	
	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at,
                               Actor cl) {
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

	@Override
	public String getDescription(GameData gameData, Player performingClient) {
		return super.getDescription(gameData, performingClient) +
				(isDemolitionsExpert(performingClient)?" this kind can be rigged to electrical machinery so that it will detonate when somebody uses that machine.":"");
	}
}
