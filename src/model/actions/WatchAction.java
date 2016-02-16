package model.actions;

import model.Client;
import model.GameData;
import model.GameItem;


public class WatchAction extends TargetingAction {


	public WatchAction(Client cl) {
		super("Watch", cl);
	}

	@Override
	protected void applyTargetingAction(GameData gameData,
			Client performingClient, Target target, GameItem item) {
		//target.beAttackedBy(performingClient, (Weapon)item);
		//TODO: implement this correctly
	}



}
