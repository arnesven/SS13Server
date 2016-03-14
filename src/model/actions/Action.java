package model.actions;

import java.util.List;

import model.Actor;
import model.Player;
import model.GameData;
import model.Target;

/**
 * @author erini02
 * Represent an action which can be taken by a client.
 * TODO: NPCs should also be able to take actions.
 */
public abstract class Action {
	
	private String name;
	private SensoryLevel senses;
	protected Actor performer;
	
	
	
	/**
	 * @param name the name of this action
	 * @param isStealthy if the action is stealthy it will not be displayed to other players standing in that room.
	 */
	public Action(String name, SensoryLevel senses) {
		this.name = name;
		this.senses = senses;
	}

	@Override
	public String toString() {
		return name + "{}";
	}
	
	public String getName() {
		return name;
	}

	public void doTheAction(GameData gameData, Actor performingClient) {
		this.execute(gameData, performingClient);
		this.performer = performingClient;
		performingClient.getPosition().addToActionsHappened(this);
	}
	
	/**
	 * Gets the string which is a textual description of the action to all
	 * other clients in the same room.
	 * DoTheAction must have been called before!
	 * @param performingClient the ActionPerformer who is performing the action
	 * @return the textual description of the action (as seen by bystanders).
	 */
	public String getDescription() {
		if (performer == null) {
			throw new IllegalStateException("doTheAction was not called before call to getDescription!");
		}
		return performer.getPublicName() + " " + this.getVerb().toLowerCase() + "";
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
	protected abstract void execute(GameData gameData, Actor performingClient);

	/**
	 * Sets the arguments with which this action was executed
	 * MUST be called before execute
	 * @param args the arguments
	 */
	public abstract void setArguments(List<String> args);

	public SensoryLevel getSense() {
		return senses;
	}

	public void setSense(SensoryLevel sensedAs) {
		this.senses = sensedAs;
	}

	public String getDistantDescription() {
		return "";
	}

	public void lateExecution(GameData gameData, Actor performingClient) {	}





}
