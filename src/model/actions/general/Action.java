package model.actions.general;

import java.io.Serializable;
import java.util.List;

import model.Actor;
import model.GameData;
import model.Target;

/**
 * @author erini02
 * Represent an action which can be taken by a client.
 * TODO: NPCs should also be able to take actions.
 */
public abstract class Action implements Serializable {
	
	public static final String FAILED_STRING = "Your action failed.";
	private String name;
	private SensoryLevel senses;
	protected Actor performer;
	
	
	/**
	 * @param name the name of this action
	 * @param senses if the action is stealthy it will not be displayed to other players standing in that room.
	 */
	public Action(String name, SensoryLevel senses) {
		this.name = name;
		this.senses = senses;
	}
	
	public void setName(String string) {
		this.name = string;
	}

	@Override
	final public String toString() {
		throw new UnsupportedOperationException("toString() should not be called on Actions!");
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
	 * @param whosAsking the ActionPerformer who is performing the action
	 * @return the textual description of the action (as seen by bystanders).
	 */
	public String getDescription(Actor whosAsking) {
		if (performer == null) {
			throw new IllegalStateException("doTheAction was not called before call to getDescription!");
		}
		return performer.getPublicName() + " " + this.getVerb(whosAsking).toLowerCase() + "";
	}

	/**
	 * Gets the name of the aciton as a verb, passed tense
	 * @return the verb as a string.
	 */
	protected abstract String getVerb(Actor whosAsking);

	/**
	 * Returns true if cl is a argument for this action.
	 * To be overloaded by specific actions.
	 * @param t, the client to check if it is an argument of this action
	 * @return true if the client is an argument of this action, false otherwise.
	 */
	protected boolean isArgumentOf(Target t) {
		return false;
	}

	/**
	 * This puts the selected action into effect. Remember, if
	 * this action uses an item, it may be gone before this action
	 * executes, in which case the action will fail.
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
	public abstract void setArguments(List<String> args, Actor performingClient);

	public SensoryLevel getSense() {
		return senses;
	}

	public void setSense(SensoryLevel sensedAs) {
		this.senses = sensedAs;
	}

	public String getDistantDescription(Actor whosAsking) {
		return "";
	}

	public void lateExecution(GameData gameData, Actor performingClient) {	}

	public ActionOption getOptions(GameData gameData, Actor whosAsking) {
		return new ActionOption(getName());
	}


}
