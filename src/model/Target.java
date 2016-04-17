package model;

import java.util.List;

import model.events.damage.Damager;
import model.items.general.GameItem;
import model.items.general.Locatable;
import model.items.general.MedKit;
import model.items.weapons.Weapon;

/**
 * @author erini02
 * Interface for targets. A target is an object which can be targeted
 * by a targeting action, i.e. the attack action. Anything targetable
 * should implement this interface, e.g. characters, gameobjects, items etc.
 */
public interface Target extends Locatable {

	/**
	 * Gets the name of this target is it appears when being targeted
	 * @return the target's name
	 */
	String getName();

	boolean isTargetable();

	boolean beAttackedBy(Actor performingClient, Weapon item);

	boolean isDead();

	double getHealth();

	double getMaxHealth();

	boolean hasSpecificReaction(MedKit objectRef);

	void addToHealth(double d);

	List<GameItem> getItems();

	void beExposedTo(Actor performingClient, Damager damager);

	boolean hasInventory();

	boolean isHealable();

	/**
	 * True if this target can be interacted 
	 * (watched, attacked, infected et.c (targeting action)
	 * by a actor.
	 * @param performingClient an actor.
	 * @return True if this target can be interacted by the actor
	 */
	boolean canBeInteractedBy(Actor performingClient);

}
