package model.actions;

import java.util.List;

import model.Actor;
import model.GameData;

public class HissAction extends Action {

	public HissAction() {
		super("Hiss", false);
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		performingClient.addTolastTurnInfo("You hissed.");
	}

	@Override
	public void setArguments(List<String> args) {
		
	}
	
}
