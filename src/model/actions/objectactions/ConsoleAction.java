package model.actions.objectactions;

import model.Actor;
import model.GameData;
import model.actions.general.Action;
import model.actions.general.SensoryLevel;

public abstract class ConsoleAction extends Action {

	public ConsoleAction(String name, SensoryLevel senses) {
		super(name, senses);
	}

	@Override
	protected abstract void execute(GameData gameData, Actor performingClient);
}
