package model.actions;

import java.util.List;

import model.Client;
import model.GameData;

public abstract class Action {
	
	private String name;
	
	public Action(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + "{}";
	}
	
	public String getName() {
		return name;
	}

	/**
	 * Do not call this method before calling execute!
	 * @param gameData the game's data
	 * @param performingClient who is performing this action
	 */
	public abstract void execute(GameData gameData, Client performingClient);

	/**
	 * Sets the arguments with which this action was executed
	 * MUST be called before execute
	 * @param args the arguments
	 */
	public abstract void setArguments(List<String> args);

}
