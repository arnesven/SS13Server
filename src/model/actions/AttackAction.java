package model.actions;

import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.Target;
import model.items.GameItem;
import model.items.weapons.Weapon;
import model.objects.GameObject;


public class AttackAction extends TargetingAction {
	
	public AttackAction(Actor ap) {
		super("Attack", SensoryLevel.PHYSICAL_ACTIVITY, ap);
	}

	@Override
	protected void addMoreTargets(Actor ap) {
//		System.out.println("Adding more targets to attack action");

	}
	
	@Override
	public void addClientsItemsToAction(Player client) {
		withWhats.add(new Weapon("Fists", 0.5, 0.5, false, 0.0));
		for (GameItem it : client.getItems()) {
			if (it instanceof Weapon) {
				if (((Weapon)it).isReadyToUse()) {
					withWhats.add(it);
				}
			}
		}
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		Weapon w = (Weapon)item;
		if (w.isReadyToUse()) {
			target.beAttackedBy(performingClient, (Weapon)item);
			this.setSense(w.getSensedAs());
		} else {
			performingClient.addTolastTurnInfo(w.getName() + " isn't working."); 
		}
	}


	@Override
	protected String getVerb() {
		if (target.isDead()) {
			return "killed";
		}
		return getName().toLowerCase() + "ed";
	}
	
	@Override
	public String getDistantDescription() {
		return "You hear a loud bang.";
	}
	
}
