package model.actions.general;

import java.util.List;

import model.Actor;
import model.GameData;


public class DoNothingAction extends Action {

	public DoNothingAction() {
		super("Do Nothing", SensoryLevel.NO_SENSE);
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "Idled";
	}

	@Override
	public void execute(GameData gameData, Actor performingClient) {
		//performingClient.addTolastTurnInfo("You're chilling out...");
		
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		// Not needed
		
	}

}
