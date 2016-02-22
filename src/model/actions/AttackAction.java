package model.actions;

import java.util.List;

import model.Client;
import model.GameData;
import model.items.GameItem;
import model.items.Weapon;
import model.objects.GameObject;


public class AttackAction extends TargetingAction {
	
	public AttackAction(ActionPerformer ap) {
		super("Attack", false, ap);
	}

	@Override
	protected void addMoreTargets(ActionPerformer ap) {
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
	public void adddClientsItemsToAction(Client client) {
		withWhats.add(new Weapon("Fists", 0.5, 0.5, false));
		for (GameItem it : client.getItems()) {
			if (it instanceof Weapon) {
				withWhats.add(it);
			}
		}
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			ActionPerformer performingClient, Target target, GameItem item) {
		target.beAttackedBy(performingClient, (Weapon)item);
	}

	@Override
	protected String getVerb() {
		if (target.isDead()) {
			return "killed";
		}
		return getName().toLowerCase() + "ed";
	}
}
