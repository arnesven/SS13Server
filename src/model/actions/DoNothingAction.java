package model.actions;

import java.util.List;

import model.Client;
import model.GameData;


public class DoNothingAction extends Action {

	public DoNothingAction(String name) {
		super(name);
	}


	@Override
	public void execute(GameData gameData, Client performingClient) {
		performingClient.addTolastTurnInfo("You're chilling out...");
		
	}

	@Override
	public void setArguments(List<String> args) {
		// Not needed
		
	}

}
