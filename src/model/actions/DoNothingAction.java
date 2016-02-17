package model.actions;

import java.util.List;

import model.Client;
import model.GameData;


public class DoNothingAction extends Action {

	public DoNothingAction() {
		super("Do Nothing", true);
	}


	@Override
	public void execute(GameData gameData, ActionPerformer performingClient) {
		//performingClient.addTolastTurnInfo("You're chilling out...");
		
	}

	@Override
	public void setArguments(List<String> args) {
		// Not needed
		
	}

}
