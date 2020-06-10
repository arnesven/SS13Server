package model.actions.characteractions;

import java.util.List;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;
import sounds.Sound;

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

	@Override
	protected String getVerb(Actor whosAsking) {
		return "hissed";
	}

	@Override
	public boolean hasRealSound() {
		return true;
	}

	@Override
	public Sound getRealSound() {
		return new Sound("cat-hissing");
	}
}
