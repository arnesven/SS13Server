package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.Action;
import model.actions.SensoryLevel;
import model.actions.SensoryLevel.AudioLevel;
import model.actions.SensoryLevel.OlfactoryLevel;
import model.actions.SensoryLevel.VisualLevel;

public class HissAction extends Action {

	public HissAction() {
		super("Hiss", SensoryLevel.SPEECH);
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		performingClient.addTolastTurnInfo("You hissed.");
	}

	@Override
	public void setArguments(List<String> args, Actor p) {
		
	}
	
}
