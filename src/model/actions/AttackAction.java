package model.actions;

import java.util.List;

import model.Client;
import model.GameData;
import model.GameItem;


public class AttackAction extends TargetingAction {
	
	public AttackAction(Client cl) {
		super("Attack", cl);
	}

	@Override
	public void addItemsToAction(Client client) {
		withWhats.add(new Weapon("Fists", 0.5, 0.5, false));
		for (GameItem it : client.getItems()) {
			if (it instanceof Weapon) {
				withWhats.add(it);
			}
		}
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Client performingClient, Target target, GameItem item) {
		target.beAttackedBy(performingClient, (Weapon)item);
	}


}
