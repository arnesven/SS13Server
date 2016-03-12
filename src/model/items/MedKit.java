package model.items;

import java.util.ArrayList;
import java.util.List;

import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.HealWithMedKitAction;
import model.actions.Target;
import model.actions.TargetingAction;
import model.map.Room;
import model.npcs.NPC;


public class MedKit extends GameItem {

	
	public MedKit() {
		super("MedKit", 1.0);
	}

	@Override
	public void addYourActions(GameData gameData, ArrayList<Action> at, Player cl) {	
		TargetingAction act = new HealWithMedKitAction(cl, this);
		if (act.getTargets().size() > 0) {
			at.add(act);
		}
	}

}
