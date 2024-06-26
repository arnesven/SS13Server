package model.actions.general;

import model.*;
import model.actions.QuickAction;
import model.items.general.BombItem;
import model.items.general.GameItem;

import java.util.List;

public class GiveAction extends TargetingAction implements QuickAction {

	public GiveAction(Actor ap) {
		super("Give", SensoryLevel.PHYSICAL_ACTIVITY, ap);
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "gave";
	}
	
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
        Actor targetAsActor = (Actor)target;
		if (target instanceof Player && ((Player)target).getSettings().get(PlayerSettings.ALWAYS_REFUSE_GIFTS)) {
            performingClient.addTolastTurnInfo(target.getName() + " refused your gift!");
		    targetAsActor.addTolastTurnInfo(performingClient.getPublicName() + " tried to give you a " +
                    item.getPublicName(targetAsActor) + ", but you refused it.");

            return;
        }

        item.transferBetweenActors(performingClient, ((Actor)target), otherArgs);
		performingClient.addTolastTurnInfo("You gave a " + item.getPublicName(performingClient) + " to " + target.getName() + ".");
		targetAsActor.addTolastTurnInfo("You got a " + item.getPublicName(targetAsActor) + " from " + performingClient.getPublicName() + ".");
		
	}
	
	@Override
	public void addClientsItemsToAction(Actor client) {
		for (GameItem it : client.getItems()) {
			if (! (it instanceof BombItem)) {
				withWhats.add(it);
			}
		}
	}

	@Override
	protected boolean requiresProximityToTarget() {
		return true;
	}

	@Override
	public void performQuickAction(GameData gameData, Player performer) {
		execute(gameData, performer);
	}

	@Override
	public boolean isValidToExecute(GameData gameData, Player performer) {
		return true;
	}

	@Override
	public List<Player> getPlayersWhoNeedToBeUpdated(GameData gameData, Player performer) {
		return performer.getPosition().getClients();
	}
}
