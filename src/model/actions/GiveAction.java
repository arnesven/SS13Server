package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.Target;
import model.items.BombItem;
import model.items.GameItem;
import model.items.weapons.Weapon;

public class GiveAction extends TargetingAction {

	public GiveAction(Actor ap) {
		super("Give", SensoryLevel.PHYSICAL_ACTIVITY, ap);
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "gave";
	};
	
	@Override
	public String getDescription(Actor whosAsking) {
		return performer.getPublicName() + " gave something to " + target.getName() + ".";
	}
	
	@Override
	public boolean isViableForThisAction(Target target2) {
		return target2.hasInventory() && !target2.isDead();
	}
	
	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		
		((Actor)target).getCharacter().giveItem(item, performingClient.getAsTarget());
		performingClient.getItems().remove(item);
		performingClient.addTolastTurnInfo("You gave a " + item.getPublicName(performingClient) + " to " + target.getName() + ".");
		Actor targetAsActor = (Actor)target;
		targetAsActor.addTolastTurnInfo("You got a " + item.getPublicName(targetAsActor) + " from " + performingClient.getPublicName() + ".");
		
	}
	
	@Override
	public void addClientsItemsToAction(Player client) {
		for (GameItem it : client.getItems()) {
			if (! (it instanceof BombItem)) {
				withWhats.add(it);
			}
		}
	}

}
