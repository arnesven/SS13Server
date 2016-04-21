package model.actions.general;

import java.util.ArrayList;
import java.util.List;

import model.Actor;
import model.GameData;

public class ActionGroup extends Action {
	public ActionGroup(String string) {
		super(string, SensoryLevel.NO_SENSE);
	}

	private List<Action> actions = new ArrayList<>();

	public void addAll(List<Action> acts) {
		actions.addAll(acts);
	}
	
	@Override
	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		ActionOption opt = super.getOptions(gameData, whosAsking);
		for (Action a : actions) {
			opt.addOption(a.getOptions(gameData, whosAsking));
		}
		return opt;
	}
	
	@Override
	protected String getVerb(Actor whosAsking) {
		return "";
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		throw new UnsupportedOperationException("This action should never be executed!");
	}

	@Override
	public void setArguments(List<String> args, Actor performingClient) {
		throw new UnsupportedOperationException("This action should never be executed!");
	}

	public List<Action> getActions() {
		return actions;
	}

}
