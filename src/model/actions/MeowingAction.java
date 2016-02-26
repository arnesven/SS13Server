package model.actions;

import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;

public class MeowingAction extends Action {

	public MeowingAction() {
		super("Meow", false);
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		performingClient.addTolastTurnInfo("You meowed.");
	}

	@Override
	public void setArguments(List<String> args) {
		
	}

}
