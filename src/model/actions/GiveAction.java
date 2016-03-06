package model.actions;

import model.Actor;
import model.GameData;
import model.Player;
import model.items.GameItem;
import model.items.Weapon;

public class GiveAction extends TargetingAction {

	public GiveAction(Actor ap) {
		super("Give", SensoryLevel.PHYSICAL_ACTIVITY, ap);
	}

	@Override
	protected String getVerb() {
		return "gave";
	};
	
	@Override
	public String getDescription() {
		return performer.getPublicName() + " gave something to " + target.getName() + ".";
	}
	
	@Override
	protected boolean isViableForThisAction(Target target2) {
		return target2.isHuman() && !target2.isDead();
	}
	
	@Override
	protected void applyTargetingAction(GameData gameData,
			Actor performingClient, Target target, GameItem item) {
		
		target.getItems().add(item);
		performingClient.getItems().remove(item);
		performingClient.addTolastTurnInfo("You gave a " + item.getName() + " to " + target.getName() + ".");
		Actor targetAsActor = (Actor)target;
		targetAsActor.addTolastTurnInfo("You got a " + item.getName() + " from " + performingClient.getPublicName() + ".");
		
	}
	
	@Override
	public void addClientsItemsToAction(Player client) {
		for (GameItem it : client.getItems()) {
			withWhats.add(it);
		}
	}

}
