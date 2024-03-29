package model.actions.itemactions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

public class CancelAction extends Action {

	public CancelAction() {
		super("cancel", SensoryLevel.NO_SENSE);
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "'s action was cancelled.";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) { 
		performingClient.addTolastTurnInfo("Your action was cancelled.");
	}

	@Override
	public void setArguments(List<String> args, Actor p) { }

}
