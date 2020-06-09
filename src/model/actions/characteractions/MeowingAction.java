package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import sounds.Sound;

public class MeowingAction extends Action {

	public MeowingAction() {
		super("Meow", SensoryLevel.SPEECH);
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		performingClient.addTolastTurnInfo("You meowed.");
	}

	@Override
	public void setArguments(List<String> args, Actor p) {
		
	}

	@Override
	protected String getVerb(Actor whosAsking) {
		return "meowed";
	}

	@Override
	public boolean hasRealSound() {
		return true;
	}

	@Override
	public Sound getRealSound() {
		return new Sound("meow1");
	}
}
