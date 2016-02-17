package model.actions;

import model.Client;
import model.GameData;
import model.items.GameItem;


public class WatchAction extends TargetingAction {


	public WatchAction(Client cl) {
		super("Watch", true, cl);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			ActionPerformer performingClient, Target target, GameItem item) {
		performingClient.addTolastTurnInfo("You're watching " + target.getName() + ".");
	}



}
