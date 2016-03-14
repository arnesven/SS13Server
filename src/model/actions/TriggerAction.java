package model.actions;

import java.util.List;

import model.Actor;
import model.GameData;

public class TriggerAction extends Action {

	private Action innerAction;

	public TriggerAction(Action inner) {
		super("Trigger", SensoryLevel.NO_SENSE);
		this.innerAction = inner;
	}

	@Override
	public String getDescription() {
		return innerAction.getDescription();
	}

	@Override
	public String getDistantDescription() {
		return innerAction.getDistantDescription();
	}

	@Override
	public void doTheAction(GameData gameData, Actor performingClient) {
		innerAction.doTheAction(gameData, performingClient);
	}
	
	
	@Override
	public String getName() {
		return innerAction.getName();
	}

	@Override
	protected String getVerb() {
		return innerAction.getVerb();
	}

	@Override
	public SensoryLevel getSense() {
		return innerAction.getSense();
	}

	@Override
	public void lateExecution(GameData gameData, Actor performingClient) {
		innerAction.lateExecution(gameData, performingClient);
	}

	@Override
	protected void execute(GameData gameData, Actor performingClient) {
		innerAction.execute(gameData, performingClient);
	}

	@Override
	public void setArguments(List<String> args) {
		innerAction.setArguments(args);					
	}

	@Override
	public String toString() {
		return innerAction.toString();
	}
	

}
