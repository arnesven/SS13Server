package model.actions;

import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;


public class DoNothingAction extends Action {

	public DoNothingAction() {
		super("Do Nothing", SensoryLevel.NO_SENSE);
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
