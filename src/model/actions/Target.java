package model.actions;

import model.Client;
import model.items.Weapon;

/**
 * @author erini02
 * Interface for targets. A target is an object which can be targeted
 * by a targeting action, i.e. the attack action. Anything targetable
 * should implement this interface, e.g. characters, gameobjects, items etc.
 */
public interface Target {

	/**
	 * Gets the name of this target is it appears when being targeted
	 * @return the target's name
	 */
	String getName();

	boolean isTargetable();

	void beAttackedBy(ActionPerformer performingClient, Weapon item);

	boolean isDead();

	double getHealth();

	double getMaxHealth();
}
