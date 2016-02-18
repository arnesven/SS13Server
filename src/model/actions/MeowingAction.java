package model.actions;

import java.util.List;

import model.Client;
import model.GameData;

public class MeowingAction extends Action {

	public MeowingAction() {
		super("Meow", false);
	}

	@Override
	protected void execute(GameData gameData, ActionPerformer performingClient) {
		performingClient.addTolastTurnInfo("You meowed.");
	}

	@Override
	public void setArguments(List<String> args) {
		
	}

}
