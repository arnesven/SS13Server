package model.actions.general;

import model.Actor;
import model.GameData;

public class NoPowerAction extends TriggerAction {

	public NoPowerAction(Action inner) {
		super(inner);
	}

	@Override
	public void doTheAction(GameData gameData, Actor performingClient) {
		execute(gameData, performingClient);
	}
	
	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		performingClient.addTolastTurnInfo("There's no power! Your action failed");
	}
	
}
