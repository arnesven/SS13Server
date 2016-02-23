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
		this.execute(gameData, performingClient);
		if (!isStealthy) {
			for (Client cl : performingClient.getPosition().getClients()) {
//				System.out.println("Broadcasting " + getName() + 
//								   " ... performer=" + performingClient.getPublicName() + 
//								   " cl=" + cl.getName());
//				System.out.println("isclient = " + performingClient.isClient(cl) + " isargument= " + this.isArgumentOf(cl));
				if (!(performingClient == cl) && !this.isArgumentOf(cl)) {
					String str = this.getPrintString(performingClient) + ".";
//					System.out.println("This was added =>" + str);
					cl.addTolastTurnInfo(str);
				}
			}
		}
	}
	
	/**
	 * Gets the string which is a textual description of the action to all
	 * other clients in the same room.
	 * @param performingClient the ActionPerformer who is performing the action
	 * @return the textual description of the action (as seen by bystanders).
	 */
	protected String getPrintString(ActionPerformer performingClient) {
		return performingClient.getPublicName() + " " + this.getVerb();
	}

	/**
	 * Gets the name of the aciton as a verb, passed tense
	 * @return the verb as a string.
	 */
	protected String getVerb() {
		return getName().toLowerCase() + "ed";
	}

	/**
	 * Returns true if cl is a argument for this action.
	 * To be overloaded by specific actions.
	 * @param cl, the client to check if it is an argument of this action
	 * @return true if the client is an argument of this action, false otherwise.
	 */
	protected boolean isArgumentOf(Target t) {
		return false;
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
