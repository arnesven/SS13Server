package model.actions;

import model.Client;

public interface Target {

	/**
	 * Gets the name of this target is it appears when being targeted
	 * @return the target's name
	 */
	String getName();

	void beAttackedBy(Client performingClient, Weapon item);

}
