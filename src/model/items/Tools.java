package model.items;

import java.util.ArrayList;

import model.Actor;
import model.GameData;
import model.Player;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.Target;
import model.actions.TargetingAction;
import model.map.Room;
import model.objects.BreakableObject;
import model.objects.GameObject;

public class Tools extends BluntWeapon {

	public Tools() {
		super("Tools");
	}
	
	@Override
	public void addYourActions(ArrayList<Action> at, Player cl) {
		if (hasBrokenObjects(cl.getPosition())) {
			at.add(new TargetingAction("Repair", 
					SensoryLevel.PHYSICAL_ACTIVITY, cl) {
				
				@Override
				protected void applyTargetingAction(GameData gameData,
						Actor performingClient, Target target, GameItem item) {
					((BreakableObject)target).addToHealth(1.0);
					performingClient.addTolastTurnInfo("You repaired " + target.getName());
				}
				
				@Override
				protected boolean isViableForThisAction(Target target2) {
					return isRepairable(target2);
					
				}
			});
		}
	}

	protected boolean isRepairable(Target target2) {
		if (target2 instanceof BreakableObject) {
			return ( ((BreakableObject)target2).isDamaged() || 
					 ((BreakableObject)target2).isBroken() );
		}
		return false;
	}

	private boolean hasBrokenObjects(Room position) {
		for (GameObject ob : position.getObjects()) {
			if (ob instanceof Target) {
				if (isRepairable((Target)ob)) {
					return true;
				}
			}
		}
		return false;
	}

}
