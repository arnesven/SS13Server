package model.actions;

import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.items.GameItem;
import model.items.Weapon;
import model.objects.GameObject;


public class AttackAction extends TargetingAction {
	
	public AttackAction(Actor ap) {
		super("Attack", SensoryLevel.PHYSICAL_ACTIVITY, ap);
	}

	@Override
	protected void addMoreTargets(Actor ap) {
//		System.out.println("Adding more targets to attack action");
		for (GameObject ob : ap.getPosition().getObjects()) {
			System.out.println("Handling " + ob.getName());
			if (ob instanceof Target) {
				Target objectAsTarget = (Target)ob;
				if (((Target) ob).isTargetable()) {
					System.out.println("Adding " + ob.getName() + " as a target.");
					this.addTarget((Target)ob);
				}
			}
		}
	}
	
	@Override
	public void addClientsItemsToAction(Player client) {
		withWhats.add(new Weapon("Fists", 0.5, 0.5, false));
		for (GameItem it : client.getItems()) {
			if (it instanceof Weapon) {
				withWhats.add(it);
			}
		}
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		Weapon w = (Weapon)item;
		target.beAttackedBy(performingClient, (Weapon)item);
		this.setSense(w.getSensedAs());
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
