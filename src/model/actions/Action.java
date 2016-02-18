package model.actions;

import java.util.List;

import model.Client;
import model.GameData;

/**
 * @author erini02
 * Represent an action which can be taken by a client.
 * TODO: NPCs should also be able to take actions.
 */
public abstract class Action {
	
	private String name;
	private boolean isStealthy = false;
	

	
	/**
	 * @param name the name of this action
	 * @param isStealthy if the action is stealthy it will not be displayed to other players standing in that room.
	 */
	public Action(String name, boolean isStealthy) {
		this.name = name;
		this.isStealthy = isStealthy;
	}

	@Override
	public String toString() {
		return name + "{}";
	}
	
	public String getName() {
		return name;
	}

	public void printAndExecute(GameData gameData, ActionPerformer performingClient) {
		if (!isStealthy) {
			for (Client cl : performingClient.getPosition().getClients()) {
				if (!performingClient.isClient(cl)) {
					cl.addTolastTurnInfo(performingClient.getPublicName() + " " + this.getName().toLowerCase() + "ed.");
				}
			}

		}
		this.execute(gameData, performingClient);
	}
	
	/**
	 * Do not call this method before calling setArguments!
	 * @param gameData the game's data
	 * @param performingClient who is performing this action
	 */
	protected abstract void execute(GameData gameData, ActionPerformer performingClient);

	/**
	 * Sets the arguments with which this action was executed
	 * MUST be called before execute
	 * @param args the arguments
	 */
	public abstract void setArguments(List<String> args);

}
